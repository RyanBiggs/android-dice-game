//*********************************
// MyOpenGLRenderer.java
//*********************************
// Code created and maintained by:
// Ryan Tinman
//*********************************

package com.ryantryanb.coursework2_6048.dicegame.opengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import java.util.Random;

public class MyOpenGLRenderer implements GLSurfaceView.Renderer
{
    private GLDice dice;
    private static float angleDice = 0;         // Rotational angle of dice
    private static float speedDice = -1.5f;     // Rotational speed of dice

    private static Random RANDOM = new Random();

    // Group of six random floating point values between -1 and +1
    // for the X, Y and Z rotation values for dice 1 and dice 2.
    // This gives each die a random rotation.
    private float dice1RotX = (RANDOM.nextFloat() * 2.0f) - 1.0f;
    private float dice1RotY = (RANDOM.nextFloat() * 2.0f) - 1.0f;
    private float dice1RotZ = (RANDOM.nextFloat() * 2.0f) - 1.0f;
    private float dice2RotX = (RANDOM.nextFloat() * 2.0f) - 1.0f;
    private float dice2RotY = (RANDOM.nextFloat() * 2.0f) - 1.0f;
    private float dice2RotZ = (RANDOM.nextFloat() * 2.0f) - 1.0f;

    // Setting up the lighting for the dice
    private float[] lightAmbient = {0.5f, 0.5f, 0.5f, 1.0f};    // Ambient light colour
    private float[] lightSpecular = {1.0f, 1.0f, 1.0f, 1.0f};   // Specular light position
    private float[] lightPosition = {0.0f, 2.0f, 2.0f, 1.0f};   // Light position

    // Constructor
    public MyOpenGLRenderer(Context context)
    {
        dice = new GLDice(context);
    }

    //************************************
    // Called when new surface is created
    //************************************
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        gl.glClearColor(0.8f, 0.8f, 1.0f, 1.5f);                            // Setting the background colour of the OpenGL frame
        gl.glClearDepthf(1.0f);                                             // Setting the depth value
        gl.glEnable(GL10.GL_DEPTH_TEST);                                    // Enabling the depth buffer to remove hidden surfaces
        gl.glDepthFunc(GL10.GL_LEQUAL);                                     // Setting the type of depth testing
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);     // Setting perspective view, and graphics mode
        gl.glShadeModel(GL10.GL_SMOOTH);                                    // Enabling smooth shading
        gl.glDisable(GL10.GL_DITHER);                                       // Disabling dithering

        dice.loadTexture(gl);                                               // Loading texture on to cube
        gl.glEnable(GL10.GL_TEXTURE_2D);                                    // Enabling texture

        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, lightAmbient, 0);     // Setting up light 1 with ambient light
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_SPECULAR, lightSpecular, 0);   // Setting up light 1 with specular light
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, lightPosition, 0);   // Setting light 1 position
        gl.glEnable(GL10.GL_LIGHT1);                                        // Enabling light 1
        gl.glEnable(GL10.GL_LIGHT0);                                        // Enabling OpenGL default light
    }

    //**********************************
    // Called after onSurfaceCreated()
    // and when the window size changes
    //**********************************
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        if (height == 0)
            height = 1;                                     // If height is 0, set to 1, to prevent divide by zero

        float aspect = (float) width / height;              // Setting aspect ratio

        gl.glViewport(0, 0, width, height);                 // Setting viewport size to cover entire window area

        gl.glMatrixMode(GL10.GL_PROJECTION);                // Selecting projection matrix
        gl.glLoadIdentity();                                // Resetting projection matrix


        GLU.gluPerspective(gl, 45, aspect, 0.1f, 100.f);    // Setting perspective projection

        gl.glMatrixMode(GL10.GL_MODELVIEW);                 // Selecting model view matrix mode
        gl.glLoadIdentity();                                // Resetting matrix
    }

    //*************************************
    // Called when each new frame is drawn
    //*************************************
    @Override
    public void onDrawFrame(GL10 gl)
    {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);        // Clearing colour and depth buffers

        gl.glEnable(GL10.GL_LIGHTING);                                          // Enabling lighting

        drawDice(gl, -0.8f, 0.0f, -6.0f, dice1RotX, dice1RotY, dice1RotZ);      // Drawing dice 1
        drawDice(gl, 0.8f, 0.0f, -6.0f, dice2RotX, dice2RotY, dice2RotZ);       // Drawing dice 2

        angleDice += speedDice;                                                 // Updating rotational angle
    }

    //***************************
    // Rendering of a single die
    //***************************
    private void drawDice(GL10 gl, float transX, float transY, float transZ, float rotX, float rotY, float rotZ)
    {
        gl.glLoadIdentity();                            // Resetting matrix
        gl.glTranslatef(transX, transY, transZ);        // Setting dice translation in world space
        gl.glRotatef(angleDice, rotX, rotY, rotZ);      // Setting dice rotation
        gl.glScalef(0.4f, 0.4f, 0.4f);                  // Setting dice scale
        dice.draw(gl);                                  // Drawing dice
    }
}