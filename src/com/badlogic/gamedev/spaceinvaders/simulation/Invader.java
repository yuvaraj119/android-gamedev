package com.badlogic.gamedev.spaceinvaders.simulation;

public class Invader 
{
	public final static float INVADER_VELOCITY = 1;
	public final static int STATE_MOVE_LEFT = 0;
	public final static int STATE_MOVE_DOWN = 1;
	public final static int STATE_MOVE_RIGHT = 2;
	
	public final Vector position = new Vector();
	public int state = STATE_MOVE_LEFT;
	public boolean wasLastStateLeft = true;
	public float movedDistance = 6;	
		
	public void update(float delta) 
	{			
		movedDistance += delta * INVADER_VELOCITY;
		if( state == STATE_MOVE_LEFT )
		{
			position.x -= delta * INVADER_VELOCITY;
			if( movedDistance > 12 )
			{
				state = STATE_MOVE_DOWN;
				movedDistance = 0;
				wasLastStateLeft = true;
			}
		}
		if( state == STATE_MOVE_RIGHT )
		{
			position.x += delta * INVADER_VELOCITY;
			if( movedDistance > 12 )
			{
				state = STATE_MOVE_DOWN;
				movedDistance = 0;
				wasLastStateLeft = false;
			}
		}
		if( state == STATE_MOVE_DOWN )
		{
			position.z += delta * INVADER_VELOCITY;
			if( movedDistance > 1 )
			{
				if( wasLastStateLeft )
					state = STATE_MOVE_RIGHT;
				else
					state = STATE_MOVE_LEFT;
				movedDistance = 0;
			}
		}		
	}
}
