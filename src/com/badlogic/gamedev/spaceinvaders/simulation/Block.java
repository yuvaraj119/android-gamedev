package com.badlogic.gamedev.spaceinvaders.simulation;

public class Block 
{	
	public final static float BLOCK_RADIUS = 0.5f;
	
	public Vector position = new Vector( );
	
	public Block(Vector position) 
	{
		this.position.set( position );
	}
}
