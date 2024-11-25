package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class VentanaPlayList extends JPanel {

	private String string;
	private PlayLists playList;
	private VentanaPrincipal principal;

	public VentanaPlayList(VentanaPrincipal principal, PlayLists playList) {
		this.principal = principal;
		this.playList = playList;

		setLayout(new BorderLayout());

		JPanel panelMedio = añadirPanelMedio();
		crearBotonesCanciones(panelMedio);
		JPanel panelSuperior = añadirPanelSuperior(panelMedio);

		JScrollPane scrollPane = new JScrollPane(panelMedio);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		add(panelSuperior, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
	}

	public void crearBotonesCanciones(JPanel panelMedio) {
		if (playList.getCanciones().size() > 0) {
			for (int i = 0; i < playList.getCanciones().size(); i++) {
				JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
				itemPanel.setBackground(new Color(212, 188, 123));
				JButton cancion = new JButton(playList.getCanciones().get(i).getTitulo());
				cancion.setPreferredSize(new Dimension(400, 50));

				itemPanel.add(cancion);
				panelMedio = (JPanel) getParent();
				panelMedio.add(itemPanel);
			}

			panelMedio.revalidate();
			panelMedio.repaint();
		}
	}

	public void addCancion(PlayLists playList) {
		JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		JButton cancion = new JButton(playList.getNombre());
		cancion.setPreferredSize(new Dimension(400, 50));
		itemPanel.add(cancion);
		add(itemPanel);
		revalidate();
		repaint();
	}

	public JPanel añadirPanelMedio() {
		JPanel panelMedio = new JPanel();
		panelMedio.setLayout(new BoxLayout(panelMedio, BoxLayout.Y_AXIS));
		panelMedio.setBackground(Color.YELLOW);
		return panelMedio;
	}

	public JPanel añadirPanelSuperior(JPanel panelMedio) {

		JPanel panelSuperior = new JPanel(new BorderLayout());
		panelSuperior.setPreferredSize(new Dimension(600, 150));
		panelSuperior.setBackground(Color.BLACK);

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBackground(Color.BLACK);

		JButton volverButton = new JButton("Volver");
		volverButton.setPreferredSize(new Dimension(200, 50));
		topPanel.add(volverButton, BorderLayout.WEST);

		panelSuperior.add(topPanel, BorderLayout.NORTH);

		JButton playlistButton = new JButton(playList.getNombre());
		playlistButton.setPreferredSize(new Dimension(600, 100));
		panelSuperior.add(playlistButton, BorderLayout.CENTER);

		volverButton.addActionListener(e -> {
			this.setVisible(false);
			VentanaCrearPlayList ventanaCrearPlayList = new VentanaCrearPlayList(this.principal);
			principal.add(ventanaCrearPlayList);
		});

		return panelSuperior;
	}
}
