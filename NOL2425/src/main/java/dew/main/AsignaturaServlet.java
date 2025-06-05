package dew.main;

import dew.clases.Asignatura;
import dew.clases.Alumno;

import dew.servicios.ServicioAsignatura;
import dew.servicios.ServicioProfesor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

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
        String acronimo = request.getParameter("acronimo");
        if (acronimo == null || acronimo.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro 'acronimo'");
            return;
        }
        if (acronimo == null || acronimo.trim().isEmpty()) {
            response.sendRedirect("lista_asignaturas.jsp"); // o index.jsp
            return;
        }

        // Obtener los detalles de la asignatura utilizando el servicio
        Asignatura asignaturaDetallada = ServicioAsignatura.obtenerDetallesAsignatura(
                getServletContext(), sesion, acronimo);
        
        // ** Obtener lista de alumnos matriculados
        /*  List<Alumno> alumnosMatriculados = ServicioProfesor.obtenerAlumnosPorAsignatura(
        /*    getServletContext(), sesion, acronimo);
		/*
        /* Convertir la lista de alumnos a JSON
        /*String alumnosJson = new com.google.gson.Gson().toJson(alumnosMatriculados);
		*/
        
        // Enviar los datos al JSP para su visualización
        request.setAttribute("asignatura", asignaturaDetallada);
        //request.setAttribute("alumnosJson", alumnosJson);
        request.getRequestDispatcher("/info_asignatura.jsp").forward(request, response);
    }

}
