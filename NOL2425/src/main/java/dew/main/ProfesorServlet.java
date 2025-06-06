
package dew.main;

import dew.clases.Alumno; 
import dew.clases.Asignatura;
import dew.clases.Profesor;
import dew.servicios.ServicioProfesor;
import dew.servicios.ServicioAsignatura; // Para obtener detalles de la asignatura

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import org.apache.hc.core5.http.ParseException; 
import com.google.gson.JsonSyntaxException;   

public class ProfesorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        if (!esAutenticadoConCentroEducativo(request, response)) {
            return;
        }

        HttpSession session = request.getSession(); 
        String action = request.getParameter("action");
        String servletPath = request.getServletPath();
        // String pathInfo = request.getPathInfo(); // Ya no se usa con el enfoque de 'action'

        if ("cierra".equals(action)) {
            session.invalidate();
            Cookie cookie = new Cookie("JSESSIONID", ""); 
            cookie.setMaxAge(0);
            String contextPath = request.getContextPath();
            cookie.setPath(contextPath.isEmpty() ? "/" : contextPath);
            response.addCookie(cookie);
            response.sendRedirect(request.getContextPath() + "/"); 
            return;
        }

        // Lógica para manejar rutas y acciones
        if ("/profesor".equals(servletPath)) {
            if ("verAlumnosDeAsignatura".equals(action)) {
                String acronimoAsignatura = request.getParameter("acronimo");
                if (acronimoAsignatura != null && !acronimoAsignatura.trim().isEmpty()) {
                    mostrarAlumnosDeAsignatura(request, response, session, acronimoAsignatura);
                } else {
                    System.err.println("ProfesorServlet: Acrónimo de asignatura no proporcionado para action=verAlumnosDeAsignatura.");
                    request.setAttribute("error", "No se especificó la asignatura para ver los alumnos.");
                    mostrarPerfil(request, response, session); // Volver al perfil con un mensaje de error implícito
                }
            } else { // Por defecto, si es /profesor sin otra acción reconocida, mostrar perfil
                 mostrarPerfil(request, response, session);
            }
        } else if ("/profesores".equals(servletPath)) { 
            mostrarListaTodosProfesores(request, response, session);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ruta no reconocida: " + servletPath);
        }
    }

    private boolean esAutenticadoConCentroEducativo(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null ||
            session.getAttribute("apiKey") == null ||
            session.getAttribute("dni") == null ) { 
            
            System.err.println("ProfesorServlet: Intento de acceso sin autenticación completa con CentroEducativo (apiKey o dni faltante).");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                               "No autenticado con CentroEducativo. Por favor, inicie sesión.");
            return false;
        }
        return true;
    }

    private void mostrarPerfil(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {
        if (session == null) { 
            request.setAttribute("error", "Sesión no válida o expirada.");
            request.getRequestDispatcher("/login-error.jsp").forward(request, response); 
            return;
        }
        try {
            String dniProfesor = (String) session.getAttribute("dni");
            if (dniProfesor == null) {
                throw new ServletException("DNI del profesor no encontrado en la sesión.");
            }
            Profesor profesor = ServicioProfesor.obtenerProfesorPorDni(getServletContext(), session, dniProfesor);
            if (profesor == null) {
                request.setAttribute("error", "No se pudo cargar la información del profesor.");
                request.getRequestDispatcher("/login-error.jsp").forward(request, response);
                return;
            }
            request.setAttribute("profesor", profesor);
            request.getRequestDispatcher("/ficha_profesor.jsp").forward(request, response); 
        } catch (Exception e) { 
            System.err.println("ProfesorServlet (mostrarPerfil): Error - " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error interno al cargar el perfil del profesor.");
            request.getRequestDispatcher("/login-error.jsp").forward(request, response);
        }
    }

    private void mostrarListaTodosProfesores(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {
        if (session == null) { 
            request.setAttribute("error", "Sesión no válida o expirada.");
            request.getRequestDispatcher("/login-error.jsp").forward(request, response);
            return;
        }
        try {
            List<Profesor> profesores = ServicioProfesor.obtenerProfesores(getServletContext(), session);
            request.setAttribute("profesores", profesores);
            request.getRequestDispatcher("/lista_profesores.jsp").forward(request, response); 
        } catch (Exception e) { 
            System.err.println("ProfesorServlet (mostrarListaTodosProfesores): Error - " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error interno al cargar la lista de profesores.");
            request.getRequestDispatcher("/login-error.jsp").forward(request, response);
        }
    }

    // Método para mostrar alumnos de una asignatura específica
    private void mostrarAlumnosDeAsignatura(HttpServletRequest request, HttpServletResponse response, HttpSession session, String acronimoAsignatura)
            throws ServletException, IOException {
        if (session == null) { 
            request.setAttribute("error", "Sesión no válida o expirada.");
            request.getRequestDispatcher("/login-error.jsp").forward(request, response);
            return;
        }
        try {
            // Obtener información de la asignatura para mostrar su nombre, etc.
            // Se asume que ServicioAsignatura.obtenerDetallesAsignatura existe y funciona.
            Asignatura asignaturaInfo = ServicioAsignatura.obtenerDetallesAsignatura(getServletContext(), session, acronimoAsignatura);
            
            // Obtener la lista de alumnos con sus notas para esta asignatura
            List<Alumno> alumnosEnAsignatura = ServicioProfesor.obtenerAlumnosConNotaPorAsignatura(getServletContext(), session, acronimoAsignatura);
            
            request.setAttribute("asignatura", asignaturaInfo); 
            request.setAttribute("alumnosDeAsignatura", alumnosEnAsignatura);
            request.getRequestDispatcher("/detalles_asig_profesor.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("ProfesorServlet (mostrarAlumnosDeAsignatura): Error para acrónimo " + acronimoAsignatura + " - " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error interno al cargar los alumnos de la asignatura: " + acronimoAsignatura);
            request.getRequestDispatcher("/login-error.jsp").forward(request, response);
        }
    }
}
