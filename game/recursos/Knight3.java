package com.mygdx.game.recursos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.red.UtilesRed;
import com.mygdx.game.utiles.Animador;
import com.mygdx.game.utiles.Render;

public class Knight3 {

	private float alto, ancho;

	private Texture textura;
	private Animador animacionQuieto;
	private Animador animacionIzquierda;
	private Animador animacionDerecha;

	private Animador idleAnimation;
	private Animador walkingLeftAnimation;
	private Animador walkingRightAnimation;
	private Animador runAnimation;
	private Animador runLeftAnimation;
	private Animador attackAnimation;
	private Animador coverAnimation;
	private Animador coverWalkRightAnimation;
	private Animador coverWalkLeftAnimation;
	private Animador jumpAnimation;
	private Animador fallAnimation;

	private boolean ataqueIniciado = false;
	private float tiempoAtaque;
	private boolean atacando = false;
	private EstadosKnight estadoActual;
	private EstadosKnight direccion;
	public int vida = 100;
	public boolean bloqueando = false;
	public boolean jumping = false;
	public boolean falling = false;
	public boolean terminoSalto = true;
	public boolean cayendo = false;
	private boolean spacePressed = false;
	private float GROUND_LEVEL = 168f;
	public final float GRAVITY = -100; // Ajusta según la gravedad deseada
	public float JUMP_SPEED = 200; // Ajusta según la velocidad de salto deseada
	public final float RANGO_ATAQUE = 50; // Ajusta según el rango de ataque deseado
	private final float ALTURA_SALTO = 300f;
	public float ySpeed = 0;
	public boolean lastimable = false;
	boolean bloqueoActivo;
	boolean moverse = true;
	private int idJugador;
	float delta;

	// colisiones
	public Rectangle areaJugador;
	public Vector2 posicion;
	boolean pasoPlataforma = false;

	public Knight3(float x, float y, float ancho, float alto, int idJugador) {
		posicion = new Vector2();
		posicion.x = x;
		posicion.y = y;
		this.alto = alto;
		this.ancho = ancho;
		this.idJugador = idJugador;

		areaJugador = new Rectangle(this.posicion.x, this.posicion.y, this.ancho, this.alto / 2);
		crearAnimacion();
		estadoActual = EstadosKnight.IDLE;
	}

	/*public void moverPersonaje() {

		// Maneja las entradas del teclado para cambiar el estado del personaje
		if (Gdx.input.isKeyPressed(Keys.A) && !bloqueando && moverse /* && direccion != EstadosKnight.WALKING_LEFT ) {

			cambiarEstado(EstadosKnight.WALKING_LEFT);

			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
				cambiarEstado(EstadosKnight.RUN_LEFT);

			}

		} else if (Gdx.input.isKeyPressed(Keys.D) && !bloqueando
				&& moverse /* && direccion != EstadosKnight.WALKING_RIGHT ) {
			cambiarEstado(EstadosKnight.WALKING_RIGHT);
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
				cambiarEstado(EstadosKnight.RUN_RIGHT);

			}
		} else if (Gdx.input.isKeyJustPressed(Keys.SPACE) && !bloqueando) {

			cambiarEstado(EstadosKnight.JUMP);

		} else if (Gdx.input.isKeyJustPressed(Keys.E)) {
			encenderHoguera();

		} else if (Gdx.input.isButtonJustPressed(Buttons.LEFT)) {

			atacando = true;
			cambiarEstado(EstadosKnight.ATTACK);
			System.out.println("ataca");

		} else if (Gdx.input.isButtonPressed(Buttons.RIGHT)) {
			if (Gdx.input.isButtonPressed(Buttons.RIGHT)) {
				// Inicia la acción de bloqueo
				bloqueoActivo = true;
				// Cambia el estado del personaje a COVER
				cambiarEstado(EstadosKnight.COVER);
				if (Gdx.input.isKeyPressed(Keys.A)) {
					// spr.setRegion(coverWalkLeftAnimation.getKeyFrame(time, true));
					cambiarEstado(EstadosKnight.COVER_LEFT);
					bloqueando = true;

				} else if (Gdx.input.isKeyPressed(Keys.D)) {
					cambiarEstado(EstadosKnight.COVER_RIGHT);
					bloqueando = true;

				}

			}
		}
	}*/

	/*
	 * public void moverseRed(EstadosKnight estados) { updateAnimation(estados); }
	 */

	/*
	 * public void update(float delta) { this.delta = delta;
	 * 
	 * 
	 * if(!terminoSalto) {
	 * 
	 * if(jumping) { moverse = false; estadoActual = EstadosKnight.JUMP; saltar(); }
	 * 
	 * if(cayendo) { estadoActual = EstadosKnight.FALL; caer(); }
	 * UtilesRed.hs.enviarMensaje("seMovio#"+posicion.x+"#"+posicion.y+"#"+idJugador
	 * +"#"+estadoActual); }
	 * 
	 * }
	 */

	public void inciarSalto() {
		System.out.println("se llamo");
		// salto
		estadoActual = EstadosKnight.JUMP;
		jumping = true;
		moverse = false;
		terminoSalto = false;

	}

	public void saltar() {
		if (!cayendo) {

			posicion.y -= GRAVITY * delta;

			if (posicion.y < ALTURA_SALTO) {// la altura deseada
				posicion.y += JUMP_SPEED * delta; // esto se sube para arriba

			}

			if (posicion.y >= ALTURA_SALTO) {// aca ya estas cayendo
				cayendo = true;

			}
		}

	}

	public void caer() {
		if (!terminoSalto && cayendo) {
			estadoActual = EstadosKnight.FALL;
			posicion.y += GRAVITY * delta * 2;
			System.out.println(posicion.y);

			if (!pasoPlataforma) {

				if (posicion.y <= GROUND_LEVEL) {// la altura deseada
					posicion.y = GROUND_LEVEL; // aca cuando termino de caer
					terminoSalto = true;
					moverse = true;
					jumping = false;
					cayendo = false;
					estadoActual = EstadosKnight.IDLE;
				}
			} else {
				posicion.y = 260; // aca cuando termino de caer
				terminoSalto = true;
				moverse = true;
				jumping = false;
				cayendo = false;
				estadoActual = EstadosKnight.IDLE;
			}
		}

	}

	public void updateAnimation(EstadosKnight estados) {
		float delta = Gdx.graphics.getDeltaTime();

		switch (estados) {
		case IDLE:
			if (!jumping) {
				
				bloqueando = false;
				terminoSalto = true;
				moverse = true;
//			posicion.y = 145;
				atacando = false;
				break;

			} else {
				estadoActual = EstadosKnight.JUMP;
			}
			break;

		case WALKING_LEFT:
			if (estadoActual != EstadosKnight.JUMP) {

				// spr.setRegion(walkingLeftAnimation.getKeyFrame(time, true));
				posicion.x -= 3;
				
				atacando = false;
				UtilesRed.hs.enviarMensaje("seMovio" + "#" + posicion.x + "#" + posicion.y + "#" + idJugador);
				bloqueando = false;
				if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
					// spr.setRegion(runLeftAnimation.getKeyFrame(time, true));
					posicion.x -= 6;
					atacando = false;
					cambiarEstado(EstadosKnight.RUN_LEFT);

				}
			}
			break;

		case WALKING_RIGHT:
			if (estadoActual != EstadosKnight.JUMP) {

				// spr.setRegion(walkingRightAnimation.getKeyFrame(time, true));
				posicion.x += 3;
				
				atacando = false;
				UtilesRed.hs.enviarMensaje("seMovio" + "#" + posicion.x + "#" + posicion.y + idJugador);
				bloqueando = false;
				if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {

					// spr.setRegion(runLeftAnimation.getKeyFrame(time, true));
					posicion.x += 6;
					atacando = false;
					cambiarEstado(EstadosKnight.RUN_RIGHT);
				}

			}
			break;

		case JUMP:
			moverse = false;
			jumping = true;
			terminoSalto = false;
			atacando = false;

			if (posicion.y < ALTURA_SALTO) {// la altura deseada
				posicion.y += JUMP_SPEED * delta;
				UtilesRed.hs.enviarMensaje("saltando#" + posicion.x + "#" + posicion.y + "#" + idJugador);
				System.out.println("esta saltando");
				if (posicion.y >= ALTURA_SALTO) {
					estadoActual = EstadosKnight.FALL;

				}

			}

			break;

		case FALL:
			atacando = false;
			if (!pasoPlataforma) {

				if (posicion.y > GROUND_LEVEL) {
					posicion.y -= JUMP_SPEED * delta;
				}

				if (posicion.y <= GROUND_LEVEL) {
					jumping = false;
					terminoSalto = true;
					estadoActual = EstadosKnight.IDLE;
					moverse = true;
				} else {
					// spr.setRegion(fallAnimation.getKeyFrame(time, true));
				}
				UtilesRed.hs.enviarMensaje("cayendo#" + posicion.x + "#" + posicion.y + "#" + idJugador);
			} else {
				posicion.y = 260;
				estadoActual = EstadosKnight.IDLE;
				terminoSalto = true;
			}
			break;

		/*
		 * case RUN_RIGHT: //spr.setRegion(runAnimation.getKeyFrame(time, true));
		 * posicion.x += 6; break;
		 * 
		 * case RUN_LEFT: //spr.setRegion(runLeftAnimation.getKeyFrame(time, true));
		 * posicion.x -= 6; break;
		 */
		case ATTACK:

			// spr.setRegion(attackAnimation.getKeyFrame(time, true));
			break;

		case COVER:
			// spr.setRegion(coverAnimation.getKeyFrame(time, true));
			bloqueando = true;

			if (Gdx.input.isKeyPressed(Input.Keys.A)) {

				// spr.setRegion(coverWalkLeftAnimation.getKeyFrame(time, true));
				cambiarEstado(EstadosKnight.COVER_LEFT);
				bloqueando = true;
				posicion.x -= 3;

			} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {

				// spr.setRegion(coverWalkRightAnimation.getKeyFrame(time, true));
				cambiarEstado(EstadosKnight.COVER_RIGHT);
				bloqueando = true;
				posicion.x += 3;
			}
			break;
		case COVER_RIGHT:
			// spr.setRegion(coverWalkRightAnimation.getKeyFrame(time, true));

			break;
		case COVER_LEFT:
			// spr.setRegion(coverWalkLeftAnimation.getKeyFrame(time, true));

			break;

		}

		// spr.setPosition(posicion.x, posicion.y);

		/*
		 * if (ataqueIniciado) { tiempoAtaque += delta; if (tiempoAtaque <
		 * attackAnimation.getAnimationDuration()) {
		 * 
		 * // Guarda las dimensiones originales float tempWidth = spr.getWidth(); float
		 * tempHeight = spr.getHeight();
		 * 
		 * // Establece la región de ataque
		 * //spr.setRegion(attackAnimation.getKeyFrame(tiempoAtaque, false));
		 * 
		 * // Restaura las dimensiones originales spr.setSize(tempWidth, tempHeight);
		 * 
		 * } else {
		 * 
		 * ataqueIniciado = false; tiempoAtaque = 0f;
		 * //cambiarEstado(EstadosKnight.IDLE);
		 * 
		 * } } else {
		 * 
		 * // Si no está atacando, actualiza la animación normal
		 * Animation<TextureRegion> currentAnimation = getAnimationForCurrentState();
		 * //spr.setRegion(currentAnimation.getKeyFrame(time, true));
		 * 
		 * }
		 */

		areaJugador.setPosition(posicion.x, posicion.y);
		// UtilesRed.hs.enviarMensaje("seMovio#"+posicion.x+"#"+posicion.y+"#"+idJugador+"#"+estadoActual);
		// System.out.println(estadoActual);

	}

	public void cambiarEstado(EstadosKnight nuevoEstado) {
		estadoActual = nuevoEstado;
		// spr.setRegion(getAnimationForCurrentState().getKeyFrame(0));

		if (nuevoEstado == EstadosKnight.ATTACK) {
			iniciarAtaque();
		}

	}

	private void iniciarAtaque() {
		ataqueIniciado = true;
		tiempoAtaque = 0f;

	}

	public void restarVida(int cantidad) {

		if (!bloqueando) {
			vida -= cantidad;
		}

	}

	public void encenderHoguera() {

		if (!Hoguera.encendida) {
			if (posicion.x >= Hoguera.x - Hoguera.distancia || posicion.x <= Hoguera.x + Hoguera.distancia) {
				Hoguera.encendida = true;
			} else {
				System.out.println("No hay hoguera cerca");
			}
		}
	}

	/*
	 * public void chequearColisiones(Ghost area) {
	 * 
	 * if (areaJugador.overlaps(area.areaJugador)) { lastimable = true;
	 * //System.out.println("contacto"); area.atacarKnight(this);
	 * 
	 * }else { lastimable = false; }
	 * 
	 * }
	 */

	public void chequearColisionesMapa(Rectangle plataformas) {

		if (areaJugador.overlaps(plataformas)) {
			lastimable = true;
			System.out.println("contacto");
			direccion = estadoActual;
			pasoPlataforma = true;

		} else {
			lastimable = false;

			pasoPlataforma = false;

		}

	}

	public void getAnimationForCurrentState() {
		switch (estadoActual) {
		case IDLE:
			idleAnimation.render();
			break;

		case WALKING_LEFT:
			walkingLeftAnimation.render();
			break;

		case WALKING_RIGHT:
			walkingRightAnimation.render();
			break;

		case RUN_RIGHT:
			runAnimation.render();
			break;
		case RUN_LEFT:
			runLeftAnimation.render();
			break;
		case ATTACK:
			attackAnimation.render();
			break;
		case COVER:
			coverAnimation.render();
			break;
		case COVER_RIGHT:
			coverWalkRightAnimation.render();
			break;
		case COVER_LEFT:
			coverWalkLeftAnimation.render();
			break;
		case JUMP:
			jumpAnimation.render();
			break;
		case FALL:
			fallAnimation.render();
			break;
		default:
			idleAnimation.render();
			break;
		}
	}

	public void dibujarAreaInteraccion() {
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(Render.batch.getProjectionMatrix());
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(areaJugador.x, areaJugador.y, areaJugador.width, areaJugador.height);
		shapeRenderer.end();
	}

	private void crearAnimacion() {
		// Crea animaciones
		idleAnimation = new Animador(Texturas.KnightSpriteSheet, posicion, 0, 6, 11);
		walkingLeftAnimation = new Animador(Texturas.KnightSpriteSheet, posicion, 2, 6, 11);
		walkingRightAnimation = new Animador(Texturas.KnightSpriteSheet, posicion, 1, 6, 11);
		runAnimation = new Animador(Texturas.KnightSpriteSheet, posicion, 3, 6, 11);
		runLeftAnimation = new Animador(Texturas.KnightSpriteSheet, posicion, 4, 6, 11);
		attackAnimation = new Animador(Texturas.KnightSpriteSheet, posicion, 10, 6, 11);
		coverAnimation = new Animador(Texturas.KnightSpriteSheet, posicion, 7, 6, 11);
		coverWalkRightAnimation = new Animador(Texturas.KnightSpriteSheet, posicion, 8, 6, 11);
		coverWalkLeftAnimation = new Animador(Texturas.KnightSpriteSheet, posicion, 9, 6, 11);
		jumpAnimation = new Animador(Texturas.KnightSpriteSheet, posicion, 5, 6, 11);
		fallAnimation = new Animador(Texturas.KnightSpriteSheet, posicion, 6, 6, 11);

	}

	public void setPosition(float newX, float newY) {
		posicion.x = newX;
		posicion.y = newY;
	}

	public float getX() {
		return posicion.x;
	}

	public float getY() {
		return posicion.y;
	}

	public float getWidth() {
		return alto;
	}

	public float getHeight() {
		return ancho;
	}

	public Vector2 getPosicion() {
		return posicion;
	}

	public void dispose() {

	}

}