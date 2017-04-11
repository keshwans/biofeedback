package be.xuv.biofeedback;

import android.support.annotation.NonNull;

import com.illposed.osc.OSCMessage;
import com.intel.heartratecore.api.HeartRateCoreListener;
import com.intel.heartratecore.api.HeartRateCoreStatus;

import java.util.EnumSet;

class CustomHeartRateCoreListener implements HeartRateCoreListener {
    private Sketch sketch;
    private CustomOSC osc;
    private OSCMessage msg;

    public CustomHeartRateCoreListener(Sketch sketch, CustomOSC osc){
        this.sketch = sketch;
        this.osc = osc;
    }

    @Override
    public void onHeartRateChanged(int i) {
        sketch.setHeartRate(i);

        msg = new OSCMessage("/heartrate");
        msg.addArgument( i );
        new SendOSCMessage(osc).execute(msg);
    }

    @Override
    public void onStatusChanged(@NonNull EnumSet<HeartRateCoreStatus> enumSet) {
        sketch.setHeartRateCoreStatus( enumSet );

        msg = new OSCMessage("/status");
        msg.addArgument( enumSet.toString() );
        new SendOSCMessage(osc).execute(msg);
    }

}


