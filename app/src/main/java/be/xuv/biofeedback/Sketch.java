package be.xuv.biofeedback;

import com.intel.heartratecore.api.HeartRateCoreStatus;
import java.util.EnumSet;

import processing.core.PApplet;

public class Sketch extends PApplet {
    private int heartRate;
    private EnumSet<HeartRateCoreStatus> status;
    private int lastBeat = 0;
    private int heartColor = color(128, 0, 0);

    private static final float MINSCALE = 17;
    private static final float MAXSCALE = 20;
    private float scale = MINSCALE;
    private int interval;

    private static final int WIDTH = 2000;
    private static final int HEIGHT = 1100;

    private static final boolean DEBUG = true;

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void setup() {
        colorMode(HSB, 360, 100, 100);
        textFont(createFont("Arial", 40));
    }

    public void draw() {
        background(0);
        fill(255);

        if (DEBUG) {
            text("Heart Rate: " + heartRate, 10, 60);
            text("Status: " + status, 300, 60);
            text("FrameRate: " + (int)frameRate, 1000, 60);
        }

        float steps = (float)(frameRate * interval * .001);

        if (beat()) {
            heartColor = color(0, 100, 50);
            scale = 20;
        } else {
            heartColor = color( 0 , saturation(heartColor) - 50/steps, brightness(heartColor) - 25/steps );
        }

        if (scale > MINSCALE) {
            scale = scale - (MAXSCALE - MINSCALE)/steps;
        }

        fill(heartColor);
        drawHeart(WIDTH/2, HEIGHT/8, scale);
    }

    public void setHeartRate( int i ) {
        heartRate = i;
        print("Heart rate: " + i);
    }

    public void setHeartRateCoreStatus( EnumSet<HeartRateCoreStatus> s ) {
        status = s;
        print("Status: " + s);
    }

    public boolean beat() {
        if (heartRate > 0 ) {
            interval = 60000 / heartRate;
            int delay = millis() - lastBeat;
            if (delay >= interval) {
                lastBeat = millis();
                return true;
            }
        }
        return false;
    }

    public void drawHeart(int x, int y, float s){
        pushMatrix();
        translate(x, y);
        scale(s);
        smooth();
        noStroke();
        beginShape();
        vertex(0, 15);
        bezierVertex(0, -5, 40, 5, 0, 40);
        vertex(0, 15);
        bezierVertex(0, -5, -40, 5, 0, 40);
        endShape();
        popMatrix();
    }
}

