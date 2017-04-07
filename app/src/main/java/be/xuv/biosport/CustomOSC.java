package be.xuv.biosport;

import android.util.Log;
import android.os.AsyncTask;

import java.util.ArrayList;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class CustomOSC {

    private OSCPortOut sender;
    private ArrayList<Object> args;
    public InetAddress targetIP;
    OSCMessage msg;

    public CustomOSC(){
        msg = new OSCMessage("/heartrate");
    }

    public void setConnection(){
        try {
            targetIP = InetAddress.getByName("10.3.126.34");
            Log.d(MainActivity.TAG, "Connecting to Broadcast");
            //targetIP = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            sender = new OSCPortOut(targetIP, 44444);
            Log.d(MainActivity.TAG, "Setting output port");
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    public void send(OSCMessage msg) {
        try {
            sender.send(msg);
        } catch (Exception e) {
            Log.d(MainActivity.TAG, "Couldn't send OSC message");
            e.printStackTrace();
        }
    }
}

class SendOSCMessage extends AsyncTask<OSCMessage, Void, Void> {

    private Exception exception;
    private CustomOSC osc;

    public SendOSCMessage(CustomOSC osc) {
        this.osc = osc;
    }

    protected Void doInBackground(OSCMessage... msg) {
        int count = msg.length;
        try {
            for (int i = 0; i < count; i++) {
                osc.send(msg[i]);
                Log.d(MainActivity.TAG, "Sending heartrate");
            }
        } catch (Exception e) {
            this.exception = e;
        }
        return null;
    }
}


