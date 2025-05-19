package dew.main;

import dew.clases.Asignatura;
import dew.servicios.ServicioAsignatura;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Servlet encargado de mostrar los detalles de una asignatura concreta.
 */

public class AsignaturaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Validar sesión activa
        HttpSession sesion = request.getSession(false);
        if (sesion == null ||
            sesion.getAttribute("apiKey") == null ||
            sesion.getAttribute("sessionCookie") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Acceso denegado: sin autenticación");
            return;
        }

        // Extraer el acrónimo de la asignatura desde el parámetro GET
        String codigo = request.getParameter("codigo");
        if (codigo == null || codigo.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro 'acronimo'");
            return;
        }

        // Obtener los detalles de la asignatura utilizando el servicio
        Asignatura asignaturaDetallada = ServicioAsignatura.obtenerDetallesAsignatura(
                getServletContext(), sesion, codigo);

        // Enviar los datos al JSP para su visualización
        request.setAttribute("asignatura", asignaturaDetallada);
        request.getRequestDispatcher("/info_asignatura.jsp").forward(request, response);
    }
}
