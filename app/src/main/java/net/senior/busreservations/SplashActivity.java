package net.senior.busreservations;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    ImageView bus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bus=findViewById(R.id.splashImg);
//        Path path = new Path();
//        path.arcTo(10f, 100f, 500f, 300f, 270f, -90f, true);
//        ObjectAnimator animator = ObjectAnimator.ofFloat(bus, View.X, View.Y, path);
//        animator.setDuration(2000);
//        animator.start();

        ObjectAnimator animation = ObjectAnimator.ofFloat(bus, "translationX", 160f);
        animation.setDuration(3000);
        animation.start();


//        bus.startAnimation(AnimationUtils.loadAnimation(SplashActivity.this,R.anim.anim));

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(mainIntent);

            }
        }, 3000);
    }

}