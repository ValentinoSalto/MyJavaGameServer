package com.mygdx.game.pantallas;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music; 
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.recursos.Imagen;
import com.mygdx.game.red.Servidor;
import com.mygdx.game.utiles.Config;
import com.mygdx.game.utiles.EstiloFuente;
import com.mygdx.game.utiles.Recursos;
import com.mygdx.game.utiles.Render;

	
	
	public class ScreenMenu implements Screen {

		private ScreenViewport screenViewPort;
		private Stage stage;
		private Label textoJugador1;
		private Label textoJugador2;
		private Label textoTodoListo;
		private Table tabla, contenedor;
		private Label.LabelStyle estiloLabel;

		//private Servidor server;
		
		
		public ScreenMenu() {

			crearFuente();
			crearActores();
			poblarStage();
			
			//server = new Servidor();
			

		}

		public void crearFuente() {
			estiloLabel = EstiloFuente.generarFuente(32, "#ffffff");

		}

		public void crearActores() {

			screenViewPort = new ScreenViewport();
			stage = new Stage(screenViewPort);

			tabla = new Table();
			contenedor = new Table();
			textoJugador1 = new Label("Jugador 1 conectado", estiloLabel);
			textoJugador2 = new Label("Jugador 2 conectado", estiloLabel);
			textoTodoListo = new Label("Todo listo", estiloLabel);


		}

		public void poblarStage() {

			tabla.setFillParent(true);
//			stage.setDebugAll(true);

			contenedor.add(textoJugador1).pad(20);
			contenedor.row();
			contenedor.add(textoJugador2).pad(20);
			contenedor.row();
			contenedor.add(textoTodoListo).pad(20);
			contenedor.row();
			tabla.add(contenedor);
			stage.addActor(tabla);

		}

		@Override
		public void show() {
			// TODO Auto-generated method stub

		}

		@Override
		public void render(float delta) {
			stage.draw();
			stage.act();
		
			textoJugador1.setVisible(false);
			textoJugador2.setVisible(false);
			textoTodoListo.setVisible(false);
		}

		@Override
		public void resize(int width, int height) {
			screenViewPort.update(width, height, true);

		}

		@Override
		public void pause() {
			// TODO Auto-generated method stub

		}

		@Override
		public void resume() {
			// TODO Auto-generated method stub

		}

		@Override
		public void hide() {
			// TODO Auto-generated method stub

		}

		@Override
		public void dispose() {
			
		}
		

	}