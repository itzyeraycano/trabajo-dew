package dew.main;

import dew.clases.Alumno;
import dew.clases.Asignatura;
import dew.clases.Profesor;
import dew.servicios.ServicioProfesor;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ProfesorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null ||
            session.getAttribute("apiKey") == null ||
            session.getAttribute("sessionCookie") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autenticado");
            return;
        }

        String action = request.getParameter("action");
        if ("cierra".equals(action)) {
            session.invalidate();
            response.sendRedirect("/NOL2425/");
            return;
        }

        String path = request.getServletPath();

        switch (path) {
            case "/profesor":
                mostrarPerfil(request, response);
                break;
            case "/profesor/asignaturas":
                listarAsignaturas(request, response);
                break;
            case "/profesor/alumnos":
                listarAlumnosAsignatura(request, response);
                break;
            case "/profesor/nota":
                obtenerNota(request, response);
                break;
            case "/profesor/nota/modificar":
                // No implementado aún porque ClienteCentro no tiene PUT
                response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Modificación de nota no implementada");
                break;
            case "/profesor/nota/media":
                obtenerNotaMedia(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void mostrarPerfil(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dniProfesor = (String) request.getSession().getAttribute("dni");
        Profesor profesor = ServicioProfesor.obtenerProfesorPorDni(getServletContext(), request.getSession(), dniProfesor);
        request.setAttribute("profesor", profesor);
        request.getRequestDispatcher("ficha_profesor.jsp").forward(request, response);
    }

    private void listarAsignaturas(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String dniProfesor = (String) request.getSession().getAttribute("dni");
        List<Asignatura> asignaturas = ServicioProfesor.obtenerAsignaturasDeProfesor(getServletContext(), request.getSession(), dniProfesor);
        response.setContentType("application/json");
        response.getWriter().write(new com.google.gson.Gson().toJson(asignaturas));
    }

    private void listarAlumnosAsignatura(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String acronimo = request.getParameter("acronimo");
        if (acronimo == null || acronimo.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta parámetro acronimo");
            return;
        }
        // Aquí no tienes método en ServicioProfesor para obtener alumnos por asignatura.
        // Puedes lanzar error o implementar si amplías ClienteCentro.
        response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Listado de alumnos por asignatura no implementado");
    }

    private void obtenerNota(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // No tienes método para obtener nota individual en ServicioProfesor con ClienteCentro actual
        response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Obtención de nota no implementada");
    }

    private void obtenerNotaMedia(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // No tienes método para calcular nota media en ServicioProfesor con ClienteCentro actual
        response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Cálculo de nota media no implementado");
    }
    
    private void modificarNota(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String acronimo = request.getParameter("acronimo");
        String dniAlumno = request.getParameter("dni");
        String nuevaNota = request.getParameter("nota");

        if (acronimo == null || dniAlumno == null || nuevaNota == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan parámetros");
            return;
        }

        boolean exito;
        try {
            exito = ServicioProfesor.modificarNotaAlumno(getServletContext(), request.getSession(), dniAlumno, acronimo, nuevaNota);
        } catch (IOException e) {
            exito = false;
            // Opcional: log error
            e.printStackTrace();
        }

        response.setContentType("application/json");
        response.getWriter().write(new com.google.gson.Gson().toJson(exito));
    }
}
