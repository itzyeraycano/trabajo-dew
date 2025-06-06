package dew.main;

import com.google.gson.JsonObject;
import dew.servicios.ClienteCentro;
import dew.servicios.ServicioProfesor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Clase ActualizarNotaServlet:
 * ----------------------------
 * Este servlet procesa solicitudes HTTP PUT que contienen un JSON con los campos { "dni": "...", "acronimo": "...", "nota": "7.0" }.
 * A continuación:
 *   1) Verifica que haya una sesión válida y que existan apiKey y sessionCookie en la HttpSession.
 *   2) Extrae del cuerpo de la petición el objeto JSON con dni, acrónimo de la asignatura y la nota.
 *   3) Construye una petición HTTP PUT al servicio CentroEducativo en la ruta /alumnos/{dni}/asignaturas/{acronimo}?key={apiKey}, incluyendo la cookie de sesión.
 *   4) Envía la nota (como cuerpo de la petición) al servicio remoto.
 *   5) Gestiona la respuesta del servicio CentroEducativo y devuelve al cliente frontend el resultado apropiado:
 *      – “OK” si la actualización fue satisfactoria,
 *      – errores HTTP 403, 404 o 500 según sea el caso.
 */
@WebServlet("/modificarNota")
public class ModificarNotaServlet extends HttpServlet {

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Recuperar los parámetros de la solicitud
        String dniAlumno = request.getParameter("dniAlumno");
        String acronimo = request.getParameter("acronimoAsignatura");
        String nuevaNota = request.getParameter("nuevaNota");

        // Validar que los parámetros no sean nulos
        if (dniAlumno == null || acronimo == null || nuevaNota == null || nuevaNota.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"message\": \"Parámetros inválidos\"}");
            return;
        }

        // Intentar modificar la nota (aquí debes hacer el PUT a la API)
        boolean exito = ServicioProfesor.modificarNotaAlumno(
                getServletContext(), request.getSession(), dniAlumno, acronimo, nuevaNota);

        // Responder con el resultado en formato JSON
        if (exito) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"success\": true, \"message\": \"Nota actualizada correctamente.\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"Error al actualizar la nota\"}");
        }
    }
}
