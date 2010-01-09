package com.badlogic.gamedev.samples;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.os.Bundle;

import com.badlogic.gamedev.tools.GameActivity;
import com.badlogic.gamedev.tools.GameListener;
import com.badlogic.gamedev.tools.Mesh;
import com.badlogic.gamedev.tools.Mesh.PrimitiveType;

public class ZBufferSample extends GameActivity implements GameListener 
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
		mesh = new Mesh( gl, 6, true, false, false );		
		
		mesh.color( 0, 1, 0, 1 );
		mesh.vertex( 0f, -0.5f, -5 );
		mesh.color( 0, 1, 0, 1 );
		mesh.vertex( 1f, -0.5f, -5 );
		mesh.color( 0, 1, 0, 1 );
		mesh.vertex( 0.5f, 0.5f, -5 );
		
		mesh.color( 1, 0, 0, 1 );
		mesh.vertex( -0.5f, -0.5f, -2 );
		mesh.color( 1, 0, 0, 1 );
		mesh.vertex( 0.5f, -0.5f, -2 );
		mesh.color( 1, 0, 0, 1 );
		mesh.vertex( 0, 0.5f, -2);
	}
	
	@Override
	public void mainLoopIteration(GameActivity activity, GL10 gl) 
	{			
		gl.glViewport( 0, 0, activity.getViewportWidth(), activity.getViewportHeight() );
		
		gl.glClearColor( 0, 0, 0, 0 );
		gl.glClear( GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT );
		
		gl.glMatrixMode( GL10.GL_PROJECTION );
		gl.glLoadIdentity();
		float aspectRatio = (float)activity.getViewportWidth() / activity.getViewportHeight();
		GLU.gluPerspective( gl, 67, aspectRatio, 1, 100 );

		gl.glMatrixMode( GL10.GL_MODELVIEW );
		gl.glLoadIdentity();
		GLU.gluLookAt( gl, 0, 0, -7, 0, 0, 0, 0, 1, 0 );
		
		gl.glEnable( GL10.GL_DEPTH_TEST );
		
		mesh.render( PrimitiveType.Triangles );
	}
}
