import java.util.*;

class Reunion {
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

class Agenda {
    private List<Reunion> reuniones = new ArrayList<>();
    private Map<String, List<Reunion>> reunionesPorEtiqueta = new HashMap<>();

    // Sobrecarga de métodos
    public void agregarReunion(String fecha, String hora, String descripcion) {
        Reunion r = new Reunion(fecha, hora, descripcion, "General");
        reuniones.add(r);
        agregarPorEtiqueta(r);
    }

    public void agregarReunion(String fecha, String hora, String descripcion, String etiqueta) {
        Reunion r = new Reunion(fecha, hora, descripcion, etiqueta);
        reuniones.add(r);
        agregarPorEtiqueta(r);
    }

    private void agregarPorEtiqueta(Reunion r) {
        reunionesPorEtiqueta.putIfAbsent(r.getEtiqueta(), new ArrayList<>());
        reunionesPorEtiqueta.get(r.getEtiqueta()).add(r);
    }

    public void mostrarReuniones() {
        for (Reunion r : reuniones) {
            System.out.println(r);
        }
    }

    // 🔹 NUEVO MÉTODO: mostrar agrupadas por etiquetas
    public void mostrarReunionesPorEtiqueta() {
        for (String etiqueta : reunionesPorEtiqueta.keySet()) {
            System.out.println("\nEtiqueta: " + etiqueta);
            for (Reunion r : reunionesPorEtiqueta.get(etiqueta)) {
                System.out.println("  " + r);
            }
        }
    }
}


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Agenda agenda = new Agenda();

        // Datos iniciales (SIA1.4)
        agenda.agregarReunion("23/08/2025", "10:00", "Reunión de proyecto", "Trabajo");
        agenda.agregarReunion("24/08/2025", "18:00", "Cita médica", "Personal");

        int opcion;
        do {
            System.out.println("\n--- MENU AGENDA ---");
            System.out.println("1. Insertar nueva reunión");
            System.out.println("2. Mostrar todas las reuniones");
            System.out.println("3. Mostrar reuniones por etiqueta"); // ✅ Nueva opción
            System.out.println("0. Salir");
            System.out.print("Seleccione opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Fecha (dd/mm/yyyy): ");
                    String fecha = sc.nextLine();
                    System.out.print("Hora (HH:mm): ");
                    String hora = sc.nextLine();
                    System.out.print("Descripción: ");
                    String desc = sc.nextLine();
                    System.out.print("Etiqueta: ");
                    String etiqueta = sc.nextLine();
                    agenda.agregarReunion(fecha, hora, desc, etiqueta);
                    System.out.println("Reunión agregada con éxito.");
                    break;

                case 2:
                    System.out.println("\nListado de reuniones:");
                    agenda.mostrarReuniones();
                    break;

                case 3:
                    System.out.println("\nReuniones agrupadas por etiqueta:");
                    agenda.mostrarReunionesPorEtiqueta();
                    break;

                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);


        sc.close();
    }
}
