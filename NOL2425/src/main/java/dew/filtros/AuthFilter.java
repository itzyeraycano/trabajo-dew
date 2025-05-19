package dew.filtros;

import dew.filtros.ComprobadorInput;
import dew.servicios.AuthResult;
import dew.servicios.ClienteCentro;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class AuthFilter implements Filter {
  private Properties creds = new Properties();

  @Override
  public void init(FilterConfig cfg) throws ServletException {
    ServletContext ctx = cfg.getServletContext();
    try (InputStream in = ctx.getResourceAsStream("/WEB-INF/credenciales.properties")) {
      creds.load(in);
    } catch (IOException e) {
      throw new ServletException("No pude cargar credenciales.properties", e);
    }
  }

  @Override
  public void doFilter(ServletRequest rq, ServletResponse rs, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest  req = (HttpServletRequest) rq;
    HttpServletResponse res = (HttpServletResponse) rs;
    HttpSession session = req.getSession(true);

    if (session.getAttribute("apiKey") == null && req.getRemoteUser() != null) {
      String user = req.getRemoteUser();
      String pass = creds.getProperty(user);
      
      //Con la clase que he creado valido todos los tipos de entradas posibles
      if (!ComprobadorInput.isValidRequired(pass) || !ComprobadorInput.isValidRequired(user) ) {
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED,
            "No existe credenciales para " + user);
        return;
      }
      
      AuthResult auth = ClienteCentro.instance().login(user, pass);
      session.setAttribute("apiKey",        auth.getApiKey());
      session.setAttribute("sessionCookie", auth.getSessionCookie());
      session.setAttribute("dni",           user);
    }
    chain.doFilter(rq, rs);
  }

  @Override
  public void destroy() {}
}