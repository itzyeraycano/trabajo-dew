package dew.main;

import dew.clases.Alumno;
import dew.servicios.ServicioAlumno;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * Servlet que maneja las peticiones relacionadas con los datos de los alumnos.
 * Se puede acceder a los detalles de un solo alumno o a la lista de todos los alumnos.
 */

public class AlumnoServlet extends HttpServlet {

    /**
     * Maneja las peticiones GET para mostrar los datos de los alumnos.
     * Dependiendo de la URL, muestra el perfil de un alumno o la lista de alumnos.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verifica si el usuario está autenticado
        if (!esAutenticado(request, response)) {
            return;
        }
        
        HttpSession session = request.getSession();
		
        String action = request.getParameter("action");

        if (action != null && action.equals("cierra")) { // si se da la orden de cerrar sesión...
            session.invalidate();

            // Eliminar cookie JSESSIONID en el navegador
            Cookie cookie = new Cookie("JSESSIONID", "");
            cookie.setMaxAge(0); // expira inmediatamente
            cookie.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath()); // importante el path
            response.addCookie(cookie);

            response.sendRedirect("/NOL2425/");
            return;
        }

        // Recupera la ruta solicitada
        String ruta = request.getServletPath();

        switch (ruta) {
            case "/alumno":
                mostrarPerfil(request, response);
                break;
            case "/alumnos":
                mostrarLista(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Verifica si el usuario está autenticado.
     * Si no lo está, devuelve un error 401.
     */
    private boolean esAutenticado(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null ||
            session.getAttribute("apiKey") == null ||
            session.getAttribute("sessionCookie") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                               "No autenticado con CentroEducativo");
            return false;
        }
        return true;
    }

    /**
     * Muestra el perfil del alumno actual. Utiliza los datos almacenados en la sesión.
     */
    private void mostrarPerfil(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println(">> Entrando en mostrarPerfil()");

        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println(">> No hay sesión");
            request.setAttribute("error", "Sesión no válida.");
            request.getRequestDispatcher("login-error.jsp").forward(request, response);
            return;
        }

        try {
            Alumno alumno = ServicioAlumno.obtenerAlumno(getServletContext(), session);
            if (alumno == null) {
                System.out.println(">> Alumno es null");
                request.setAttribute("error", "No se pudo cargar la ficha del alumno.");
                request.getRequestDispatcher("login-error.jsp").forward(request, response);
                return;
            }

            System.out.println(">> Alumno obtenido: " + alumno.getDni());

            request.setAttribute("alumno", alumno);
            request.getRequestDispatcher("ficha_alumno.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println(">> ERROR en mostrarPerfil: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error interno al cargar el alumno.");
            request.getRequestDispatcher("login-error.jsp").forward(request, response);
        }
    }



    /**
     * Muestra la lista de todos los alumnos.
     */
    private void mostrarLista(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Alumno alumno = ServicioAlumno.obtenerAlumno(getServletContext(), session);

        // Pasa la lista de alumnos como atributo para la vista
        request.setAttribute("alumnos", alumno);

        // Redirige a la página lista_alumnos.html para mostrar todos los alumnos
        request.getRequestDispatcher("lista_alumnos.jsp").forward(request, response);
    }
}
