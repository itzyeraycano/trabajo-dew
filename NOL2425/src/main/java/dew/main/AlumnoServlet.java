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
        HttpSession session = request.getSession();
        Alumno alumno = ServicioAlumno.obtenerAlumno(getServletContext(), session);

        // Pasa el objeto Alumno como atributo para que esté disponible en la vista
        request.setAttribute("alumno", alumno);

        // Redirige a la página ficha_alumno.html para mostrar los detalles del alumno
        request.getRequestDispatcher("/alumno/ficha_alumno.jsp").forward(request, response); //hay que hacerlo con .jsp
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
        request.getRequestDispatcher("/alumno/lista_alumnos.jsp").forward(request, response); //hay que hacerlo con .jsp
    }
}
