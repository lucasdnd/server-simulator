package com.lucasdnd.serversimulator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.serversimulator.gameplay.Player;
import com.lucasdnd.serversimulator.ui.SideBar;

public class ServerSimulator extends ApplicationAdapter {

	// General stuff
	public static final String GAME_NAME = "Server Simulator";
	public static final String VERSION = "v0.1.0";
	private boolean debug = false;
	
	// Rendering, font
	private SpriteBatch fontBatch;
	private FontUtils font;
	private int playableAreaWidth, playableAreaHeight;
	
	// Game objects
	private Player player;

	// UI
	private SideBar sideBar;
	
	// Input, camera
	private InputHandler inputHandler;
	private ShapeRenderer shapeRenderer;
	private ShapeRenderer uiShapeRenderer;
	
	@Override
	public void create() {

		// Render, camera
		shapeRenderer = new ShapeRenderer();
 		uiShapeRenderer = new ShapeRenderer();
		fontBatch = new SpriteBatch();
		font = new FontUtils();
		
		// Input
		inputHandler = new InputHandler();
		Gdx.input.setInputProcessor(inputHandler);
		
		// Game Objects
		player = new Player();

		// UI
		sideBar = new SideBar(Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH, 0);
		playableAreaWidth = Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH;
		playableAreaHeight = Gdx.graphics.getHeight();
	}
	
	public void update() {
		
		// UI update
		sideBar.update();
		
		// Game objects
		player.update();
		
		inputHandler.refreshMouseClicks();
	}

	@Override
	public void render() {
		
		this.update();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// UI render
		sideBar.render(uiShapeRenderer);
		
		// Game render
		player.render();
	}
	
	public InputHandler getInputHandler() {
		return inputHandler;
	}

	public Player getPlayer() {
		return player;
	}
}
