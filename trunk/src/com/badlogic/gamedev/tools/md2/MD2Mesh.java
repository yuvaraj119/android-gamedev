package com.badlogic.gamedev.tools.md2;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.gamedev.tools.Mesh.PrimitiveType;

public class MD2Mesh 
{
	public final MD2Frame frames[];
	public final short indices[];
	public final float texCoords[];
	public final float normals[];	
	public final IndexedMesh mesh;
	public final int numVertices;	
	
	public MD2Mesh( GL10 gl, MD2Frame[] frames, short[] indices, float texCoords[], float normals[] )
	{		
		this.mesh = new IndexedMesh( gl, frames[0].vertices.length / 3, false, false, true, true, indices.length, false );
		this.frames = frames;
		this.indices = indices;
		this.texCoords = texCoords;
		this.normals = normals;
		this.numVertices = frames[0].vertices.length / 3;		
		System.arraycopy( indices, 0, mesh.getIndices(), 0, indices.length );
		System.arraycopy( texCoords, 0, mesh.getUV(), 0, texCoords.length );	
		System.arraycopy( frames[0].vertices, 0, mesh.getVertices(), 0, frames[0].vertices.length );
	}			

	public void render(int startIdx, int endIdx, float alpha) 
	{
		mesh.reset();		
		interpolateFrame( startIdx, endIdx, alpha );				
		mesh.setDirty();		
		mesh.render( PrimitiveType.Triangles, indices.length, 0 );	
	}
	
	public void dispose( )
	{
		mesh.dispose();
	}

	private void interpolateFrame(int startIdx, int endIdx, float alpha) 
	{	
		float[] frame = mesh.getVertices();
		float[] src = frames[startIdx].vertices;
		float[] dst = frames[endIdx].vertices;
		for( int i = 0; i < frame.length; i++ )
		{
			float d = dst[i];
			float s = src[i];
			frame[i] = s + (d - s) * alpha;
		}
	}
}
