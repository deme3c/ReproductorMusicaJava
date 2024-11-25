package modelo;

import java.util.ArrayList;
import java.util.Objects;

public class Cancion {

	private String titulo;
	private String artista;
	private String album;
	private String archivo;
	private String caratula;
	private String descripcion;
	private ArrayList<Cancion> canciones;

	public ArrayList<Cancion> getCanciones() {
		return canciones;
	}

	public void setCanciones(ArrayList<Cancion> canciones) {
		this.canciones = canciones;
	}

	public Cancion(String titulo, String artista, String album, String archivo, String caratula, String descripcion) {
		super();
		this.titulo = titulo;
		this.artista = artista;
		this.album = album;
		this.archivo = archivo;
		this.caratula = caratula;
		this.descripcion = descripcion;

	}

	public Cancion() {
		canciones = new ArrayList<>();
		cargarCanciones();
	}

	private void cargarCanciones() {

		canciones.add(new Cancion("California Dreamin", " The Mamas & the Papas", "California Dreamin'",
				"resources/archivosCanciones/c1.wav", "resources/images/car1.jpg",
				"Una de las canciones más icónicas sobre California, que habla sobre el deseo de escapar al clima cálido del estado."));
		canciones.add(new Cancion("Hotel California", "Eagles", "Hotel California",
				"resources/archivosCanciones/c2.wav", "resources/images/car2.jpg",
				"Esta clásica canción de rock pinta una imagen surrealista de una experiencia en un misterioso hotel en California."));
		canciones.add(new Cancion("California Love", "2Pac ft. Dr. Dre", " All Eyez on Me",
				"resources/archivosCanciones/c3.wav", "resources/images/2pac.jpg",
				"Un himno del hip-hop que celebra la vida en California, especialmente en Los Ángeles."));
		canciones.add(new Cancion("California Girls", "The Beach Boys", "Summer Days (and Summer Nights!!)",
				"resources/archivosCanciones/c4.wav", "resources/images/car4.jpg",
				"Un homenaje a las chicas de California y el estilo de vida playero del estado."));
		canciones.add(new Cancion("Going to California", "Led Zeppelin", "Led Zeppelin IV",
				"resources/archivosCanciones/c5.wav", "resources/images/car5.jpg",
				"Esta canción melancólica trata sobre un viaje a California en busca de una vida mejor y libertad."));
		canciones.add(new Cancion("Dani California", "Red Hot Chili Peppers", "Californication",
				"resources/archivosCanciones/c6.wav", "resources/images/car6.jpg",
				"Una historia sobre una chica llamada Dani y su vida en el estado dorado."));
		canciones.add(new Cancion("California", "Phantom Planet", "California", "resources/archivosCanciones/c7.wav",
				"resources/images/car7.jpg",
				"Famosa por ser la canción de apertura de la serie The O.C., esta canción captura la esencia del deseo de estar en California.\r\n"));
		canciones.add(new Cancion("California Waiting", "Kings of Leon", "Youth And Young Manhood",
				"resources/archivosCanciones/c8.wav", "resources/images/car8.jpg",
				"Esta canción expresa impaciencia y anticipación, con California como símbolo de nuevas oportunidades."));
		canciones.add(new Cancion("California", "Joni Mitchell", "Azul", "resources/archivosCanciones/c9.wav",
				"resources/images/car9.jpg",
				"Un reflejo poético sobre regresar a California después de viajar por el mundo."));
		canciones.add(new Cancion("California", " Grimes", "Art Angels", "resources/archivosCanciones/c10.wav",
				"resources/images/car10.jpg",
				"Un tema más reciente que habla sobre las dificultades y el encanto de la vida en el estado."));
	}


	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public String getCaratula() {
		return caratula;
	}

	public void setCaratula(String caratula) {
		this.caratula = caratula;
	}

}
