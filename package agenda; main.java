package agenda;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Agenda ag = new Agenda(); 
            ag.cargarDesdeArchivo("agenda.csv");
            ag.inicializarDatosDePruebaSiFaltan(); //carga primeros datos al iniciar una sola vez
            
            // Datos iniciales en avance
            //ag.agregarReunion("Carlos", 101, "23/08/2025", "10:00", "Trabajo");
            //ag.agregarReunion(202, "24/08/2025", "18:00", "Personal");

            MainWindow mw = new MainWindow(ag);
            mw.setVisible(true);
        });
    }
}
