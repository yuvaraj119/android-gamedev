package com.badlogic.gamedev.samples;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.os.Bundle;

import com.badlogic.gamedev.tools.Font;
import com.badlogic.gamedev.tools.GameActivity;
import com.badlogic.gamedev.tools.GameListener;
import com.badlogic.gamedev.tools.Font.FontStyle;
import com.badlogic.gamedev.tools.Font.Text;

public class TextSample extends GameActivity implements GameListener
{
	Font font;
	Text text;
	
	public void onCreate( Bundle bundle )
	{
		super.onCreate( bundle );
		setGameListener( this );
	}

	@Override
	public void setup(GameActivity activity, GL10 gl) 
	{	
		font = new Font( gl, activity.getAssets(), "font.ttf", 16, FontStyle.Plain );
		text = font.newText( gl );
		text.setText( "This is a test string!!" );
	}
	
	@Override
	public void mainLoopIteration(GameActivity activity, GL10 gl) 
	{
		gl.glViewport( 0, 0, activity.getViewportWidth(), activity.getViewportHeight() );
		gl.glClear( GL10.GL_COLOR_BUFFER_BIT );
		
		gl.glMatrixMode( GL10.GL_PROJECTION );
		gl.glLoadIdentity();
		GLU.gluOrtho2D(gl, 0, activity.getViewportWidth(), 0, activity.getViewportHeight() );
		
		gl.glMatrixMode( GL10.GL_MODELVIEW );
		gl.glLoadIdentity();
		gl.glTranslatef( 100, 100, 0 );
		
		gl.glEnable( GL10.GL_TEXTURE_2D );
		gl.glEnable( GL10.GL_BLEND );
		gl.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA );
		text.render();
	}
}
