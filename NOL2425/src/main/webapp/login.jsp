<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
	<head>
	 	<meta charset="UTF-8">
	 	<title>Registro / Login Alumno</title>
	 	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css">
		<link href="<%= request.getContextPath() %>/css/estilos.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div class="container">
			<div class="row justify-content-center mt-5">
				<div class="col-md-6">
					<div class="card shadow">
						<div class="card-header bg-primary text-white text-center py-3">
							<h2 class="h4 mb-0">Iniciar Sesión - Alumno</h2>
						</div>
						<div class="card-body p-4">
							<form action="j_security_check" method="post">
								<div class="mb-3">
									<label for="usuarioAlumno" class="form-label">Usuario</label>
									<div class="input-group">
										<span class="input-group-text"><i class="bi bi-person"></i></span>
										<input type="text" id="usuarioAlumno" name="j_username" class="form-control" required>
									</div>
								</div>
								<div class="mb-4">
									<label for="contrasenaAlumno" class="form-label">Contraseña</label>
									<div class="input-group">
										<span class="input-group-text"><i class="bi bi-lock"></i></span>
										<input type="password" id="contrasenaAlumno" name="j_password" class="form-control" required>
									</div>
								</div>
								<div class="d-grid">
									<button type="submit" class="btn btn-primary btn-lg">Iniciar sesión</button>
								</div>
							</form>
						</div>
					</div>
					<div class="text-center mt-3">
						<a href="<%= request.getContextPath() %>/" class="text-decoration-none">
							<i class="bi bi-arrow-left"></i> Volver al inicio
						</a>
					</div>
				</div>
			</div>
		</div>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	</body>
</html>