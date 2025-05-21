<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, dew.clases.Alumno, dew.clases.Asignatura" %>
<!DOCTYPE html>
<html>
<head>	
  <meta charset="UTF-8">
  <title>Ficha del Alumno</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>
  <link href="<%= request.getContextPath() %>/css/estilos.css" rel="stylesheet" type="text/css">
</head>
<body>
  <div class="container py-5">
    <header class="text-center mb-5 rounded shadow-sm">
      <div class="bg-primary text-white py-4 rounded">
        <h1 class="display-5">
          <i class="fas fa-user-graduate me-3"></i>Ficha del Alumno
        </h1>
      </div>
    </header>

    <main class="pb-5">
      <div class="row justify-content-center">
        <div class="col-lg-8">
          <div class="card shadow-sm">
            <div class="card-body p-4">
              <%
                Alumno alumno = (Alumno) request.getAttribute("alumno");
                if (alumno == null) {
              %>
                <div class="alert alert-danger" role="alert">
                  <i class="fas fa-exclamation-triangle me-2"></i>
                  Error: no se pudo cargar la ficha del alumno.
                </div>
              <%
                return;
                }
              %>

              <section class="datos-personales mb-5">
                <h3 class="h5 text-primary mb-4">
                  <i class="fas fa-id-card me-2"></i>Datos Personales
                </h3>
                <div class="row g-3">
                  <div class="col-md-4">
                    <div class="p-3 border rounded bg-light">
                      <p class="mb-2"><strong>Nombre</strong></p>
                      <p class="mb-0 text-primary">${alumno.nombre}</p>
                    </div>
                  </div>
                  <div class="col-md-5">
                    <div class="p-3 border rounded bg-light">
                      <p class="mb-2"><strong>Apellidos</strong></p>
                      <p class="mb-0 text-primary">${alumno.apellidos}</p>
                    </div>
                  </div>
                  <div class="col-md-3">
                    <div class="p-3 border rounded bg-light">
                      <p class="mb-2"><strong>DNI</strong></p>
                      <p class="mb-0 text-primary">${alumno.dni}</p>
                    </div>
                  </div>
                </div>
              </section>

              <section class="asignaturas">
                <h3 class="h5 text-success mb-4">
                  <i class="fas fa-book me-2"></i>Asignaturas Matriculadas
                </h3>
                <div class="table-responsive">
                  <table class="table table-hover align-middle">
                    <thead class="table-primary">
                      <tr>
                        <th>Asignatura</th>
                        <th>Nota</th>
                        <th class="text-center">Acciones</th>
                      </tr>
                    </thead>
                    <tbody>
                      <%
                        List<Asignatura> lista = alumno.getAsignaturas();
                        if (lista != null && !lista.isEmpty()) {
                          for (Asignatura asig : lista) {
                      %>
                      <tr>
                        <td class="fw-medium"><%= asig.getCodigo() %></td>
                        <td>
                          <%
                            String nota = asig.getNota();
                            if (nota == null || nota.isEmpty()) {
                          %>
                            <span class="text-muted">—</span>
                          <%
                            } else {
                              double notaNum = Double.parseDouble(nota);
                              String badgeClass = notaNum >= 5 ? "bg-success" : "bg-danger";
                          %>
                            <span class="badge <%= badgeClass %>"><%= nota %></span>
                          <%
                            }
                          %>
                        </td>
                        <td class="text-center">
                          <a class="btn btn-primary btn-sm" 
                             href="<%= request.getContextPath() %>/asignatura?codigo=<%= asig.getCodigo() %>">
                            <i class="fas fa-eye me-1"></i>Ver detalles
                          </a>
                        </td>
                      </tr>
                      <%
                          }
                        } else {
                      %>
                      <tr>
                        <td colspan="3" class="text-center text-muted py-4">
                          <i class="fas fa-info-circle me-2"></i>No hay asignaturas matriculadas
                        </td>
                      </tr>
                      <% } %>
                    </tbody>
                  </table>
                </div>
              </section>
            </div>
          </div>

          <div class="text-center mt-4">
            <a href="<%= request.getContextPath() %>/" class="btn btn-outline-primary">
              <i class="fas fa-arrow-left me-2"></i>Volver al inicio
            </a>
          </div>
        </div>
      </div>
    </main>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/ficha_alumno.js"></script>
</body>
</html>