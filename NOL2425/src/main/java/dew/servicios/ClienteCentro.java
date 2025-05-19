package dew.servicios;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import dew.servicios.AuthResult;

/**
 * Cliente para interactuar con la API REST del Centro Educativo.
 * Realiza peticiones GET y POST para obtener datos y autenticar usuarios.
 */
public class ClienteCentro {

    // URL base para las peticiones al servidor
    private static final String BASE_URL = "http://localhost:9090/CentroEducativo/";

    // Instancia única del cliente HTTP
    private static final CloseableHttpClient CLIENTE_HTTP = HttpClients.createDefault();

    // Constructor privado para prevenir la creación de instancias
    private ClienteCentro() {}

    /**
     * Método para obtener la instancia única del cliente.
     * 
     * @return la única instancia de ClienteCentro
     */
    public static ClienteCentro obtenerInstancia() {
        return SingletonHolder.INSTANCE;
    }

    // Clase interna para el patrón Singleton
    private static class SingletonHolder {
        private static final ClienteCentro INSTANCE = new ClienteCentro();
    }

    /**
     * Método para autenticar a un usuario mediante POST al endpoint /login.
     * Devuelve un objeto AuthResult con apiKey y sessionCookie.
     *
     * @param dni  El DNI del usuario
     * @param pass La contraseña del usuario
     * @return AuthResult con apiKey y sessionCookie
     * @throws IOException Si ocurre un error al realizar la petición
     */
    public AuthResult autenticarUsuario(String dni, String pass) throws IOException {
        // Construir la solicitud POST para login
        HttpPost solicitudPost = new HttpPost(BASE_URL + "login");
        solicitudPost.setEntity(new StringEntity(
            String.format("{\"dni\":\"%s\",\"password\":\"%s\"}", dni, pass),
            StandardCharsets.UTF_8));
        solicitudPost.setHeader("Content-Type", "application/json");

        try (CloseableHttpResponse respuesta = CLIENTE_HTTP.execute(solicitudPost)) {
            int codigoRespuesta = respuesta.getCode();
            if (codigoRespuesta != 200) {
                throw new IOException("Error en login: HTTP " + codigoRespuesta);
            }

            // Obtener la apiKey del cuerpo de la respuesta
            String apiKey;
            try {
                apiKey = EntityUtils.toString(respuesta.getEntity(), StandardCharsets.UTF_8).trim();
            } catch (Exception e) {
                apiKey = null;
            }

            // Obtener la cookie de sesión JSESSIONID
            String sessionCookie = null;
            for (Header cabecera : respuesta.getHeaders("Set-Cookie")) {
                String valor = cabecera.getValue();
                if (valor.startsWith("JSESSIONID=")) {
                    sessionCookie = valor.split(";", 2)[0];
                    break;
                }
            }

            if (sessionCookie == null) {
                throw new IOException("No se encontró cookie JSESSIONID en la respuesta");
            }

            // Retornar el resultado de autenticación
            return new AuthResult(apiKey, sessionCookie);
        }
    }

    /**
     * Método GET general para obtener cualquier recurso REST.
     * Utiliza la apiKey y sessionCookie para la autenticación.
     *
     * @param recurso   El endpoint al que se quiere acceder
     * @param apiKey    La apiKey del usuario
     * @param cookie    La cookie de sesión
     * @return El contenido JSON del recurso solicitado
     * @throws IOException Si ocurre un error al realizar la petición
     */
    public String obtenerRecurso(String recurso, String apiKey, String cookie) throws IOException {
        String url = String.format("%s%s?key=%s", BASE_URL, recurso, apiKey);
        HttpGet solicitudGet = new HttpGet(url);
        solicitudGet.setHeader("Accept", "application/json");
        solicitudGet.setHeader("Cookie", cookie);

        try (CloseableHttpResponse respuesta = CLIENTE_HTTP.execute(solicitudGet)) {
            int codigoRespuesta = respuesta.getCode();
            if (codigoRespuesta != 200) {
                throw new IOException("GET " + recurso + " falló: HTTP " + codigoRespuesta);
            }

            try {
                // Retornar el cuerpo de la respuesta como cadena JSON
                return EntityUtils.toString(respuesta.getEntity(), StandardCharsets.UTF_8);
            } catch (Exception e) {
                return "";
            }
        }
    }
}
