package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import modelo.Reproducir;

public class Reproductor extends JPanel {

	private JButton botonPlay, botonPause, botonStop, botonForward, botonRewind;
	private JCheckBox checkBoxAleatorio;
	private JProgressBar progressBar;
	private Timer timer;

	private Reproducir reprod;
	private PlayLists playlist;
	private Portada portada;
	private ReproductorListener reproductorListener;

	private int currentTrackIndex;
	private boolean modoAleatorio = false;

	public Reproductor(Portada portada) {

		this.reprod = new Reproducir();
		this.playlist = null;
		this.currentTrackIndex = 0;

		setPreferredSize(new Dimension(600, 100));
		setBackground(new Color(33, 48, 61));

		initComponents();
		addComponents();
		addListeners();
		updateButtonStates();
	}

	private void initComponents() {

		botonPlay = new JButton("Play");
		botonPause = new JButton("Pause");
		botonStop = new JButton("Stop");
		botonForward = new JButton(">>|");
		botonRewind = new JButton("|<<");

		checkBoxAleatorio = new JCheckBox("Reproducción aleatoria");
		customizeCheckBox(checkBoxAleatorio);

		progressBar = new JProgressBar();

		progressBar.setPreferredSize(new Dimension(400, 20));
		progressBar.setStringPainted(true);
	}

	private void addComponents() {

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new FlowLayout());
		panelBotones.setBackground(new Color(33, 48, 61));
		panelBotones.add(botonRewind);
		panelBotones.add(botonPlay);
		panelBotones.add(botonPause);
		panelBotones.add(botonStop);
		panelBotones.add(botonForward);
		JPanel panelInferior = new JPanel();
		panelInferior.setLayout(new FlowLayout());
		panelInferior.setBackground(new Color(33, 48, 61));
		panelInferior.add(progressBar);

		add(panelBotones);
		add(panelInferior);
		add(checkBoxAleatorio);
	}

	private void addListeners() {

		botonPlay.addActionListener(e -> reproducir());
		botonPause.addActionListener(e -> pausar());
		botonStop.addActionListener(e -> detener());
		botonForward.addActionListener(e -> siguienteCancion());
		botonRewind.addActionListener(e -> anteriorCancion());

		checkBoxAleatorio.addActionListener(e -> actualizarModoAleatorio());

		progressBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int clickX = e.getX();
				int progressBarWidth = progressBar.getWidth();

				int newProgress = (int) ((double) clickX / progressBarWidth * 100);

				progressBar.setValue(newProgress);

				long nuevaPosicion = (long) (reprod.getDuracionCancion() * (newProgress / 100.0));

				reprod.irAPosicion(nuevaPosicion);
				actualizarBarraProgreso();
			}
		});
	}

	private void customizeCheckBox(JCheckBox checkBox) {

		checkBox.setOpaque(false);
		checkBox.setForeground(Color.WHITE);
		checkBox.setBorderPainted(false);
		checkBox.setContentAreaFilled(false);
		checkBox.setFont(new Font("Arial", Font.PLAIN, 14));

		checkBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				checkBox.setForeground(Color.BLUE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				checkBox.setForeground(Color.WHITE);
			}
		});
	}

	private void actualizarModoAleatorio() {
		modoAleatorio = checkBoxAleatorio.isSelected();
	}

	private void actualizarBarraProgreso() {
		long posicion = reprod.getPosicionActual();
		long duracion = reprod.getDuracionCancion();
		int progreso = duracion > 0 ? (int) ((posicion * 100) / duracion) : 0;
		progressBar.setValue(progreso);
	}

	private void notificarCambioCancion(String rutaImagen) {
		if (reproductorListener != null) {
			reproductorListener.cancionCambiada(rutaImagen);
		}
	}

	public void reproducir() {
		if (playlist != null && !playlist.getCanciones().isEmpty()) {

			if (modoAleatorio) {
				Random random = new Random();
				currentTrackIndex = random.nextInt(playlist.getCanciones().size());
			}
			String rutaCancion = playlist.getCanciones().get(currentTrackIndex).getArchivo();
			String rutaImagen = playlist.getCanciones().get(currentTrackIndex).getCaratula();
			System.out.println(rutaImagen);
			new Thread(() -> {
				reprod.reproducirCancion(rutaCancion);
				notificarCambioCancion(rutaImagen);
				iniciarBarraProgreso();
				updateButtonStates();
			}).start();
		}
	}

	private void pausar() {
		reprod.pausarCancion();
		detenerBarraProgreso();
		updateButtonStates();
	}

	private void detener() {
		reprod.detenerCancion();
		detenerBarraProgreso();
		progressBar.setValue(0);
		updateButtonStates();
	}

	private void siguienteCancion() {
		if (playlist != null && currentTrackIndex < playlist.getCanciones().size() - 1) {
			currentTrackIndex++;
			reproducir();
		}
	}

	private void anteriorCancion() {
		if (playlist != null && currentTrackIndex > 0) {
			currentTrackIndex--;
			reproducir();
		}
	}

	private void iniciarBarraProgreso() {
		detenerBarraProgreso();
		timer = new Timer(100, e -> {
			long posicion = reprod.getPosicionActual();
			long duracion = reprod.getDuracionCancion();
			int progreso = duracion > 0 ? (int) ((posicion * 100) / duracion) : 0;
			progressBar.setValue(progreso);

			if (progreso >= 100) {
				detenerBarraProgreso();
				siguienteCancion();
			}
		});
		timer.start();
	}

	public void setReproductorListener(ReproductorListener listener) {
		this.reproductorListener = listener;
	}

	private void detenerBarraProgreso() {
		if (timer != null) {
			timer.stop();
		}
	}

	private void updateButtonStates() {
		boolean hasPlaylist = playlist != null && !playlist.getCanciones().isEmpty();
		botonPlay.setEnabled(hasPlaylist);
		botonPause.setEnabled(hasPlaylist);
		botonStop.setEnabled(hasPlaylist);
		botonForward.setEnabled(hasPlaylist && currentTrackIndex < playlist.getCanciones().size() - 1);
		botonRewind.setEnabled(hasPlaylist && currentTrackIndex > 0);
	}

	public void setPlaylist(PlayLists playlist) {
		this.playlist = playlist;
		currentTrackIndex = 0;
		updateButtonStates();
	}

	public interface ReproductorListener {
		void cancionCambiada(String rutaImagen);
	}
}
