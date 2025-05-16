package dew.filtros;

import dew.servicios.ClienteCentro;
import dew.servicios.AuthResult;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Filtro que:
 *  1) Tomcat nos autentica en la web (getRemoteUser())
 *  2) Usa las credenciales para contraseña
 *  3) Llama a ClienteCentro.login() = apiKey + SesionCookie
 *  4) Guarda en HttpSession solo la apiKey + SesionCookie
 */
public class AuthFilter implements Filter {
    private String baseUrl;
    private Properties creds;

    @Override
    public void init(FilterConfig cfg) throws ServletException {
        baseUrl = cfg.getServletContext().getInitParameter("centro.baseUrl");
        creds   = new Properties();
        try (InputStream in = cfg.getServletContext()
               .getResourceAsStream("/WEB-INF/credenciales")) {
            if (in == null) throw new ServletException("Missing credenciales");
            creds.load(in);
        } catch (IOException e) {
            throw new ServletException("Error loading credentials", e);
        }
    }

    @Override
    public void doFilter(ServletRequest rq, ServletResponse rs, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest  req  = (HttpServletRequest)  rq;
        HttpServletResponse res  = (HttpServletResponse) rs;
        HttpSession session = req.getSession(true);

        // Si no hay apiKey/Sesioncookie y el usuario web está logeado:
        if (session.getAttribute("apiKey") == null && req.getRemoteUser() != null) {
            String user = req.getRemoteUser();
            String pass = creds.getProperty(user);
            if (pass == null) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "No REST credential for user " + user);
                return;
            }

            // 1) Login REST
            ClienteCentro cliente = ClienteCentro.getInstance(baseUrl);
            AuthResult auth;
            try {
                auth = cliente.login(user, pass);
            } catch (IOException e) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Data auth failed: " + e.getMessage());
                return;
            }

            // 2) Guardar apiKey y sessionCookie en HttpSession
            session.setAttribute("apiKey",       auth.getApiKey());
            session.setAttribute("sessionCookie", auth.getSessionCookie());
            session.setAttribute("dni", user);
        }

        chain.doFilter(rq, rs);
    }

    @Override
    public void destroy() {}
}