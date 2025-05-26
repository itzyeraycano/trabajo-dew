package dew.servicios;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.CookieStore;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import com.google.gson.Gson;
/**
 * Cliente para interactuar con la API REST del Centro Educativo.
 * Realiza peticiones GET y POST para obtener datos y autenticar usuarios.
 */
public class ClienteCentro {

	private static final String BASE_URL = "http://localhost:9090/CentroEducativo/";
	  private static final Gson GSON = new Gson();


	// 1) Un CookieStore estático para gestionar cookies
	  private static final CookieStore COOKIE_STORE = new BasicCookieStore();

	  // 2) Cliente HTTP que utiliza el CookieStore para mantener sesión
	  private static final CloseableHttpClient HTTP = HttpClients.custom()
	      .setDefaultCookieStore(COOKIE_STORE)
	      .build();

	  private ClienteCentro() {}

	  public static ClienteCentro obtenerInstancia() {
	      return Holder.INSTANCE;
	  }

	  private static class Holder {
	      private static final ClienteCentro INSTANCE = new ClienteCentro();
	  }

	  /** Limpia todas las cookies guardadas para resetear sesión */
	  public static void limpiarCookies() {
	      COOKIE_STORE.clear();
	  }

	  /**
	   * Realiza la autenticación de usuario enviando un POST con JSON al endpoint /login.
	   * Devuelve un AuthResult con apiKey y cookie JSESSIONID.
	   */
	  public AuthResult autenticarUsuario(String dni, String pass) throws IOException {
	      
	      limpiarCookies();

	      HttpPost postLogin = new HttpPost(BASE_URL + "login");

	      // Construir payload JSON con credenciales
	      Map<String, String> datosCredenciales = Map.of(
	          "dni", dni,
	          "password", pass
	      );
	      String jsonPayload = GSON.toJson(datosCredenciales);

	      postLogin.setEntity(new StringEntity(jsonPayload, StandardCharsets.UTF_8));
	      postLogin.setHeader("Content-Type", "application/json");

	      try (CloseableHttpResponse resp = HTTP.execute(postLogin)) {
	          int statusCode = resp.getCode();
	          if (statusCode != 200) {
	              throw new IOException("Login fallido: código HTTP " + statusCode);
	          }

	          // NO MODIFICAR ESTA LÍNEA, lectura segura del cuerpo JSON con apiKey
	          String apiKey;
	          try {
	              apiKey = EntityUtils.toString(
	                  resp.getEntity(), StandardCharsets.UTF_8
	              ).trim();
	          } catch (Exception e) {
	              throw new IOException("Error parseando la respuesta de login", e);
	          }

	          // Extraer cookie JSESSIONID de las cabeceras Set-Cookie
	          String sessionCookie = null;
	          for (Header cabecera : resp.getHeaders("Set-Cookie")) {
	              String valor = cabecera.getValue();
	              if (valor.startsWith("JSESSIONID=")) {
	                  sessionCookie = valor.split(";", 2)[0];
	                  break;
	              }
	          }
	          if (sessionCookie == null) {
	              throw new IOException("No se encontró cookie JSESSIONID en la respuesta de login");
	          }

	          return new AuthResult(apiKey, sessionCookie);
	      }
	  }

	  /**
	   * Método GET genérico para obtener cualquier recurso REST autenticado.
	   * No modifico esta función porque me has pedido dejarla igual.
	   */
	  public String obtenerRecurso(String resource, String apiKey, String cookie)
	          throws IOException {

	      String url = String.format("%s%s?key=%s", BASE_URL, resource, apiKey);
	      HttpGet get = new HttpGet(url);
	      get.setHeader("Accept", "application/json");
	      get.setHeader("Cookie", cookie);

	      try (CloseableHttpResponse resp = HTTP.execute(get)) {
	          int code = resp.getCode();
	          if (code != 200) {
	              throw new IOException("GET " + resource + " failed: HTTP " + code);
	          }
	          try {
	              // Esta línea no se toca, como me pediste
	              return EntityUtils.toString(resp.getEntity(), StandardCharsets.UTF_8);
	          } catch (IOException | RuntimeException | ParseException e) {
	              throw new IOException(
	                      "Error parsing response body for resource `" + resource + "`", e
	              );
	          }
	      }
	  }

    
    /**
     * Enviar una petición PUT con cuerpo JSON al recurso REST.
     * 
     * @param recurso El endpoint relativo (sin /, p.ej: "alumnos/{dni}/asignaturas/{acronimo}")
     * @param jsonBody JSON string con los datos
     * @param apiKey Api key para autenticación
     * @param cookie Cookie de sesión JSESSIONID
     * @return true si el servidor responde con código 2xx
     * @throws IOException si hay fallo HTTP o red
     */
    /*public boolean enviarPut(String recurso, String jsonBody, String apiKey, String cookie) throws IOException {
        String url = String.format("%s%s?key=%s", BASE_URL, recurso, apiKey);
        HttpPut put = new HttpPut(url);
        put.setEntity(new StringEntity(jsonBody, StandardCharsets.UTF_8));
        put.setHeader("Content-Type", "application/json");
        put.setHeader("Cookie", cookie);

        try (CloseableHttpResponse response = CLIENTE_HTTP.execute(put)) {
            int code = response.getCode();
            if (code < 200 || code >= 300) {
                throw new IOException("PUT " + recurso + " falló: HTTP " + code);
            }
            return true;
        }
    }*/
}
