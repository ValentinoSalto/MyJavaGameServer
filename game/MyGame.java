package com.mygdx.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.pantallas.ScreenGame;
import com.mygdx.game.pantallas.ScreenLoad;
import com.mygdx.game.pantallas.ScreenMenu;
import com.mygdx.game.red.UtilesRed;
import com.mygdx.game.utiles.Render;

public class MyGame extends Game {
	
	//JUEGO EN NUEVA VERSION
	

	
	
	
	@Override
	public void create () {
		
		System.out.println("Bienvenido");
		Render.app = this;
		Render.batch = new SpriteBatch();
		this.setScreen(new ScreenGame()); //Muestro pantalla de carga.
		
		

		
		
	}
	
	
    
	@Override
	public void render () {
	    ScreenUtils.clear(0, 0, 0, 1);
		super.render();
		
	}
	
	public void resize(int width, int height) {
		
	}
	
	@Override
	public void dispose () {
		UtilesRed.hs.fin();
	}
}
