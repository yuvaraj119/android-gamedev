package com.badlogic.gamedev.samples;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.os.Bundle;

import com.badlogic.gamedev.tools.GameActivity;
import com.badlogic.gamedev.tools.GameListener;
import com.badlogic.gamedev.tools.Mesh;
import com.badlogic.gamedev.tools.Mesh.PrimitiveType;

public class OrthoSample extends GameActivity implements GameListener
{
	Mesh mesh;	
	
	public void onCreate( Bundle bundle )
	{
		super.onCreate( bundle );
		setGameListener( this );
	}

	@Override
	public void setup(GameActivity activity, GL10 gl) 
	{	
		mesh = new Mesh( gl, 3, false, false, false );
		mesh.vertex( 0, 0, 0 );
		mesh.vertex( 50, 0, 0 );
		mesh.vertex( 25, 50, 0 );			
	}
	
	@Override
	public void mainLoopIteration(GameActivity activity, GL10 gl) 
	{	
		gl.glViewport( 0, 0, activity.getViewportWidth(), activity.getViewportHeight() );
		
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluOrtho2D(gl, 0, activity.getViewportWidth(), 0, activity.getViewportHeight() );
		
		mesh.render(PrimitiveType.Triangles);
	}
}
