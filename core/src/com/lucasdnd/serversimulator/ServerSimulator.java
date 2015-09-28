package com.lucasdnd.serversimulator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.serversimulator.gameplay.Market;
import com.lucasdnd.serversimulator.gameplay.Player;
import com.lucasdnd.serversimulator.gameplay.Request;
import com.lucasdnd.serversimulator.ui.ButtonClickListener;
import com.lucasdnd.serversimulator.ui.SideBar;

public class ServerSimulator extends ApplicationAdapter {

	// General stuff
	public static final String GAME_NAME = "Server Simulator";
	public static final String VERSION = "v0.1.0";
	private boolean debug = true;
	
	// Rendering, font
	public static final float BLOCK_SIZE = 8f;
	private SpriteBatch fontBatch;
	private FontUtils font;
	
	// Game objects
	private Market market;
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
		market = new Market();

		// UI
		sideBar = new SideBar(Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH, 0);
		sideBar.getNewFeaturesButton().setClickListener(new ButtonClickListener() {

			@Override
			public void onClick() {
				player.getSoftware().addFeatures();
			}
			
		});
		
		sideBar.getOptimizeButton().setClickListener(new ButtonClickListener() {

			@Override
			public void onClick() {
				player.optimizeSoftware();
			}
			
		});
		
		sideBar.getBugFixButton().setClickListener(new ButtonClickListener() {

			@Override
			public void onClick() {
				player.getSoftware().fixBug();
			}
			
		});
		
		sideBar.getAsyncIOButton().setClickListener(new ButtonClickListener() {

			@Override
			public void onClick() {
				player.getSoftware().setNonBlockingIO(true);
			}
			
		});
		
		sideBar.getBuyServerButton().setClickListener(new ButtonClickListener() {

			@Override
			public void onClick() {
				player.addNewServer();
			}
			
		});
		
	}
	
	private void handleInput() {
		
		if (Gdx.input.isKeyJustPressed(Keys.F3)) {
			debug = !debug;
		}
		
		if (debug) {
			if (Gdx.input.isKeyJustPressed(Keys.S)) {
				player.addNewServer();
			}
			if (Gdx.input.isKeyJustPressed(Keys.T)) {
				player.optimizeSoftware();
			}
			if (Gdx.input.isKeyJustPressed(Keys.R)) {
				player.createNewRequest();
			}
		}
	}
	
	public void update() {
		
		handleInput();
		
		// UI update
		sideBar.update();
		
		// Game objects
		market.update(player.getSoftware().getFeatures());
		player.update();
		
		inputHandler.refreshMouseClicks();
	}

	@Override
	public void render() {
		
		this.update();
		
		Gdx.gl.glClearColor(0, 0.5f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// UI render
		sideBar.render(uiShapeRenderer);
		
		// Game render
		player.render();
		
		// Debug
		if (debug) {
			float servers = player.getServers().size();
			float threadsPerServer = player.getServers().get(0).getThreads().size();
			float totalThreads = servers * threadsPerServer;
			font.drawWhiteFont("servers: " + servers, 0f, Gdx.graphics.getHeight(), false);
			font.drawWhiteFont("features: " + player.getSoftware().getFeatures(), 0f, Gdx.graphics.getHeight() - 20f, false);
			font.drawWhiteFont("demand: " + market.getDemand(), 0f, Gdx.graphics.getHeight() - 40f, false);
			font.drawWhiteFont("optimization: " + player.getSoftware().getOptimization(), 0f, Gdx.graphics.getHeight() - 60f, false);
			
//			Request request = player.getServers().get(0).getThreads().get(0).getRequest();
//			if (request != null) {
//				font.drawWhiteFont("request: " + request.getX(), 0f, Gdx.graphics.getHeight() - 100f, false);
//				font.drawWhiteFont("ticks: " + request.getTicks(), 0f, Gdx.graphics.getHeight() - 120f, false);
//				font.drawWhiteFont("ticks / 60: " + (request.getTicks() / 60f), 0f, Gdx.graphics.getHeight() - 140f, false);
//			} else {
//				font.drawWhiteFont("request: null", 0f, Gdx.graphics.getHeight() - 100f, false);
//			}
		}
	}
	
	public InputHandler getInputHandler() {
		return inputHandler;
	}

	public Player getPlayer() {
		return player;
	}
}
