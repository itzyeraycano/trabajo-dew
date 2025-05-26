package dew.servicios;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dew.clases.Asignatura;
import dew.clases.Profesor;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

public class ServicioProfesor {

    private static final Gson gson = new Gson();

    // Login usando autenticarUsuario de ClienteCentro
    public static dew.servicios.AuthResult loginProfesor(String dni, String password) throws IOException {
        return ClienteCentro.obtenerInstancia().autenticarUsuario(dni, password);
    }

    // Obtener lista de profesores
    public static List<Profesor> obtenerProfesores(ServletContext context, HttpSession session) throws IOException {
        String apiKey = (String) session.getAttribute("apiKey");
        String cookie = (String) session.getAttribute("sessionCookie");
        String json = ClienteCentro.obtenerInstancia().obtenerRecurso("profesores", apiKey, cookie);
        return gson.fromJson(json, new TypeToken<List<Profesor>>(){}.getType());
    }

    // Obtener profesor por DNI
    public static Profesor obtenerProfesorPorDni(ServletContext context, HttpSession session, String dni) throws IOException {
        String apiKey = (String) session.getAttribute("apiKey");
        String cookie = (String) session.getAttribute("sessionCookie");
        String json = ClienteCentro.obtenerInstancia().obtenerRecurso("profesores/" + dni, apiKey, cookie);
        return gson.fromJson(json, Profesor.class);
    }

    // Obtener asignaturas de profesor por DNI
    public static List<Asignatura> obtenerAsignaturasDeProfesor(ServletContext context, HttpSession session, String dniProfesor) throws IOException {
        String apiKey = (String) session.getAttribute("apiKey");
        String cookie = (String) session.getAttribute("sessionCookie");
        String json = ClienteCentro.obtenerInstancia().obtenerRecurso("profesores/" + dniProfesor + "/asignaturas", apiKey, cookie);
        return gson.fromJson(json, new TypeToken<List<Asignatura>>(){}.getType());
    }

    // Obtener profesores de asignatura
    public static List<Profesor> getProfesoresDeAsignatura(ServletContext context, HttpSession session, String acronimoAsignatura) throws IOException {
        String apiKey = (String) session.getAttribute("apiKey");
        String cookie = (String) session.getAttribute("sessionCookie");
        String json = ClienteCentro.obtenerInstancia().obtenerRecurso("asignaturas/" + acronimoAsignatura + "/profesores", apiKey, cookie);
        return gson.fromJson(json, new TypeToken<List<Profesor>>(){}.getType());
    }
    
    /*public static boolean modificarNotaAlumno(ServletContext context, HttpSession session,
            String dniAlumno, String acronimoAsignatura, String nuevaNota)
			throws IOException {
			String apiKey = (String) session.getAttribute("apiKey");
			String sessionCookie = (String) session.getAttribute("sessionCookie");
			
			String recurso = String.format("alumnos/%s/asignaturas/%s", dniAlumno, acronimoAsignatura);
			String jsonBody = String.format("{\"nota\":\"%s\"}", nuevaNota);
			
			return ClienteCentro.obtenerInstancia().enviarPut(recurso, jsonBody, apiKey, sessionCookie);
			}*/


}
