package com.badlogic.gamedev.samples;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.os.Bundle;

import com.badlogic.gamedev.tools.GameActivity;
import com.badlogic.gamedev.tools.GameListener;

public class ColorSample extends GameActivity implements GameListener 
{
	private FloatBuffer vertices;
	
	public void onCreate( Bundle savedInstance )
	{
		super.onCreate( savedInstance );
		setGameListener( this );
	}

	
	@Override
	public void setup(GameActivity activity, GL10 gl) 
	{
		ByteBuffer buffer = ByteBuffer.allocateDirect( 3 * 4 * 3 );
		buffer.order(ByteOrder.nativeOrder());
		vertices = buffer.asFloatBuffer();
		
		vertices.put( -0.5f );
		vertices.put( -0.5f );
		vertices.put( 0 );
		
		vertices.put( 0.5f );
		vertices.put( -0.5f );
		vertices.put( 0 );
		
		vertices.put( 0 );
		vertices.put( 0.5f );
		vertices.put( 0 );
		
		vertices.rewind();
	}
	
	@Override
	public void mainLoopIteration(GameActivity activity, GL10 gl) 
	{	
		gl.glViewport( 0, 0, activity.getViewportWidth(), activity.getViewportHeight() );
		gl.glLoadIdentity();
		gl.glColor4f( 1, 0, 1, 1 );
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY );    
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertices);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
	}
	
}
