package com.example.drawing_app;

import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.Button;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

import androidx.constraintlayout.motion.widget.MotionLayout;

public class MainActivity extends AppCompatActivity {
    private DrawingSurface drawingSurface;
    private Button cyanButton;
    private Button magentaButton;
    private Button yellowButton;
    private Button clearButton;

    private MotionLayout motionLayout;
    //private MotionLayout cyanMotionLayout;
    //private MotionLayout magentaMotionLayout;
    //private MotionLayout yellowMotionLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setButtonColors();
        setOnClickListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_clear) {
            drawingSurface.clear();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews() {
        drawingSurface = findViewById(R.id.DrawingSurface);
        cyanButton = findViewById(R.id.cyanButton);
        magentaButton = findViewById(R.id.magentaButton);
        yellowButton = findViewById(R.id.yellowButton);
        clearButton = findViewById(R.id.clearButton);

        motionLayout = findViewById(R.id.motionLayout);

        //cyanMotionLayout = findViewById(R.id.cyanMotionLayout);
        //magentaMotionLayout = findViewById(R.id.magentaMotionLayout);
        //yellowMotionLayout = findViewById(R.id.yellowMotionLayout);
    }

    private void setButtonColors() {
        cyanButton.setBackgroundColor(PaintColor.CYAN.value);
        magentaButton.setBackgroundColor(PaintColor.MAGENTA.value);
        yellowButton.setBackgroundColor(PaintColor.YELLOW.value);
        clearButton.setBackgroundColor(PaintColor.GRAY.value);
    }

    private void setOnClickListeners() {
        cyanButton.setOnClickListener(v -> {

            motionLayout.setTransition(R.id.cyan_animation);
            motionLayout.transitionToEnd();

            //cyanMotionLayout.transitionToEnd();
            drawingSurface.setpaint_color(PaintColor.CYAN.value);
        });
        magentaButton.setOnClickListener(v -> {

            motionLayout.setTransition(R.id.magenta_animation);
            motionLayout.transitionToEnd();

            //magentaMotionLayout.transitionToEnd();
            drawingSurface.setpaint_color(PaintColor.MAGENTA.value);
        });
        yellowButton.setOnClickListener(v -> {


            motionLayout.setTransition(R.id.yellow_animation);
            motionLayout.transitionToEnd();
           // yellowMotionLayout.transitionToEnd();
            drawingSurface.setpaint_color(PaintColor.YELLOW.value);
        });
        clearButton.setOnClickListener(v ->{

            motionLayout.setTransition(R.id.clear_animation);
            motionLayout.transitionToEnd();

            drawingSurface.clear();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        drawingSurface.stopDrawingThread();
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawingSurface.startDrawingThread();
    }
}