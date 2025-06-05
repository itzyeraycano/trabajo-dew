package dew.main;

import dew.clases.Asignatura;
import dew.clases.Profesor;
import dew.servicios.ServicioProfesor;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class ProfesorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!esAutenticado(request, response)) {
            return;
        }

        HttpSession session = request.getSession();

        String action = request.getParameter("action");

        if ("cierra".equals(action)) {
            session.invalidate();

            // Eliminar cookie JSESSIONID en el navegador para cerrar sesión correctamente
            Cookie cookie = new Cookie("JSESSIONID", "");
            cookie.setMaxAge(0);
            cookie.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
            response.addCookie(cookie);

            response.sendRedirect("/NOL2425/");
            return;
        }

        String ruta = request.getServletPath();

        switch (ruta) {
            case "/profesor":
                mostrarPerfil(request, response);
                break;
            case "/profesores":
                mostrarLista(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        // Solo atender PUT a /profesor
        if ("/profesor".equals(path)) {
            if (!esAutenticado(req, resp)) {
                return;
            }

            // Leer JSON del cuerpo
            StringBuilder sb = new StringBuilder();
            String line;
            try (BufferedReader reader = req.getReader()) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }

            try {
                com.google.gson.JsonObject json = com.google.gson.JsonParser.parseString(sb.toString()).getAsJsonObject();
                String dniAlumno = json.get("dniAlumno").getAsString();
                String acronimo = json.get("acronimo").getAsString();
                String nota = json.get("nota").getAsString();

                boolean resultado = ServicioProfesor.modificarNotaAlumno(
                        getServletContext(), req.getSession(), dniAlumno, acronimo, nota
                );

                resp.setContentType("application/json");
                if (resultado) {
                    resp.getWriter().write("{\"estado\":\"ok\"}");
                } else {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    resp.getWriter().write("{\"estado\":\"error\"}");
                }
            } catch (Exception e) {
                e.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.setContentType("application/json");
                resp.getWriter().write("{\"estado\":\"error\",\"mensaje\":\"Petición inválida\"}");
            }
        } else {
            
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }


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

    private void mostrarPerfil(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            request.setAttribute("error", "Sesión no válida.");
            request.getRequestDispatcher("login-error.jsp").forward(request, response);
            return;
        }

        try {
            String dniProfesor = (String) session.getAttribute("dni");
            Profesor profesor = ServicioProfesor.obtenerProfesorPorDni(getServletContext(), session, dniProfesor);
            if (profesor == null) {
                request.setAttribute("error", "No se pudo cargar la ficha del profesor.");
                request.getRequestDispatcher("login-error.jsp").forward(request, response);
                return;
            }

            request.setAttribute("profesor", profesor);
            request.getRequestDispatcher("ficha_profesor.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error interno al cargar el profesor.");
            request.getRequestDispatcher("login-error.jsp").forward(request, response);
        }
    }

    private void mostrarLista(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Profesor> profesores = ServicioProfesor.obtenerProfesores(getServletContext(), session);
        request.setAttribute("profesores", profesores);
        request.getRequestDispatcher("lista_profesores.jsp").forward(request, response);
    }
}
