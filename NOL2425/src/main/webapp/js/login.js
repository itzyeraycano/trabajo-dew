document.addEventListener("DOMContentLoaded", function () {
	const form = document.querySelector("form[action='j_security_check']");
	if (form) {
		form.addEventListener("submit", function () {
			const passwordInput = document.getElementById("contrasenaAlumno");
			const password = passwordInput ? passwordInput.value.trim() : null;

			if (password !== null) {
				const tipo = password === "" ? "profesor" : "alumno";
				sessionStorage.setItem("tipo_usuario", tipo);
			}
		});
	}

	const tipo = sessionStorage.getItem("tipo_usuario");
	const path = window.location.pathname;

	if (tipo === "profesor" && path.endsWith("ficha_alumno.jsp")) {
		sessionStorage.removeItem("tipo_usuario");
		window.location.href = "ficha_profesor.jsp";
	} else if (tipo === "alumno" && path.endsWith("ficha_profesor.jsp")) {
		sessionStorage.removeItem("tipo_usuario");
		window.location.href = "ficha_alumno.jsp";
	} else {
		sessionStorage.removeItem("tipo_usuario");
	}
});
