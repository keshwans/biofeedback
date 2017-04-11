package be.xuv.biofeedback;

import processing.android.PFragment;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.intel.heartratecore.api.HeartRateCore;
import android.Manifest;
import android.util.Log;

import android.view.View;
import android.view.Window;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "BioFeedback";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Starts the app fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        setContentView(be.xuv.biofeedback.R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Sketch sketch = new Sketch();

        CustomOSC osc = new CustomOSC();
        osc.setConnection();

        PFragment fragment = new PFragment();
        fragment.setSketch(sketch);
        fragmentManager.beginTransaction()
                .replace(be.xuv.biofeedback.R.id.container, fragment)
                .commit();

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                1);
        CustomHeartRateCoreListener hrcl = new CustomHeartRateCoreListener( sketch, osc );

        HeartRateCore hrc = HeartRateCore.getInstance(this.getApplicationContext(), hrcl);
        hrc.start();

    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.i(MainActivity.TAG, "permission: " + permissions.toString());
        if(grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        }
    }

}



