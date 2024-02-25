package com.mygdx.game.red;

import java.net.InetAddress;

import com.mygdx.game.recursos.Knight3;
import com.mygdx.game.recursos.KnightBlue;

public class JugadorRed {

	private Knight3 jugador;
	public InetAddress ip;
	public int puerto;
	
	private KnightBlue jugador2;
	public InetAddress ip2;
	public int puerto2;

	public JugadorRed(Knight3 jugador, InetAddress ip, int puerto) {
		this.jugador = jugador;
		this.ip = ip;
		this.puerto = puerto;
	}

	public Knight3 getEntidadJugador() {
		return jugador;
	}
	
	/*public JugadorRed(KnightBlue jugador2, InetAddress ip2, int puerto2) {
		this.jugador2 = jugador2;
		this.ip2 = ip2;
		this.puerto2 = puerto2;
	}

	public KnightBlue getEntidadJugador2() {
		return jugador2;
	}
	*/
}