package dew.main;

import dew.clases.Alumno;
import dew.servicios.ServicioAlumno;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet({"/alumno","/alumnos"})
public class AlumnoServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    if (!checkAuthentication(req, resp)) {
      return;
    }
    
    //dos opciones de paths
    String path = req.getServletPath();
    switch (path) {
      case "/alumno":
        showPerfil(req, resp);
        break;
      case "/alumnos":
        showLista(req, resp);
        break;
      default:
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
  }

  //Si ya no hay sesion valida envía 401 
  private boolean checkAuthentication(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    HttpSession session = req.getSession(false);
    if (session == null ||
        session.getAttribute("apiKey") == null ||
        session.getAttribute("sessionCookie") == null) {
      resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                     "No autenticado con CentroEducativo");
      return false;
    }
    return true;
  }

  // GET un alumno y lo enseña en ficha_alumno.html 
  private void showPerfil(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession session = req.getSession();
    Alumno alumno = ServicioAlumno.fetchOne(getServletContext(), session);
    req.setAttribute("alumno", alumno);
    req.getRequestDispatcher("/alumno/ficha_alumno.html")
       .forward(req, resp);
  }

  // Carga todos los alumnos y los muestra en lista_alumnos.html 
  private void showLista(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpSession session = req.getSession();
    Alumno  alumno = ServicioAlumno.fetchOne(getServletContext(), session);
    req.setAttribute("alumnos", alumno);
    req.getRequestDispatcher("/alumno/lista_alumnos.html")
       .forward(req, resp);
  }
}