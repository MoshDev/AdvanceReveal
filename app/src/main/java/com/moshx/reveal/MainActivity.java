package com.moshx.reveal;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import com.moshx.reveallibrary.ColorRevealDrawable;
import com.moshx.reveallibrary.RevealAnimator;
import com.moshx.reveallibrary.RevealDrawable;

public class MainActivity extends ActionBarActivity {

  private ColorRevealDrawable drawable;
  View contentView;

  @Override protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    contentView = findViewById(R.id.content);
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
    drawable.setPivot(contentView.getWidth() / 2, 0);
    drawable.setRadius(1);

    Path path = new Path();
    path.moveTo(0, 0);
    //path.lineTo(contentView.getWidth() / 3, contentView.getHeight() / 2);
    path.addArc(new RectF(0, 0, contentView.getWidth(), contentView.getHeight() / 2), 0, 180);

    drawable.animat()
        .radius(300)
        .alpha(1f)
        .path(path)
        .duration(2000)
        .addListener(new Animator.AnimatorListener() {
          @Override public void onAnimationStart(Animator animation) {

          }

          @Override public void onAnimationEnd(Animator animation) {

            drawable.animat()
                .radius(contentView.getHeight())
                .color(Color.GREEN)
                .duration(1000)
                .start();
          }

          @Override public void onAnimationCancel(Animator animation) {

          }

          @Override public void onAnimationRepeat(Animator animation) {

          }
        })
        .start();
  }

  public void reduceReveal(View v) {

    drawable.animat()
        .radius(100)
        .color(Color.BLUE)
        .duration(1000)
        .addListener(new Animator.AnimatorListener() {
          @Override public void onAnimationStart(Animator animation) {

          }

          @Override public void onAnimationEnd(Animator animation) {
            drawable.animat().pivot(contentView.getWidth() / 2, 0).radius(0).alpha(0).start();
          }

          @Override public void onAnimationCancel(Animator animation) {

          }

          @Override public void onAnimationRepeat(Animator animation) {

          }
        })
        .start();
    //drawable.animat().radius(0).alpha(0).duration(1000).color(Color.RED).pivot(0, 0).start();
    //  drawable.setDirectionReduce().start();
  }

  public void stopReveal(View v) {
    //drawable.stop();
  }
}
