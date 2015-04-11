package com.moshx.reveallibrary;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by M.Ersan on 3/29/15.
 */
public class ColorRevealDrawable extends RevealDrawable {

  /**
   * TODO: features
   * 1- movable
   * 2- fade in fade out
   * 3- color elevator
   * 4- gradient
   * 5- animated dialogs (spring effect)
   * 6- support gravity
   * *
   */

  @Override public void draw(Canvas canvas) {
    canvas.drawCircle(pivotX, pivotY, radius, mPaint);
  }

  @Override protected void onInitPaint(Paint paint) {
    paint.setColor(Color.RED);
    paint.setStyle(Paint.Style.FILL);
  }
}


