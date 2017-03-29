package be.xuv.biosport;

import processing.core.PApplet;
import processing.android.PFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        PApplet sketch = new Sketch();
        PFragment fragment = new PFragment();
        fragment.setSketch(sketch);
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }



}
