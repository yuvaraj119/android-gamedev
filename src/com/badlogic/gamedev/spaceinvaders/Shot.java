package com.badlogic.gamedev.spaceinvaders;

public class Shot 
{
	public static float SHOT_VELOCITY = 1;
	public final Vector position = new Vector();
	public boolean isInvaderShot;
	public boolean hasLeftField = false;

	public Shot( boolean isInvaderShot )
	{
		this.isInvaderShot = isInvaderShot;
	}
	
	public void update(float delta) 
	{	
		if( isInvaderShot )
			position.z += SHOT_VELOCITY * delta;
		else
			position.z -= SHOT_VELOCITY * delta;
		
		if( position.z > 2 )
			hasLeftField = true;
		if( position.z > 20 )
			hasLeftField = true;
	}
}
