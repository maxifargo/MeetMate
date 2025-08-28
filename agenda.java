package agenda;

import java.util.*;

public class Agenda {
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

    // Mostrar agrupadas por etiquetas
    public void mostrarReunionesPorEtiqueta() {
        for (String etiqueta : reunionesPorEtiqueta.keySet()) {
            System.out.println("\nEtiqueta: " + etiqueta);
            for (Reunion r : reunionesPorEtiqueta.get(etiqueta)) {
                System.out.println("  " + r);
            }
        }
    }
}
