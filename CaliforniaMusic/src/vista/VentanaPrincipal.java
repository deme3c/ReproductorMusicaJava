package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;

import modelo.Cancion;

public class VentanaPrincipal extends JFrame {

	private VentanaCrearPlayList vcpl = new VentanaCrearPlayList(this);
	private boolean existeVcpl;
	private Portada portada;
	private Reproductor reproductor;
	private VentanaPlayList ventanaPlayList;

	public VentanaPrincipal() {

		existeVcpl = false;
		this.portada = new Portada(this);
		reproductor = new Reproductor(this.portada);
		ventanaPlayList = new VentanaPlayList(this, new PlayLists(this, this.reproductor));
		creaVentanaPpal();
	}

	private void creaVentanaPpal() {

		setTitle("California JukeBox");
		Toolkit miPantalla = Toolkit.getDefaultToolkit();
		Dimension dimension = miPantalla.getScreenSize();
		int ancho = (int) dimension.getWidth();
		int alto = (int) dimension.getHeight();
		ancho = (ancho - 600) / 2;
		alto = (alto - 600) / 2;
		setBounds(ancho, alto, 600, 600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		mostrarPanelPrincipal();
		add(reproductor, BorderLayout.SOUTH);

		setVisible(true);
	}

	public VentanaPlayList getVentanaPlayList() {

		return ventanaPlayList;
	}

	public void mostrarPanelPrincipal() {

		getContentPane().removeAll();
		PanelPrincipal panelPrincipal = new PanelPrincipal(this, this.reproductor, this.portada);
		add(panelPrincipal, BorderLayout.CENTER);
		add(reproductor, BorderLayout.SOUTH);
		revalidate();
		repaint();
	}

	public void mostrarPanelPortada(Cancion cancion) {
		getContentPane().removeAll();
		Portada panel = new Portada(this);
		add(this.portada, BorderLayout.CENTER);
		add(reproductor, BorderLayout.SOUTH);
		revalidate();
		repaint();
	}

	public void mostrarPanelPortada(ArrayList<Cancion> cancion) {
		getContentPane().removeAll();
		Portada panel = new Portada(this);
		add(this.portada, BorderLayout.CENTER);
		add(reproductor, BorderLayout.SOUTH);
		revalidate();
		repaint();
	}

	public void mostrarVentanaCrearPlayList() {

		getContentPane().removeAll();
		VentanaCrearPlayList crearPlayListPanel = new VentanaCrearPlayList(this);
		add(crearPlayListPanel, BorderLayout.CENTER);
		add(reproductor, BorderLayout.SOUTH);
		this.existeVcpl = true;
		revalidate();
		repaint();
	}

	public Reproductor obtenerReprod() {
		return this.reproductor;

	}

	public void mostrarVentanaPlayList() {

		if (ventanaPlayList == null) {
			ventanaPlayList = new VentanaPlayList(this, new PlayLists(this, this.reproductor));
		}
		getContentPane().removeAll();
		add(ventanaPlayList, BorderLayout.CENTER);
		add(reproductor, BorderLayout.SOUTH);
		revalidate();
		repaint();
	}

}