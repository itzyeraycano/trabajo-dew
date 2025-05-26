<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="dew.clases.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Alumnos - Profesor</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <link rel="stylesheet" href="css/estilos.css">
</head>
<body class="profesor">
    <div class="banner-container text-center">
        <h1 class="titulo mb-0">
            <i class="bi bi-person-workspace me-2"></i>Portal del Profesor
        </h1>
    </div>

    <div class="container">
        <div class="panel">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2 class="mb-0">
                    <% Asignatura asignatura = (Asignatura) request.getAttribute("asignatura"); %>
                    <%= asignatura != null ? asignatura.getNombre() : "Asignatura" %>
                </h2>
                <span class="badge-gradient">
                    <%= asignatura != null ? asignatura.getCodigo() : "" %>
                </span>
            </div>

            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>DNI</th>
                            <th>Nombre</th>
                            <th>Apellidos</th>
                            <th>Email</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                        List<Alumno> alumnos = (List<Alumno>) request.getAttribute("alumnos");
                        if (alumnos != null) {
                            for (Alumno alumno : alumnos) {
                        %>
                        <tr>
                            <td><%= alumno.getDni() %></td>
                            <td><%= alumno.getNombre() %></td>
                            <td><%= alumno.getApellidos() %></td>
                            <td><%= alumno.getEmail() %></td>
                            <td>
                                <a href="profesor?action=verFichaAlumno&asignatura=<%= asignatura.getCodigo() %>&alumno=<%= alumno.getDni() %>" 
                                   class="btn btn-outline-primary btn-sm">
                                    <i class="bi bi-person-vcard-fill me-1"></i>Ver Ficha
                                </a>
                            </td>
                        </tr>
                        <% 
                            }
                        }
                        %>
                    </tbody>
                </table>
            </div>

            <div class="text-center mt-4">
                <a href="profesor?action=listarAsignaturas" class="btn btn-outline-primary me-2">
                    <i class="bi bi-arrow-left me-1"></i>Volver a Asignaturas
                </a>
                <a href="login.jsp" class="btn btn-outline-secondary">
                    <i class="bi bi-box-arrow-left me-1"></i>Cerrar Sesión
                </a>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 