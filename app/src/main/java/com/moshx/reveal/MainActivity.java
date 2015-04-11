package com.moshx.reveal;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import com.moshx.reveallibrary.ColorRevealDrawable;
import com.moshx.reveallibrary.RevealAnimator;

public class MainActivity extends ActionBarActivity {

  private ColorRevealDrawable drawable;

  @Override protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    final View contentView = findViewById(R.id.content);
    contentView.setBackgroundResource(R.mipmap.ic_launcher);
    contentView.setBackgroundDrawable(drawable = new ColorRevealDrawable());

    contentView.setOnTouchListener(new View.OnTouchListener() {
      @Override public boolean onTouch(View v, MotionEvent event) {
        drawable.setPivot(event.getX(), event.getY());
        return true;
      }
    });
  }

  public void enlargeReveal(View v) {
    RevealAnimator animator = new RevealAnimator(drawable);
    animator.radius(1000).duration(1000).alpha(1).color(Color.BLUE).start();
  }

  public void reduceReveal(View v) {
    RevealAnimator animator=new RevealAnimator(drawable);
    animator.radius(100).alpha(0).duration(1000).color(Color.RED).start();
    //  drawable.setDirectionReduce().start();
  }

  public void stopReveal(View v) {
    //drawable.stop();
  }
}
