<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, dew.clases.Alumno, dew.clases.Asignatura" %>
<%
    Alumno alumno = (Alumno) request.getAttribute("alumno");
    if (alumno == null) {
%>
    <h2 style="color:red;text-align:center;">No se pudo cargar el expediente del alumno.</h2>
    <% return; }
    List<Asignatura> asignaturas = alumno.getAsignaturas();

    double suma = 0;
    int contador = 0;
    for (Asignatura asig : asignaturas) {
        try {
            if (asig.getNota() != null && !asig.getNota().isEmpty()) {
                double nota = Double.parseDouble(asig.getNota());
                suma += nota;
                contador++;
            }
        } catch (Exception e) {
            // Nota no válida
        }
    }
    double media = (contador > 0) ? (suma / contador) : -1;
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Certificado Académico</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>
  <style>
    .certificado {
      max-width: 800px;
      margin: auto;
      padding: 2rem;
      border: 2px solid #333;
      background-color: #fff;
    }
    .foto-alumno {
      width: 120px;
      height: 160px;
      object-fit: cover;
      border: 1px solid #ccc;
    }
    @media print {
      .no-print {
        display: none;
      }
    }
    @page {
      margin: 10mm;
    }
  </style>
</head>
<body class="bg-light">

  <div class="certificado">
    <div class="text-center mb-4">
      <h2>Universidad Politécnica de Valencia</h2>
      <h4>Certificado Académico</h4>
    </div>

    <div class="d-flex justify-content-between align-items-center mb-4">
      <div>
        <p><strong>Nombre:</strong> <%= alumno.getNombre() %></p>
        <p><strong>Apellidos:</strong> <%= alumno.getApellidos() %></p>
        <p><strong>DNI:</strong> <%= alumno.getDni() %></p>
      </div>
      <div>
        <img src="<%= request.getContextPath() %>/img/<%= alumno.getDni() %>.jpeg"
             alt="Foto del alumno"
             class="foto-alumno rounded"/>
      </div>
    </div>

    <h5 class="mb-3">Asignaturas Cursadas</h5>
    <table class="table table-bordered">
      <thead class="table-light">
        <tr>
          <th>Acrónimo</th>
          <th>Nombre</th>
          <th>Curso</th>
          <th>Cuatrimestre</th>
          <th>Créditos</th>
          <th>Nota</th>
        </tr>
      </thead>
      <tbody>
        <%
          if (asignaturas != null && !asignaturas.isEmpty()) {
            for (Asignatura asig : asignaturas) {
        %>
        <tr>
          <td><%= asig.getAcronimo() %></td>
          <td><%= asig.getNombre() %></td>
          <td><%= asig.getCurso() %></td>
          <td><%= asig.getCuatrimestre() %></td>
          <td><%= asig.getCreditos() %></td>
          <td><%= (asig.getNota() == null || asig.getNota().isEmpty()) ? "—" : asig.getNota() %></td>
        </tr>
        <% }
          } else { %>
        <tr>
          <td colspan="6" class="text-center">No hay asignaturas registradas</td>
        </tr>
        <% } %>
      </tbody>
    </table>

    <div class="mt-4">
      <p><strong>Nota media:</strong>
        <%= (media >= 0) ? String.format("%.2f", media) : "—" %>
      </p>
    </div>

    <div class="text-end mt-4 no-print">
      <button class="btn btn-primary" onclick="window.print()">
        <i class="bi bi-printer me-1"></i> Imprimir Certificado
      </button>
    </div>
    
    <div class="text-start mt-3 no-print">
	  <button class="btn btn-secondary" onclick="window.location.href='<%= request.getContextPath() %>/alumno'">
	    <i class="bi bi-arrow-left me-1"></i> Volver
	  </button>
	</div>
	
  </div>

</body>
</html>
