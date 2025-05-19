package dew.clases;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Asignatura {
	
  // Este será el código para ambos JSON:
  @SerializedName(value = "codigo", alternate = { "asignatura" })
  private String codigo;

  // Sólo existe en el array de asignaturas del alumno:
  @SerializedName("nota")
  private String nota;

  // Sólo existen en el detalle de asignatura:
  private String nombre;
  private int curso;
  private String cuatrimestre;
  private double creditos;

  // getters y setters
  public String getCodigo()      { return codigo; }
  public void setCodigo(String c){ this.codigo = c; }

  public String getNota()          { return nota; }
  public void setNota(String n)    { this.nota = n; }

  public String getNombre()        { return nombre; }
  public void setNombre(String n)  { this.nombre = n; }

  public int getCurso()            { return curso; }
  public void setCurso(int c)      { this.curso = c; }

  public String getCuatrimestre()  { return cuatrimestre; }
  public void setCuatrimestre(String c) { this.cuatrimestre = c; }

  public double getCreditos()      { return creditos; }
  public void setCreditos(double c){ this.creditos = c; }
}