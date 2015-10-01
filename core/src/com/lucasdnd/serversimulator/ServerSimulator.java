package com.lucasdnd.serversimulator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.lucasdnd.serversimulator.gameplay.Market;
import com.lucasdnd.serversimulator.gameplay.Player;
import com.lucasdnd.serversimulator.gameplay.Request;
import com.lucasdnd.serversimulator.gameplay.Software;
import com.lucasdnd.serversimulator.ui.ButtonClickListener;
import com.lucasdnd.serversimulator.ui.SideBar;

public class ServerSimulator extends ApplicationAdapter {

	// General stuff
	public static final String GAME_NAME = "Server Simulator";
	public static final String VERSION = "v0.1.0";
	private boolean debug = true;
	
	// Rendering, font
	public static final float BLOCK_SIZE = 8f;
	private FontUtils font;
	
	// Game objects
	private TimeController timeController;
	private Market market;
	private Player player;

	// UI
	private SideBar sideBar;
	
	// Input, camera
	private InputHandler inputHandler;
	private ShapeRenderer uiShapeRenderer;
	
	@Override
	public void create() {

		// Render, camera
 		uiShapeRenderer = new ShapeRenderer();
		font = new FontUtils();
		
		// Input
		inputHandler = new InputHandler();
		Gdx.input.setInputProcessor(inputHandler);
		
		// Game Objects
		timeController = new TimeController();
		player = new Player();
		market = new Market();

		// UI
		sideBar = new SideBar(Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH, 0);
		
		sideBar.getIncreasePriceButton().setClickListener(new ButtonClickListener() {

			@Override
			public void onClick() {
				player.increaseServicePrice();
			}
			
		});
		
		sideBar.getDecreasePriceButton().setClickListener(new ButtonClickListener() {

			@Override
			public void onClick() {
				player.decreaseServicePrice();
			}
			
		});
		
		sideBar.getNewFeaturesButton().setClickListener(new ButtonClickListener() {

			@Override
			public void onClick() {
				if (player.spendMoney(player.getFeaturesPrice())) {
					player.getSoftware().addFeatures();
				}
				
				if (player.getSoftware().getFeatures() >= Software.maxFeatures) {
					sideBar.getNewFeaturesButton().setEnabled(false);
				}
				
				updateBugFixButtonState();
			}
			
		});
		
		sideBar.getBuyThreadButton().setClickListener(new ButtonClickListener() {

			@Override
			public void onClick() {
				if (player.spendMoney(player.getThreadPrice())) {
					player.getSoftware().addNewThread();
				}
				
				if (player.getSoftware().getThreads().size() >= Software.maxThreads) {
					sideBar.getBuyThreadButton().setEnabled(false);
				}
			}
			
		});
		
		sideBar.getOptimizeButton().setClickListener(new ButtonClickListener() {

			@Override
			public void onClick() {
				if (player.spendMoney(player.getOptimizationPrice())) {
					player.optimizeSoftware();
				}
				
				if (player.getSoftware().getOptimization() >= Software.maxOptimization) {
					sideBar.getOptimizeButton().setEnabled(false);
				}
			}
			
		});
		
		updateBugFixButtonState();
		sideBar.getBugFixButton().setClickListener(new ButtonClickListener() {

			@Override
			public void onClick() {
				if (player.spendMoney(player.getBugFixPrice())) {
					player.getSoftware().fixBug();
				}
				
				updateBugFixButtonState();
			}
			
		});
		
		sideBar.getAsyncIOButton().setClickListener(new ButtonClickListener() {

			@Override
			public void onClick() {
				if (player.spendMoney(player.getAsyncIOPrice())) {
					player.getSoftware().setNonBlockingIO(true);
				}
				
				sideBar.getAsyncIOButton().setEnabled(player.getSoftware().isNonBlockingIO() == false);
			}
			
		});
		
		sideBar.getNewGameButton().setClickListener(new ButtonClickListener() {

			@Override
			public void onClick() {
				create();
			}
			
		});
	}
	
	private void updateBugFixButtonState() {
		sideBar.getBugFixButton().setEnabled(player.getSoftware().getBugs() != 0);
	}
	
	private void handleInput() {
		
		if (Gdx.input.isKeyJustPressed(Keys.D)) {
			debug = !debug;
		}
		
		if (debug) {
			if (Gdx.input.isKeyJustPressed(Keys.R)) {
				this.create();
			}
		}
	}
	
	public void update() {
		
		handleInput();
		
		// UI update
		sideBar.update();
		
		// Game objects
		timeController.update();
		market.update(this);
		player.update(this);
		
		inputHandler.refreshMouseClicks();
	}

	@Override
	public void render() {
		
		this.update();
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// UI render
		font.drawWhiteFont("Request time", 20f, Gdx.graphics.getHeight() - 20f, true);
		font.drawWhiteFont("I/O time", 0f, Gdx.graphics.getHeight() - 20f, true, Align.center, Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH);
		if (player.getSoftware().isNonBlockingIO()) {
			font.drawWhiteFont("(non-blocking)", 0f, Gdx.graphics.getHeight() - 40f, true, Align.center, Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH);
		} else {
			font.drawWhiteFont("(blocking)", 0f, Gdx.graphics.getHeight() - 40f, true, Align.center, Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH);
		}
		font.drawWhiteFont("Response time", Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH - 180f, Gdx.graphics.getHeight() - 20f, true);
		sideBar.render(this, uiShapeRenderer);
		timeController.render(uiShapeRenderer, sideBar.getX() + 20f, Gdx.graphics.getHeight() - 100f);
		
		// Game render
		player.render(this);
		
		// Debug
		if (debug) {
			
			// Threads
			float offsetY = 140f;
			for (int i = 0; i < player.getSoftware().getThreads().size(); i++) {
				com.lucasdnd.serversimulator.gameplay.Thread t = player.getSoftware().getThreads().get(i);
				if (t.getRequest() == null) {
					font.drawWhiteFont("thread " + i + ": no requests", 0f, Gdx.graphics.getHeight() - offsetY, false);
				} else {
					font.drawWhiteFont("thread " + i + ": " + t.getRequest().getState(), 0f, Gdx.graphics.getHeight() - offsetY, false);
				}
				
				offsetY += 20f;
			}
			
			// Requests
			for (com.lucasdnd.serversimulator.gameplay.Thread t : player.getSoftware().getThreads()) {
				if (t.getRequest() != null) {
					font.drawWhiteFont("request state: " + t.getRequest().getState() + ", ticks: " + t.getRequest().getTicks(), 0f, Gdx.graphics.getHeight() - offsetY, false);
					offsetY += 20f;
				}
			}
			
			// Server requests
			font.drawWhiteFont("--- server requests ---", 0f, Gdx.graphics.getHeight() - offsetY, false);
			offsetY += 20;
			
			// Requests
			for (Request r : player.getServer().getRequests()) {
				font.drawWhiteFont("request: " + r.getState() + ", ticks: " + r.getTicks(), 0f, Gdx.graphics.getHeight() - offsetY, false);
				offsetY += 20f;
			}
			
			// General stats
			font.drawWhiteFont("demand: " + market.getDemand(), 0f, 120f, false);
			font.drawWhiteFont("features: " + player.getSoftware().getFeatures(), 0f, 100f, false);
			font.drawWhiteFont("threads: " + player.getSoftware().getThreads().size(), 0f, 80f, false);
			font.drawWhiteFont("optimization: " + player.getSoftware().getOptimization(), 0f, 60f, false);
			font.drawWhiteFont("bugs: " + player.getSoftware().getBugs(), 0f, 40f, false);
			font.drawWhiteFont("request gen ticks: " + market.getMaxRequestGenerationTicks(), 0f, 20f, false);
			
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
