package dew.servicios;

/** Resultado de la peticion en el login: apiKey + SesionCookie (JSESSIONID=...).*/
public class AuthResult {
    private final String apiKey;
    private final String sessionCookie;

    public AuthResult(String apiKey, String sessionCookie) {
        this.apiKey        = apiKey;
        this.sessionCookie = sessionCookie;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getSessionCookie() {
        return sessionCookie;
    }
}