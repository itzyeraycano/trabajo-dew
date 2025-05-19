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
		<div class="container my-4">
			<h2>Iniciar Sesión - Alumno</h2>
			<form action="j_security_check" method="post" class="ms-3">
			  <div class="row mb-2">
			    <div class="col-sm-3 text-end">
			      <label for="usuarioAlumno" class="col-form-label col-form-label-sm">Usuario:</label>
			    </div>
			    <div class="col-sm-6">
			      <input type="text" id="usuarioAlumno" name="j_username" class="form-control form-control-sm" required>
			    </div>
			  </div>
			  <div class="row mb-2">
			    <div class="col-sm-3 text-end">
			      <label for="contrasenaAlumno" class="col-form-label col-form-label-sm">Contraseña:</label>
			    </div>
			    <div class="col-sm-6">
			      <input type="password" id="contrasenaAlumno" name="j_password" class="form-control form-control-sm" required>
			    </div>
			  </div>
			  <div class="row">
			    <div class="col-sm-6 offset-sm-3">
			      <button type="submit" class="btn btn-primary btn-sm w-100">Iniciar sesión</button>
			    </div>
			  </div>
			</form>
		</div>
	</body>
</html>