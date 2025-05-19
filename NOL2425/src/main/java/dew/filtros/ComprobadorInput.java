package dew.filtros;

import java.util.regex.Pattern;

/**
 * Helper de validación de entradas de formulario.
 * Todos los métodos devuelven true si la entrada es "segura" según las reglas,
 * false en caso contrario.
 */
public class ComprobadorInput{


    private static final Pattern DNI_PATTERN =
            Pattern.compile("^\\d{8}[A-Za-z]$");
    
    private static final Pattern SQL_INJECTION =
        Pattern.compile("('|\")|(--|;|/\\*|\\*/)|\\b(xp_)\\b",
                        Pattern.CASE_INSENSITIVE);
    
    private static final Pattern ALLOWED_CHARS =
            Pattern.compile("^[a-zA-Z0-9\\s\\.,\\-_]*$");
    
    private static final Pattern XSS =
        Pattern.compile("<[^>]*>", Pattern.CASE_INSENSITIVE);
 

    public static boolean isValidDni(String dni) {
        if (!isNotEmpty(dni)) {
            return false;
        }
        if (!DNI_PATTERN.matcher(dni).matches()) {
            return false;
        }
        return isClean(dni);
    }

    public static boolean isAllowedChars(String input) {
        if (input == null) return false;
        return ALLOWED_CHARS.matcher(input).matches();
    }

    public static boolean hasSqlRisk(String input) {
        if (input == null) return false;
        return SQL_INJECTION.matcher(input).find();
    }

    public static boolean hasXssRisk(String input) {
        if (input == null) return false;
        return XSS.matcher(input).find();
    }

    private static boolean isClean(String input) {
        return isAllowedChars(input)
            && !hasSqlRisk(input)
            && !hasXssRisk(input);
    }

    public static boolean isValidRequired(String input) {
        return isNotEmpty(input) && isClean(input);
    }

    public static boolean isValidOptional(String input) {
        if (input == null || input.trim().isEmpty()) {
            return true;
        }
        return isClean(input);
    }
    
    public static boolean isNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

  
}