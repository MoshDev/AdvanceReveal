package com.moshx.reveal;

import android.animation.Animator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import com.moshx.reveallibrary.ColorRevealDrawable;
import com.moshx.reveallibrary.RevealAnimator;
import com.moshx.reveallibrary.RevealDrawable;

public class MainActivity extends ActionBarActivity {

  private ColorRevealDrawable drawable;

  @Override protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    final View contentView = findViewById(R.id.contentButton);
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
    drawable.animat()
        .radius(300)
        .alpha(0.5f)
        .color(Color.BLUE)
        .duration(1000)
        .addListener(new Animator.AnimatorListener() {
          @Override public void onAnimationStart(Animator animation) {

          }

          @Override public void onAnimationEnd(Animator animation) {
            drawable.animat().color(Color.GREEN).duration(1000).start();
          }

          @Override public void onAnimationCancel(Animator animation) {

          }

          @Override public void onAnimationRepeat(Animator animation) {

          }
        })
        .start();
  }

  public void reduceReveal(View v) {
    drawable.animat().radius(0).alpha(0).duration(1000).color(Color.RED).start();
    //  drawable.setDirectionReduce().start();
  }

  public void stopReveal(View v) {
    //drawable.stop();
  }
}
