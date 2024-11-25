package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Portada extends JPanel {

	private VentanaPrincipal principal;
	private JLabel songImageLabel;

	public Portada() {

	}

	public Portada(VentanaPrincipal vp) {

		principal = vp;

		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(600, 500));

		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		topPanel.setBackground(new Color(33, 48, 61));
		topPanel.setPreferredSize(new Dimension(600, 100));

		JButton botonVolver = new JButton("VOLVER");
		botonVolver.setFont(new Font("Arial", Font.BOLD, 14));
		botonVolver.addActionListener(e -> principal.mostrarPanelPrincipal());
		customizeButton(botonVolver);
		topPanel.add(botonVolver);

		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(new Color(100, 57, 208));
		centerPanel.setPreferredSize(new Dimension(600, 400));
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

		centerPanel.add(Box.createVerticalGlue());

		songImageLabel = new JLabel();
		songImageLabel.setPreferredSize(new Dimension(300, 300));
		songImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerPanel.add(songImageLabel);

		centerPanel.add(Box.createVerticalGlue());

		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
	}

	private void customizeButton(JButton button) {
		button.setFocusPainted(false);
		button.setForeground(java.awt.Color.WHITE);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setOpaque(false);

		button.setRolloverEnabled(true);

		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {

				button.setForeground(Color.RED);
			}

			@Override
			public void mouseExited(MouseEvent e) {

				button.setForeground(Color.WHITE);
				button.setBackground(null);
			}
		});
		button.setPressedIcon(null);
		button.setRolloverIcon(null);
	}	

	public void actualizarCaratula(String rutaImagen) {
		try {
			ImageIcon songImageIcon = new ImageIcon(rutaImagen);
			if (songImageIcon.getIconWidth() == -1) {
				System.out.println("Error: No se pudo cargar la imagen desde " + rutaImagen);
				songImageLabel.setText("Imagen no disponible");
			} else {
				Image scaledImage = songImageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
				songImageLabel.setIcon(new ImageIcon(scaledImage));
				songImageLabel.setText("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		revalidate();
		repaint();
	}
	



	public void actualizarImagenCancion(String rutaImagen) {
		actualizarCaratula(rutaImagen);
	}

}
