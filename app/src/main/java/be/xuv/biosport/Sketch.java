package be.xuv.biosport;

import com.intel.heartratecore.api.HeartRateCoreStatus;
import java.util.EnumSet;

import processing.core.PApplet;

public class Sketch extends PApplet {
    private int heartRate;
    private EnumSet<HeartRateCoreStatus> status;

    public void settings() {
        size(1100, 2000);
    }

    public void setup() {
        textFont(createFont("Arial", 40));
    }

    public void draw() {
        background(0);
        text("Heart Rate: " + heartRate, 10, 60);
        text("Status: " + status, 300, 60);

    }

    public void setHeartRate( int i ) {
        heartRate = i;
        print("Heart rate: " + i);
    }

    public void setHeartRateCoreStatus( EnumSet<HeartRateCoreStatus> s ) {
        status = s;
        print("Status: " + s);
    }
}

