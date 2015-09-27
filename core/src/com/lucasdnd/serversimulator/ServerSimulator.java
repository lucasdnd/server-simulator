package com.lucasdnd.serversimulator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
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

	// UI
	private SideBar sideBar;
	
	// Input, camera
	private InputHandler inputHandler;
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private ShapeRenderer uiShapeRenderer;
	
	@Override
	public void create() {

		// Render, camera
		shapeRenderer = new ShapeRenderer();
 		uiShapeRenderer = new ShapeRenderer();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		fontBatch = new SpriteBatch();
		font = new FontUtils();
		
		// Input
		inputHandler = new InputHandler();
		Gdx.input.setInputProcessor(inputHandler);

		// UI
		sideBar = new SideBar(Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH, 0);
		playableAreaWidth = Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH;
		playableAreaHeight = Gdx.graphics.getHeight();
	}
	
	public void update() {
		
		// Camera update
//		camera.position.set(player.getX() * OnePixel.blockSize + (SideBar.SIDEBAR_WIDTH * 0.5f), player.getY() * OnePixel.blockSize, 0f);
		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		// UI update
		sideBar.update();
		
		inputHandler.refreshMouseClicks();
	}

	@Override
	public void render() {
		
		this.update();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// UI Render
		sideBar.render(uiShapeRenderer);
	}
	
	public InputHandler getInputHandler() {
		return inputHandler;
	}
}
