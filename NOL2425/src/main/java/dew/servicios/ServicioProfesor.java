package dew.servicios;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import dew.clases.Alumno;
import dew.clases.Asignatura;
import dew.clases.Profesor;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import org.apache.hc.core5.http.ParseException;

public class ServicioProfesor {

    private static final Gson gson = new Gson();

    /**
     * Clase interna para agrupar credenciales de sesión.
     */
    private static class CredencialesSesion {
        final String apiKey;
        final String cookie;

        CredencialesSesion(String apiKey, String cookie) {
            this.apiKey = apiKey;
            this.cookie = cookie;
        }
    }

    /**
     * Obtiene las credenciales (apiKey y cookie) de la sesión y valida que existan.
     *
     * @param session La sesión HTTP actual
     * @return Un objeto CredencialesSesion con apiKey y cookie
     * @throws IOException Si apiKey o cookie no están presentes en la sesión
     */
    private static CredencialesSesion obtenerCredenciales(HttpSession session) throws IOException {
        String apiKey = (String) session.getAttribute("apiKey");
        String cookie = (String) session.getAttribute("sessionCookie");
        if (apiKey == null || cookie == null) {
            throw new IOException("Sesión inválida: falta apiKey o sessionCookie");
        }
        return new CredencialesSesion(apiKey, cookie);
    }

    /**
     * Realiza la autenticación del profesor mediante dni y contraseña.
     *
     * @param dni El DNI del profesor
     * @param password La contraseña del profesor
     * @return Un objeto AuthResult con apiKey y sessionCookie
     * @throws IOException Si hay error en la comunicación o autenticación
     */
    public static dew.servicios.AuthResult loginProfesor(String dni, String password) throws IOException {
        return ClienteCentro.obtenerInstancia().autenticarUsuario(dni, password);
    }

    /**
     * Obtiene la lista completa de profesores.
     *
     * @param context Contexto del servlet (no usado directamente aquí)
     * @param session Sesión HTTP con credenciales válidas
     * @return Lista de objetos Profesor
     * @throws IOException Si falla la obtención de datos o sesión inválida
     */
    public static List<Profesor> obtenerProfesores(ServletContext context, HttpSession session) throws IOException {
        CredencialesSesion cred = obtenerCredenciales(session);
        String json = ClienteCentro.obtenerInstancia().obtenerRecurso("profesores", cred.apiKey, cred.cookie);
        return gson.fromJson(json, new TypeToken<List<Profesor>>(){}.getType());
    }

    /**
     * Obtiene un profesor concreto por su DNI.
     *
     * @param context Contexto del servlet (no usado directamente aquí)
     * @param session Sesión HTTP con credenciales válidas
     * @param dni DNI del profesor a buscar
     * @return Objeto Profesor correspondiente al DNI
     * @throws IOException Si falla la obtención o sesión inválida
     */
    public static Profesor obtenerProfesorPorDni(ServletContext context, HttpSession session, String dni) throws IOException {
        CredencialesSesion cred = obtenerCredenciales(session);
        String json = ClienteCentro.obtenerInstancia().obtenerRecurso("profesores/" + dni, cred.apiKey, cred.cookie);
        return gson.fromJson(json, Profesor.class);
    }

    /**
     * Obtiene las asignaturas que imparte un profesor dado su DNI.
     *
     * @param context Contexto del servlet (no usado directamente aquí)
     * @param session Sesión HTTP con credenciales válidas 
     * @param dniProfesor DNI del profesor
     * @return Lista de asignaturas impartidas por el profesor
     * @throws IOException Si falla la obtención o sesión inválida
     */
    public static List<Asignatura> obtenerAsignaturasDeProfesor(ServletContext context, HttpSession session, String dniProfesor) throws IOException {
        CredencialesSesion cred = obtenerCredenciales(session);
        String json = ClienteCentro.obtenerInstancia().obtenerRecurso("profesores/" + dniProfesor + "/asignaturas", cred.apiKey, cred.cookie);
        return gson.fromJson(json, new TypeToken<List<Asignatura>>(){}.getType());
    }

    /**
     * Obtiene la lista de profesores que imparten una asignatura específica.
     *
     * @param context Contexto del servlet (no usado directamente aquí)
     * @param session Sesión HTTP con credenciales válidas
     * @param acronimoAsignatura Acrónimo de la asignatura
     * @return Lista de profesores que imparten la asignatura
     * @throws IOException Si falla la obtención o sesión inválida
     */
    public static List<Profesor> getProfesoresDeAsignatura(ServletContext context, HttpSession session, String acronimoAsignatura) throws IOException {
        CredencialesSesion cred = obtenerCredenciales(session);
        String json = ClienteCentro.obtenerInstancia().obtenerRecurso("asignaturas/" + acronimoAsignatura + "/profesores", cred.apiKey, cred.cookie);
        return gson.fromJson(json, new TypeToken<List<Profesor>>(){}.getType());
    }

    /**
     * Modifica la nota de un alumno en una asignatura específica.
     *
     * @param context Contexto del servlet (no usado directamente aquí)
     * @param session Sesión HTTP con credenciales válidas
     * @param dniAlumno DNI del alumno cuya nota se modificará
     * @param acronimoAsignatura Acrónimo de la asignatura
     * @param nuevaNota Nueva nota a asignar
     * @return true si la modificación fue exitosa
     * @throws IOException Si falla la petición o sesión inválida
     */
    public static boolean modificarNotaAlumno(ServletContext context, HttpSession session,
            String dniAlumno, String acronimoAsignatura, String nuevaNota) throws IOException {
        CredencialesSesion cred = obtenerCredenciales(session);
        String recurso = String.format("alumnos/%s/asignaturas/%s", dniAlumno, acronimoAsignatura);
        
        return ClienteCentro.obtenerInstancia().enviarPut(recurso, nuevaNota, cred.apiKey, cred.cookie);
    }

    /**
     * Obtiene la lista de alumnos matriculados en una asignatura impartida por el profesor.
     *
     * @param context Contexto del servlet (no usado directamente aquí)
     * @param session Sesión HTTP con credenciales válidas
     * @param acronimoAsignatura Acrónimo de la asignatura
     * @return Lista de alumnos matriculados en la asignatura
     * @throws IOException Si falla la obtención o sesión inválida
     */
    public static List<dew.clases.Alumno> obtenerAlumnosPorAsignatura(ServletContext context, HttpSession session, String acronimoAsignatura) throws IOException {
        CredencialesSesion cred = obtenerCredenciales(session);
        String json = ClienteCentro.obtenerInstancia().obtenerRecurso("asignaturas/" + acronimoAsignatura + "/alumnos", cred.apiKey, cred.cookie);
        return gson.fromJson(json, new TypeToken<List<dew.clases.Alumno>>(){}.getType());
    }
    
    /**
     * Calcula la nota media de una asignatura que imparte un profesor.
     * 
     * @param context contexto ServletContext
     * @param session sesión HttpSession con apiKey y cookie
     * @param acronimo acrónimo de la asignatura
     * @return nota media como double
     * @throws IOException en caso de error HTTP o parsing
     */
    public static double calcularNotaMedia(ServletContext context, HttpSession session, String acronimo) throws IOException {
        String apiKey = (String) session.getAttribute("apiKey");
        String cookie = (String) session.getAttribute("sessionCookie");
        
        // Aquí la ruta de la API que devuelve la nota media, si existe (si no, se debería calcular localmente)
        String recurso = "asignaturas/" + acronimo + "/notamedia"; 
        
        String json = ClienteCentro.obtenerInstancia().obtenerRecurso(recurso, apiKey, cookie);
        
        // Parsear la respuesta, que debería ser un número (o un JSON con campo notaMedia)
        // Suponiendo que es solo el número en texto
        try {
            return Double.parseDouble(json);
        } catch (NumberFormatException e) {
            throw new IOException("Error al parsear nota media: " + json, e);
        }
    }
    
    public static String obtenerNotaAlumno(ServletContext context, HttpSession session, String acronimo, String dniAlumno) throws IOException {
        // Obtener el alumno (sus datos y asignaturas)
        Alumno alumno = null;
		try {
			alumno = ServicioAlumno.obtenerAlumno(context, session);
		} catch (ParseException e) {
			
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
        
        if (alumno == null || alumno.getAsignaturas() == null) {
            return null;
        }
        
        for (Asignatura asignatura : alumno.getAsignaturas()) {
            if (asignatura.getAcronimo().equalsIgnoreCase(acronimo)) {
                return asignatura.getNota();
            }
        }
        
        return null;  // no encontrada la asignatura o nota
    }


}

