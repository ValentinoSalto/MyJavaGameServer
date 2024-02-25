package com.mygdx.game.red;

import com.mygdx.game.pantallas.ScreenGame;
import com.mygdx.game.utiles.Recursos;
import com.mygdx.game.utiles.Render;

public class Servidor {

	
	public Servidor(ScreenGame game) {
		UtilesRed.game = game;
		UtilesRed.hs = new HiloServidor(UtilesRed.game);
		UtilesRed.hs.start();
		System.out.println("Servidor iniciado");
	}
	
	public void cerrarHilo() {
		UtilesRed.hs.fin();//sacar de aca
	}
}
