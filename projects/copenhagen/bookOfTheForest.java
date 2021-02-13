import processing.core.*; 
import processing.xml.*; 

import ddf.minim.*; 
import ddf.minim.signals.*; 
import ddf.minim.analysis.*; 
import ddf.minim.effects.*; 
import processing.opengl.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class bookOfTheForest extends PApplet {






Minim minim;
AudioPlayer lobby;
AudioPlayer track;
//AudioPlayer track_1;
//AudioPlayer track_2;

PImage img;       // The source image
PImage imgBg;
PFont base;
PFont title;
int cellsize = 2; // Dimensions of each cell in the grid
int columns, rows;   // Number of columns and rows in our system
int step = 0;
int state = -2;
int fadeState = 0;
int counter = 0;

public void setup() {
  frameRate(30);
  size(screen.width, screen.height, P3D); 
  img = loadImage("copenhagen.png");  // Load the image");
  imgBg = loadImage("facebook.png");
  base = loadFont("Times-Roman-24.vlw");
  title = loadFont("BettyNoir-48.vlw");
  columns = img.width / cellsize;  // Calculate # of columns
  rows = img.height / cellsize;  // Calculate # of rows
  noCursor();
  minim = new Minim(this);
  lobby = minim.loadFile("fan.mp3");
  lobby.setGain(-20);
  track = minim.loadFile("track.mp3");
  //track_1 = minim.loadFile("track_1.mp3");
  //track_2 = minim.loadFile("theme_2.mp3");
}

public void draw() {
  background(255 - (step * .05f));  
 
  // Begin loop for columns
  for ( int i = 0; i < columns; i++) {
    // Begin loop for rows
    for ( int j = 0; j < rows; j++) {
      int x = i*cellsize + cellsize/2;  // x position
      int y = j*cellsize + cellsize/2;  // y position
      int loc = x + y*img.width;  // Pixel array location
      int c = img.pixels[loc];  // Grab the color
      // Calculate a z position as a function of mouseX and pixel brightness
      float z = (step / PApplet.parseFloat(width)) * brightness(img.pixels[loc]) - 20.0f;
      // Translate to the location, set fill and stroke, and draw the rect
      pushMatrix();
      translate(x + (screen.width/2 - img.width/2), y + (screen.height/2 - img.height/2), z);
      if(state <2) {fill(c, (204 - step * .05f));}
      if(state == 2) {fill(c, (204 - step * .9f));}
      noStroke();
      rectMode(CENTER);
      rect(0, 0, cellsize, cellsize);
      popMatrix();
    }
  }
 if(state == 0 && keyPressed){state = 1;}



 switch(state){
   case -2:
     background(0);
     textFont(base, 12);
     fill(255);
     lobby.play();
     if(frameCount> 100) {text("She left for Copenhagen.", screen.width/2 - 100, screen.height/2 + img.height/2 + 50);}
     if(frameCount > 250){state++;}
     break;
   case -1:
     image(imgBg, screen.width/2 - 640/2, screen.height/2 - 580/2 , 640, 580);
     if(frameCount> 300){state++;}
     break;
   case 0:
     textFont(base, 12);
     fill(50, fadeState);
     text("Press any key to tell her you love her.", screen.width/2 - 100, screen.height/2 + img.height/2 + 50);
     image(imgBg, screen.width/2 - 640/2, screen.height/2 - 580/2 , 640, 580);
     if(fadeState < 255){fadeState+=15;}
     break;
   case 1:
     track.play();
     tint(255, fadeState);
     fill(0, fadeState);
     if(fadeState>0){
       image(imgBg, screen.width/2 - 640/2, screen.height/2 - 580/2 , 640, 580);
       text("Press any key to tell her you love her.", screen.width/2 - 100, screen.height/2 + img.height/2 + 50);
     }
     if(counter>3360){state++; lobby.close();counter = 0;}
     step+=3;
     counter++;
     fadeState--;
     break;
   case 2:
     step+=30;
     break;
}
}
  static public void main(String args[]) {
    PApplet.main(new String[] { "--present", "--bgcolor=#666666", "--hide-stop", "bookOfTheForest" });
  }
}
