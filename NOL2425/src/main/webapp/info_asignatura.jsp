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
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
  <!-- Banner superior con título -->
  <div class="banner-container mb-4">
    <div class="container">
      <div class="d-flex align-items-center justify-content-center py-4">
        <i class="fas fa-book-open me-3 fs-2"></i>
        <h1 class="titulo mb-0">Información de Asignatura</h1>
      </div>
    </div>
  </div>

  <div class="container">
    <div class="panel">
      <!-- Nombre de la asignatura -->
      <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="mb-0">${asignatura.nombre}</h2>
        <span class="badge badge-gradient">${asignatura.acronimo}</span>
      </div>

      <!-- Profesor -->
      <div class="info-block">
        <h3 class="mb-4">
          <i class="fas fa-chalkboard-teacher me-2 text-primary"></i>
          Profesor
        </h3>
        <div class="datos-field">
          <div class="datos-value">Nombre del Profesor</div>
        </div>
      </div>

      <!-- Detalles de la Asignatura -->
      <div class="info-block">
        <h3 class="mb-4">
          <i class="fas fa-circle-info me-2 text-success"></i>
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

      <!-- Botones de navegación -->
      <div class="d-flex justify-content-between mt-4">
        <button class="btn btn-outline-primary" onclick="history.back()">
          <i class="fas fa-arrow-left me-2"></i>Volver
        </button>
        <a href="/NOL2425" class="btn btn-outline-secondary">
          <i class="fas fa-home me-2"></i>Inicio
        </a>
      </div>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
