package com.badlogic.gamedev.samples;

import java.io.IOException;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLU;
import android.os.Bundle;

import com.badlogic.gamedev.tools.GameActivity;
import com.badlogic.gamedev.tools.GameListener;
import com.badlogic.gamedev.tools.Mesh;
import com.badlogic.gamedev.tools.MeshLoader;
import com.badlogic.gamedev.tools.Texture;
import com.badlogic.gamedev.tools.Mesh.PrimitiveType;
import com.badlogic.gamedev.tools.Texture.TextureFilter;
import com.badlogic.gamedev.tools.Texture.TextureWrap;

public class ObjSample extends GameActivity implements GameListener 
{
    Mesh mesh;
    Texture texture;
    float angle = 0;
	
	public void onCreate( Bundle savedInstance )
	{
		super.onCreate( savedInstance );
		setGameListener( this );
	}

	@Override
	public void setup(GameActivity activity, GL10 gl) 
	{			
		try {
			mesh = MeshLoader.loadObj( gl, getAssets().open( "ship.obj" ) );
			Bitmap bitmap = BitmapFactory.decodeStream( getAssets().open( "ship.png" ) );
			texture = new Texture( gl, bitmap, TextureFilter.Linear, TextureFilter.Linear, TextureWrap.ClampToEdge, TextureWrap.ClampToEdge );
			bitmap.recycle();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException( "Couldn't load mesh" );
		}
				
		float[] lightColor = { 1, 1, 1, 1 };
		float[] ambientLightColor = {0.0f, 0.0f, 0.0f, 1 };		
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
		
		gl.glEnable( GL10.GL_LIGHTING );
		gl.glEnable( GL10.GL_LIGHT0 );		
		float[] direction = { 1, 0.5f, 0, 0 };
		gl.glLightfv( GL10.GL_LIGHT0, GL10.GL_POSITION, direction, 0 );
		gl.glEnable( GL10.GL_COLOR_MATERIAL );		
		
		gl.glEnable( GL10.GL_TEXTURE_2D );
		texture.bind();			
		
		gl.glRotatef( angle, 0, 1, 0 );
		
		mesh.render( PrimitiveType.Triangles );
		
		angle+= activity.getDeltaTime() * 180; // wir drehen uns um 180 Grad pro Sekunde
		if( angle > 360 )
			angle = 0;
	}
}

