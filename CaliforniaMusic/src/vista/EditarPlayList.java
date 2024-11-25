package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import modelo.Cancion;

public class EditarPlayList extends JPanel {

	private ArrayList<Cancion> cancionesDisponibles;
	private ArrayList<Cancion> cancionesSeleccionadas;
	private JPanel panelIzquierdo, panelDerecho;
	private DefaultListModel<String> modelDisponibles, modelSeleccionadas;
	private JList<String> listaCancionesDisponibles, listaCancionesSeleccionadas;
	private JButton finalizarButton;
	private PlayLists playList;
	private VentanaPrincipal principal;
	private JTextField campoTexto;

	public EditarPlayList(PlayLists playList, VentanaPrincipal principal) {
		this.principal = principal;
		this.playList = playList;
		Cancion cancion = new Cancion();

		ArrayList<Cancion> todasLasCanciones = cancion.getCanciones();

		cancionesSeleccionadas = playList.getCanciones();
		cancionesDisponibles = new ArrayList<>();
		for (Cancion c : todasLasCanciones) {
			if (!cancionesSeleccionadas.contains(c)) {
				cancionesDisponibles.add(c);
			}
		}

		crearPaneles();

		setLayout(new BorderLayout());
		add(crearPanelSuperior(), BorderLayout.NORTH);
		add(crearContenedorPaneles(), BorderLayout.CENTER);
		add(crearPanelBoton(), BorderLayout.SOUTH);

	}

	public JPanel crearPanelSuperior() {
		JPanel panelSuperior = new JPanel();
		panelSuperior.setPreferredSize(new Dimension(600, 100));
		panelSuperior.setLayout(new BorderLayout());

		JLabel titulo = new JLabel("Nombre de la Playlist");
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 16));
		panelSuperior.add(titulo, BorderLayout.NORTH);

		campoTexto = new JTextField();
		campoTexto.setHorizontalAlignment(JTextField.CENTER);
		campoTexto.setFont(new Font("Arial", Font.BOLD, 20));
		campoTexto.setText(playList.getNombre());
		panelSuperior.add(campoTexto);
		return panelSuperior;
	}

	private void crearPaneles() {

		panelIzquierdo = new JPanel();
		panelIzquierdo.setLayout(new BorderLayout());

		modelDisponibles = new DefaultListModel<>();
		for (Cancion cancion : cancionesDisponibles) {
			if (!cancionesSeleccionadas.contains(cancion)) {
				modelDisponibles.addElement(cancion.getTitulo());
			}
		}

		listaCancionesDisponibles = new JList<>(modelDisponibles);
		listaCancionesDisponibles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaCancionesDisponibles.setFont(new Font("Arial", Font.PLAIN, 14));
		listaCancionesDisponibles.setFixedCellHeight(30);

		listaCancionesDisponibles.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					int selectedIndex = listaCancionesDisponibles.getSelectedIndex();
					if (selectedIndex != -1) {

						String cancionSeleccionada = modelDisponibles.getElementAt(selectedIndex);

						modelDisponibles.remove(selectedIndex);
						modelSeleccionadas.addElement(cancionSeleccionada);

						Cancion cancion = obtenerCancionPorTitulo(cancionSeleccionada);
						cancionesSeleccionadas.add(cancion);
						cancionesDisponibles.remove(cancion);

					}
				}
			}
		});

		JScrollPane scrollIzquierdo = new JScrollPane(listaCancionesDisponibles);
		panelIzquierdo.add(scrollIzquierdo, BorderLayout.CENTER);
		panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Canciones Disponibles"));

		panelDerecho = new JPanel();
		panelDerecho.setLayout(new BorderLayout());

		modelSeleccionadas = new DefaultListModel<>();

		for (Cancion cancion : cancionesSeleccionadas) {
			modelSeleccionadas.addElement(cancion.getTitulo());
		}

		listaCancionesSeleccionadas = new JList<>(modelSeleccionadas);
		listaCancionesSeleccionadas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaCancionesSeleccionadas.setFont(new Font("Arial", Font.PLAIN, 14));
		listaCancionesSeleccionadas.setFixedCellHeight(30);

		listaCancionesSeleccionadas.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					int selectedIndex = listaCancionesSeleccionadas.getSelectedIndex();
					if (selectedIndex != -1) {
						String cancionSeleccionada = modelSeleccionadas.getElementAt(selectedIndex);

						modelSeleccionadas.remove(selectedIndex);
						modelDisponibles.addElement(cancionSeleccionada);

						cancionesSeleccionadas.removeIf(c -> c.getTitulo().equals(cancionSeleccionada));
						Cancion cancion = obtenerCancionPorTitulo(cancionSeleccionada);
						cancionesDisponibles.add(cancion);

					}
				}
			}
		});

		JScrollPane scrollDerecho = new JScrollPane(listaCancionesSeleccionadas);
		panelDerecho.add(scrollDerecho, BorderLayout.CENTER);
		panelDerecho.setBorder(BorderFactory.createTitledBorder("Canciones Seleccionadas"));
	}

	private Cancion obtenerCancionPorTitulo(String titulo) {
		for (Cancion cancion : cancionesDisponibles) {
			if (cancion.getTitulo().equals(titulo)) {
				return cancion;
			}
		}
		for (Cancion cancion : cancionesSeleccionadas) {
			if (cancion.getTitulo().equals(titulo)) {
				return cancion;
			}
		}
		return null;
	}

	private JPanel crearContenedorPaneles() {
		JPanel contenedor = new JPanel();
		contenedor.setLayout(new GridLayout(1, 2));
		contenedor.add(panelIzquierdo);
		contenedor.add(panelDerecho);
		return contenedor;
	}

	private JPanel crearPanelBoton() {
		finalizarButton = new JButton("Finalizar");
		finalizarButton.setFont(new Font("Arial", Font.BOLD, 14));
		finalizarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				finalizarPlayList();
			}
		});
		JPanel panelBoton = new JPanel();
		panelBoton.add(finalizarButton);
		return panelBoton;
	}

	private void moverCancionASeleccionadas(Cancion cancionSeleccionada, int selectedIndex) {
		modelDisponibles.remove(selectedIndex);
		modelSeleccionadas.addElement(cancionSeleccionada.getTitulo());
		cancionesSeleccionadas.add(cancionSeleccionada);
		cancionesDisponibles.remove(selectedIndex);
	}

	private void finalizarPlayList() {

		String nombrePlaylist = campoTexto.getText().trim();
		if (nombrePlaylist.isEmpty()) {
			JOptionPane.showMessageDialog(this, "El nombre de la playlist no puede estar vacío.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (cancionesSeleccionadas.isEmpty()) {
			JOptionPane.showMessageDialog(this, "La playlist debe contener al menos una canción.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas finalizar la playlist?",
				"Confirmar Playlist", JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			ArrayList<Cancion> playlist = obtenerPlaylist();
			playList.setCanciones(playlist);
			System.out.println("Playlist Finalizado:");
			for (Cancion cancion : playlist) {
				System.out.println(cancion.getTitulo());
			}
			playList.setNombre(this.campoTexto.getText());
			JOptionPane.showMessageDialog(this, "Playlist Finalizada.");
			principal.mostrarVentanaCrearPlayList();
		}
	}

	private ArrayList<Cancion> obtenerPlaylist() {
		return cancionesSeleccionadas;
	}
}