package agenda;


public class Reunion {
    private String fecha;   // formato: dd/mm/yyyy
    private String hora;
    private String descripcion;
    private String etiqueta;

    public Reunion(String fecha, String hora, String descripcion, String etiqueta) {
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.etiqueta = etiqueta;
    }

    // Getters y setters
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEtiqueta() { return etiqueta; }
    public void setEtiqueta(String etiqueta) { this.etiqueta = etiqueta; }

    @Override
    public String toString() {
        return fecha + " " + hora + " - " + descripcion + " [" + etiqueta + "]";
    }
}

