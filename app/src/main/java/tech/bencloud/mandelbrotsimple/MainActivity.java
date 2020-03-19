package tech.bencloud.mandelbrotsimple;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageView mandelbrotImageView;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call the Activity constructor
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        LinearLayout lin = findViewById(R.id.mainLayout);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        mandelbrotImageView = new ImageView(this);
        GenerateMandelbrotTask gmbbat = new GenerateMandelbrotTask (mandelbrotImageView, -0.743643135, 0.131825963, 0.000024628, 0.000024628, screenWidth, screenHeight/2);
        gmbbat.execute(10_000);

        lin.addView(mandelbrotImageView);

        lin.invalidate();
    }

}

