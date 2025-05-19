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
 * Servicio encargado de recuperar la información de un alumno,
 * incluyendo su lista de asignaturas desde la API de CentroEducativo.
 */
public class ServicioAlumno {

    private static final Gson gson = new Gson();

    /**
     * Recupera el alumno y sus asignaturas desde el backend.
     *
     * @param context El contexto de la aplicación web.
     * @param session La sesión actual con apiKey y cookie ya autenticadas.
     * @return Objeto Alumno con todos sus datos y asignaturas.
     * @throws IOException Si ocurre un fallo en la conexión o el parseo.
     */
    public static Alumno obtenerAlumno(ServletContext context, HttpSession session)
            throws IOException {

        // Validación previa: asegúrate de que la sesión contiene lo necesario
        String apiKey = (String) session.getAttribute("apiKey");
        String sessionCookie = (String) session.getAttribute("sessionCookie");
        String dni = (String) session.getAttribute("dni");

        if (apiKey == null || sessionCookie == null || dni == null) {
            throw new IOException("Sesión inválida: faltan apiKey, sessionCookie o dni");
        }

        ClienteCentro cliente = ClienteCentro.obtenerInstancia();

        // 1) Obtener datos básicos del alumno
        String jsonAlumno = cliente.obtenerRecurso("/alumnos/" + dni, apiKey, sessionCookie);
        if (jsonAlumno == null || jsonAlumno.isEmpty()) {
            throw new IOException("No se recibió información del alumno");
        }

        Alumno alumno;
        try {
            alumno = gson.fromJson(jsonAlumno, Alumno.class);
            System.out.println(">> JSON Alumno: " + jsonAlumno);
        } catch (Exception e) {
            throw new IOException("Error al parsear JSON del alumno", e);
        }

        if (alumno == null) {
            throw new IOException("El JSON recibido no contenía datos válidos del alumno");
        }

        // 2) Obtener asignaturas del alumno
        String jsonAsignaturas = cliente.obtenerRecurso("/alumnos/" + dni + "/asignaturas", apiKey, sessionCookie);
        System.out.println(">> JSON Asignaturas: " + jsonAsignaturas);
        if (jsonAsignaturas == null || jsonAsignaturas.isEmpty()) {
            throw new IOException("No se recibieron asignaturas para el alumno");
        }

        List<Asignatura> asignaturas;
        try {
            asignaturas = gson.fromJson(jsonAsignaturas, new TypeToken<List<Asignatura>>() {}.getType());
        } catch (Exception e) {
            throw new IOException("Error al parsear JSON de asignaturas", e);
        }

        alumno.setAsignaturas(asignaturas != null ? asignaturas : List.of());
   

        return alumno;
    }
}
