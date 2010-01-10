package com.badlogic.gamedev.spaceinvaders;

import java.util.ArrayList;

public class Simulation 
{
	public ArrayList<Invader> invaders = new ArrayList<Invader>();
	public ArrayList<Block> blocks = new ArrayList<Block>( );
	public ArrayList<Shot> shots = new ArrayList<Shot>( );
	public Ship ship;	
	
	public Simulation( )
	{
		populate( );
	}
	
	private void populate( )
	{
		ship = new Ship();
		ship.position.set(0, 0, 0);		
		
		for( int row = 0; row < 4; row++ )
		{
			for( int column = 0; column < 8; column++ )
			{
				Invader invader = new Invader( );
				invader.position.set( -7 + column * 2f, 0, -15 + row * 1.5f );
				invaders.add( invader );
			}
		}
	}

	ArrayList<Shot> removedShots = new ArrayList<Shot>();
	public void update( float delta )
	{			
		updateInvaders( delta );
		updateShots( delta );
		checkShipCollision( );
		checkInvaderCollision( );
	}
	
	private void updateInvaders( float delta )
	{
		for( int i = 0; i < invaders.size(); i++ )
		{
			Invader invader = invaders.get(i);
			invader.update( delta );
		}
	}
	
	private void updateShots( float delta )
	{
		removedShots.clear();
		for( int i = 0; i < shots.size(); i++ )
		{
			Shot shot = shots.get(i);
			shot.update(delta);
			if( shot.hasLeftField )
				removedShots.add(shot);
		}
	}
	
	private void checkInvaderCollision() 
	{	
		removedShots.clear();
		
		for( int i = 0; i < shots.size(); i++ )
		{
			Shot shot = shots.get(i);
			if( shot.isInvaderShot )
				continue;
			
			for( int j = 0; j < invaders.size(); j++ )
			{
				Invader invader = invaders.get(i);
				if( invader.position.distance(shot.position) < 0.5f )
				{					
					removedShots.add( shot );
					invaders.remove(invader);
					break;
				}
			}
		}
		
		for( int i = 0; i < removedShots.size(); i++ )		
			shots.remove( removedShots.get(i) );		
	}

	private void checkShipCollision() 
	{	
		removedShots.clear();
		
		for( int i = 0; i < shots.size(); i++ )
		{
			Shot shot = shots.get(i);
			if( !shot.isInvaderShot )
				continue;
									
			if( ship.position.distance(shot.position) < 0.5f )
			{					
				removedShots.add( shot );
				ship.lives--;
				break;
			}			
		}
		
		for( int i = 0; i < removedShots.size(); i++ )		
			shots.remove( removedShots.get(i) );	
		
		for( int i = 0; i < invaders.size(); i++ )
		{
			Invader invader = invaders.get(i);
			if( invader.position.distance(ship.position) < 0.5f )
			{
				ship.lives--;
				invaders.remove(invader);
				break;
			}
		}
	}		
}
