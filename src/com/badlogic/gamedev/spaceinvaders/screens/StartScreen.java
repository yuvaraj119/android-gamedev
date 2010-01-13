package com.badlogic.gamedev.spaceinvaders.screens;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.opengl.GLU;
import android.util.Log;

import com.badlogic.gamedev.spaceinvaders.GameScreen;
import com.badlogic.gamedev.spaceinvaders.SoundManager;
import com.badlogic.gamedev.tools.Font;
import com.badlogic.gamedev.tools.GameActivity;
import com.badlogic.gamedev.tools.Mesh;
import com.badlogic.gamedev.tools.Texture;
import com.badlogic.gamedev.tools.Font.FontStyle;
import com.badlogic.gamedev.tools.Font.Text;
import com.badlogic.gamedev.tools.Mesh.PrimitiveType;
import com.badlogic.gamedev.tools.Texture.TextureFilter;
import com.badlogic.gamedev.tools.Texture.TextureWrap;

public class StartScreen implements GameScreen
{	
	Mesh backgroundMesh;
	Texture backgroundTexture;
	Mesh titleMesh;
	Texture titleTexture;
	boolean isDone = false;
	SoundManager soundManager;
	Font font;
	Text text;
	String pressText = "Touch Screen to Start!";
	
	public StartScreen( GL10 gl, GameActivity activity )
	{			
		backgroundMesh = new Mesh( gl, 4, false, true, false );
		backgroundMesh.texCoord(0, 0);
		backgroundMesh.vertex(-1, 1, 0 );
		backgroundMesh.texCoord(1, 0);
		backgroundMesh.vertex(1, 1, 0 );
		backgroundMesh.texCoord(1, 1);
		backgroundMesh.vertex(1, -1, 0 );
		backgroundMesh.texCoord(0, 1);
		backgroundMesh.vertex(-1, -1, 0 );
		
		titleMesh = new Mesh( gl, 4, false, true, false );
		titleMesh.texCoord(0, 0);
		titleMesh.vertex(-256, 256, 0);
		titleMesh.texCoord(1, 0);
		titleMesh.vertex(256, 256, 0);
		titleMesh.texCoord(1, 1);
		titleMesh.vertex(256, -256, 0);
		titleMesh.texCoord(0, 1);
		titleMesh.vertex(-256, -256, 0);
		
		try
		{
			Bitmap bitmap = BitmapFactory.decodeStream( activity.getAssets().open( "planet.jpg" ) );
			backgroundTexture = new Texture( gl, bitmap, TextureFilter.MipMap, TextureFilter.Nearest, TextureWrap.ClampToEdge, TextureWrap.ClampToEdge );
			bitmap.recycle();
			
			bitmap = BitmapFactory.decodeStream( activity.getAssets().open( "title.png" ) );
			titleTexture = new Texture( gl, bitmap, TextureFilter.Nearest, TextureFilter.Nearest, TextureWrap.ClampToEdge, TextureWrap.ClampToEdge );
			bitmap.recycle();	
		}
		catch( Exception ex )
		{
			Log.d( "Space Invaders", "couldn't load textures" );
			throw new RuntimeException( ex );
		}
		
		soundManager = new SoundManager(activity);
		
		font = new Font( gl, activity.getAssets(), "font.ttf", 16, FontStyle.Plain );
		text = font.newText( gl );
		text.setText( pressText );
	}	

	@Override
	public boolean isDone() 
	{	
		return isDone;
	}

	@Override
	public void update(GameActivity activity) 
	{	
		if( activity.isTouched() )
			isDone = true;
	}
	
	@Override
	public void render(GL10 gl, GameActivity activity) 
	{	
		gl.glViewport( 0, 0, activity.getViewportWidth(), activity.getViewportHeight() );
		gl.glClear( GL10.GL_COLOR_BUFFER_BIT );
		gl.glEnable( GL10.GL_TEXTURE_2D );
		gl.glMatrixMode( GL10.GL_PROJECTION );
		gl.glLoadIdentity();
		gl.glMatrixMode( GL10.GL_MODELVIEW );
		gl.glLoadIdentity();
		
		gl.glEnable( GL10.GL_BLEND );
		gl.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA );
		
		backgroundTexture.bind();
		backgroundMesh.render(PrimitiveType.TriangleFan );
		
		gl.glMatrixMode( GL10.GL_PROJECTION );
		GLU.gluOrtho2D( gl, 0, activity.getViewportWidth(), 0, activity.getViewportHeight() );
		gl.glMatrixMode( GL10.GL_MODELVIEW );
		gl.glLoadIdentity();
		
		gl.glLoadIdentity();
		gl.glTranslatef( activity.getViewportWidth() / 2, activity.getViewportHeight() - 256, 0 );
		titleTexture.bind();
		titleMesh.render(PrimitiveType.TriangleFan);
		
		gl.glLoadIdentity();
		gl.glTranslatef( activity.getViewportWidth() / 2 - font.getStringWidth( pressText ) / 2, 100, 0 );
		text.render();
		
		gl.glDisable( GL10.GL_TEXTURE_2D );
		gl.glDisable( GL10.GL_BLEND );
	}
	
	@Override
	public void dispose() 
	{	
		backgroundTexture.dispose();
		titleTexture.dispose();
		soundManager.dispose();
		font.dispose();
		text.dispose();
		backgroundMesh.dispose();
		titleMesh.dispose();
	}
}
