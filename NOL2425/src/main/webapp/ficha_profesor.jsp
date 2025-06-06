<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="dew.clases.Profesor, dew.clases.Asignatura, java.util.List" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Ficha del Profesor - Notas OnLine</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/estilos.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; }
        .card-header { background-color: #007bff; color: white; }
        .list-group-item-action:hover { background-color: #e9ecef; }
    </style>
</head>
<body>
    <%
        Profesor profesor = (Profesor) request.getAttribute("profesor");
        String errorMsg = (String) request.getAttribute("error");

        if (errorMsg != null) {
    %>
        <div class="container mt-5">
            <div class="alert alert-danger text-center" role="alert">
                <h4>Error al Cargar Datos</h4>
                <p><%= errorMsg %></p>
                <a href="${pageContext.request.contextPath}/" class="btn btn-primary mt-2">Volver al Inicio</a>
            </div>
        </div>
    <%
        } else if (profesor == null) {
    %>
        <div class="container mt-5">
            <div class="alert alert-warning text-center" role="alert">
                No se pudo cargar la información del profesor.
                <a href="${pageContext.request.contextPath}/" class="btn btn-secondary mt-2">Volver al Inicio</a>
            </div>
        </div>
    <%
        } else {
            List<Asignatura> asignaturas = profesor.getAsignaturas();
    %>
    <div class="container py-4">
        <header class="mb-4 p-3 rounded bg-white shadow-sm">
            <div class="d-flex justify-content-between align-items-center">
                <h1 class="h3"><i class="bi bi-person-workspace me-2"></i>Perfil del Profesor</h1>
                <a href="${pageContext.request.contextPath}/profesor?action=cierra" class="btn btn-outline-danger btn-sm">
                    <i class="bi bi-box-arrow-right me-1"></i>Cerrar Sesión
                </a>
            </div>
        </header>

        <div class="card mb-4 shadow-sm">
            <div class="card-header">
                <h5 class="mb-0">Datos Personales</h5>
            </div>
            <div class="card-body">
                <p><strong>DNI:</strong> <%= profesor.getDni() != null ? profesor.getDni() : "N/A" %></p>
                <p><strong>Nombre:</strong> <%= profesor.getNombre() != null ? profesor.getNombre() : "N/A" %></p>
                <p><strong>Apellidos:</strong> <%= profesor.getApellidos() != null ? profesor.getApellidos() : "N/A" %></p>
            </div>
        </div>

        <div class="card shadow-sm">
            <div class="card-header">
                <h5 class="mb-0">Asignaturas que Imparte</h5>
            </div>
            <div class="card-body">
                <% if (asignaturas != null && !asignaturas.isEmpty()) { %>
                    <ul class="list-group">
                        <% for (Asignatura asignatura : asignaturas) { %>
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                <div>
                                    <strong><%= asignatura.getAcronimo() != null ? asignatura.getAcronimo() : "N/A" %></strong> 
                                    - <%= asignatura.getNombre() != null ? asignatura.getNombre() : "Nombre no disponible" %>
                                    <br>
                                    <small class="text-muted">
                                        Curso: <% if(asignatura.getCurso() != 0) { out.print(asignatura.getCurso()); } else { out.print("N/A"); } %> | 
                                        Cuatrimestre: <%= asignatura.getCuatrimestre() != null ? asignatura.getCuatrimestre() : "N/A" %>
                                    </small>
                                </div>
                                <a href="${pageContext.request.contextPath}/profesor?action=verAlumnosDeAsignatura&acronimo=<%= asignatura.getAcronimo() %>" 
                                   class="btn btn-sm btn-outline-primary">
                                   Ver Alumnos <i class="bi bi-chevron-right"></i>
                                </a>
                            </li>
                        <% } %>
                    </ul>
                <% } else { %>
                    <p class="text-muted">Este profesor no tiene asignaturas asignadas actualmente.</p>
                <% } %>
            </div>
        </div>

        <footer class="mt-4 pt-3 text-center text-muted border-top">
            <p>&copy; 2024-2025 Notas OnLine</p>
        </footer>
    </div>
    <% } %>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
