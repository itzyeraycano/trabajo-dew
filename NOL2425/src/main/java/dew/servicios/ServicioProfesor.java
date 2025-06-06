package dew.servicios;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonSyntaxException;
import dew.clases.Alumno; 
import dew.clases.Asignatura;
import dew.clases.Profesor;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map; 
import org.apache.hc.core5.http.ParseException;

public class ServicioProfesor {

    private static final Gson gson = new Gson();

    private static class CredencialesSesion {
        final String apiKey;
        final String cookie;

        CredencialesSesion(String apiKey, String cookie) {
            this.apiKey = apiKey;
            this.cookie = cookie;
        }
    }

    private static CredencialesSesion obtenerCredenciales(HttpSession session) throws IOException {
        String apiKey = (String) session.getAttribute("apiKey");
        String dni = (String) session.getAttribute("dni");
        String cookie = (String) session.getAttribute("sessionCookie");

        if (apiKey == null || dni == null) {
            throw new IOException("ServicioProfesor: Sesión inválida, faltan apiKey o dni del usuario.");
        }
        if (cookie == null) {
            System.out.println("ServicioProfesor (obtenerCredenciales): sessionCookie de CentroEducativo es null para DNI " + dni + ". Se procederá solo con API Key.");
        }
        return new CredencialesSesion(apiKey, cookie);
    }

    public static List<Profesor> obtenerProfesores(ServletContext context, HttpSession session) 
            throws IOException, ParseException, JsonSyntaxException {
        CredencialesSesion cred = obtenerCredenciales(session);
        String json = ClienteCentro.obtenerInstancia().obtenerRecurso("profesores", cred.apiKey, cred.cookie);
        System.out.println("ServicioProfesor (obtenerProfesores): JSON recibido: " + json);
        if (json == null || json.trim().isEmpty() || json.trim().equalsIgnoreCase("null") || json.trim().equals("[]")) {
            return Collections.emptyList();
        }
        return gson.fromJson(json, new TypeToken<List<Profesor>>(){}.getType());
    }

    public static Profesor obtenerProfesorPorDni(ServletContext context, HttpSession session, String dniProfesor) 
            throws IOException, ParseException, JsonSyntaxException {
        CredencialesSesion cred = obtenerCredenciales(session);

        System.out.println("ServicioProfesor (obtenerProfesorPorDni): Solicitando datos del profesor DNI: " + dniProfesor);
        String jsonProfesor = ClienteCentro.obtenerInstancia().obtenerRecurso("profesores/" + dniProfesor, cred.apiKey, cred.cookie);
        System.out.println("ServicioProfesor (obtenerProfesorPorDni): JSON datos profesor " + dniProfesor + ": " + jsonProfesor);
        
        if (jsonProfesor == null || jsonProfesor.trim().isEmpty() || jsonProfesor.trim().equalsIgnoreCase("null")) {
            System.err.println("ServicioProfesor (obtenerProfesorPorDni): No se recibió información del profesor para DNI: " + dniProfesor);
            return null; 
        }
        Profesor profesor = gson.fromJson(jsonProfesor, Profesor.class);
        if (profesor == null) {
             System.err.println("ServicioProfesor (obtenerProfesorPorDni): Error al mapear datos del profesor para DNI: " + dniProfesor + ". JSON: " + jsonProfesor);
            return null; 
        }

        System.out.println("ServicioProfesor (obtenerProfesorPorDni): Solicitando asignaturas del profesor DNI: " + dniProfesor);
        String jsonAsignaturas = ClienteCentro.obtenerInstancia().obtenerRecurso("profesores/" + dniProfesor + "/asignaturas", cred.apiKey, cred.cookie);
        
        System.out.println("ServicioProfesor (obtenerProfesorPorDni): JSON CRUDO recibido para asignaturas del profesor " + dniProfesor + ": " + jsonAsignaturas);
        
        if (jsonAsignaturas != null && !jsonAsignaturas.trim().isEmpty() && !jsonAsignaturas.trim().equalsIgnoreCase("null") && !jsonAsignaturas.trim().equals("[]")) {
            List<Asignatura> asignaturas = gson.fromJson(jsonAsignaturas, new TypeToken<List<Asignatura>>() {}.getType());
            profesor.setAsignaturas(asignaturas != null ? asignaturas : Collections.emptyList());
        } else {
            System.out.println("ServicioProfesor (obtenerProfesorPorDni): No se recibieron asignaturas o la lista está vacía para el profesor DNI: " + dniProfesor + ". JSON recibido: " + jsonAsignaturas);
            profesor.setAsignaturas(Collections.emptyList());
        }
        return profesor;
    }

    /**
     * Obtiene la lista de alumnos matriculados en una asignatura específica,
     * incluyendo sus datos completos y la nota en esa asignatura.
     */
    public static List<Alumno> obtenerAlumnosConNotaPorAsignatura(ServletContext context, HttpSession session, String acronimoAsignatura) 
            throws IOException, ParseException, JsonSyntaxException {
        CredencialesSesion cred = obtenerCredenciales(session); // Credenciales del profesor logueado
        
        System.out.println("ServicioProfesor: Solicitando alumnos para asignatura: " + acronimoAsignatura);
        // Paso 1: Obtener la lista de DNI de alumnos y sus notas para la asignatura
        String jsonAlumnosNotas = ClienteCentro.obtenerInstancia().obtenerRecurso("asignaturas/" + acronimoAsignatura + "/alumnos", cred.apiKey, cred.cookie);
        System.out.println("ServicioProfesor: JSON CRUDO para alumnos de " + acronimoAsignatura + ": " + jsonAlumnosNotas);

        List<Alumno> listaFinalAlumnos = new ArrayList<>();
        if (jsonAlumnosNotas == null || jsonAlumnosNotas.trim().isEmpty() || jsonAlumnosNotas.trim().equalsIgnoreCase("null") || jsonAlumnosNotas.trim().equals("[]")) {
            System.out.println("ServicioProfesor: No se recibieron alumnos o la lista está vacía para la asignatura: " + acronimoAsignatura);
            return listaFinalAlumnos; 
        }

        // El JSON esperado es un array de objetos: [{"alumno": "DNI_X", "nota": "NOTA_X"}, ...]
        TypeToken<List<Map<String, String>>> typeToken = new TypeToken<List<Map<String, String>>>() {};
        List<Map<String, String>> alumnosConNotaRaw = null;
        try {
            alumnosConNotaRaw = gson.fromJson(jsonAlumnosNotas, typeToken.getType());
        } catch (JsonSyntaxException e) {
            System.err.println("ServicioProfesor: Error de sintaxis JSON al parsear la lista de alumnos/notas de la asignatura " + acronimoAsignatura + ". JSON: " + jsonAlumnosNotas);
            e.printStackTrace();
            return listaFinalAlumnos; // Devuelve lista vacía si el JSON es inválido
        }


        if (alumnosConNotaRaw != null) {
            for (Map<String, String> alumnoRaw : alumnosConNotaRaw) {
                String dniAlumno = alumnoRaw.get("alumno"); 
                String notaEnAsignatura = alumnoRaw.get("nota");

                if (dniAlumno != null) {
                    // Paso 2: Obtener detalles completos del alumno (nombre, apellidos)
                    System.out.println("ServicioProfesor: Solicitando detalles del alumno: " + dniAlumno);
                    String jsonAlumnoDetalles = ClienteCentro.obtenerInstancia().obtenerRecurso("alumnos/" + dniAlumno, cred.apiKey, cred.cookie);
                    System.out.println("ServicioProfesor: JSON detalles alumno " + dniAlumno + ": " + jsonAlumnoDetalles);
                    
                    if (jsonAlumnoDetalles != null && !jsonAlumnoDetalles.isEmpty() && !jsonAlumnoDetalles.equalsIgnoreCase("null")) {
                        Alumno alumnoCompleto = gson.fromJson(jsonAlumnoDetalles, Alumno.class);
                        if (alumnoCompleto != null) {
                            // Para pasar la nota al JSP, creamos una lista temporal de asignaturas para este alumno
                            // que solo contiene la asignatura actual con su nota.
                            Asignatura asignaturaConNota = new Asignatura();
                            asignaturaConNota.setAcronimo(acronimoAsignatura); 
                            asignaturaConNota.setNota(notaEnAsignatura);
                            alumnoCompleto.setAsignaturas(Collections.singletonList(asignaturaConNota)); 
                            
                            listaFinalAlumnos.add(alumnoCompleto);
                        } else {
                             System.err.println("ServicioProfesor: Error al mapear detalles del alumno " + dniAlumno);
                        }
                    } else {
                         System.err.println("ServicioProfesor: No se recibieron detalles para el alumno " + dniAlumno);
                    }
                } else {
                    System.err.println("ServicioProfesor: No se pudo extraer el DNI de uno de los alumnos en la asignatura " + acronimoAsignatura + ". Objeto JSON raw: " + alumnoRaw);
                }
            }
        }
        return listaFinalAlumnos;
    }
    /**
     * Método para modificar la nota de un alumno en una asignatura específica
     * @param context El contexto del servlet
     * @param session La sesión actual del usuario (debe contener la apiKey y sessionCookie)
     * @param dniAlumno El DNI del alumno cuyo nota se va a modificar
     * @param acronimoAsignatura El acrónimo de la asignatura donde se va a modificar la nota
     * @param nuevaNota La nueva nota a asignar
     * @return boolean Si la operación fue exitosa o no
     * @throws IOException Si ocurre un error en la comunicación con la API
     */
    public static boolean modificarNotaAlumno(ServletContext context, HttpSession session,
                                        String dniAlumno, String acronimoAsignatura, String nuevaNota) throws IOException {

        // Obtener las credenciales de la sesión
        String apiKey = (String) session.getAttribute("apiKey");
        String sessionCookie = (String) session.getAttribute("sessionCookie");

        // Verificar que la sesión es válida
        if (apiKey == null || sessionCookie == null) {
            throw new IOException("No autenticado con CentroEducativo");
        }

        // Construir la URL para hacer el PUT al servidor de la API
        String urlString = "http://localhost:9090/CentroEducativo/alumnos/" + dniAlumno + "/asignaturas/" + acronimoAsignatura + "?key=" + apiKey;
        URL url = new URL(urlString);

        // Configurar la conexión HTTP PUT
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("PUT");
        con.setRequestProperty("Content-Type", "application/json");  // El cuerpo es JSON
        con.setRequestProperty("Cookie", sessionCookie);  // Añadir la cookie de sesión
        con.setDoOutput(true);  // Necesitamos enviar un cuerpo en la solicitud

        // El cuerpo de la solicitud es la nueva nota en formato JSON
        String jsonBody = "\"" + nuevaNota + "\"";  // La nueva nota es un número entre comillas, en formato JSON

        // Escribir el cuerpo de la solicitud en la conexión HTTP
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), Charset.forName("UTF-8")))) {
            writer.write(jsonBody);
        }

        // Obtener el código de respuesta de la solicitud
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Si el código es 200, la operación fue exitosa
            return true;
        } else {
            // Si el código no es 200, hubo un error
            return false;
        }
    }
}