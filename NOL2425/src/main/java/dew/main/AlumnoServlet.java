package dew.main;

import dew.servicios.ClienteCentro;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Devuelve el JSON de los alumnos con apiKey + sessionCookie.
 */
public class AlumnoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Sin sesión");
            return;
        }

        String apiKey        = (String) session.getAttribute("apiKey");
        String sessionCookie = (String) session.getAttribute("sessionCookie");
        String dni 			 = (String) session.getAttribute("dni");
        if (apiKey == null || sessionCookie == null || dni == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                           "No autenticado con CentroEducativo");
            return;
        }

        String baseUrl = getServletContext().getInitParameter("centro.baseUrl");
        ClienteCentro client = ClienteCentro.getInstance(baseUrl);
        String json = client.getAlumnoPorDNI(apiKey, sessionCookie, dni);

        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(json);
    }
}