<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Error de Acceso - Notas OnLine</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/css/estilos.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container d-flex align-items-center justify-content-center min-vh-100">
        <div class="card shadow-lg border-0" style="max-width: 500px; width: 100%;">
            <div class="card-body p-5 text-center">
                <div class="text-danger mb-4">
                    <i class="bi bi-exclamation-circle-fill" style="font-size: 3rem;"></i>
                </div>
                
                <h3 class="card-title text-danger mb-4">Error de Acceso</h3>
                
                <div class="alert alert-light border mb-4">
                    <p class="card-text mb-1">Algo salió mal durante el proceso de autenticación.</p>
                    <p class="card-text text-muted small">Verifica tus credenciales e inténtalo de nuevo.</p>
                </div>
                
                <div class="d-grid gap-2">
                    <a href="/NOL2425/alumno" class="btn btn-primary btn-lg">
                        <i class="bi bi-arrow-return-left me-2"></i>Volver al login
                    </a>
                    <a href="<%= request.getContextPath() %>/" class="btn btn-outline-secondary">
                        <i class="bi bi-house-door me-2"></i>Ir al inicio
                    </a>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>