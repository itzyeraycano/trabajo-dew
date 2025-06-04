package dew.servicios;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonSyntaxException;
import dew.clases.Asignatura;
import dew.clases.Profesor;
import dew.clases.Alumno; // Importa Alumno si otros métodos de este servicio lo necesitan
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList; // Si la necesitas para otros métodos
import java.util.Collections;
import java.util.List;
import java.util.Map; // Si la necesitas para otros métodos
import org.apache.hc.core5.http.ParseException;

public class ServicioProfesor {

    private static final Gson gson = new Gson();

    // Clase interna para agrupar credenciales de sesión
    private static class CredencialesSesion {
        final String apiKey;
        final String cookie;

        CredencialesSesion(String apiKey, String cookie) {
            this.apiKey = apiKey;
            this.cookie = cookie;
        }
    }

    // Obtiene las credenciales de la sesión
    private static CredencialesSesion obtenerCredenciales(HttpSession session) throws IOException {
        String apiKey = (String) session.getAttribute("apiKey");
        String dni = (String) session.getAttribute("dni"); // DNI del usuario logueado
        String cookie = (String) session.getAttribute("sessionCookie");

        if (apiKey == null || dni == null) {
            throw new IOException("ServicioProfesor: Sesión inválida, faltan apiKey o dni del usuario.");
        }
        if (cookie == null) {
            // Esto es solo una advertencia, ClienteCentro puede manejar una cookie nula
            System.out.println("ServicioProfesor (obtenerCredenciales): sessionCookie de CentroEducativo es null para DNI " + dni + ". Se procederá solo con API Key.");
        }
        return new CredencialesSesion(apiKey, cookie);
    }

    /**
     * Obtiene la lista completa de todos los profesores.
     * (Este método se llamaría desde ProfesorServlet para la ruta /profesores)
     */
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

    /**
     * Obtiene un profesor concreto por su DNI y también sus asignaturas.
     * Este método es el que usa ProfesorServlet para mostrar el perfil del profesor.
     */
    public static Profesor obtenerProfesorPorDni(ServletContext context, HttpSession session, String dniProfesor) 
            throws IOException, ParseException, JsonSyntaxException {
        CredencialesSesion cred = obtenerCredenciales(session);

        // 1. Obtener datos básicos del profesor
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

        // 2. Obtener asignaturas del profesor
        System.out.println("ServicioProfesor (obtenerProfesorPorDni): Solicitando asignaturas del profesor DNI: " + dniProfesor);
        String jsonAsignaturas = ClienteCentro.obtenerInstancia().obtenerRecurso("profesores/" + dniProfesor + "/asignaturas", cred.apiKey, cred.cookie);
        
        // !!!!! LÍNEA CRUCIAL PARA DEBUG !!!!!
        // Imprime el JSON crudo de las asignaturas ANTES de intentar parsearlo.
        System.out.println("ServicioProfesor (obtenerProfesorPorDni): JSON CRUDO recibido para asignaturas del profesor " + dniProfesor + ": " + jsonAsignaturas);
        // !!!!! LÍNEA CRUCIAL PARA DEBUG !!!!!
        
        if (jsonAsignaturas != null && !jsonAsignaturas.trim().isEmpty() && !jsonAsignaturas.trim().equalsIgnoreCase("null") && !jsonAsignaturas.trim().equals("[]")) {
            // Asegúrate que Asignatura.java mapea correctamente este JSON
            List<Asignatura> asignaturas = gson.fromJson(jsonAsignaturas, new TypeToken<List<Asignatura>>() {}.getType());
            profesor.setAsignaturas(asignaturas != null ? asignaturas : Collections.emptyList());
        } else {
            System.out.println("ServicioProfesor (obtenerProfesorPorDni): No se recibieron asignaturas o la lista está vacía para el profesor DNI: " + dniProfesor + ". JSON recibido: " + jsonAsignaturas);
            profesor.setAsignaturas(Collections.emptyList());
        }
        return profesor;
    }

    // Si necesitas otros métodos que tenías antes (como los relacionados con modificar notas,
    // obtener alumnos de una asignatura, etc.), deberías añadirlos aquí, asegurándote
    // de que manejan las excepciones IOException, ParseException y JsonSyntaxException si
    // llaman a ClienteCentro o usan gson.fromJson.
}