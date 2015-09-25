package com.lucasdnd.serversimulator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ServerSimulator extends ApplicationAdapter {

	SpriteBatch batch;
	BitmapFont font;
	
	@Override
	public void create() {
		font = Resources.get().whiteFont;
		batch = new SpriteBatch();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin(); // batchman begins
		font.draw(batch, "Hello, world!", 100, 100);
		batch.end();
	}
}
