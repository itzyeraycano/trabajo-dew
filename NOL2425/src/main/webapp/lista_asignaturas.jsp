<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Asignaturas - Notas OnLine</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
	    <link href="<%= request.getContextPath() %>/css/estilos.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div class="container py-5">
		    <header class="text-center mb-5">
		      <h1 class="display-5 text-primary">
		        <i class="bi bi-journal-text me-3"></i>Mis Asignaturas
		      </h1>
		      <p class="lead text-muted">
		        Bienvenid@ <span class="nombre_alumno fw-bold text-primary"></span>
		      </p>
		      <p class="text-muted">
		        Aquí encontrarás todas las asignaturas en las que estás matriculad@.<br>
		        Pulsa en cada una para ver tu calificación.
		      </p>
		    </header>
	
			<!-- Asignaturas y grupo -->
			<div class="row g-4">
				<div class="col-lg-8">
					<div class="card shadow-sm">
						<div class="card-body p-0">
							<!-- Navegación por pestañas -->
							<ul class="nav nav-tabs nav-fill" id="asignaturasTabs" role="tablist">
								<li class="nav-item" role="presentation">
									<button class="nav-link active px-4" id="dew-tab" data-bs-toggle="tab" data-bs-target="#dew" type="button" role="tab">
										<i class="bi bi-code-slash me-2"></i>DEW
									</button>
								</li>
								<li class="nav-item" role="presentation">
									<button class="nav-link px-4" id="dcu-tab" data-bs-toggle="tab" data-bs-target="#dcu" type="button" role="tab">
										<i class="bi bi-people me-2"></i>DCU
									</button>
								</li>
								<li class="nav-item" role="presentation">
									<button class="nav-link px-4" id="isw-tab" data-bs-toggle="tab" data-bs-target="#isw" type="button" role="tab">
										<i class="bi bi-gear me-2"></i>ISW
									</button>
								</li>
							</ul>
	
							<div class="tab-content p-4" id="asignaturasTabsContent">
								<div class="tab-pane fade show active" id="dew" role="tabpanel">
									<div class="d-flex align-items-center mb-3">
										<div class="flex-shrink-0">
											<div class="bg-primary text-white rounded-circle p-3">
												<i class="bi bi-code-slash fs-4"></i>
											</div>
										</div>
										<div class="flex-grow-1 ms-3">
											<h3 class="h5 mb-1">Desarrollo Web</h3>
											<p class="text-muted mb-0">Aprende a crear aplicaciones web modernas</p>
										</div>
									</div>
									<div class="card bg-light border-0">
										<div class="card-body">
											<p class="mb-0">La asignatura Desarrollo Web te introduce en el mundo del desarrollo web full-stack, 
											abarcando tanto el frontend como el backend de las aplicaciones web modernas.</p>
										</div>
									</div>
								</div>
								<div class="tab-pane fade" id="dcu" role="tabpanel">
									<div class="d-flex align-items-center mb-3">
										<div class="flex-shrink-0">
											<div class="bg-success text-white rounded-circle p-3">
												<i class="bi bi-people fs-4"></i>
											</div>
										</div>
										<div class="flex-grow-1 ms-3">
											<h3 class="h5 mb-1">Diseño Centrado en el Usuario</h3>
											<p class="text-muted mb-0">Aprende a diseñar interfaces efectivas</p>
										</div>
									</div>
									<div class="card bg-light border-0">
										<div class="card-body">
											<p class="mb-0">La asignatura Diseño Centrado en el Usuario te enseña a crear interfaces 
											que satisfagan las necesidades de los usuarios finales.</p>
										</div>
									</div>
								</div>
								<div class="tab-pane fade" id="isw" role="tabpanel">
									<div class="d-flex align-items-center mb-3">
										<div class="flex-shrink-0">
											<div class="bg-info text-white rounded-circle p-3">
												<i class="bi bi-gear fs-4"></i>
											</div>
										</div>
										<div class="flex-grow-1 ms-3">
											<h3 class="h5 mb-1">Integración de Sistemas Web</h3>
											<p class="text-muted mb-0">Aprende a integrar diferentes sistemas web</p>
										</div>
									</div>
									<div class="card bg-light border-0">
										<div class="card-body">
											<p class="mb-0">La asignatura Integración de Sistemas Web te enseña a conectar 
											y hacer funcionar juntos diferentes sistemas y aplicaciones web.</p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
	
				<!-- Grupo listado -->
				<div class="col-lg-4">
					<div class="card shadow-sm">
						<div class="card-header bg-primary text-white py-3">
							<h5 class="card-title mb-0">
								<i class="bi bi-people-fill me-2"></i>Grupo G4_labo_Lunes
							</h5>
						</div>
						<div class="card-body p-0">
							<ol class="list-group list-group-numbered list-group-flush">
								<li class="list-group-item px-4 py-3">Caballero Martínez, Fabio</li>
								<li class="list-group-item px-4 py-3">Cano Moya, Yeray</li>
								<li class="list-group-item px-4 py-3">Domenech Gascó, Marc</li>
								<li class="list-group-item px-4 py-3">Genés Bastidas, Rubén</li>
								<li class="list-group-item px-4 py-3">Makmar, Laila</li>
								<li class="list-group-item px-4 py-3">Marín Hernández, José</li>
							</ol>
						</div>
					</div>
				</div>
			</div>
	
			<!-- Pie de página -->
			<footer class="mt-5 py-3 text-center border-top">
				<p class="mb-0">
					<i class="bi bi-code-slash me-2"></i>
					Trabajo en grupo realizado para la asignatura Desarrollo Web. Curso 2024-2025
				</p>
			</footer>
		</div>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	</body>
</html>