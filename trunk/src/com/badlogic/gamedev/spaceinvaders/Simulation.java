package com.badlogic.gamedev.spaceinvaders;

import java.util.ArrayList;

public class Simulation 
{
	private ArrayList<Invader> invaders = new ArrayList<Invader>();
	private ArrayList<Block> blocks = new ArrayList<Block>( );
	private ArrayList<Shot> shots = new ArrayList<Shot>( );
	private Ship ship;	
	
	public Simulation( )
	{
		populate( );
	}
	
	private void populate( )
	{
		ship = new Ship();
		ship.position.set(0, 0, 0);			
	}
	
	public void update( float delta )
	{
		for( int i = 0; i < invaders.size(); i++ )
			invaders.get(i).update( delta );
		
		for( int i = 0; i < shots.size(); i++ )
			shots.get(i).update(delta);	
		
		
	}
}
