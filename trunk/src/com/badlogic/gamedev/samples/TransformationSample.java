package com.badlogic.gamedev.samples;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.os.Bundle;

import com.badlogic.gamedev.tools.GameActivity;
import com.badlogic.gamedev.tools.GameListener;
import com.badlogic.gamedev.tools.Mesh;
import com.badlogic.gamedev.tools.Mesh.PrimitiveType;

public class TransformationSample extends GameActivity implements GameListener 
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
		mesh = new Mesh( gl, 9, false, false, true );
		mesh.normal( 0, 0, 1 );
		mesh.vertex( 1, 0, 0 );
		mesh.normal( 0, 0, 1 );
		mesh.vertex( 1, 1, 0 );
		mesh.normal( 0, 0, 1 );
		mesh.vertex( 0, 1, 0 );
		mesh.normal( 1, 0, 0 );
		mesh.vertex( 1, 0, 0 );
		mesh.normal( 1, 0, 0 );
		mesh.vertex( 1, 0, -1);
		mesh.normal( 1, 0, 0 );
		mesh.vertex( 1, 1, 0 );
		mesh.normal( 0, 1, 0 );
		mesh.vertex( 1, 1, 0 );
		mesh.normal( 0, 1, 0 );
		mesh.vertex( 1, 1, -1 );
		mesh.normal( 0, 1, 0 );
		mesh.vertex( 0, 1, 0 );
				 
		float[] lightColor = { 1, 1, 1, 1 };
		float[] ambientLightColor = {0.2f, 0.2f, 0.2f, 1 };		
		gl.glLightfv( GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientLightColor, 0 );
		gl.glLightfv( GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightColor, 0 );
		gl.glLightfv( GL10.GL_LIGHT0, GL10.GL_SPECULAR, lightColor, 0 );		
	}
	
	@Override
	public void mainLoopIteration(GameActivity activity, GL10 gl) 
	{			
		gl.glClear( GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT );
		gl.glViewport( 0, 0, activity.getViewportWidth(), activity.getViewportHeight() );
		
		gl.glEnable( GL10.GL_DEPTH_TEST );
		
		gl.glMatrixMode( GL10.GL_PROJECTION );
		gl.glLoadIdentity();
		float aspectRatio = (float)activity.getViewportWidth() / activity.getViewportHeight();
		GLU.gluPerspective( gl, 67, aspectRatio, 1, 100 );

		gl.glMatrixMode( GL10.GL_MODELVIEW );
		gl.glLoadIdentity();
		GLU.gluLookAt( gl, 3, 3, 3, 0.5f, 0.5f, -0.5f, 0, 1, 0 );
		
		gl.glRotatef( 45, 0, 0, 1 );
		gl.glTranslatef( 2, 0, 0 );
		
		gl.glEnable( GL10.GL_LIGHTING );
		gl.glEnable( GL10.GL_LIGHT0 );		
		float[] direction = { 1 / (float)Math.sqrt(2), 1 / (float)Math.sqrt(2), 0, 0 };
		gl.glLightfv( GL10.GL_LIGHT0, GL10.GL_POSITION, direction, 0 );
		gl.glEnable( GL10.GL_COLOR_MATERIAL );		
		
		mesh.render( PrimitiveType.Triangles );
	}
}

