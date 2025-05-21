<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Información de Asignatura</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
  <link href="<%= request.getContextPath() %>/css/estilos.css" rel="stylesheet" type="text/css">
</head>
<body>
  <div class="container py-5">
    <header class="text-center mb-5">
      <h1 class="display-5 text-primary">
        <i class="fas fa-book-open me-3"></i>Información de Asignatura
      </h1>
    </header>

    <main class="pb-5">
      <div class="row justify-content-center">
        <div class="col-lg-8">
          <div class="card shadow-sm">
            <div class="card-body p-4">
              <div class="d-flex justify-content-between align-items-center mb-4">
                <h2 id="nombreAsignatura" class="h3 text-primary mb-0">
                  ${asignatura.nombre}
                </h2>
                <span id="codigoAsignatura" class="badge bg-primary px-3 py-2">
                  ${asignatura.codigo}
                </span>
              </div>

              <div class="row g-4 mb-4">
                <!-- Profesor -->
                <div class="col-md-12">
                  <div class="card bg-light border-0">
                    <div class="card-body">
                      <h3 class="h5 text-primary mb-3">
                        <i class="fas fa-chalkboard-teacher me-2"></i>Profesor
                      </h3>
                      <p id="nombreProfesor" class="mb-0 ps-4">
                        Nombre del Profesor
                      </p>
                    </div>
                  </div>
                </div>

                <!-- Detalles -->
                <div class="col-md-12">
                  <div class="card bg-light border-0">
                    <div class="card-body">
                      <h3 class="h5 text-success mb-3">
                        <i class="fas fa-info-circle me-2"></i>Detalles de la Asignatura
                      </h3>
                      <div class="row g-3 ps-4">
                        <div class="col-md-4">
                          <div class="p-3 border rounded bg-white">
                            <p class="small text-muted mb-1">Curso</p>
                            <p class="h6 mb-0">${asignatura.curso}</p>
                          </div>
                        </div>
                        <div class="col-md-4">
                          <div class="p-3 border rounded bg-white">
                            <p class="small text-muted mb-1">Cuatrimestre</p>
                            <p class="h6 mb-0">${asignatura.cuatrimestre}</p>
                          </div>
                        </div>
                        <div class="col-md-4">
                          <div class="p-3 border rounded bg-white">
                            <p class="small text-muted mb-1">Créditos</p>
                            <p class="h6 mb-0">${asignatura.creditos}</p>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <div class="d-flex justify-content-between align-items-center">
                <button onclick="history.back()" class="btn btn-outline-primary">
                  <i class="fas fa-arrow-left me-2"></i>Volver
                </button>
                <a href="<%= request.getContextPath() %>/" class="btn btn-outline-secondary">
                  <i class="fas fa-home me-2"></i>Inicio
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>

  <script src="${pageContext.request.contextPath}/js/infoAsignatura.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>