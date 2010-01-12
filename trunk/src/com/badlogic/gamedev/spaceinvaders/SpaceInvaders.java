package com.badlogic.gamedev.spaceinvaders;

import javax.microedition.khronos.opengles.GL10;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.badlogic.gamedev.spaceinvaders.screens.GameLoop;
import com.badlogic.gamedev.spaceinvaders.screens.GameOverScreen;
import com.badlogic.gamedev.spaceinvaders.screens.StartScreen;
import com.badlogic.gamedev.tools.GameActivity;
import com.badlogic.gamedev.tools.GameListener;

public class SpaceInvaders extends GameActivity implements GameListener
{
	GameScreen module;
	
	public void onCreate( Bundle bundle )
	{
		setRequestedOrientation(0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
		
		super.onCreate( bundle );
		setGameListener( this );
	}

	@Override
	public void onPause( )
	{
		super.onPause();
		if( module != null )			
			module.dispose();
	}
	
	public void onResume( )
	{
		super.onResume();		
	}
	
	@Override
	public void setup(GameActivity activity, GL10 gl) 
	{	
		module = new StartScreen(gl, activity);
	}

	long start = System.nanoTime();
	int frames = 0;	
	@Override
	public void mainLoopIteration(GameActivity activity, GL10 gl) {		
		
		module.update( activity );
		module.render( gl, activity);
		
		if( module.isDone() )
		{
			module.dispose();
			if( module instanceof StartScreen )
				module = new GameLoop( gl, activity );
			else
			if( module instanceof GameLoop )	
				module = new GameOverScreen( gl, activity,((GameLoop)module).simulation.score );
			else
			if( module instanceof GameOverScreen )
				module = new StartScreen( gl, activity );			
		}
		
		frames++;
		if( System.nanoTime() - start > 1000000000 )
		{
			Log.d( "Space Invaders", "fps: " + frames );
			frames = 0;
			start = System.nanoTime();
		}
	}
}
