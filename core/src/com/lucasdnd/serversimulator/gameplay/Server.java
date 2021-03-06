package com.lucasdnd.serversimulator.gameplay;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.serversimulator.ServerSimulator;
import com.lucasdnd.serversimulator.ui.SideBar;

/**
 * This is just the visual representation of the I/O area on the screen. It also takes care
 * of the Requests that are currently in there.
 * 
 * Maybe I should choose a better name for this Class...
 * 
 * @author lucasdnd
 *
 */
public class Server {
	
	private float x, y;
	private float width = ServerSimulator.BLOCK_SIZE * 4f;
	private float height = 96f;
	private float lineWeight = ServerSimulator.BLOCK_SIZE;
	private ShapeRenderer sr;
	
	/**
	 * Tells if this Server is performing an IO operation.
	 * In sync IO mode, the current request will remain in the Thread until the Server is free to perform
	 * the requered IO.
	 * In async IO, this attribute can be ignored as these operations can be executed simultaneously
	 * (in the "requests" list).
	 */
	private boolean performingIO;
	
	private LinkedList<Request> requests; // These are all requests in the I/O state.
	
	public Server(Software software) {
		sr = new ShapeRenderer();
		requests = new LinkedList<Request>();
	}
	
	public void update(ServerSimulator game) {
		
		// Update its requests
		for (Request request : requests) {
			
			// Normal request update (ticks++, screen position)
			request.update(game, request.getY());
			
			// Check if the IO is done and set the appropriate Request status
			if (request.getTicks() >= game.getPlayer().getRequestTicks() + game.getPlayer().getIoTicks()) {
				request.setState(Request.WAITING_FOR_RESPONSE);
			}
		}
		
		// Width: the part inside the Server is the IO time
		// Should this code be in the render() area? Since it has to do with the drawing...
		float requestTime = game.getPlayer().getRequestTime();
		float ioTime = game.getPlayer().getIoTime();
		float responseTime = game.getPlayer().getResponseTime();
		float totalTime = requestTime + ioTime + responseTime;
		float playableAreaWidth = Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH;
		width = playableAreaWidth * ioTime / totalTime;
		height = game.getPlayer().getSoftware().getThreads().size() * ServerSimulator.BLOCK_SIZE * 2f + (ServerSimulator.BLOCK_SIZE * 2f);
		
		x = (Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH) / 2f - (width / 2f);
		y = Gdx.graphics.getHeight() / 2f + height / 2f;
	}
	
	public void render(ServerSimulator game) {
		
		drawRectFrame();
		
		for (Request r : requests) {
			r.render();
		}
	}
	
	private void drawRectFrame() {
		final float lineHeight = height;
		final float lineWidth = width + lineWeight;
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.WHITE);
		
		// Left
		sr.rect(x, y, lineWeight, lineWeight - lineHeight);
		
		// Right
		sr.rect(x + width, y, lineWeight, lineWeight - lineHeight);
		
		// Top
		sr.rect(x, y, lineWidth, lineWeight);
		
		// Bottom
		sr.rect(x, y - lineHeight, lineWidth, lineWeight); 
		
		sr.end();
	}
	
	public boolean isPerformingIO() {
		return performingIO;
	}
	
	public LinkedList<Request> getRequests() {
		return requests;
	}

	public float getY() {
		return y;
	}

	public void setPerformingIO(boolean performingIO) {
		this.performingIO = performingIO;
	}
}
