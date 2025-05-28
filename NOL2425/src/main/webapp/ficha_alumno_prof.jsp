<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ficha de Alumno - Profesor</title>
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
            <div class="row">
                <div class="col-md-4">
                    <div class="text-center mb-4">
                        <img src="img/student-avatar.png" alt="Foto del alumno" class="student-photo mb-3">
                        <h3 class="student-header">Juan Pérez García</h3>
                        <span class="badge-gradient">12345678A</span>
                    </div>

                    <div class="info-block">
                        <h3 class="mb-4">Información Personal</h3>
                        <div class="datos-field">
                            <label>Email</label>
                            <div class="datos-value">juan.perez@example.com</div>
                        </div>
                        <div class="datos-field">
                            <label>Teléfono</label>
                            <div class="datos-value">666123456</div>
                        </div>
                        <div class="datos-field">
                            <label>Dirección</label>
                            <div class="datos-value">Calle Mayor 123, Valencia</div>
                        </div>
                    </div>
                </div>

                <div class="col-md-8">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h2 class="mb-0">Desarrollo Web</h2>
                        <span class="badge-gradient">11545</span>
                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <div class="detail-card">
                                <span class="detail-label">Curso</span>
                                <div class="detail-value">2º</div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="detail-card">
                                <span class="detail-label">Cuatrimestre</span>
                                <div class="detail-value">B</div>
                            </div>
                        </div>
                    </div>

                    <div class="info-block mt-4">
                        <h3 class="mb-4">Calificaciones</h3>
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Evaluación</th>
                                        <th>Nota</th>
                                        <th>Estado</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>Práctica 1</td>
                                        <td>8.5</td>
                                        <td><span class="badge bg-success">Aprobado</span></td>
                                    </tr>
                                    <tr>
                                        <td>Práctica 2</td>
                                        <td>7.8</td>
                                        <td><span class="badge bg-success">Aprobado</span></td>
                                    </tr>
                                    <tr>
                                        <td>Examen Final</td>
                                        <td>9.0</td>
                                        <td><span class="badge bg-success">Aprobado</span></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <div class="text-center mt-4">
                <a href="#" class="btn btn-outline-primary me-2">
                    <i class="bi bi-arrow-left me-1"></i>Volver a Lista de Alumnos
                </a>
                <a href="#" class="btn btn-outline-primary me-2">
                    <i class="bi bi-book me-1"></i>Mis Asignaturas
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