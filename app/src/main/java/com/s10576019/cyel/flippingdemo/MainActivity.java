package com.s10576019.cyel.flippingdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.s10576019.cyel.flippingdemo.sensors.FlippingManager;

public class MainActivity extends AppCompatActivity {
    private FlippingManager flippingManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        flippingManager = new FlippingManager(this);
        flippingManager.start(new FlippingManager.OnScreenFlipListener() {
            @Override
            public void onScreenFlipped(int facingDirection) {
                switch (facingDirection) {
                    case DIRECTION_UP:
                        setVisible(true);
                        break;
                    case DIRECTION_DOWN:
                        setVisible(false);
                        break;
                }
            }
        });
    }

    @Override
    protected void onStop() {
        flippingManager.stop();
        flippingManager = null;
        super.onStop();
    }
}
