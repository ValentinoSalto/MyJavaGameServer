package com.mygdx.game.red;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.mygdx.game.pantallas.ScreenGame;
import com.mygdx.game.recursos.EstadosKnight;
import com.mygdx.game.recursos.Knight3;

public class HiloServidor extends Thread {

	private DatagramSocket socket;
	private boolean fin = false;
	private int cantConexiones = 0, maxConexiones = 2;
	private JugadorRed[] jugadores;
	private ScreenGame game;

	public HiloServidor(ScreenGame game) {
		jugadores = new JugadorRed[maxConexiones];
		this.game = game;
		try {
			socket = new DatagramSocket(56366);

		} catch (SocketException e) {
			fin = true;
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (!fin) {
			byte[] datos = new byte[1024];
			DatagramPacket dp = new DatagramPacket(datos, datos.length);
			try {
				socket.receive(dp);
				procesarMensaje(dp);
			} catch (IOException e) {
				// e.printStackTrace();
			}
		}
	}

	private void procesarMensaje(DatagramPacket dp) {
		String msg = new String(dp.getData()).trim();// trim() lo que hace es sacar los espacios
		String[] mensajeCompuesto = msg.split("#");
		
		System.out.println(msg);
		
		switch (mensajeCompuesto[0]) {
		case "conectar":

			if (cantConexiones == 0) {
				jugadores[0] = new JugadorRed(game.getJugador1(), dp.getAddress(), dp.getPort());
				cantConexiones++;
				System.out.println(cantConexiones + " Jugador Conectado");
				enviarMensaje("Exito#0", jugadores[0].ip, jugadores[0].puerto);
			} else if (cantConexiones == 1) {
				jugadores[1] = new JugadorRed(game.getJugador2(), dp.getAddress(), dp.getPort());
				cantConexiones++;
				System.out.println(cantConexiones + " Jugador Conectado");
				// Da el okey para empezar a jugar (los 2 jugadores)
				enviarMensaje("Exito#1", jugadores[1].ip, jugadores[1].puerto);
				enviarMensaje("empezo");

			} else if (cantConexiones >= 2) {
				enviarMensaje("Servidor lleno", dp.getAddress(), dp.getPort());

			}

			break;

		default:
			break;

		case "desconectar":
			if (dp.getAddress().equals(jugadores[0].ip) && dp.getPort() == jugadores[0].puerto) {
				enviarMensaje("salir_del_juego", jugadores[1].ip, jugadores[1].puerto);

			} else if (dp.getAddress().equals(jugadores[1].ip) && dp.getPort() == jugadores[1].puerto) {
				enviarMensaje("salir_del_juego", jugadores[0].ip, jugadores[0].puerto);
			}

			cantConexiones = 0;

			break;

		case "moverse":

			int idMensaje = (dp.getAddress().equals(jugadores[0].ip) && dp.getPort() == jugadores[0].puerto ? 0 : 1); // operador
																														// ternario

			if (mensajeCompuesto[1].equals("izquierda")) {
				//System.out.println("ir a la izquierda");
				jugadores[idMensaje].getEntidadJugador().updateAnimation(EstadosKnight.WALKING_LEFT);
				enviarMensaje("SeMovio#" + jugadores[idMensaje].getEntidadJugador().posicion.x + jugadores[idMensaje].getEntidadJugador().posicion.y,jugadores[0].ip, jugadores[0].puerto);
			}
			if (mensajeCompuesto[1].equals("correrizquierda")) {
				//System.out.println("correr a la izquierda");
				jugadores[idMensaje].getEntidadJugador().updateAnimation(EstadosKnight.RUN_LEFT);
				enviarMensaje("SeMovio#" + jugadores[idMensaje].getEntidadJugador().posicion.x + jugadores[idMensaje].getEntidadJugador().posicion.y,jugadores[0].ip, jugadores[0].puerto);
			}
			if (mensajeCompuesto[1].equals("derecha")) {
				//System.out.println("ir a la derecha");
				jugadores[idMensaje].getEntidadJugador().updateAnimation(EstadosKnight.WALKING_RIGHT);
				enviarMensaje("SeMovio#" + jugadores[idMensaje].getEntidadJugador().posicion.x + jugadores[idMensaje].getEntidadJugador().posicion.y,jugadores[0].ip, jugadores[0].puerto);
			}
			if (mensajeCompuesto[1].equals("correrderecha")) {
				//System.out.println("correr a la derecha");
				jugadores[idMensaje].getEntidadJugador().updateAnimation(EstadosKnight.RUN_RIGHT);
			}
			if (mensajeCompuesto[1].equals("arriba")) {
				//System.out.println("saltar");
				jugadores[idMensaje].getEntidadJugador().inciarSalto();
				enviarMensaje("SeMovio#" + jugadores[idMensaje].getEntidadJugador().posicion.x + jugadores[idMensaje].getEntidadJugador().posicion.y,jugadores[0].ip, jugadores[0].puerto);
			}
			if (mensajeCompuesto[1].equals("pasoplataforma")) {
				//System.out.println("paso plataforma");
				enviarMensaje(
						"SeMovio#" + jugadores[idMensaje].getEntidadJugador().posicion.x
								+ jugadores[idMensaje].getEntidadJugador().posicion.y,
						jugadores[0].ip, jugadores[0].puerto);
				jugadores[idMensaje].getEntidadJugador().updateAnimation(EstadosKnight.JUMP);
			}
			if (mensajeCompuesto[1].equals("ataca")) {
				System.out.println("ataca");
				jugadores[idMensaje].getEntidadJugador().updateAnimation(EstadosKnight.ATTACK);
			}

			break;
		}

		switch (mensajeCompuesto[0]) {
		}
	}

	public void enviarMensaje(String msg, InetAddress ipDestino, int puerto) {
		byte[] mensaje = msg.getBytes();
		try {
			DatagramPacket dp = new DatagramPacket(mensaje, mensaje.length, ipDestino, puerto);
			socket.send(dp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void enviarMensaje(String msg) {// Manda mensaje a todos los clientes
		byte[] mensaje = msg.getBytes();
		try {
			for (int i = 0; i < jugadores.length; i++) {
				DatagramPacket dp = new DatagramPacket(mensaje, mensaje.length, jugadores[i].ip, jugadores[i].puerto);
				socket.send(dp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void fin() {
		fin = true;
		socket.close();
	}

}
