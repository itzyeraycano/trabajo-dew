package dew.servicios;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dew.clases.Alumno;
import dew.clases.Asignatura;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ServicioAlumno {
  private static final Gson GSON = new Gson();

  public static Alumno fetchOne(ServletContext ctx, HttpSession ses)
      throws IOException {
    String apiKey        = (String) ses.getAttribute("apiKey");
    String sessionCookie = (String) ses.getAttribute("sessionCookie");
    String dni           = (String) ses.getAttribute("dni");

    // 1) Get el JSON del alumno 
    String alumnoJson = ClienteCentro.instance()
        .getResource("/alumnos/" + dni, apiKey, sessionCookie);
    Alumno alumno = GSON.fromJson(alumnoJson, Alumno.class);

    // 2)Get el JSON de asignaturas
    String asigJson = ClienteCentro.instance()
        .getResource("/alumnos/" + dni + "/asignaturas", apiKey, sessionCookie);

    // 3) Parseamos a una lista de asignaturas
    List<Asignatura> asignaturas = GSON.fromJson(
      asigJson,
      new TypeToken<List<Asignatura>>(){}.getType()
    );

    // 4)Introducimos las asignaturas en el alumno
    alumno.setAsignaturas(asignaturas);
    return alumno;
  }
}