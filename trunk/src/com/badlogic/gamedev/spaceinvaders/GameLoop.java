package com.badlogic.gamedev.spaceinvaders;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.gamedev.tools.GameActivity;

public class GameLoop implements GameModul
{
	public Simulation simulation;
	Renderer renderer;

	public GameLoop( GL10 gl, GameActivity activity )
	{
		simulation = new Simulation();
		renderer = new Renderer( gl, activity );
	}
	
	@Override
	public void render(GL10 gl, GameActivity activity) 
	{	
		renderer.render( gl, activity, simulation);
	}

	@Override
	public void update(GameActivity activity) 
	{	
		processInput( activity );
		simulation.update( activity.getDeltaTime() );
	}
	
	private void processInput( GameActivity activity )
	{		
		if( activity.getAccelerationOnYAxis() < 0 )
			simulation.moveShipLeft( activity.getDeltaTime(), Math.abs(activity.getAccelerationOnYAxis()) / 10 );
		else
			simulation.moveShipRight( activity.getDeltaTime(), Math.abs(activity.getAccelerationOnYAxis()) / 10 );
	
		
		if( activity.isTouched() )
			simulation.shot();
	}

	public boolean isDone( )
	{
		return simulation.ship.lives == 0;
	}
	
	public void dispose( )
	{
		renderer.dispose();
	}
}
