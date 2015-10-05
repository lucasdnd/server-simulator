package com.lucasdnd.serversimulator;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

/**
 * Controls.
 * 
 * @author lucasdnd
 *
 */
public class InputHandler implements InputProcessor {
	
	public boolean leftMouseDown, leftMouseJustClicked, rightMouseDown, rightMouseJustClicked;
	public boolean shiftPressed, ctrlPressed;
	
	public InputHandler() {

	}
	
	public void refreshMouseClicks() {
		leftMouseJustClicked = false;
		rightMouseJustClicked = false;
	}
	
	@Override
	public boolean keyDown(int keycode) {
				
		if (keycode == Keys.SHIFT_LEFT || keycode == Keys.SHIFT_RIGHT) {
			shiftPressed = true;
		}
		
		if (keycode == Keys.CONTROL_LEFT || keycode == Keys.CONTROL_RIGHT) {
			ctrlPressed = true;
		}
	
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		if (keycode == Keys.SHIFT_LEFT || keycode == Keys.SHIFT_RIGHT) {
			shiftPressed = false;
		}
		
		if (keycode == Keys.CONTROL_LEFT || keycode == Keys.CONTROL_RIGHT) {
			ctrlPressed = false;
		}
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == 0) {
			leftMouseDown = true;
			leftMouseJustClicked = true;
		} else if (button == 1) {
			rightMouseDown = true;
			rightMouseJustClicked = true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (button == 0) {
			leftMouseDown = false;
		} else if (button == 1) {
			rightMouseDown = false;
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
