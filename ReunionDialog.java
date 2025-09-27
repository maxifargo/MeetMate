package agenda;

import javax.swing.*;
import java.awt.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException; 

public class ReunionDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private JTextField txtId, txtUsuario, txtFecha, txtHora, txtEtiqueta;
    private Reunion result;

    public ReunionDialog(Frame owner) {
        super(owner, "Nueva Reunión", true);
        setSize(400, 300);
        setLocationRelativeTo(owner);

        initUI();
    }
    public ReunionDialog(Frame owner, Reunion reunion) {
        this(owner); // llama al constructor original

        if (reunion != null) {
            txtId.setText(String.valueOf(reunion.getId()));
            txtUsuario.setText(reunion.getUsuario());
            txtFecha.setText(reunion.getFecha());
            txtHora.setText(reunion.getHora());
            txtEtiqueta.setText(reunion.getEtiqueta());
            txtId.setEnabled(false); // 🔹 no permitir cambiar el ID
        }
    } 
    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));

        panel.add(new JLabel("ID:"));
        txtId = new JTextField();
        panel.add(txtId);

        panel.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panel.add(txtUsuario);

        panel.add(new JLabel("Fecha (dd/mm/yyyy):"));
        txtFecha = new JTextField();
        panel.add(txtFecha);

        panel.add(new JLabel("Hora (HH:mm):"));
        txtHora = new JTextField();
        panel.add(txtHora);

        panel.add(new JLabel("Etiqueta:"));
        txtEtiqueta = new JTextField();
        panel.add(txtEtiqueta);

        JButton btnOk = new JButton("Guardar");
        JButton btnCancel = new JButton("Cancelar");

        btnOk.addActionListener(e -> onSave());
        btnCancel.addActionListener(e -> {
            result = null;
            dispose();
        });

        panel.add(btnOk);
        panel.add(btnCancel);

        getContentPane().add(panel);
    }

    private void onSave() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String usuario = txtUsuario.getText().isBlank() ? "desconocido" : txtUsuario.getText().trim();
            String fecha = txtFecha.getText().trim();
            String hora = txtHora.getText().trim();
            String etiqueta = txtEtiqueta.getText().trim();

            // Validar fecha con formato dd/MM/yyyy
            DateTimeFormatter fechaFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            try {
                LocalDate.parse(fecha, fechaFormatter);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "La fecha debe tener formato válido dd/MM/yyyy (ej: 25/12/2025).");
                return;
            }

            // Validar hora con formato HH:mm
            DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");
            try {
                LocalTime.parse(hora, horaFormatter);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "La hora debe tener formato válido HH:mm (ej: 09:30).");
                return;
            }

            result = new Reunion(id, usuario, fecha, hora, etiqueta);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.");
        }
    }

    public Reunion getResult() {
        return result;
    }
    @Override
    public void dispose() {
        System.out.println("Cerrando diálogo de reunión...");
        super.dispose();
    }
}
