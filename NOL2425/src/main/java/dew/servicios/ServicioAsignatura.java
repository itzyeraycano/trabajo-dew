package dew.servicios;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dew.clases.Asignatura;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servicios estáticos para manejar Asignaturas,
 * reutilizando CentroClient para todas las llamadas HTTP.
 */
public class ServicioAsignatura {
  private static final Gson GSON = new Gson();

  
  //Obtiene la lista de asignaturas (con nota) para el alumno identificado en sesión.
  
  public static List<Asignatura> fetchForAlumno(ServletContext ctx, HttpSession ses)
      throws IOException {
    String apiKey        = (String) ses.getAttribute("apiKey");
    String sessionCookie = (String) ses.getAttribute("sessionCookie");
    String dni           = (String) ses.getAttribute("dni");

    String json = ClienteCentro.instance()
        .getResource("/alumnos/" + dni + "/asignaturas", apiKey, sessionCookie);

    return GSON.fromJson(
      json,
      new TypeToken<List<Asignatura>>(){}.getType()
    );
  }

  //Obtiene los detalles de cualquier asignatura por su nonbre
 
  public static Asignatura fetchOne(ServletContext ctx, HttpSession ses, String acronimo)
      throws IOException {
    String apiKey        = (String) ses.getAttribute("apiKey");
    String sessionCookie = (String) ses.getAttribute("sessionCookie");

    String json = ClienteCentro.instance()
        .getResource("/asignaturas/" + acronimo, apiKey, sessionCookie);

    return GSON.fromJson(json, Asignatura.class);
  }
}