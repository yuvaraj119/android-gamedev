package com.badlogic.gamedev.samples;

import javax.microedition.khronos.opengles.GL10;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import com.badlogic.gamedev.tools.GameActivity;
import com.badlogic.gamedev.tools.GameListener;

public class MusicSample extends GameActivity implements GameListener
{
	MediaPlayer mediaPlayer;
	
	public void onCreate( Bundle bundle )
	{
		super.onCreate( bundle );
		setGameListener( this );
	}
	
	@Override
	protected void onPause( )
	{
		super.onPause();
		mediaPlayer.release();
	}

	@Override
	public void setup(GameActivity activity, GL10 gl) 
	{	
		mediaPlayer = new MediaPlayer();
		try
		{
			AssetFileDescriptor descriptor = getAssets().openFd( "music.mp3" );
			mediaPlayer.setDataSource( descriptor.getFileDescriptor() );
			mediaPlayer.prepare();
			mediaPlayer.start();
		}
		catch( Exception ex )
		{
			Log.d( "Sound Sample", "couldn't load music 'music.mp3'" );
			throw new RuntimeException( ex );
		}
	}
	
	@Override
	public void mainLoopIteration(GameActivity activity, GL10 gl) 
	{	
		
	}
}
