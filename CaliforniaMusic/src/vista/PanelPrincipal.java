package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import modelo.Cancion;

import modelo.Reproducir;

public class PanelPrincipal extends JPanel {

	private BufferedImage backgroundImage;
	private ArrayList<Cancion> canciones;

	private Portada portada;
	private Cancion cancion;
	private VentanaPrincipal vp;
	private boolean estado = false;
	private Reproductor reproductor;

	public PanelPrincipal(VentanaPrincipal vp, Reproductor reproductor, Portada portada) {

		this.vp = vp;
		this.reproductor = reproductor;
		this.portada = portada;
		cancion = new Cancion();
		canciones = cancion.getCanciones();

		reproductor.setReproductorListener(new Reproductor.ReproductorListener() {
			@Override
			public void cancionCambiada(String rutaImagen) {

				portada.actualizarImagenCancion(rutaImagen);
			}
		});

		añadeFondo();

		JPanel backgroundPanel = creaPanelPpal();
		JPanel topPanel = creaTopPanel();
		JPanel songsPanel = creaSongsPanel();

		backgroundPanel.add(topPanel, BorderLayout.NORTH);
		backgroundPanel.add(songsPanel, BorderLayout.CENTER);

		add(backgroundPanel, BorderLayout.CENTER);

		setVisible(true);
	}

	JPanel creaPanelPpal() {

		JPanel backgroundPanel = new JPanel() {

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (backgroundImage != null) {
					g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
				}
			}
		};
		backgroundPanel.setLayout(new BorderLayout());
		backgroundPanel.setPreferredSize(new Dimension(600, 480));

		return backgroundPanel;
	}

	private void añadeFondo() {
		try {
			backgroundImage = ImageIO.read(new File("resources/images/jukebox7.png"));
			if (backgroundImage == null) {
				System.out.println("No se pudo cargar la imagen.");
			}
		} catch (IOException e) {
			System.out.println("Error al cargar la imagen: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private JPanel creaTopPanel() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

		JLabel titleLabel = new JLabel("California Jukebox");
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
		titleLabel.setForeground(Color.WHITE);

		JButton goToPlaylistButton = new JButton("Ir a tus Playlist");
		goToPlaylistButton.setAlignmentX(CENTER_ALIGNMENT);
		goToPlaylistButton.setFont(new Font("Arial", Font.BOLD, 14));
		goToPlaylistButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				goToPlaylist();
			}
		});
		customizeButton(goToPlaylistButton);
		JCheckBoxMenuItem jCheckMenu1 = new JCheckBoxMenuItem("Información Canciones");
		jCheckMenu1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		jCheckMenu1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				estado = !estado;
			}
		});
		customizeCheckBoxMenuItem(jCheckMenu1);

		topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		topPanel.add(titleLabel);
		topPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		topPanel.add(goToPlaylistButton);
		topPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		topPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		topPanel.add(jCheckMenu1);
		topPanel.setOpaque(false);

		return topPanel;
	}

	private void customizeCheckBoxMenuItem(JCheckBoxMenuItem checkBoxMenuItem) {

		checkBoxMenuItem.setOpaque(false);
		checkBoxMenuItem.setForeground(Color.WHITE);
		checkBoxMenuItem.setBorderPainted(false);
		checkBoxMenuItem.setContentAreaFilled(false);
		checkBoxMenuItem.setFont(new Font("Arial", Font.PLAIN, 14));

		checkBoxMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				checkBoxMenuItem.setForeground(Color.RED);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				checkBoxMenuItem.setForeground(Color.WHITE);
			}
		});
	}

	private JPanel creaSongsPanel() {
		JPanel songsPanel = new JPanel();
		songsPanel.setLayout(new GridLayout(5, 2, 10, 10));

		for (int i = 0; i < 10; i++) {
			JButton songButton = new JButton(canciones.get(i).getTitulo());
			songButton.setFont(new Font("Arial", Font.BOLD, 18));
			songButton.setForeground(java.awt.Color.WHITE);
			songButton.setToolTipText(canciones.get(i).getDescripcion());
			final Cancion cancionSeleccionada = canciones.get(i);
			ArrayList<Cancion> cancionesS = new ArrayList();
			cancionesS.add(cancionSeleccionada);

			songButton.setPreferredSize(new Dimension(250, 30));
			songButton.setMaximumSize(new Dimension(250, 30));
			songButton.setMinimumSize(new Dimension(200, 30));

			songButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if (!estado) {
						PlayLists playList = new PlayLists(cancionesS, vp.obtenerReprod(), vp);
						reproductor.setPlaylist(playList);
						vp.mostrarPanelPortada(cancionSeleccionada);

					} else {

						String informacionCancion = "<html><b>Título:</b> " + cancionSeleccionada.getTitulo() + "<br>"
								+ "<html><b>Artista:</b> " + cancionSeleccionada.getArtista() + "<br>"
								+ "<b>Álbum:</b> " + cancionSeleccionada.getAlbum() + "<br>" + "<b>Descripción:</b> "
								+ cancionSeleccionada.getDescripcion() + "</html>";

						JOptionPane.showMessageDialog(songsPanel, informacionCancion, "Información de la Canción",
								JOptionPane.INFORMATION_MESSAGE);

					}
				}
			});
			customizeButton(songButton);
			songsPanel.add(songButton);

		}

		songsPanel.setOpaque(false);

		return songsPanel;
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

	private void goToPlaylist() {

		vp.mostrarVentanaCrearPlayList();

	}
}
