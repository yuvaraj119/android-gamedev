package com.badlogic.gamedev.spaceinvaders;

import javax.microedition.khronos.opengles.GL10;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.badlogic.gamedev.tools.GameActivity;
import com.badlogic.gamedev.tools.GameListener;

public class SpaceInvaderTest extends GameActivity implements GameListener
{
	GameLoop loop;
	
	public void onCreate( Bundle bundle )
	{
		setRequestedOrientation(0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
		
		super.onCreate( bundle );
		setGameListener( this );
	}

	@Override
	public void setup(GameActivity activity, GL10 gl) 
	{	
		loop = new GameLoop(gl, activity);
	}

	long start = System.nanoTime();
	int frames = 0;
	@Override
	public void mainLoopIteration(GameActivity activity, GL10 gl) {		
		
		loop.update( activity );
		loop.render( gl, activity);
		
		frames++;
		if( System.nanoTime() - start > 1000000000 )
		{
			Log.d( "Space Invaders", "fps: " + frames );
			frames = 0;
			start = System.nanoTime();
		}
	}
}
