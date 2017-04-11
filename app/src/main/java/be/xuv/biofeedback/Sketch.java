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

    public void settings() {
        size(1100, 2000);
    }

    public void setup() {
        textFont(createFont("Arial", 40));
    }

    public void draw() {
        background(0);
        fill(255);
        text("Heart Rate: " + heartRate, 10, 60);
        text("Status: " + status, 300, 60);
        if (beat()) {
            heartColor = color(255, 0, 0);
            scale = 20;
        } else {
            heartColor = color( red(heartColor)-1 , green(heartColor)+1, blue(heartColor)+1 );
        }

        if (scale > MINSCALE) {
            scale = scale - (MAXSCALE - MINSCALE)/frameRate;
        }



        fill(heartColor);
        drawHeart(550, 500, scale);
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

