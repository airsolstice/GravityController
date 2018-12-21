package org.demo.gravity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.TextureView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager = null;
    private Sensor mSensor = null;
    private float x, y, z;
    private SeekBar seekBar;
    private TextView value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = this.findViewById(R.id.seekBar);
        seekBar.setMax(20);
        value = this.findViewById(R.id.value);

        mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }


    SensorEventListener lsn = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            x = event.values[SensorManager.DATA_X];
            y = event.values[SensorManager.DATA_Y];
            z = event.values[SensorManager.DATA_Z];

            seekBar.setProgress((int) (z + 10));
            value.setText(z + "");
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            mSensorManager.registerListener(lsn, mSensor, SensorManager.SENSOR_DELAY_GAME);

            String str = "x=" + x + "; y=" + y + "; z=" + z;
            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onResume() {
        mSensorManager.registerListener(lsn, mSensor, SensorManager.SENSOR_DELAY_GAME);
        super.onResume();
    }


    @Override
    public void onPause() {
        mSensorManager.unregisterListener(lsn);
        super.onPause();
    }
}
