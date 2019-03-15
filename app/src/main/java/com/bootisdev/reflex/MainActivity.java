package com.bootisdev.reflex;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.bootisdev.reflex.view.ReflexView;

public class MainActivity extends AppCompatActivity {
    private ReflexView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout layout = findViewById(R.id.relativeLayout);

        gameView = new ReflexView(this, getPreferences(Context.MODE_PRIVATE), layout);

        layout.addView(gameView, 0);
    }
}
