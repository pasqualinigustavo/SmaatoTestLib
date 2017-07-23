package com.smattotest.app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.smattotest.app.fragment.QuestionFragment;
import com.smattotest.app.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new QuestionFragment())
                    .commit();
        }
    }
}
