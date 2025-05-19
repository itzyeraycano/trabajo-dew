package dew.filtros;

import dew.servicios.AuthResult;
import dew.servicios.ClienteCentro;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Filtro para gestionar la autenticación de los usuarios.
 * Valida las credenciales, obtiene el API key y la cookie de sesión, y guarda los datos en la sesión.
 */
public class AuthFiltro implements Filter {
    private Properties credenciales = new Properties();

    /**
     * Inicializa el filtro cargando las credenciales desde el archivo /WEB-INF/credenciales.
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        try (InputStream input = context.getResourceAsStream("/WEB-INF/credenciales")) {
            if (input == null) {
                throw new ServletException("No se pudo cargar el archivo de credenciales");
            }
            credenciales.load(input);
        } catch (IOException e) {
            throw new ServletException("Error al cargar las credenciales", e);
        }
    }

    /**
     * Ejecuta el filtro de autenticación. Verifica que el usuario esté autenticado y que las credenciales sean válidas.
     * Si no están disponibles, realiza el login y guarda la sesión.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(true);

        // Si no hay apiKey en la sesión, pero hay usuario remoto autenticado
        if (session.getAttribute("apiKey") == null && req.getRemoteUser() != null) {
            String usuario = req.getRemoteUser();
            String contrasena = credenciales.getProperty(usuario);

            // Validación de las credenciales con una clase personalizada
            if (!ValidadorInput.esValido(contrasena) || !ValidadorInput.esValido(usuario)) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "No existen credenciales para el usuario " + usuario);
                return;
            }

            // Realizamos el login mediante ClienteCentro
            AuthResult authResult = ClienteCentro.obtenerInstancia().autenticarUsuario(usuario, contrasena);

            // Guardamos la apiKey, sessionCookie y dni en la sesión
            session.setAttribute("apiKey", authResult.getApiKey());
            session.setAttribute("sessionCookie", authResult.getSessionCookie());
            session.setAttribute("dni", usuario);
        }

        // Continuamos con la cadena de filtros
        chain.doFilter(request, response);
    }

    /**
     * Método que destruye el filtro (sin acciones adicionales en este caso).
     */
    @Override
    public void destroy() {
        // No se requiere ninguna acción en este caso
    }
}
