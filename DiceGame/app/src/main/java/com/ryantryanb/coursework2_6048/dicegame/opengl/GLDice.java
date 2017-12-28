//*********************************
// GLDice.java
//*********************************
// Code created and maintained by:
// Ryan Tinman
//*********************************

package com.ryantryanb.coursework2_6048.dicegame.opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import com.ryantryanb.coursework2_6048.dicegame.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

//*********************************************************
// Creating a textured cube
// Defines verts for one face (Each face made of two polys)
// and renders by rotating and translating each face
//*********************************************************
public class GLDice
{
    private FloatBuffer vertexBuffer;   // Vertex Buffer
    private FloatBuffer textureBuffer;      // Texture Coords Buffer

    private int numFaces = 6;

    // Setting image resources for dice faces,
    // they are in this order so they match a real dice
    private int[] imageFileIDs =
    {
        R.drawable.one,
        R.drawable.four,
        R.drawable.six,
        R.drawable.three,
        R.drawable.five,
        R.drawable.two
    };

    private int[] textureIDs = new int[numFaces];
    private Bitmap[] bitmap = new Bitmap[numFaces];

    //*********************************
    // Constructor, setting up buffers
    //*********************************
    public GLDice(Context context)
    {
        // Allocating vertex buffer
        ByteBuffer vertexBuffer = ByteBuffer.allocateDirect(12 * 4 * numFaces);     // Allocating vertex buffer
        vertexBuffer.order(ByteOrder.nativeOrder());                                // Setting vertex buffer order

        this.vertexBuffer = vertexBuffer.asFloatBuffer();

        for (int face = 0; face < numFaces; face++)                                                                     // For each of the six faces
        {
            bitmap[face] = BitmapFactory.decodeStream(context.getResources().openRawResource(imageFileIDs[face]));      // Open each image file

            int imgWidth = bitmap[face].getWidth();         // Get the width of image
            int imgHeight = bitmap[face].getHeight();       // Get the height of image
            float faceWidth = 2.0f;                         // Allocate face width
            float faceHeight = 2.0f;                        // Allocate face height

            if (imgWidth > imgHeight)                               // Setting the aspect ratio
                faceHeight = faceHeight * imgHeight / imgWidth;     // Landscape

            else
                faceWidth = faceWidth * imgWidth / imgHeight;       // Portrait

            float faceLeft = -faceWidth / 2;    // Define left face vertex coordinate
            float faceRight = -faceLeft;        // Define right face vertex coordinate
            float faceTop = faceHeight / 2;     // Define top face vertex coordinate
            float faceBottom = -faceTop;        // Define bottom face vertex coordinate

            float[] vertices =
            {
                faceLeft, faceBottom, 0.0f,     // Bottom left vertex
                faceRight, faceBottom, 0.0f,    // Bottom right vertex
                faceLeft, faceTop, 0.0f,        // Top left vertex
                faceRight, faceTop, 0.0f,       // Top right vertex
            };

            this.vertexBuffer.put(vertices);    // Populate vertex buffer with vertices
        }
        this.vertexBuffer.position(0);          // Reset buffer position to 0

        float[] textureCoords =
        {
            0.0f, 1.0f,     // Bottom left vertex
            1.0f, 1.0f,     // Bottom right vertex
            0.0f, 0.0f,     // Top left vertex
            1.0f, 0.0f,     // Top right vertex
        };

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(textureCoords.length * 4 * numFaces);     // Allocating texture buffer
        byteBuffer.order(ByteOrder.nativeOrder());                                                  // Setting texture buffer order

        textureBuffer = byteBuffer.asFloatBuffer();

        for (int face = 0; face < numFaces; face++)     // For each of the six faces
        {
            textureBuffer.put(textureCoords);           // Populate texture buffer with texture coordinates
        }

        textureBuffer.position(0);                      // Reset buffer position to 0
    }

    //*****************
    // Render the dice
    //*****************
    public void draw(GL10 gl)
    {
        gl.glFrontFace(GL10.GL_CCW);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);               // Enabling vertex array
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);        // Enabling texture coordinate array
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);      // Setting vertex pointer
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);   // Setting texture coordinate pointer

        // Front face
        gl.glPushMatrix();                                          // Pushes current matrix stack
        gl.glTranslatef(0f, 0f, 1.0f);                              // Setting face translation
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[0]);        // Binding texture
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);              // Drawing face
        gl.glPopMatrix();                                           // Pops current matrix stack

        // Left face
        gl.glPushMatrix();
        gl.glRotatef(270.0f, 0f, 1f, 0f);
        gl.glTranslatef(0f, 0f, 1.0f);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[1]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4, 4);
        gl.glPopMatrix();

        // Rear face
        gl.glPushMatrix();
        gl.glRotatef(180.0f, 0f, 1f, 0f);
        gl.glTranslatef(0f, 0f, 1.0f);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[2]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 8, 4);
        gl.glPopMatrix();

        // Right face
        gl.glPushMatrix();
        gl.glRotatef(90.0f, 0f, 1f, 0f);
        gl.glTranslatef(0f, 0f, 1.0f);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[3]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 12, 4);
        gl.glPopMatrix();

        // Top face
        gl.glPushMatrix();
        gl.glRotatef(270.0f, 1f, 0f, 0f);
        gl.glTranslatef(0f, 0f, 1.0f);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[4]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 16, 4);
        gl.glPopMatrix();

        // Bottom face
        gl.glPushMatrix();
        gl.glRotatef(90.0f, 1f, 0f, 0f);
        gl.glTranslatef(0f, 0f, 1.0f);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[5]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 20, 4);
        gl.glPopMatrix();

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);          // Disabling vertex array
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);   // Disabling texture coordinate array
    }

    //************************************
    // Create textures from loaded images
    //************************************
    public void loadTexture(GL10 gl)
    {
        gl.glGenTextures(6, textureIDs, 0);                                                         // Generate texture ID array

        for (int face = 0; face < numFaces; face++)                                                 // For each of the six faces
        {
            gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[face]);                                 // Bind texture

            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);    // Setting nearest texture filtering
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);     // Setting linear texture filtering

            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap[face], 0);                             // Create texture from loaded image for current texture ID
        }
    }
}