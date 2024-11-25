package vista;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import modelo.Cancion;

public class PlayLists extends JButton {

	private String nombre;
	private ArrayList<String> cancionesS = new ArrayList();
	private ArrayList<Cancion> canciones = new ArrayList();
	private Reproductor reproductor;
	private VentanaPrincipal vp;

	public PlayLists(VentanaPrincipal vp, Reproductor reproductor) {

		this.setText(nombre);
		this.setPreferredSize(new Dimension(400, 50));
		this.reproductor = reproductor;
		this.vp = vp;
		this.llamarReproduccion();
	}

	public PlayLists(ArrayList<Cancion> canciones, Reproductor reproductor, VentanaPrincipal vp) {

		this.canciones = canciones;
		this.vp = vp;
		this.reproductor = reproductor;
		this.setText(nombre);
		this.setPreferredSize(new Dimension(400, 50));
		this.llamarReproduccion();
	}

	private void llamarReproduccion() {
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (reproductor != null) {

					reproductor.setPlaylist(PlayLists.this);
					vp.mostrarPanelPortada(PlayLists.this.getCanciones());
					reproductor.reproducir();
				}
			}
		});
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<String> getCancionesS() {
		return cancionesS;
	}

	public void setCancionesS(ArrayList<String> cancionesS) {
		this.cancionesS = cancionesS;
	}

	public ArrayList<Cancion> getCanciones() {
		return canciones;
	}

	public void setCanciones(ArrayList<Cancion> canciones) {
		this.canciones = canciones;
	}

}
