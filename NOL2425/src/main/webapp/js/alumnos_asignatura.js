document.addEventListener("DOMContentLoaded", function () {
  const contextPath = document.body.getAttribute("data-context-path") || "";
  const acronimo = document.body.getAttribute("data-asignatura") || "";

  const jsonTag = document.getElementById("alumnos-data");
  let alumnos = [];

  try {
    alumnos = JSON.parse(jsonTag.textContent);
  } catch (error) {
    console.error("Error al parsear alumnosJson:", error);
    return;
  }

  const tbody = document.getElementById("tbodyAlumnos");

  if (Array.isArray(alumnos)) {
    alumnos.forEach(alumno => {
      const tr = document.createElement("tr");

      const nota = alumno.nota != null ? alumno.nota : "—";
      const rowHtml = `
        <td>
          <img src="${contextPath}/fotos/${alumno.dni}.png"
               alt="Foto de ${alumno.nombre}"
               class="rounded-circle"
               width="48" height="48"
               onerror="this.onerror=null;this.src='${contextPath}/img/default.png';" />
        </td>
        <td>${alumno.nombre}</td>
        <td>${alumno.apellidos}</td>
        <td>${alumno.dni}</td>
        <td contenteditable="true"
            class="nota-editable"
            data-dni="${alumno.dni}">
            ${nota}
        </td>
      `;

      tr.innerHTML = rowHtml;
      tbody.appendChild(tr);
    });

    document.querySelectorAll(".nota-editable").forEach(celda => {
      celda.addEventListener("blur", function () {
        const nuevaNota = this.innerText.trim();
        const dni = this.dataset.dni;

		fetch(contextPath + "/profesor", {
		  method: "PUT",
		  headers: {
		    "Content-Type": "application/json"
		  },
		  body: JSON.stringify({
		    dniAlumno: dni,
		    acronimo: acronimo,
		    nota: nuevaNota
		  })
		})

        .then(response => {
          if (!response.ok) throw new Error("Error al guardar nota");
          return response.json();
        })
        .then(() => {
          this.style.backgroundColor = "#d4edda"; // Éxito (verde)
          setTimeout(() => {
            this.style.backgroundColor = "";
          }, 1500);
        })
        .catch(error => {
          console.error(error);
          this.style.backgroundColor = "#f8d7da"; // Error (rojo)
          setTimeout(() => {
            this.style.backgroundColor = "";
          }, 1500);
        });
      });
    });
  }
});
