package com.badlogic.gamedev.spaceinvaders.simulation;

import java.util.ArrayList;

public class Simulation 
{	
	public final static float PLAYFIELD_HALF_WIDTH = 13;
	public final static float PLAYFIELD_HEIGHT = 15;
	
	public ArrayList<Invader> invaders = new ArrayList<Invader>();
	public ArrayList<Block> blocks = new ArrayList<Block>( );
	public ArrayList<Shot> shots = new ArrayList<Shot>( );
	public ArrayList<Explosion> explosions = new ArrayList<Explosion>( );
	public Ship ship;
	public Shot shipShot = null;
	public SimulationListener listener;
	public float speedMultiplier = 1;
	public int score;
	
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
				invader.position.set( -7 + column * 2f, 0, -PLAYFIELD_HEIGHT + row * 2f );
				invaders.add( invader );
			}
		}
		
		for( int shield = 0; shield < 3; shield++ )
		{
			blocks.add( new Block( new Vector( -10 + shield * 10 -1, 0, -2) ) );
			blocks.add( new Block( new Vector( -10 + shield * 10 -1, 0, -3) ) );
			blocks.add( new Block( new Vector( -10 + shield * 10 + 0, 0, -3) ) );
			blocks.add( new Block( new Vector( -10 + shield * 10 + 1, 0, -3) ) );
			blocks.add( new Block( new Vector( -10 + shield * 10 + 1, 0, -2 ) ) );
		}
	}

	ArrayList<Shot> removedShots = new ArrayList<Shot>();
	public void update( float delta )
	{			
		ship.update( delta );
		updateInvaders( delta );
		updateShots( delta );
		updateExplosions(delta);
		checkShipCollision( );
		checkInvaderCollision( );
		checkBlockCollision( );
		checkNextLevel( );		
	}
	
	private void updateInvaders( float delta )
	{
		for( int i = 0; i < invaders.size(); i++ )
		{
			Invader invader = invaders.get(i);
			invader.update( delta, speedMultiplier );
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
		
		if( shipShot != null && shipShot.hasLeftField )
			shipShot = null;
		
		if( Math.random() < 0.01 && invaders.size() > 0 )
		{
			Shot shot = new Shot( true );
			int index = (int)(Math.random() * (invaders.size() - 1));
			shot.position.set( invaders.get(index).position );
			shots.add( shot );
			if( listener != null )
				listener.shot();
		}
	}
	
	ArrayList<Explosion> removedExplosions = new ArrayList<Explosion>( );	
	public void updateExplosions( float delta )
	{
		removedExplosions.clear();
		for( int i = 0; i < explosions.size(); i++ )
		{
			Explosion explosion = explosions.get(i);
			explosion.update( delta );
			if( explosion.aliveTime > Explosion.EXPLOSION_LIVE_TIME )
				removedExplosions.add( explosion );
		}
		
		for( int i = 0; i < removedExplosions.size(); i++ )
			explosions.remove( explosions.get(i) );
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
				Invader invader = invaders.get(j);
				if( invader.position.distance(shot.position) < Invader.INVADER_RADIUS )
				{					
					removedShots.add( shot );
					shot.hasLeftField = true;
					invaders.remove(invader);
					explosions.add( new Explosion( invader.position ) );
					if( listener != null )
						listener.explosion();
					score += Invader.INVADER_POINTS;
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
									
			if( ship.position.distance(shot.position) < Ship.SHIP_RADIUS )
			{					
				removedShots.add( shot );
				shot.hasLeftField = true;
				ship.lives--;
				ship.isExploding = true;
				explosions.add( new Explosion( ship.position ) );
				if( listener != null )
					listener.explosion();
				break;
			}			
		}
		
		for( int i = 0; i < removedShots.size(); i++ )		
			shots.remove( removedShots.get(i) );	
		
		for( int i = 0; i < invaders.size(); i++ )
		{
			Invader invader = invaders.get(i);
			if( invader.position.distance(ship.position) < Ship.SHIP_RADIUS )
			{
				ship.lives--;
				invaders.remove(invader);
				ship.isExploding = true;
				explosions.add( new Explosion( ship.position ) );
				if( listener != null )
					listener.explosion();
				break;
			}
		}
	}

	private void checkBlockCollision( )
	{
		removedShots.clear();
		
		for( int i = 0; i < shots.size(); i++ )
		{
			Shot shot = shots.get(i);			
									
			for( int j = 0; j < blocks.size(); j++ )
			{
				Block block = blocks.get(j);
				if( block.position.distance(shot.position) < Block.BLOCK_RADIUS )
				{					
					removedShots.add( shot );
					shot.hasLeftField = true;
					blocks.remove(block);
					break;
				}
			}			
		}
		
		for( int i = 0; i < removedShots.size(); i++ )		
			shots.remove( removedShots.get(i) );
	}
	
	private void checkNextLevel( )
	{
		if( invaders.size() == 0 && ship.lives > 0 )
		{
			blocks.clear();
			shots.clear();
			shipShot = null;
			populate();
		}
	}
	
	public void moveShipLeft(float delta, float scale) 
	{	
		if( ship.isExploding )
			return;
		
		ship.position.x -= delta * Ship.SHIP_VELOCITY * scale;
		if( ship.position.x < -PLAYFIELD_HALF_WIDTH )
			ship.position.x = -PLAYFIELD_HALF_WIDTH;
	}

	public void moveShipRight(float delta, float scale ) 
	{	
		if( ship.isExploding )
			return;
		
		ship.position.x += delta * Ship.SHIP_VELOCITY * scale;
		if( ship.position.x > PLAYFIELD_HALF_WIDTH )
			ship.position.x = PLAYFIELD_HALF_WIDTH;
	}

	public void shot() 
	{	
		if( shipShot == null && !ship.isExploding )
		{
			shipShot = new Shot( false );
			shipShot.position.set( ship.position );
			shots.add( shipShot );
			listener.shot();
		}
	}		
}
