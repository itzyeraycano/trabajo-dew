<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, dew.clases.Alumno, dew.clases.Asignatura" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Ficha del Alumno</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
  <style>
    body {
      background-color: #f4f6f8;
    }
    .ficha-container {
      background: white;
      border-radius: 10px;
      padding: 30px;
      margin-top: 50px;
      box-shadow: 0 0 15px rgba(0,0,0,0.1);
    }
    .titulo {
      color: #0d6efd;
    }
    .datos-personales p {
      margin-bottom: 8px;
    }
  </style>
</head>
<body>
  <div class="container ficha-container">
    <h2 class="text-center titulo mb-4">Ficha del Alumno</h2>

    <%
      Alumno alumno = (Alumno) request.getAttribute("alumno");
      if (alumno == null) {
    %>
      <div class="alert alert-danger text-center">
        No se pudo cargar la información del alumno.
      </div>
    <%
      return;
      }
    %>

    <!-- DATOS DEL ALUMNO -->
    <div class="row mb-4">
      <div class="col-md-6">
        <p><i class="fas fa-user me-2"></i><strong>Nombre:</strong> ${alumno.nombre}</p>
        <p><i class="fas fa-user-tag me-2"></i><strong>Apellidos:</strong> ${alumno.apellidos}</p>
        <p><i class="fas fa-id-card me-2"></i><strong>DNI:</strong> ${alumno.dni}</p>
      </div>
      <div class="col-md-6 text-end">
        <img src="https://cdn-icons-png.flaticon.com/512/3135/3135715.png" alt="Avatar" 
             width="100" height="100" class="rounded-circle border"/> <!-- poner la foto aqui -->
      </div>
    </div>

    <!-- TABLA DE ASIGNATURAS -->
    <h4 class="mb-3"><i class="fas fa-book-open me-2"></i>Asignaturas Matriculadas</h4>
    <div class="table-responsive">
      <table class="table table-bordered table-striped align-middle">
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
            <td><%= asig.getAcronimo() %></td>
            <td>
              <%
                String nota = asig.getNota();
                out.print((nota == null || nota.isEmpty()) ? "—" : nota);
              %>
            </td>
            <td class="text-center">
              <a class="btn btn-outline-primary btn-sm"
                 href="<%= request.getContextPath() %>/asignatura?acronimo=<%= asig.getAcronimo() %>">
                <i class="fas fa-eye"></i> Ver
              </a>
            </td>
          </tr>
          <%
              }
            } else {
          %>
          <tr>
            <td colspan="3" class="text-center">No hay asignaturas matriculadas</td>
          </tr>
          <% } %>
        </tbody>
      </table>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/ficha_alumno.js"></script>
</body>
</html>
