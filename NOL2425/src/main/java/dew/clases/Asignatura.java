    package dew.clases;

    import com.google.gson.annotations.SerializedName;

    public class Asignatura {

        // Asumimos que el nombre del atributo Java es 'acronimo'.
        // 'value' puede ser el nombre más común o uno de ellos.
        // 'alternate' son otros posibles nombres para el mismo campo en diferentes JSONs.
        @SerializedName(value = "acronimo", alternate = {"codigo_asignatura", "cod_asig", "idAsig"}) 
        private String acronimo;

        @SerializedName(value = "nombre", alternate = {"nombre_asignatura", "titulo_asig"})
        private String nombre;
        
        // La nota usualmente solo vendrá para el contexto del alumno.
        // Si el JSON del profesor también trae un campo "nota" (quizás como null), 
        // o si el JSON del alumno usa otro nombre para la nota.
        @SerializedName(value = "nota", alternate = {"calificacion", "notaFinal"})
        private String nota; 

        // Para estos, si el nombre del campo en AMBOS JSONs es igual al nombre del atributo,
        // no necesitas @SerializedName. Si son diferentes en alguno, sí.
        @SerializedName(value = "curso", alternate = {"nivel_curso"})
        private int curso;

        @SerializedName(value = "cuatrimestre", alternate = {"periodo"})
        private String cuatrimestre;

        @SerializedName(value = "creditos", alternate = {"creditos_ects"})
        private double creditos;

        public Asignatura() {
        }

        // --- GETTERS Y SETTERS ---
        public String getAcronimo() { return acronimo; }
        public void setAcronimo(String acronimo) { this.acronimo = acronimo; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getNota() { return nota; }
        public void setNota(String nota) { this.nota = nota; }

        public int getCurso() { return curso; }
        public void setCurso(int curso) { this.curso = curso; }

        public String getCuatrimestre() { return cuatrimestre; }
        public void setCuatrimestre(String cuatrimestre) { this.cuatrimestre = cuatrimestre; }

        public double getCreditos() { return creditos; }
        public void setCreditos(double creditos) { this.creditos = creditos; }

        @Override
        public String toString() {
            return "Asignatura{" +
                   "acronimo='" + acronimo + '\'' +
                   ", nombre='" + nombre + '\'' +
                   (nota != null ? ", nota='" + nota + '\'' : "") +
                   ", curso=" + curso +
                   ", cuatrimestre='" + cuatrimestre + '\'' +
                   ", creditos=" + creditos +
                   '}';
        }
    }
    