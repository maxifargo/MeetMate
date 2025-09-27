package agenda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    private Agenda ag;
    private JTable table;
    private DefaultTableModel model;

    public MainWindow(Agenda ag) {
        super("Agenda de Reuniones");
        this.ag = ag;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 500);
        setLocationRelativeTo(null);

        initUI();
        cargarReuniones();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                ag.guardarEnArchivo("agenda.csv"); // guarda los datos al cerrar
                System.exit(0);
            }
        });

    }

    private void initUI() {
        // Modelo y tabla
        model = new DefaultTableModel(new Object[]{"ID", "Usuario", "Fecha", "Hora", "Etiqueta"}, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        // Panel de botones
        JPanel panelBtns = new JPanel();

        // Botones
        JButton btnAdd = new JButton("Nueva reunión");
        JButton btnEdit = new JButton("Editar reunión");
        JButton btnDel = new JButton("Eliminar reunión");
        JButton btnShow = new JButton("Mostrar todas");
        JButton btnFilter = new JButton("Filtrar por etiqueta");
        JButton btnFilterRango = new JButton("Filtrar por rango de fechas");
        JButton btnBuscarId = new JButton("Buscar por ID");
        JButton btnRenameUser = new JButton("Renombrar usuario"); // <-- aquí

        // Añadir botones al panel
        panelBtns.add(btnAdd);
        panelBtns.add(btnEdit);
        panelBtns.add(btnDel);
        panelBtns.add(btnShow);
        panelBtns.add(btnFilter);
        panelBtns.add(btnFilterRango);
        panelBtns.add(btnBuscarId);
        panelBtns.add(btnRenameUser); // <-- aquí

        // Acciones de los botones
        btnAdd.addActionListener(e -> onAdd());
        btnEdit.addActionListener(e -> onEdit());
        btnDel.addActionListener(e -> onDelete());
        btnShow.addActionListener(e -> cargarReuniones());
        btnFilter.addActionListener(e -> onFilter());
        btnFilterRango.addActionListener(e -> abrirDialogoFiltrarRango());
        btnBuscarId.addActionListener(e -> onBuscarPorId());

        // Acción para renombrar usuario
        btnRenameUser.addActionListener(e -> {
            String viejo = JOptionPane.showInputDialog(this, "Ingrese el nombre actual del usuario:");
            if (viejo == null || viejo.isBlank()) return;

            String nuevo = JOptionPane.showInputDialog(this, "Ingrese el nuevo nombre:");
            if (nuevo == null || nuevo.isBlank()) return;

            if (ag.renombrarUsuario(viejo, nuevo)) {
                JOptionPane.showMessageDialog(this, "Usuario renombrado correctamente.");
                cargarReuniones();
                ag.guardarEnArchivo("agenda.csv");
            } else {
                JOptionPane.showMessageDialog(this, "Error: el usuario no existe o el nuevo nombre ya está en uso.");
            }
        });

        // Layout del JFrame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scroll, BorderLayout.CENTER);
        getContentPane().add(panelBtns, BorderLayout.SOUTH);
    }
    private void cargarReuniones() {
        model.setRowCount(0);

        Map<String, Map<String, List<Reunion>>> todas = ag.getReunionesPorUsuario();

        for (String usuario : todas.keySet()) {
            Map<String, List<Reunion>> porEtiqueta = todas.get(usuario);
            for (List<Reunion> lista : porEtiqueta.values()) {
                for (Reunion r : lista) {
                    model.addRow(new Object[]{
                            r.getId(), r.getUsuario(), r.getFecha(), r.getHora(), r.getEtiqueta()
                    });
                }
            }
        }
    }

    private void onAdd() {
        ReunionDialog dialog = new ReunionDialog(this);
        dialog.setVisible(true);
        Reunion r = dialog.getResult();
        if (r != null) {
            try {
                ag.agregarReunion(r.getUsuario(), r.getId(), r.getFecha(), r.getHora(), r.getEtiqueta());
                cargarReuniones();
                ag.guardarEnArchivo("agenda.csv");
            } catch (FechaInvalidaException | HoraInvalidaException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onDelete() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una reunión para eliminar.");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        String usuario = (String) model.getValueAt(row, 1);

        boolean eliminado = ag.eliminarReunion(usuario, id);
        if (eliminado) {
            JOptionPane.showMessageDialog(this, "Reunión eliminada.");
            cargarReuniones();
            ag.guardarEnArchivo("agenda.csv");
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró la reunión.");
        }
    }

    private void onFilter() {
        String etiqueta = JOptionPane.showInputDialog(this, "Ingrese etiqueta a filtrar:");
        if (etiqueta == null || etiqueta.isBlank()) return;

        model.setRowCount(0);

        Map<String, Map<String, List<Reunion>>> todas = ag.getReunionesPorUsuario();

        for (String usuario : todas.keySet()) {
            Map<String, List<Reunion>> porEtiqueta = todas.get(usuario);
            if (porEtiqueta.containsKey(etiqueta)) {
                for (Reunion r : porEtiqueta.get(etiqueta)) {
                    model.addRow(new Object[]{
                            r.getId(), r.getUsuario(), r.getFecha(), r.getHora(), r.getEtiqueta()
                    });
                }
            }
        }
    }
    private void abrirDialogoFiltrarRango() {
        JDialog dialog = new JDialog(this, "Filtrar por Rango de Fechas", true);
        dialog.setSize(360, 180);
        dialog.setLayout(new GridLayout(3, 2, 8, 8));

        JLabel lblInicio = new JLabel("Fecha Inicio (dd/MM/yyyy):");
        JTextField txtInicio = new JTextField();
        JLabel lblFin = new JLabel("Fecha Fin (dd/MM/yyyy):");
        JTextField txtFin = new JTextField();

        JButton btnFiltrar = new JButton("Filtrar");
        JButton btnCancelar = new JButton("Cancelar");

        dialog.add(lblInicio);
        dialog.add(txtInicio);
        dialog.add(lblFin);
        dialog.add(txtFin);
        dialog.add(btnFiltrar);
        dialog.add(btnCancelar);

        btnCancelar.addActionListener(ev -> dialog.dispose());

        btnFiltrar.addActionListener(ev -> {
            String inicio = txtInicio.getText().trim();
            String fin = txtFin.getText().trim();

            if (inicio.isEmpty() || fin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete ambas fechas (inicio y fin).", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Llamamos al método que agregamos en Agenda (usa tu variable 'ag')
            List<Reunion> filtradas = ag.getReunionesPorRango(inicio, fin);

            if (filtradas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron reuniones en ese rango o las fechas están mal formateadas.", "Información", JOptionPane.INFORMATION_MESSAGE);
                // opcional: limpiar la tabla o mantenerla como estaba
                model.setRowCount(0);
                dialog.dispose();
                return;
            }

            // Limpiar tabla y mostrar resultados filtrados
            model.setRowCount(0);
            for (Reunion r : filtradas) {
                model.addRow(new Object[] { r.getId(), r.getUsuario(), r.getFecha(), r.getHora(), r.getEtiqueta() });
            }

            dialog.dispose();
        });
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    private void onEdit() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una reunión para editar.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        String usuario = (String) model.getValueAt(row, 1);

        Reunion original = null;
        Map<String, Map<String, List<Reunion>>> todas = ag.getReunionesPorUsuario();

        if (todas.containsKey(usuario)) {
            for (List<Reunion> lista : todas.get(usuario).values()) {
                for (Reunion r : lista) {
                    if (r.getId() == id) {
                        original = r;
                        break;
                    }
                }
            }
        }

        if (original == null) {
            JOptionPane.showMessageDialog(this, "No se encontró la reunión.");
            return;
        }

        // Abrir diálogo con los datos actuales
        ReunionDialog dialog = new ReunionDialog(this, original);
        dialog.setVisible(true);
        Reunion editada = dialog.getResult();

        if (editada != null) {
            // 1. Eliminar la reunión de su ubicación actual
            ag.eliminarReunion(usuario, id);

            // 2. Volver a insertarla con los nuevos datos
            try {
                ag.agregarReunion(
                    editada.getUsuario(),
                    editada.getId(),
                    editada.getFecha(),
                    editada.getHora(),
                    editada.getEtiqueta()
                );
                cargarReuniones();
                ag.guardarEnArchivo("agenda.csv");
            } catch (FechaInvalidaException | HoraInvalidaException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

            // 3. Recargar y guardar
            cargarReuniones();
            ag.guardarEnArchivo("agenda.csv");
        }
        }

    }
    private void onBuscarPorId() {
        String input = JOptionPane.showInputDialog(this, "Ingrese ID de la reunión:");
        if (input == null || input.isBlank()) return;

        try {
            int id = Integer.parseInt(input.trim());
            Reunion r = ag.buscarPorId(id);

            model.setRowCount(0); // limpiar tabla
            if (r != null) {
                model.addRow(new Object[]{ r.getId(), r.getUsuario(), r.getFecha(), r.getHora(), r.getEtiqueta() });
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró reunión con ID " + id);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.");
        }
    }
}
