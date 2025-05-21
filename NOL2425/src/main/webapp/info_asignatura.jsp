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
  <div class="container col-lg-6 col-md-8 col-sm-12">
    <main class="text-center">
      <h2 class="mb-4"><i class="fas fa-book icono me-2"></i>Información de la Asignatura</h2>

      <div class="asignatura-panel">
        <div class="d-flex justify-content-between align-items-center mb-4">
          <h3 class="text-start mb-0">${asignatura.nombre}</h3>
          <span class="badge badge-blue">${asignatura.acronimo}</span>
        </div>

        <div class="info-block text-start">
          <h4><i class="fas fa-chalkboard-teacher me-2"></i>Profesor responsable</h4>
          <p>Nombre del Profesor</p>
        </div>

        <div class="info-block text-start">
          <h4><i class="fas fa-circle-info me-2"></i>Datos generales</h4>
          <ul class="list-unstyled">
            <li><strong>Curso:</strong> ${asignatura.curso}</li>
            <li><strong>Cuatrimestre:</strong> ${asignatura.cuatrimestre}</li>
            <li><strong>Créditos:</strong> ${asignatura.creditos}</li>
          </ul>
        </div>

        <div class="text-end mt-4">
          <button class="btn btn-volver" onclick="history.back()">
            <i class="fas fa-arrow-left me-2"></i>Volver
          </button>
        </div>
      </div>
    </main>
  </div>


  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
