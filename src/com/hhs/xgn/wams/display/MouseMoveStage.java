package com.hhs.xgn.wams.display;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hhs.xgn.wams.screen.Play;

@Deprecated
public class MouseMoveStage extends Stage {

	public MouseMoveStage() {
		// TODO Auto-generated constructor stub
		super();
	}

	public MouseMoveStage(Viewport viewport) {
		super(viewport);
		// TODO Auto-generated constructor stub
	}

	public MouseMoveStage(Viewport viewport, Batch batch) {
		super(viewport, batch);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		
		return super.mouseMoved(screenX, screenY);
	}
}
