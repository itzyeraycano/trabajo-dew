package dew.main;

import java.io.*;
import java.time.LocalDateTime;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class Logs implements Filter {
    private static final String logFilePath = "/home/dew/git/trabajo-dew/NOL2425/src/main/webapp/logs/nol2425.log";

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println(">> Filtro Logs inicializado");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {
            HttpServletRequest http = (HttpServletRequest) request;

            String user = http.getRemoteUser();
            if (user == null) user = "-";

            String logLine = LocalDateTime.now() + " " +
                             user + " " +
                             http.getRemoteAddr() + " " +
                             http.getRequestURI() + " " +
                             http.getMethod() + "\n";

            File logFile = new File(logFilePath);
            logFile.getParentFile().mkdirs();
            if (!logFile.exists()) logFile.createNewFile();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
                writer.write(logLine);
                writer.flush();
                System.out.println(">> Log guardado: " + logLine.trim());
            } catch (IOException e) {
                System.err.println(">> ERROR escribiendo log:");
                e.printStackTrace();
            }
        }

        chain.doFilter(request, response); // Continuar con la petición
    }

    @Override
    public void destroy() {}
}

