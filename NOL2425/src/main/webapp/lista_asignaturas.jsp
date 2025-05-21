<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Asignaturas - Notas OnLine</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
		<link href="./css/lista_asignaturas.css" rel="stylesheet" type="text/css">
	</head>
	<body>
	<div class="container mt-4">
	
		<header class="mb-4">
			<h2 class="fw-bold">Notas OnLine · Asignaturas del alumno</h2>
			<p class="mb-0">
				Consulta aquí tus asignaturas y pulsa sobre ellas para ver tu calificación.
				<br><span class="nombre_alumno fst-italic"></span>
			</p>
		</header>
		
		<!-- Botón de Cerrar Sesión debajo del header -->
		<div class="d-flex justify-content-end mb-3">
		    <a href="/NOL2425/alumno?action=cierra" class="btn btn-outline-danger btn-sm">
		        Cerrar sesión
		    </a>
		</div>
	
		<div class="row mb-4">
			<!-- ASIGNATURAS (2/3) -->
			<div class="col-md-8">
				<!-- Tabs -->
				<ul class="nav nav-tabs" id="asignaturasTabs" role="tablist">
					<li class="nav-item">
						<button class="nav-link active" id="dew-tab" data-bs-toggle="tab" data-bs-target="#dew" type="button">DEW</button>
					</li>
					<li class="nav-item">
						<button class="nav-link" id="dcu-tab" data-bs-toggle="tab" data-bs-target="#dcu" type="button">DCU</button>
					</li>
					<li class="nav-item">
						<button class="nav-link" id="isw-tab" data-bs-toggle="tab" data-bs-target="#isw" type="button">ISW</button>
					</li>
				</ul>
	
				<div class="tab-content border border-top-0 p-4 shadow-sm" id="asignaturasTabsContent">
					<div class="tab-pane fade show active" id="dew" role="tabpanel">
						<div class="card border-0">
							<div class="card-body">
								<h5 class="card-title">Desarrollo Web</h5>
								<p class="card-text">Esta asignatura aborda los fundamentos de HTML, CSS, JS, JSP y desarrollo con Servlets.</p>
							</div>
						</div>
					</div>
					<div class="tab-pane fade" id="dcu" role="tabpanel">
						<div class="card border-0">
							<div class="card-body">
								<h5 class="card-title">Diseño Centrado en el Usuario</h5>
								<p class="card-text">Conocerás cómo diseñar interfaces usables e inclusivas centradas en la experiencia del usuario.</p>
							</div>
						</div>
					</div>
					<div class="tab-pane fade" id="isw" role="tabpanel">
						<div class="card border-0">
							<div class="card-body">
								<h5 class="card-title">Integración de Sistemas Web</h5>
								<p class="card-text">Estudia la integración de servicios, REST APIs, autenticación, y despliegue de aplicaciones web.</p>
							</div>
						</div>
					</div>
				</div>
			</div>
	
			<!-- GRUPO (1/3) -->
			<div class="col-md-4">
				<div class="card bg-light border-0 shadow-sm">
					<div class="card-body">
						<h5 class="card-title"><strong>Grupo G4_labo_Lunes</strong></h5>
						<ol class="mb-0">
							<li>Caballero Martínez, Fabio</li>
							<li>Cano Moya, Yeray</li>
							<li>Domenech Gascó, Marc</li>
							<li>Genés Bastidas, Rubén</li>
							<li>Makmar, Laila</li>
							<li>Marín Hernández, José</li>
						</ol>
					</div>
				</div>
			</div>
		</div>
	
		<hr>
		<footer class="text-center text-muted small mt-4">
			Trabajo en grupo para la asignatura de Desarrollo Web - Curso 2024-2025
		</footer>
	</div>
	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	</body>
</html>