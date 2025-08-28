package agenda;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Agenda agenda = new Agenda();

        // Datos iniciales
        agenda.agregarReunion("23/08/2025", "10:00", "Reunión de proyecto", "Trabajo");
        agenda.agregarReunion("24/08/2025", "18:00", "Cita médica", "Personal");

        int opcion;
        do {
            System.out.println("\n--- MENU AGENDA ---");
            System.out.println("1. Insertar nueva reunión");
            System.out.println("2. Mostrar todas las reuniones");
            System.out.println("3. Mostrar reuniones por etiqueta");
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