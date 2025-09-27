package agenda;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Agenda {
    
    private Map<String, Map<String, List<Reunion>>> reunionesPorUsuario = new HashMap<>();

 // Sobrecarga con usuario
    public void agregarReunion(String usuario, int id, String fecha, String hora, String etiqueta)
            throws FechaInvalidaException, HoraInvalidaException {

        // Validar fecha
        DateTimeFormatter fechaFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate.parse(fecha.trim(), fechaFormatter);
        } catch (DateTimeParseException ex) {
            throw new FechaInvalidaException("Fecha inválida: " + fecha);
        }

        // Validar hora
        DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalTime.parse(hora.trim(), horaFormatter);
        } catch (DateTimeParseException ex) {
            throw new HoraInvalidaException("Hora inválida: " + hora);
        }

        if (usuario == null || usuario.isBlank()) usuario = "desconocido";
        Reunion r = new Reunion(id, usuario, fecha, hora, etiqueta);

        reunionesPorUsuario.putIfAbsent(usuario, new HashMap<>());
        Map<String, List<Reunion>> porEtiqueta = reunionesPorUsuario.get(usuario);
        porEtiqueta.putIfAbsent(etiqueta, new ArrayList<>());
        porEtiqueta.get(etiqueta).add(r);
    }


 // Sobrecarga sin usuario
    public void agregarReunion(int id, String fecha, String hora, String etiqueta)
            throws FechaInvalidaException, HoraInvalidaException {
        agregarReunion("desconocido", id, fecha, hora, etiqueta);
    }


    public void mostrarReunionesPorEtiqueta() {
        for (String usuario : reunionesPorUsuario.keySet()) {
            System.out.println("\nUsuario: " + usuario);
            Map<String, List<Reunion>> porEtiqueta = reunionesPorUsuario.get(usuario);
            for (String etiqueta : porEtiqueta.keySet()) {
                System.out.println("  Etiqueta: " + etiqueta);
                for (Reunion r : porEtiqueta.get(etiqueta)) {
                    System.out.println("    " + r);
                }
            }
        }
    }

    public boolean eliminarReunion(String usuario, int id) {
        if (!reunionesPorUsuario.containsKey(usuario)) return false;
        Map<String, List<Reunion>> porEtiqueta = reunionesPorUsuario.get(usuario);
        for (List<Reunion> lista : porEtiqueta.values()) {
            Iterator<Reunion> it = lista.iterator();
            while (it.hasNext()) {
                Reunion r = it.next();
                if (r.getId() == id) {
                    it.remove();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean eliminarReunion(int id) {
        for (String usuario : reunionesPorUsuario.keySet()) {
            if (eliminarReunion(usuario, id)) return true;
        }
        return false;
    }

    // Getter público para la GUI
    public Map<String, Map<String, List<Reunion>>> getReunionesPorUsuario() {
        return reunionesPorUsuario;
    }
 // Cargar desde archivo
    public void cargarDesdeArchivo(String rutaArchivo) {
        File file = new File(rutaArchivo);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 5) {
                    int id = Integer.parseInt(partes[0].trim());
                    String usuario = partes[1].trim();
                    String fecha = partes[2].trim();
                    String hora = partes[3].trim();
                    String etiqueta = partes[4].trim();

                    try {
                        agregarReunion(usuario, id, fecha, hora, etiqueta);
                    } catch (FechaInvalidaException | HoraInvalidaException e) {
                        System.err.println("Error al agregar reunión desde archivo: " + e.getMessage());
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al leer archivo: " + e.getMessage());
        }
    }

    // Guardar reuniones en CSV
    public void guardarEnArchivo(String rutaArchivo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(rutaArchivo))) {
            for (String usuario : reunionesPorUsuario.keySet()) {
                Map<String, List<Reunion>> porEtiqueta = reunionesPorUsuario.get(usuario);
                for (List<Reunion> lista : porEtiqueta.values()) {
                    for (Reunion r : lista) {
                        pw.println(r.getId() + "," + r.getUsuario() + "," + r.getFecha() + "," + r.getHora() + "," + r.getEtiqueta());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al guardar archivo: " + e.getMessage());
        }
    }
    public List<Reunion> getReunionesPorRango(String fechaInicio, String fechaFin) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate inicio;
        LocalDate fin;
        try {
            inicio = LocalDate.parse(fechaInicio.trim(), formatter);
            fin = LocalDate.parse(fechaFin.trim(), formatter);
        } catch (DateTimeParseException e) {
            // si las fechas de entrada no son válidas, devolvemos lista vacía
            return new ArrayList<>();
        }

        List<Reunion> filtradas = new ArrayList<>();

        // recorremos la estructura anidada: usuario -> (etiqueta -> lista)
        for (Map<String, List<Reunion>> porEtiqueta : reunionesPorUsuario.values()) {
            for (List<Reunion> lista : porEtiqueta.values()) {
                for (Reunion r : lista) {
                    try {
                        LocalDate fechaReunion = LocalDate.parse(r.getFecha().trim(), formatter);
                        if ((fechaReunion.isEqual(inicio) || fechaReunion.isAfter(inicio)) &&
                            (fechaReunion.isEqual(fin) || fechaReunion.isBefore(fin))) {
                            filtradas.add(r);
                        }
                    } catch (DateTimeParseException ex) {
                        // Si la fecha de la reunión no tiene el formato esperado la ignoramos
                    }
                }
            }
        }

        return filtradas;
    }
    public Reunion buscarPorId(int id) {
        for (Map<String, List<Reunion>> porEtiqueta : reunionesPorUsuario.values()) {
            for (List<Reunion> lista : porEtiqueta.values()) {
                for (Reunion r : lista) {
                    if (r.getId() == id) {
                        return r;
                    }
                }
            }
        }
        return null; // si no se encuentra
    }

    public List<Reunion> buscarPorUsuario(String usuario) {
        List<Reunion> resultado = new ArrayList<>();
        if (reunionesPorUsuario.containsKey(usuario)) {
            for (List<Reunion> lista : reunionesPorUsuario.get(usuario).values()) {
                resultado.addAll(lista);
            }
        }
        return resultado;
    }
 // 🔹 Método para cargar datos iniciales
    public void cargarDatosIniciales() {
        try {
            agregarReunion("Carlos", 1, "01/10/2025", "09:00", "Trabajo");
            agregarReunion("Ana", 2, "02/10/2025", "10:30", "Estudio");
            agregarReunion("Luis", 3, "03/10/2025", "15:00", "Deporte");
            agregarReunion("Ana", 4, "04/10/2025", "11:00", "Trabajo");
        } catch (FechaInvalidaException | HoraInvalidaException e) {
            System.err.println("Error cargando datos iniciales: " + e.getMessage());
        }
    }

    // 🔹 Método para eliminar todas las reuniones de un usuario (nivel 1)
    public boolean eliminarUsuario(String usuario) {
        if (reunionesPorUsuario.containsKey(usuario)) {
            reunionesPorUsuario.remove(usuario);
            return true;
        }
        return false;
    }

    // 🔹 Método para renombrar un usuario 
    public boolean renombrarUsuario(String viejoNombre, String nuevoNombre) {
        if (!reunionesPorUsuario.containsKey(viejoNombre)) {
            return false; // no existe el usuario original
        }
        if (reunionesPorUsuario.containsKey(nuevoNombre)) {
            return false; // ya existe el nuevo nombre
        }
        Map<String, List<Reunion>> datos = reunionesPorUsuario.remove(viejoNombre);
        for (List<Reunion> lista : datos.values()) {
            for (Reunion r : lista) {
                r.setUsuario(nuevoNombre);
            }
        }
        reunionesPorUsuario.put(nuevoNombre, datos);
        return true;
    }
}


