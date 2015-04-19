package com.moshx.reveal;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.BounceInterpolator;
import com.moshx.reveallibrary.ColorRevealDrawable;
import com.moshx.reveallibrary.RevealAnimator;

public class MainActivity extends ActionBarActivity {

  private ColorRevealDrawable drawable;
  private View contentView;
  private float centerX, centerY;

  @Override protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    contentView = findViewById(R.id.content_test);
    contentView.setBackgroundDrawable(drawable = new ColorRevealDrawable());
    drawable.setPivot(0, 0);

    contentView.setOnTouchListener(new View.OnTouchListener() {
      @Override public boolean onTouch(View v, MotionEvent event) {
        drawable.setPivot(event.getX(), event.getY());
        return true;
      }
    });

    contentView.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override public void onGlobalLayout() {
            contentView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            centerX = contentView.getWidth() / 2;
            centerY = contentView.getHeight() / 2;
            drawable.setPivot(centerX, 0);
          }
        });
  }

  public void enlargeReveal(View v) {

    RevealAnimator animator1 = new RevealAnimator(drawable);
    animator1.radius(100)
        .pivot(centerX, contentView.getHeight())
        .interpolator(new BounceInterpolator())
        .color(Color.CYAN);

    RevealAnimator animator2 = new RevealAnimator(drawable);
    animator2.radius(contentView.getHeight() + 100);

    RevealAnimator animator3 = new RevealAnimator(drawable);
    animator3.color(Color.YELLOW).duration(2000);

    animator1.withNextAnim(animator2.withNextAnim(animator3)).start();
  }

  public void reduceReveal(View v) {

    drawable.animat().radius(100).duration(300).color(Color.BLACK).withEndAction(new Runnable() {
      @Override public void run() {
        drawable.animat().pivot(contentView.getWidth(), 0).duration(500).radius(10).start();
      }
    }).start();
  }

  public void cancelReveal(View v) {
    drawable.animat().cancel();
  }
}
