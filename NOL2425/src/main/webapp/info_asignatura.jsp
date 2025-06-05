<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Detalles de Asignatura</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
  <link rel="stylesheet"
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
  <link rel="stylesheet"
        href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body data-context-path="${pageContext.request.contextPath}" data-asignatura="${asignatura.acronimo}">
  <div class="container py-4">
    <header class="text-center mb-5 rounded shadow-sm">
      <div class="bg-primary text-white py-4 rounded">
        <h1 class="display-5">
          <i class="bi bi-journal-bookmark-fill me-3"></i>Información de Asignatura
        </h1>
      </div>
    </header>

    <div class="panel">
      <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="mb-0">${asignatura.nombre}</h2>
        <span class="badge bg-secondary">${asignatura.acronimo}</span>
      </div>

      <div class="info-block">
        <h3 class="mb-4">
          <i class="bi bi-person-workspace me-2 text-primary"></i>
          Profesor
        </h3>
        <div class="datos-field">
          <div class="datos-value">Nombre del Profesor</div>
        </div>
      </div>

      <div class="info-block">
        <h3 class="mb-4">
          <i class="bi bi-info-circle me-2 text-success"></i>
          Detalles de la Asignatura
        </h3>
        <div class="row g-4">
          <div class="col-md-4">
            <div class="detail-card">
              <label class="detail-label">Curso</label>
              <div class="detail-value">${asignatura.curso}</div>
            </div>
          </div>
          <div class="col-md-4">
            <div class="detail-card">
              <label class="detail-label">Cuatrimestre</label>
              <div class="detail-value">${asignatura.cuatrimestre}</div>
            </div>
          </div>
          <div class="col-md-4">
            <div class="detail-card">
              <label class="detail-label">Créditos</label>
              <div class="detail-value">${asignatura.creditos}</div>
            </div>
          </div>
        </div>
      </div>

      <div class="info-block mt-5">
        <h3 class="mb-4">
          <i class="bi bi-people-fill me-2 text-info"></i>
          Alumnos matriculados
        </h3>

        <table id="tablaAlumnos" class="table table-striped">
          <thead>
			  <tr>
			    <th>Foto</th>
			    <th>Nombre</th>
			    <th>Apellidos</th>
			    <th>DNI</th>
			    <th>Nota</th>
			  </tr>
		  </thead>
          <tbody id="tbodyAlumnos">
            <!-- Se rellena con JavaScript -->
          </tbody>
        </table>
      </div>

      <div class="d-flex justify-content-between mt-4">
        <button class="btn btn-outline-primary" onclick="history.back()">
          <i class="bi bi-arrow-left me-2"></i>Volver
        </button>
        <a href="/NOL2425" class="btn btn-outline-secondary">
          <i class="bi bi-house me-2"></i>Inicio
        </a>
      </div>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

  <!-- Inyectar JSON en un bloque script de tipo application/json -->
  <script id="alumnos-data" type="application/json">
    ${alumnosJson}
  </script>

  <!-- Script externo -->
  <script src="${pageContext.request.contextPath}/js/alumnos_asignatura.js"></script>
</body>
</html>
