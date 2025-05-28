<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Asignaturas - Profesor</title>
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
            <h2 class="text-center">Mis Asignaturas</h2>
            
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>Código</th>
                            <th>Asignatura</th>
                            <th>Acrónimo</th>
                            <th>Curso</th>
                            <th>Cuatrimestre</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>11545</td>
                            <td>Desarrollo Web</td>
                            <td>DEW</td>
                            <td>2</td>
                            <td>B</td>
                            <td>
                                <a href="#" class="btn btn-outline-primary btn-sm">
                                    <i class="bi bi-people-fill me-1"></i>Ver Alumnos
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td>11544</td>
                            <td>Tecnología de Sistemas de Información en la Red</td>
                            <td>TSR</td>
                            <td>2</td>
                            <td>B</td>
                            <td>
                                <a href="#" class="btn btn-outline-primary btn-sm">
                                    <i class="bi bi-people-fill me-1"></i>Ver Alumnos
                                </a>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            
            <div class="text-center mt-4">
                <a href="login.jsp" class="btn btn-outline-secondary">
                    <i class="bi bi-box-arrow-left me-1"></i>Cerrar Sesión
                </a>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 