package com.badlogic.gamedev.spaceinvaders;

import javax.microedition.khronos.opengles.GL10;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.badlogic.gamedev.spaceinvaders.screens.GameLoop;
import com.badlogic.gamedev.spaceinvaders.screens.GameOverScreen;
import com.badlogic.gamedev.spaceinvaders.screens.StartScreen;
import com.badlogic.gamedev.spaceinvaders.simulation.Simulation;
import com.badlogic.gamedev.tools.GameActivity;
import com.badlogic.gamedev.tools.GameListener;

public class SpaceInvaders extends GameActivity implements GameListener
{
	GameScreen screen;
	Simulation simulation = null;
	
	public void onCreate( Bundle bundle )
	{
		setRequestedOrientation(0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
		
		super.onCreate( bundle );
		setGameListener( this );		
		
		if( bundle != null && bundle.containsKey( "simulation" ) )
			simulation = (Simulation)bundle.getSerializable( "simulation" );
		
		Log.d( "Space Invaders", "created, simulation: " + (simulation != null) );
	}
	
	@Override
	public void onSaveInstanceState( Bundle outState )
	{
		super.onSaveInstanceState( outState );
		if( screen instanceof GameLoop )
			outState.putSerializable( "simulation", ((GameLoop)screen).simulation );
		Log.d( "Space Invaders", "saved game state" );
	}

	@Override
	public void onPause( )
	{
		super.onPause();
		if( screen != null )			
			screen.dispose();
		if( screen instanceof GameLoop )
			simulation = ((GameLoop)screen).simulation;
		Log.d( "Space Invaders", "paused" );
	}
	
	@Override
	public void onResume( )
	{
		super.onResume();		
		Log.d( "Space Invaders", "resumed" );
	}	
	
	@Override
	public void onDestroy( )
	{
		super.onDestroy();
		Log.d( "Space Invaders", "destroyed" );
	}
	
	@Override
	public void setup(GameActivity activity, GL10 gl) 
	{	
		if( simulation != null )
		{
			screen = new GameLoop( gl, activity, simulation );
			simulation = null;
			Log.d( "Space Invaders", "resuming previous game" );
		}
		else
		{
			screen = new StartScreen(gl, activity);
			Log.d( "Space Invaders", "starting a new game" );
		}
	}

	long start = System.nanoTime();
	int frames = 0;	
	@Override
	public void mainLoopIteration(GameActivity activity, GL10 gl) {		
		
		screen.update( activity );
		screen.render( gl, activity);
		
		if( screen.isDone() )
		{
			screen.dispose();
			Log.d( "Space Invaders", "switching screen: " + screen );
			if( screen instanceof StartScreen )
				screen = new GameLoop( gl, activity );
			else
			if( screen instanceof GameLoop )	
				screen = new GameOverScreen( gl, activity,((GameLoop)screen).simulation.score );
			else
			if( screen instanceof GameOverScreen )
				screen = new StartScreen( gl, activity );			
			Log.d("Space Invaders", "switched to screen: " + screen );
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
