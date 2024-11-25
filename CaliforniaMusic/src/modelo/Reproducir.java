package modelo;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Reproducir {

	private Clip clip;
	private AudioInputStream audioStream;
	private long clipTimePosition;
	private boolean isPlaying;
	private boolean isPaused;

	public Reproducir() {
		this.clipTimePosition = 0;
		this.isPlaying = false;
		this.isPaused = false;
	}
	public void reproducirCancion(String rutaArchivo) {

		if (isPaused) {

			clip.setMicrosecondPosition(clipTimePosition);
			isPaused = false;
			continuarCancion();
		} else {
			detenerCancion();
			try {
				File audioFile = new File(rutaArchivo);
				audioStream = AudioSystem.getAudioInputStream(audioFile);
				clip = AudioSystem.getClip();
				clip.open(audioStream);

				clip.start();
				isPlaying = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


//	public void reproducirCancion(String rutaArchivo) {
//
//		if (isPaused) {
//
//			clip.setMicrosecondPosition(clipTimePosition);
//			isPaused = false;
//			continuarCancion();
//		} else {
//			detenerCancion();
//			try {
//				File audioFile = new File(rutaArchivo);
//				audioStream = AudioSystem.getAudioInputStream(audioFile);
//				clip = AudioSystem.getClip();
//				clip.open(audioStream);
//
//				clip.start();
//				isPlaying = true;
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}


	public void pausarCancion() {
		if (clip != null && clip.isRunning()) {
			clipTimePosition = clip.getMicrosecondPosition();
			clip.stop();
			isPlaying = false;
			isPaused = true;
		}
	}

	public void continuarCancion() {
		if (clip != null && !clip.isRunning() && clipTimePosition > 0) {
			clip.setMicrosecondPosition(clipTimePosition);
			clip.start();
			isPlaying = true;
			isPaused = false;
		}
	}

	public void detenerCancion() {
		if (clip != null) {
			clip.stop();
			clip.close();
			clip.flush();
			clipTimePosition = 0;
			isPlaying = false;
			isPaused = false;
		}
	}

	public void irAPosicion(long posicion) {
		if (posicion >= 0 && posicion <= this.getDuracionCancion()) {
			this.clipTimePosition = posicion;
			System.out.println("Reproduciendo desde la posición: " + posicion + " ms");
			if (clip != null) {
				clip.stop();
				clip.setMicrosecondPosition(posicion);
				clip.start();
			}
			isPlaying = true;
			isPaused = false;
		} else {
			System.out.println("Posición fuera de rango.");
		}
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public long getDuracionCancion() {
		return clip != null ? clip.getMicrosecondLength() : 0;
	}

	public long getPosicionActual() {
		return clip != null ? clip.getMicrosecondPosition() : 0;
	}
}
