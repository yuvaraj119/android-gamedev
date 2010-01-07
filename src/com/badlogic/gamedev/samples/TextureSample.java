package com.badlogic.gamedev.samples;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.os.Bundle;
import android.util.Log;

import com.badlogic.gamedev.tools.GameActivity;
import com.badlogic.gamedev.tools.GameListener;

public class TextureSample extends GameActivity implements GameListener
{
	private FloatBuffer vertices;
	private FloatBuffer texCoords;
	private int textureHandle;
	
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
		
		buffer = ByteBuffer.allocateDirect( 3 * 4 * 2 );
		buffer.order(ByteOrder.nativeOrder());
		texCoords = buffer.asFloatBuffer();
		
		texCoords.put(0);
		texCoords.put(1);
		
		texCoords.put(1);
		texCoords.put(1);
		
		texCoords.put(0.5f);
		texCoords.put(0);
		texCoords.rewind();
		
		
		Bitmap bitmap = null;
		try
		{
			bitmap = BitmapFactory.decodeStream( getAssets().open( "droid.png" ) );
		}
		catch( Exception ex )
		{
			Log.d( "Texture Sample", "Couldn't load bitmap 'droid.png'" );
		}
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);		
		textureHandle = textures[0];	
		gl.glBindTexture( GL10.GL_TEXTURE_2D, textureHandle );
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR );
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR );
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE );
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE );
		GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		bitmap.recycle();
	}
	
	@Override
	public void mainLoopIteration(GameActivity activity, GL10 gl) 
	{							
		gl.glEnable( GL10.GL_TEXTURE_2D );
		gl.glBindTexture( GL10.GL_TEXTURE_2D, textureHandle );
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY );    
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertices);
		
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY );
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texCoords );
		
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
	}
}
