package com.lucasdnd.serversimulator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.serversimulator.gameplay.Market;
import com.lucasdnd.serversimulator.gameplay.Player;
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
				player.createNewRequest();
			}
		}
	}
	
	public void update() {
		
		handleInput();
		
		// UI update
		sideBar.update();
		
		// Game objects
		market.update(this);
		player.update(this);
		
		inputHandler.refreshMouseClicks();
	}

	@Override
	public void render() {
		
		this.update();
		
//		Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// UI render
		sideBar.render(uiShapeRenderer);
		
		// Game render
		player.render(this);
		
		// Debug
		if (debug) {
			
			float offsetY = 20f;
			for (int i = 0; i < player.getSoftware().getThreads().size(); i++) {
				com.lucasdnd.serversimulator.gameplay.Thread t = player.getSoftware().getThreads().get(i);
				font.drawWhiteFont("thread: " + t, 0f, Gdx.graphics.getHeight() - (offsetY * i), false);
			}
			
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
