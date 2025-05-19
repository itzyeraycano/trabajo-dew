package dew.main;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@WebServlet("/LoginCentroEducativo")
public class Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Servlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener parámetros del formulario
        String dni = request.getParameter("dni");
        String password = request.getParameter("password");

        if (dni == null || password == null || dni.isBlank() || password.isBlank()) {
            request.setAttribute("error", "Debe proporcionar dni y contraseña.");
            request.getRequestDispatcher("/registro.jsp").forward(request, response);
            return;
        }

        // Crear JSON para enviar
        Gson gson = new Gson();
        JsonObject jsonLogin = new JsonObject();
        jsonLogin.addProperty("dni", dni);
        jsonLogin.addProperty("password", password);
        String jsonInput = gson.toJson(jsonLogin);

        try {
            // Conexión a CentroEducativo
            URL url = new URL("http://localhost:9090/CentroEducativo/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Enviar JSON
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Leer respuesta
            int status = conn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8);
                String jsonResponse = scanner.useDelimiter("\\A").next();
                scanner.close();

                JsonObject respuestaJson = JsonParser.parseString(jsonResponse).getAsJsonObject();
                String key = respuestaJson.get("key").getAsString();

                // Guardar en sesión
                HttpSession sesion = request.getSession();
                sesion.setAttribute("dni", dni);
                sesion.setAttribute("key", key);

                // Redirigir
                response.sendRedirect("ficha_alumno.html");
            } else {
                request.setAttribute("error", "Credenciales incorrectas o error del servidor.");
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error conectando a CentroEducativo.");
            request.getRequestDispatcher("/registro.jsp").forward(request, response);
        }
    }
}
