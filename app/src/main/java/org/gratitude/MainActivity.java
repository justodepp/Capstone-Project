package org.gratitude;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements Gratitude.OnFinishedListener{

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Gratitude app = (Gratitude) getApplication();
        app.setInterface(MainActivity.this);

        mProgressBar = findViewById(R.id.indeterminateBar);
        // new AllOrganizations.Builder().withOrganizations();
    }

    @Override
    public void onFinished() {
        mProgressBar.setVisibility(View.GONE);
    }
}
