package dew.servicios;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dew.clases.Asignatura;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servicio encargado de manejar la información de las asignaturas de los alumnos.
 * Realiza las peticiones necesarias para obtener asignaturas o detalles de una asignatura.
 */
public class ServicioAsignatura {

    private static final Gson gson = new Gson();

    /**
     * Obtiene la lista de asignaturas (con notas) para el alumno identificado en la sesión.
     *
     * @param context El contexto de la aplicación web
     * @param session La sesión HTTP del usuario
     * @return La lista de asignaturas del alumno
     * @throws IOException Si ocurre un error al obtener la información
     */
    public static List<Asignatura> obtenerAsignaturasPorAlumno(ServletContext context, HttpSession session)
            throws IOException {

        // Obtener datos de la sesión
        String apiKey = (String) session.getAttribute("apiKey");
        String sessionCookie = (String) session.getAttribute("sessionCookie");
        String dni = (String) session.getAttribute("dni");

        // Hacer la solicitud GET para obtener las asignaturas del alumno
        String json = ClienteCentro.obtenerInstancia()
                .obtenerRecurso("/alumnos/" + dni + "/asignaturas", apiKey, sessionCookie);

        // Convertir el JSON a una lista de objetos Asignatura
        return gson.fromJson(json, new TypeToken<List<Asignatura>>(){}.getType());
    }

    /**
     * Obtiene los detalles de una asignatura a partir de su acrónimo.
     *
     * @param context El contexto de la aplicación web
     * @param session La sesión HTTP del usuario
     * @param acronimo El acrónimo de la asignatura
     * @return La asignatura con todos sus detalles
     * @throws IOException Si ocurre un error al obtener la información
     */
    public static Asignatura obtenerDetallesAsignatura(ServletContext context, HttpSession session, String acronimo)
            throws IOException {

        // Obtener datos de la sesión
        String apiKey = (String) session.getAttribute("apiKey");
        String sessionCookie = (String) session.getAttribute("sessionCookie");

        // Hacer la solicitud GET para obtener los detalles de la asignatura
        String json = ClienteCentro.obtenerInstancia()
                .obtenerRecurso("/asignaturas/" + acronimo, apiKey, sessionCookie);

        // Convertir el JSON a un objeto Asignatura
        return gson.fromJson(json, Asignatura.class);
    }
}
