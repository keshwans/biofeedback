package be.xuv.biosport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.Manifest;

import com.intel.heartratecore.api.HeartRateCore;
import com.intel.heartratecore.api.HeartRateCoreListener;
import com.intel.heartratecore.api.HeartRateCoreStatus;


/**
 * Created by juliendeswaef on 3/20/17.
 */

import java.util.EnumSet;

import processing.core.PApplet;

public class Sketch extends PApplet {
    public Context context;
    public HeartRateCore hrc;
    public HrListener listener;
    public int hr;
    public Boolean hasPermission;

    public void settings() {
        size(600, 600);
    }

    public void setup() {
        //fullScreen();
        hasPermission = false;
        textFont(createFont("Arial", 60));
    }

    public void draw() {
        background(0);
        text("Heart rate: " + hr, 10, 60);
    }

    class HrListener implements HeartRateCoreListener {
        @Override
        public void onStatusChanged(@NonNull EnumSet<HeartRateCoreStatus> enumSet) {
            print("onStatusChanged::" + enumSet.toString());
        }
        @Override
        public void onHeartRateChanged(int i) {
            print("Heart rate changed: " + i);
            print("Done HR change");
        }
    }

    public void onPermissionsGranted() {
        print("REC AUDIO: " + String.valueOf(checkPermission(Manifest.permission.RECORD_AUDIO)));
        if ( checkPermission(Manifest.permission.RECORD_AUDIO) ) {
            Context context = surface.getContext();
            listener = new HrListener();
            hrc = HeartRateCore.getInstance(context, listener);
            hrc.start();
            hasPermission = true;
        } else {
            hasPermission = false;
        }
    }
}
