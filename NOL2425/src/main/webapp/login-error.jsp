<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro / Login Alumno</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%= request.getContextPath() %>/css/estilos.css" rel="stylesheet">
</head>
<body class="bg-light">

    <div class="container d-flex align-items-center justify-content-center min-vh-100">
        <div class="card shadow-sm p-4" style="max-width: 500px; width: 100%;">
            <div class="card-body text-center">
                <h3 class="card-title text-danger mb-4">Error de acceso</h3>
                <p class="card-text">Algo salió mal durante el proceso de autenticación.</p>
                <p class="card-text">Haz clic en el siguiente botón para volver a intentarlo.</p>
                
                <a href="ficha_alumno.jsp" class="btn btn-primary mt-3">Volver al login</a>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>