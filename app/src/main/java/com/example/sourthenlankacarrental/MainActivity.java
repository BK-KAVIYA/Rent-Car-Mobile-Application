package com.example.sourthenlankacarrental;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN=5000;
    //variables
    Animation toAnim,botAnim;
    ImageView image;
    TextView name,location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Animation
        toAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        botAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hook
        image=findViewById(R.id.imageView);
        name=findViewById(R.id.textView);
        location=findViewById(R.id.textView2);

        image.setAnimation(toAnim);
        name.setAnimation(botAnim);
        location.setAnimation(botAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,Login.class);

                Pair[] pairs=new Pair[2];
                pairs[0]=new Pair<View,String>(image,"logo_image");
                pairs[1]=new Pair<View,String>(name,"logo_text");

                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                startActivity(intent,options.toBundle());
                finish();
            }
        },SPLASH_SCREEN);
    }
}
