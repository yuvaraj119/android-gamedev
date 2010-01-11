package com.badlogic.gamedev.spaceinvaders.screens;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.badlogic.gamedev.spaceinvaders.GameScreen;
import com.badlogic.gamedev.tools.GameActivity;
import com.badlogic.gamedev.tools.Mesh;
import com.badlogic.gamedev.tools.Texture;
import com.badlogic.gamedev.tools.Mesh.PrimitiveType;
import com.badlogic.gamedev.tools.Texture.TextureFilter;
import com.badlogic.gamedev.tools.Texture.TextureWrap;

public class GameOverScreen implements GameScreen 
{	
	Mesh backgroundMesh;
	Texture backgroundTexture;
	
	public GameOverScreen( GL10 gl, GameActivity activity, int score )
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
		
		try
		{
			Bitmap bitmap = BitmapFactory.decodeStream( activity.getAssets().open( "planet.jpg" ) );
			backgroundTexture = new Texture( gl, bitmap, TextureFilter.MipMap, TextureFilter.Linear, TextureWrap.ClampToEdge, TextureWrap.ClampToEdge );
			bitmap.recycle();					
		}
		catch( Exception ex )
		{
			Log.d( "Space Invaders", "couldn't load textures" );
			throw new RuntimeException( ex );
		}
	}	

	@Override
	public boolean isDone() 
	{	
		return false;
	}

	@Override
	public void update(GameActivity activity) 
	{	

	}
	
	@Override
	public void render(GL10 gl, GameActivity activity) 
	{	
		gl.glClear( GL10.GL_COLOR_BUFFER_BIT );
		gl.glEnable( GL10.GL_TEXTURE_2D );
		gl.glMatrixMode( GL10.GL_PROJECTION );
		gl.glLoadIdentity();
		gl.glMatrixMode( GL10.GL_MODELVIEW );
		gl.glLoadIdentity();
		backgroundTexture.bind();
		backgroundMesh.render(PrimitiveType.TriangleFan );
		gl.glDisable( GL10.GL_TEXTURE_2D );
	}
	
	@Override
	public void dispose() 
	{	
		backgroundTexture.dispose();
	}
}
