package dew.filtros;

import java.util.regex.Pattern;

/**
 * Clase de utilidad para validar entradas de formularios.
 * Verifica si los datos introducidos son "seguros" según diversas reglas.
 */
public class ValidadorInput {

    private static final Pattern PATTERN_DNI =
            Pattern.compile("^\\d{8}[A-Za-z]$");

    private static final Pattern PATRON_INYECCION_SQL =
        Pattern.compile("('|\")|(--|;|/\\*|\\*/)|\\b(xp_)\\b", Pattern.CASE_INSENSITIVE);

    private static final Pattern CARACTERES_PERMITIDOS =
            Pattern.compile("^[a-zA-Z0-9\\s\\.,\\-_]*$");

    private static final Pattern PATRON_XSS =
        Pattern.compile("<[^>]*>", Pattern.CASE_INSENSITIVE);

    /**
     * Valida si un DNI es correcto según el formato esperado y no contiene caracteres peligrosos.
     */
    public static boolean esDniValido(String dni) {
        if (!esNoVacio(dni)) {
            return false;
        }
        if (!PATTERN_DNI.matcher(dni).matches()) {
            return false;
        }
        return esLimpio(dni);
    }

    /**
     * Valida si la entrada solo contiene caracteres permitidos (alfanuméricos, espacios y algunos símbolos).
     */
    public static boolean tieneCaracteresPermitidos(String entrada) {
        if (entrada == null) return false;
        return CARACTERES_PERMITIDOS.matcher(entrada).matches();
    }

    /**
     * Verifica si la entrada contiene patrones de posible inyección SQL.
     */
    public static boolean tieneRiesgoSql(String entrada) {
        if (entrada == null) return false;
        return PATRON_INYECCION_SQL.matcher(entrada).find();
    }

    /**
     * Verifica si la entrada contiene posibles intentos de Cross-Site Scripting (XSS).
     */
    public static boolean tieneRiesgoXss(String entrada) {
        if (entrada == null) return false;
        return PATRON_XSS.matcher(entrada).find();
    }

    /**
     * Comprueba si la entrada está limpia, es decir, sin caracteres peligrosos ni riesgos de inyección.
     */
    private static boolean esLimpio(String entrada) {
        return tieneCaracteresPermitidos(entrada)
            && !tieneRiesgoSql(entrada)
            && !tieneRiesgoXss(entrada);
    }

    /**
     * Verifica si la entrada no está vacía y es limpia.
     */
    public static boolean esValido(String entrada) {
        return esNoVacio(entrada) && esLimpio(entrada);
    }

    /**
     * Verifica si la entrada es válida cuando es opcional (puede estar vacía, pero si no lo está, debe ser limpia).
     */
    public static boolean esValidoOpcional(String entrada) {
        if (entrada == null || entrada.trim().isEmpty()) {
            return true;
        }
        return esLimpio(entrada);
    }

    /**
     * Verifica si la entrada no está vacía.
     */
    public static boolean esNoVacio(String entrada) {
        return entrada != null && !entrada.trim().isEmpty();
    }
}
