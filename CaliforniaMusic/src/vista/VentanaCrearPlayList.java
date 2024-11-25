package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;

import javax.swing.BoxLayout;

import javax.swing.JButton;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class VentanaCrearPlayList extends JPanel {

	private VentanaPrincipal ventanaPrincipal;
	private String string;
	private static ArrayList<PlayLists> listaPlayLists = new ArrayList<>();

	public VentanaCrearPlayList(VentanaPrincipal ventanaPrincipal) {

		this.ventanaPrincipal = ventanaPrincipal;

		setLayout(new BorderLayout());

		JPanel panelMedio = añadirPanelMedio();
		leerPlayList(panelMedio);
		JPanel panelSuperior = añadirPanelSuperior(panelMedio);

		JScrollPane scrollPane = new JScrollPane(panelMedio);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		add(panelSuperior, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
	}

	public void leerPlayList(JPanel panelMedio) {

		if (listaPlayLists.size() > 0) {

			for (int i = 0; i < listaPlayLists.size(); i++) {
				JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
				itemPanel.setBackground(new Color(100, 57, 208));
				PlayLists playList = listaPlayLists.get(i);
				playList.setText(playList.getNombre());

				playList.setPreferredSize(new Dimension(395, 50));
				playList.addActionListener(e -> {
					VentanaPlayList ventanaPlayList = ventanaPrincipal.getVentanaPlayList();
					ventanaPlayList.addCancion(playList);
				});
				itemPanel.add(playList);
				JButton eliminar = new JButton("Eliminar");
				eliminar.setPreferredSize(new Dimension(85, 50));

				itemPanel.add(eliminar);
				eliminar.addActionListener(e -> {
					panelMedio.remove(itemPanel);
					listaPlayLists.remove(playList);
					panelMedio.revalidate();
					panelMedio.repaint();
				});
				JButton editar = new JButton("Editar");
				editar.setPreferredSize(new Dimension(80, 50));

				itemPanel.add(editar);
				editar.addActionListener(e -> {
					EditarPlayList v = new EditarPlayList(playList, ventanaPrincipal);
					ventanaPrincipal.getContentPane().removeAll();
					ventanaPrincipal.add(v);
					ventanaPrincipal.revalidate();
					ventanaPrincipal.repaint();
				});

				panelMedio.add(itemPanel);
				panelMedio.revalidate();
				panelMedio.repaint();
			}
		}
	}

	public JPanel añadirPanelMedio() {
		JPanel panelMedio = new JPanel();
		panelMedio.setLayout(new BoxLayout(panelMedio, BoxLayout.Y_AXIS));
		panelMedio.setBackground(new Color(33, 48, 61));
		return panelMedio;
	}

	public JPanel añadirPanelSuperior(JPanel panelMedio) {

		JPanel panelSuperior = new JPanel();
		panelSuperior.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panelSuperior.setPreferredSize(new Dimension(600, 100));
		panelSuperior.setBackground(new Color(33, 48, 61));

		JButton volverButton = new JButton("Volver");
		volverButton.setFont(new Font("Arial", Font.BOLD, 14));
		customizeButton(volverButton);
		panelSuperior.add(volverButton);

		volverButton.addActionListener(e -> {

			this.ventanaPrincipal.mostrarPanelPrincipal();

		});

		JButton playlistButton = new JButton(" Crear Playlist");
		playlistButton.setFont(new Font("Arial", Font.BOLD, 24));
		customizeButton(playlistButton);
		playlistButton.setPreferredSize(new Dimension(600, 100));
		playlistButton.addActionListener(e -> {

			PlayLists playList = new PlayLists(this.ventanaPrincipal, ventanaPrincipal.obtenerReprod());
			listaPlayLists.add(playList);
			VentanaPlayList ventanaPlayList = ventanaPrincipal.getVentanaPlayList();
			ventanaPlayList.addCancion(playList);

			EditarPlayList v = new EditarPlayList(playList, ventanaPrincipal);
			ventanaPrincipal.getContentPane().removeAll();
			ventanaPrincipal.add(v);
			ventanaPrincipal.revalidate();
			ventanaPrincipal.repaint();
		});
		panelSuperior.add(playlistButton);

		return panelSuperior;
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
}
