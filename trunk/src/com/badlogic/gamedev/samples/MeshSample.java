package com.badlogic.gamedev.samples;

import javax.microedition.khronos.opengles.GL10;

import android.os.Bundle;

import com.badlogic.gamedev.tools.GameActivity;
import com.badlogic.gamedev.tools.GameListener;
import com.badlogic.gamedev.tools.Mesh;
import com.badlogic.gamedev.tools.Mesh.PrimitiveType;

/**
 * Demonstrates the use of the mesh class
 * 
 * @author mzechner
 *
 */
public class MeshSample extends GameActivity implements GameListener
{
	Mesh mesh;
	
	public void onCreate( Bundle savedInstance )
	{
		super.onCreate( savedInstance );
		setGameListener( this );
	}

	@Override
	public void setup(GameActivity activity, GL10 gl) 
	{	
		// we create a mesh with 3 vertices having different colors
		mesh = new Mesh( gl, 3, true, false, false );
		mesh.color( 1, 0, 0, 1 );
		mesh.vertex( -0.5f, -0.5f, 0 );
		mesh.color( 0, 1, 0, 1 );
		mesh.vertex( 0.5f, -0.5f, 0 );
		mesh.color( 0, 0, 1, 1 );
		mesh.vertex( 0, 0.5f, 0);
	}
	
	@Override
	public void mainLoopIteration(GameActivity activity, GL10 gl) 
	{	
		gl.glViewport( 0, 0, activity.getViewportWidth(), activity.getViewportHeight() );
		
		mesh.render( PrimitiveType.Triangles );
	}
}
