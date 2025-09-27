package agenda;

public class Reunion {
    private int id;
    private String usuario;
    private String fecha;
    private String hora;
    private String etiqueta;

    public Reunion(int id, String usuario, String fecha, String hora, String etiqueta) {
        this.id = id;
        this.usuario = usuario;
        this.fecha = fecha;
        this.hora = hora;
        this.etiqueta = etiqueta;
    }

    public String getUsuario() { return usuario; }
    public int getId() { return id; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }
    public String getEtiqueta() { return etiqueta; }
    public void setEtiqueta(String etiqueta) { this.etiqueta = etiqueta; }

    @Override
    public String toString() {
        return "ID: " + id + " | [" + usuario + "] " + fecha + " " + hora + " [" + etiqueta + "]";
    }
}


