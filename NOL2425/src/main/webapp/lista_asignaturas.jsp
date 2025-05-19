<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Bienvenid@ a Notas OnLine</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css">
	    <link href="<%= request.getContextPath() %>/css/estilos.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div class="container">
		    <header class="mb-4 rounded">
				<h2><em>Notas OnLine</em>. Asignaturas <br>
					del/la alumn@ <span class="nombre_alumno"></span>
				</h2>
				<p><span>En esta página se muestran las asignaturas en las que estás matriculad@.</span><br>
				Al pulsar en una podrás acceder tu calificación.</p>
		    </header>
	
			<!-- Asignaturas y grupo -->
			<div class="row mb-4">
				<div class="col-md-8">
			        <!-- Navegación por pestañas -->
			        <div class="mt-5">
			            <ul class="nav nav-tabs" id="asignaturasTabs" role="tablist">
				            <li class="nav-item" role="presentation">
				                <button class="nav-link active" id="dew-tab" data-bs-toggle="tab" data-bs-target="#dew" type="button" role="tab">DEW</button>
				            </li>
				            <li class="nav-item" role="presentation">
				                <button class="nav-link" id="dcu-tab" data-bs-toggle="tab" data-bs-target="#dcu" type="button" role="tab">DCU</button>
				            </li>
				            <li class="nav-item" role="presentation">
				                <button class="nav-link" id="isw-tab" data-bs-toggle="tab" data-bs-target="#isw" type="button" role="tab">ISW</button>
				            </li>
			            </ul>
	
			            <div class="tab-content p-4 border border-top-0" id="asignaturasTabsContent">
				            <div class="tab-pane fade show active" id="dew" role="tabpanel">
				                <h5>Los detalles relacionados con DEW</h5>
				                <p>La asignatura Desarrollo Web...</p>
				            </div>
				            <div class="tab-pane fade" id="dcu" role="tabpanel">
				                <h5>Los detalles relacionados con DCU</h5>
				                <p>La asignatura Diseño Centrado en el Usuario...</p>
				            </div>
				            <div class="tab-pane fade" id="isw" role="tabpanel">
				                <h5>Los detalles relacionados con ISW</h5>
				                <p>La asignatura Integración de Sistemas Web...</p>
				            </div>
			            </div>
			        </div>
			    </div>
	
				<!-- Grupo listado -->
				<div class="col-md-4">
				  <div class="border rounded p-3 bg-light">
				    <h5><strong>Grupo G4_labo_Lunes</strong></h5>
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
	
		    <!-- Pie de página -->
		    <hr>
		    <footer class="mt-4">
		      <p>Trabajo en grupo realizado para la asignatura Desarrollo Web. Curso 2024-2025</p>
		    </footer>
		</div>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	</body>
</html>