package dew.servicios;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonSyntaxException;
import dew.clases.Alumno;
import dew.clases.Asignatura; // Tu Asignatura.java actual
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map; // Para el parseo inicial de la lista de asignaturas del alumno
import org.apache.hc.core5.http.ParseException;

public class ServicioAlumno {

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
            throw new IOException("Sesión inválida: faltan apiKey o dni.");
        }
        if (cookie == null) {
            System.out.println("ServicioAlumno (obtenerCredenciales): sessionCookie es null para DNI " + dni);
        }
        return new CredencialesSesion(apiKey, cookie);
    }


    public static Alumno obtenerAlumno(ServletContext context, HttpSession session)
            throws IOException, ParseException, JsonSyntaxException {
        CredencialesSesion cred = obtenerCredenciales(session);
        String dni = (String) session.getAttribute("dni");
        Alumno alumno = null;

        // 1. Obtener datos básicos del alumno
        System.out.println("ServicioAlumno: Solicitando datos básicos del alumno DNI: " + dni);
        String jsonAlumno = ClienteCentro.obtenerInstancia().obtenerRecurso("alumnos/" + dni, cred.apiKey, cred.cookie);
        System.out.println("ServicioAlumno: JSON datos básicos alumno " + dni + ": " + jsonAlumno);
        if (jsonAlumno == null || jsonAlumno.trim().isEmpty() || jsonAlumno.trim().equalsIgnoreCase("null")) {
            System.err.println("ServicioAlumno: No se recibieron datos básicos para el alumno DNI: " + dni);
            return null;
        }
        alumno = gson.fromJson(jsonAlumno, Alumno.class);
        if (alumno == null) {
            System.err.println("ServicioAlumno: Error al mapear datos básicos del alumno DNI: " + dni);
            return null;
        }

        // 2. Obtener la lista de asignaturas del alumno (que podría tener una estructura diferente para acrónimo/nombre)
        System.out.println("ServicioAlumno: Solicitando lista de asignaturas para el alumno DNI: " + dni);
        String jsonAlumnoAsignaturasInfo = ClienteCentro.obtenerInstancia().obtenerRecurso("alumnos/" + dni + "/asignaturas", cred.apiKey, cred.cookie);
        
        // !!!!! ESTE ES EL JSON QUE NECESITO VER !!!!!
        System.out.println("ServicioAlumno: JSON CRUDO recibido para asignaturas del ALUMNO " + dni + ": " + jsonAlumnoAsignaturasInfo);
        
        List<Asignatura> asignaturasCompletas = new ArrayList<>();
        if (jsonAlumnoAsignaturasInfo != null && !jsonAlumnoAsignaturasInfo.trim().isEmpty() && !jsonAlumnoAsignaturasInfo.trim().equalsIgnoreCase("null") && !jsonAlumnoAsignaturasInfo.trim().equals("[]")) {
            
            // Parsear el JSON de /alumnos/{dni}/asignaturas. 
            // Este JSON podría ser una lista de objetos simples, ej: [{"asignatura": "DEW", "nota": "7.5"}, ...]
            // O podría ser [{"acronimo_alumno": "DEW", "calificacion_alumno": "7.5"}, ...]
            // Necesitamos saber los nombres exactos de los campos para "acronimo" y "nota" en ESTE JSON.
            TypeToken<List<Map<String, Object>>> typeToken = new TypeToken<List<Map<String, Object>>>() {};
            List<Map<String, Object>> asignaturasConNotaRaw = null;
            try {
                asignaturasConNotaRaw = gson.fromJson(jsonAlumnoAsignaturasInfo, typeToken.getType());
            } catch (JsonSyntaxException e) {
                System.err.println("ServicioAlumno: Error de sintaxis JSON al parsear la lista de asignaturas del alumno " + dni + ". JSON: " + jsonAlumnoAsignaturasInfo);
                e.printStackTrace(); // Imprimir el stack trace para más detalles
                // Continuar con una lista vacía para no romper el flujo si el JSON es inválido
            }


            if (asignaturasConNotaRaw != null) {
                for (Map<String, Object> asigRaw : asignaturasConNotaRaw) {
                    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    //      NECESITO QUE ME DIGAS LOS NOMBRES REALES DE ESTOS CAMPOS 
                    //      DEL JSON QUE IMPRIME LA LÍNEA "JSON CRUDO recibido para asignaturas del ALUMNO..."
                    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    String acronimoDesdeAlumnoJson = (String) asigRaw.get("asignatura"); // <--- EJEMPLO: AJUSTAR "asignatura"
                    Object notaRaw = asigRaw.get("nota"); // <--- EJEMPLO: AJUSTAR "nota"
                    String notaDesdeAlumnoJson = (notaRaw != null) ? String.valueOf(notaRaw) : null;


                    if (acronimoDesdeAlumnoJson != null) {
                        // Ahora, obtenemos los detalles completos de la asignatura usando el endpoint general de asignaturas
                        // Se asume que GET /asignaturas/{acronimo} devuelve un JSON que Asignatura.java puede mapear bien
                        // para nombre, curso, cuatrimestre, creditos (y también acronimo).
                        System.out.println("ServicioAlumno: Solicitando detalles completos para asignatura: " + acronimoDesdeAlumnoJson);
                        String jsonDetalleAsignatura = ClienteCentro.obtenerInstancia().obtenerRecurso("asignaturas/" + acronimoDesdeAlumnoJson, cred.apiKey, cred.cookie);
                        System.out.println("ServicioAlumno: JSON detalle asignatura " + acronimoDesdeAlumnoJson + ": " + jsonDetalleAsignatura);

                        if (jsonDetalleAsignatura != null && !jsonDetalleAsignatura.trim().isEmpty() && !jsonDetalleAsignatura.trim().equalsIgnoreCase("null")) {
                            Asignatura asignaturaCompleta = gson.fromJson(jsonDetalleAsignatura, Asignatura.class);
                            if (asignaturaCompleta != null) {
                                // Si el acrónimo no se mapeó bien desde jsonDetalleAsignatura (porque ese JSON también usa un nombre diferente),
                                // lo establecemos con el que obtuvimos del primer JSON.
                                if (asignaturaCompleta.getAcronimo() == null) {
                                    asignaturaCompleta.setAcronimo(acronimoDesdeAlumnoJson);
                                }
                                asignaturaCompleta.setNota(notaDesdeAlumnoJson); // Añadimos la nota específica del alumno
                                asignaturasCompletas.add(asignaturaCompleta);
                            } else {
                                System.err.println("ServicioAlumno: Error al mapear detalles de asignatura para " + acronimoDesdeAlumnoJson);
                            }
                        } else {
                             System.err.println("ServicioAlumno: No se recibieron detalles para la asignatura " + acronimoDesdeAlumnoJson);
                        }
                    } else {
                        System.err.println("ServicioAlumno: No se pudo extraer el acrónimo de una de las asignaturas del alumno. Objeto JSON raw: " + asigRaw);
                    }
                }
            }
        }
        
        alumno.setAsignaturas(asignaturasCompletas.isEmpty() ? Collections.emptyList() : asignaturasCompletas);
        
        if (asignaturasCompletas.isEmpty()) {
             System.out.println("ServicioAlumno: La lista final de asignaturas completas para el alumno DNI: " + dni + " está vacía (puede ser normal si no tiene o si hubo errores de parseo).");
        }
        return alumno;
    }
}
