<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="dew.clases.Alumno, dew.clases.Asignatura, java.util.List" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <% 
        Asignatura asignatura = (Asignatura) request.getAttribute("asignatura");
        String tituloPagina = "Detalles de Asignatura";
        if (asignatura != null && asignatura.getNombre() != null) {
            tituloPagina = "Alumnos de " + asignatura.getNombre() + " (" + (asignatura.getAcronimo() != null ? asignatura.getAcronimo() : "N/A") + ")";
        } else if (request.getParameter("acronimo") != null) { // Fallback si el objeto asignatura no viene completo
            tituloPagina = "Alumnos de " + request.getParameter("acronimo");
        }
    %>
    <title><%= tituloPagina %> - Notas OnLine</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/estilos.css" rel="stylesheet">
</head>
<body>
    <%
        @SuppressWarnings("unchecked") 
        List<Alumno> alumnos = (List<Alumno>) request.getAttribute("alumnosDeAsignatura");
        String errorMsg = (String) request.getAttribute("error");
    %>
    <div class="container py-4">
        <header class="mb-4 p-3 rounded bg-light shadow-sm">
            <div class="d-flex justify-content-between align-items-center">
                <h1 class="h3">
                    <i class="bi bi-card-list me-2"></i>
                    <%= tituloPagina %>
                </h1>
                <a href="${pageContext.request.contextPath}/profesor" class="btn btn-outline-secondary btn-sm">
                    <i class="bi bi-arrow-left me-1"></i>Volver al Perfil
                </a>
            </div>
        </header>

        <% if (errorMsg != null) { %>
            <div class="alert alert-danger text-center" role="alert">
                <h4>Error</h4>
                <p><%= errorMsg %></p>
            </div>
        <% } else if (alumnos == null || alumnos.isEmpty()) { %>
            <div class="alert alert-info text-center" role="alert">
                No hay alumnos matriculados en esta asignatura o no se pudieron cargar los datos.
            </div>
        <% } else { %>
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white">
                    <h5 class="mb-0">Listado de Alumnos y Calificaciones</h5>
                </div>
                <div class="table-responsive">
                    <table class="table table-striped table-hover mb-0">
                        <thead>
                            <tr>
                                <th scope="col">DNI</th>
                                <th scope="col">Apellidos</th>
                                <th scope="col">Nombre</th>
                                <th scope="col">Nota</th>
                                <th scope="col" class="text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Alumno alumno : alumnos) { 
                                String notaAlumno = "Sin calificar";
                                // La nota está en la primera (y única) asignatura del objeto Alumno
                                // que ServicioProfesor.obtenerAlumnosConNotaPorAsignatura preparó.
                                if (alumno.getAsignaturas() != null && !alumno.getAsignaturas().isEmpty()) {
                                    Asignatura asigDelAlumno = alumno.getAsignaturas().get(0);
                                    if (asigDelAlumno.getNota() != null && !asigDelAlumno.getNota().trim().isEmpty()) {
                                        notaAlumno = asigDelAlumno.getNota();
                                    }
                                }
                            %>
                            <tr>
                                <td><%= alumno.getDni() != null ? alumno.getDni() : "N/A" %></td>
                                <td><%= alumno.getApellidos() != null ? alumno.getApellidos() : "N/A" %></td>
                                <td><%= alumno.getNombre() != null ? alumno.getNombre() : "N/A" %></td>
                                <td><%= notaAlumno %></td>
                                <td class="text-center">
                                    <button class="btn btn-sm btn-outline-success" 
                                            onclick="editarNota('<%= alumno.getDni() %>', '<%= asignatura != null ? asignatura.getAcronimo() : "" %>', '<%= notaAlumno %>')">
                                        <i class="bi bi-pencil-square me-1"></i>Editar Nota
                                    </button>
                                </td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        <% } %>
        <footer class="mt-4 pt-3 text-center text-muted border-top">
            <p>&copy; 2024-2025 Notas OnLine</p>
        </footer>
    </div>

    <script>
        function editarNota(dniAlumno, acronimoAsignatura, notaActual) {
            let nuevaNota = prompt("Introduce la nueva nota para el alumno " + dniAlumno + " en " + acronimoAsignatura + ":", notaActual.startsWith("Sin calificar") ? "" : notaActual);
            if (nuevaNota !== null && nuevaNota.trim() !== "") {
                alert("Funcionalidad de guardar nota vía AJAX pendiente de implementar.\nSe intentaría guardar: Alumno " + dniAlumno + ", Asignatura " + acronimoAsignatura + ", Nueva Nota: " + nuevaNota);
                // Aquí harías la llamada AJAX al servidor (PUT /alumnos/{dni}/asignaturas/{acronimo})
                // y luego actualizarías la tabla o la recargarías.
            }
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
