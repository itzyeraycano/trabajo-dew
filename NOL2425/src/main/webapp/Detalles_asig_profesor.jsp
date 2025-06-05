<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="dew.clases.Alumno, dew.clases.Asignatura, java.util.List" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <% 
        Asignatura asignatura = (Asignatura) request.getAttribute("asignatura");
        String tituloPagina = "Detalles de Asignatura";
        String acronimoActual = "";
        if (asignatura != null && asignatura.getNombre() != null) {
            tituloPagina = "Alumnos de " + asignatura.getNombre() + " (" + (asignatura.getAcronimo() != null ? asignatura.getAcronimo() : "N/A") + ")";
            acronimoActual = asignatura.getAcronimo() != null ? asignatura.getAcronimo() : "";
        } else if (request.getParameter("acronimo") != null) { 
            tituloPagina = "Alumnos de " + request.getParameter("acronimo");
            acronimoActual = request.getParameter("acronimo");
        }
    %>
    <title><%= tituloPagina %> - Notas OnLine</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/estilos.css" rel="stylesheet">
    <%-- Necesario para peticiones POST desde AJAX si usas Spring Security con CSRF --%>
    <%-- <meta name="${_csrf.parameterName}" content="${_csrf.token}"/> --%>
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
                <h1 class="h3"><i class="bi bi-card-list me-2"></i><%= tituloPagina %></h1>
                <a href="${pageContext.request.contextPath}/profesor" class="btn btn-outline-secondary btn-sm">
                    <i class="bi bi-arrow-left me-1"></i>Volver al Perfil
                </a>
            </div>
        </header>

        <% if (errorMsg != null) { %>
            <div class="alert alert-danger text-center" role="alert"><h4>Error</h4><p><%= errorMsg %></p></div>
        <% } else if (alumnos == null || alumnos.isEmpty()) { %>
            <div class="alert alert-info text-center" role="alert">No hay alumnos matriculados en esta asignatura o no se pudieron cargar los datos.</div>
        <% } else { %>
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white"><h5 class="mb-0">Listado de Alumnos y Calificaciones</h5></div>
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
                            <tr id="fila-<%= alumno.getDni() %>-<%= acronimoActual %>">
                                <td><%= alumno.getDni() != null ? alumno.getDni() : "N/A" %></td>
                                <td><%= alumno.getApellidos() != null ? alumno.getApellidos() : "N/A" %></td>
                                <td><%= alumno.getNombre() != null ? alumno.getNombre() : "N/A" %></td>
                                <td id="nota-<%= alumno.getDni() %>-<%= acronimoActual %>"><%= notaAlumno %></td>
                                <td class="text-center">
                                    <button class="btn btn-sm btn-outline-success" 
                                            onclick="editarNota('<%= alumno.getDni() %>', '<%= acronimoActual %>', document.getElementById('nota-<%= alumno.getDni() %>-<%= acronimoActual %>'))">
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
        <footer class="mt-4 pt-3 text-center text-muted border-top"><p>&copy; 2024-2025 Notas OnLine</p></footer>
    </div>

    <script>
        function editarNota(dniAlumno, acronimoAsignatura, celdaNotaElement) {
            let notaActual = celdaNotaElement.textContent;
            // Si la nota es "Sin calificar" o "0.0" (por defecto de CE), mostrar el prompt vacío
            let promptDefault = (notaActual.startsWith("Sin calificar") || notaActual === "0.0") ? "" : notaActual;
            let nuevaNotaStr = prompt("Introduce la nueva nota para el alumno " + dniAlumno + " en " + acronimoAsignatura + ":", promptDefault);

            if (nuevaNotaStr !== null) { // Si el usuario no presiona Cancelar
                nuevaNotaStr = nuevaNotaStr.trim().replace(',', '.'); // Reemplazar coma por punto para decimales

                if (nuevaNotaStr === "") {
                    alert("La nota no puede estar vacía si se desea modificar.");
                    return;
                }

                // Validar que la nota sea un número entre 0 y 10, con un decimal opcional
                if (!/^\d(\.\d)?0?$/.test(nuevaNotaStr) && !/^(10(\.0)?)$/.test(nuevaNotaStr) || parseFloat(nuevaNotaStr) < 0 || parseFloat(nuevaNotaStr) > 10) {
                    alert("Por favor, introduce una nota válida (ej: 7, 7.5, 10). Entre 0 y 10, con un decimal como máximo.");
                    return;
                }

                const params = new URLSearchParams();
                params.append('action', 'modificarNota');
                params.append('dniAlumno', dniAlumno);
                params.append('acronimoAsignatura', acronimoAsignatura);
                params.append('nuevaNota', nuevaNotaStr);
                
                fetch('${pageContext.request.contextPath}/profesor', { // Llama a ProfesorServlet
                    method: 'POST', // Usamos POST para simplificar, el servlet lo manejará
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        // Si usas Spring Security con CSRF, necesitarías añadir el token:
                        // 'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf.token"]')?.getAttribute('content')
                    },
                    body: params
                })
                .then(response => {
                    // Siempre intentar parsear como JSON, incluso si hay error HTTP,
                    // porque el servlet debería devolver un JSON con el mensaje de error.
                    return response.json().then(data => ({ok: response.ok, status: response.status, data}));
                })
                .then(result => {
                    console.log('Respuesta del servidor:', result.data);
                    if (result.ok && result.data.success) {
                        alert(result.data.message || 'Nota actualizada correctamente.');
                        celdaNotaElement.textContent = nuevaNotaStr; // Actualizar la nota en la tabla
                    } else {
                        alert('Error al actualizar la nota: ' + (result.data.message || 'Error desconocido del servidor (status ' + result.status + ').'));
                    }
                })
                .catch((error) => {
                    console.error('Error en AJAX:', error);
                    alert('Error de conexión o respuesta no JSON al intentar actualizar la nota.');
                });
            }
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>