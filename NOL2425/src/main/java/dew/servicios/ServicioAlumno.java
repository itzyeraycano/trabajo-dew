package dew.servicios;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dew.clases.Alumno;
import dew.clases.Asignatura;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servicio encargado de manejar la información del alumno y sus asignaturas.
 * Obtiene los datos del alumno y sus asignaturas desde el backend.
 */
public class ServicioAlumno {

    private static final Gson gson = new Gson();

    /**
     * Obtiene los datos del alumno y sus asignaturas desde la API de CentroEducativo.
     *
     * @param context El contexto de la aplicación web
     * @param session La sesión HTTP del usuario
     * @return El alumno con sus asignaturas asociadas
     * @throws IOException Si hay problemas al hacer las peticiones
     */
    public static Alumno obtenerAlumno(ServletContext context, HttpSession session)
            throws IOException {

        // Obtener datos de la sesión
        String apiKey = (String) session.getAttribute("apiKey");
        String sessionCookie = (String) session.getAttribute("sessionCookie");
        String dni = (String) session.getAttribute("dni");

        // 1) Obtener la información del alumno en formato JSON
        String alumnoJson = ClienteCentro.obtenerInstancia()
                .obtenerRecurso("/alumnos/" + dni, apiKey, sessionCookie);

        Alumno alumno = gson.fromJson(alumnoJson, Alumno.class);

        // 2) Obtener las asignaturas del alumno en formato JSON
        String asignaturasJson = ClienteCentro.obtenerInstancia()
                .obtenerRecurso("/alumnos/" + dni + "/asignaturas", apiKey, sessionCookie);

        // 3) Convertir el JSON de asignaturas a una lista de objetos Asignatura
        List<Asignatura> asignaturas = gson.fromJson(asignaturasJson,
        new TypeToken<List<Asignatura>>(){}.getType());

        // 4) Asignar las asignaturas al objeto alumno
        alumno.setAsignaturas(asignaturas);

        // Retornar el alumno con sus asignaturas
        return alumno;
    }
}
