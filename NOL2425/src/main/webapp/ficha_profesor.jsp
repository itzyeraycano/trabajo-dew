<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="dew.clases.Profesor, dew.clases.Asignatura, java.util.List" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Ficha del Profesor</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <%-- <link href="${pageContext.request.contextPath}/css/ficha_profesor.css" rel="stylesheet"> --%>
    <link href="${pageContext.request.contextPath}/css/estilos.css" rel="stylesheet"> <%-- Reutiliza estilos comunes si aplica --%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
</head>
<body>
    <%
        Profesor profesor = (Profesor) request.getAttribute("profesor");
        String errorMsg = (String) request.getAttribute("error");

        if (errorMsg != null) {
    %>
        <div class="container mt-5">
            <div class="alert alert-danger text-center" role="alert">
                <h4>Error</h4>
                <p><%= errorMsg %></p>
                <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary">Volver al Inicio</a>
            </div>
        </div>
    <%
        } else if (profesor == null) {
    %>
        <div class="container mt-5">
            <div class="alert alert-warning text-center" role="alert">
                No se pudo cargar la información del profesor. Por favor, intente iniciar sesión de nuevo.
                <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary mt-2">Volver al Inicio</a>
            </div>
        </div>
    <%
        } else {
            List<Asignatura> asignaturas = profesor.getAsignaturas();
    %>
    <div class="container mt-4">
        <header class="mb-4 p-3 rounded bg-light">
            <div class="d-flex justify-content-between align-items-center">
                <h1><i class="fas fa-chalkboard-teacher me-2"></i>Ficha del Profesor</h1>
                <a href="${pageContext.request.contextPath}/profesor?action=cierra" class="btn btn-outline-danger">
                    <i class="fas fa-sign-out-alt me-1"></i>Cerrar Sesión
                </a>
            </div>
        </header>

        <div class="card mb-4">
            <div class="card-header">
                <h4>Datos Personales</h4>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-8">
                        <p><strong><i class="fas fa-id-card me-2"></i>DNI:</strong> <%= profesor.getDni() %></p>
                        <p><strong><i class="fas fa-user me-2"></i>Nombre:</strong> <%= profesor.getNombre() %></p>
                        <p><strong><i class="fas fa-user-friends me-2"></i>Apellidos:</strong> <%= profesor.getApellidos() %></p>
                    </div>
                    <%-- Opcional: Foto del profesor --%>
                    <%-- 
                    <div class="col-md-4 text-center">
                        <img src="${pageContext.request.contextPath}/img/profesores/<%= profesor.getDni() %>.jpeg" 
                             alt="Foto de <%= profesor.getNombre() %> <%= profesor.getApellidos() %>" 
                             class="img-fluid rounded-circle border" style="max-width: 150px; max-height: 150px;">
                    </div>
                    --%>
                </div>
            </div>
        </div>

        <div class="card">
            <div class="card-header">
                <h4><i class="fas fa-book-reader me-2"></i>Asignaturas que Imparte</h4>
            </div>
            <div class="card-body">
                <% if (asignaturas != null && !asignaturas.isEmpty()) { %>
                    <div class="list-group">
                        <% for (Asignatura asignatura : asignaturas) { %>
                            <div class="list-group-item list-group-item-action">
                                <div class="d-flex w-100 justify-content-between">
                                    <h5 class="mb-1"><%= asignatura.getNombre() %> (<%= asignatura.getAcronimo() %>)</h5>
                                    <%-- Enlace para ver alumnos de esta asignatura --%>
                                    <a href="${pageContext.request.contextPath}/profesor/asignatura/<%= asignatura.getAcronimo() %>/alumnos" 
                                       class="btn btn-sm btn-outline-primary">
                                        <i class="fas fa-users me-1"></i>Ver Alumnos y Calificar
                                    </a>
                                </div>
                                <p class="mb-1">
                                    Curso: <%= asignatura.getCurso() %>, 
                                    Cuatrimestre: <%= asignatura.getCuatrimestre() %>, 
                                    Créditos: <%= asignatura.getCreditos() %>
                                </p>
                                <%-- Aquí podrías añadir más acciones, como calcular nota media si esa funcionalidad está en el servlet --%>
                            </div>
                        <% } %>
                    </div>
                <% } else { %>
                    <p class="text-muted">Este profesor no tiene asignaturas asignadas actualmente.</p>
                <% } %>
            </div>
        </div>

        <footer class="mt-5 pt-4 border-top text-center text-muted">
            <p>NOL2425 - Notas Online &copy; 2025</p>
        </footer>
    </div>
    <% } %>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>