package com.lucasdnd.serversimulator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Align;

public class ServerSimulator extends ApplicationAdapter {

	FontUtils font;
	
	@Override
	public void create() {
		font = new FontUtils();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		font.drawWhiteFont("Hello, world!", 0f, Gdx.graphics.getHeight() / 2f, true, Align.center, Gdx.graphics.getWidth());
	}
}
