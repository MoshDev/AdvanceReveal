package com.moshx.reveallibrary;

import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.Property;

/**
 * Created by M.Ersan on 4/6/15.
 */
public abstract class RevealDrawable extends Drawable {

  public static final Property<RevealDrawable, Float> RADIUS =
      new Property<RevealDrawable, Float>(Float.TYPE, "radius") {
        @Override public void set(RevealDrawable object, Float value) {
          object.setRadius(value);
        }

        @Override public Float get(RevealDrawable object) {
          return object.getRadius();
        }
      };

  public static final Property<RevealDrawable, Integer> COLOR =
      new Property<RevealDrawable, Integer>(Integer.TYPE, "color") {
        @Override public void set(RevealDrawable object, Integer value) {
          object.setColor(value);
        }

        @Override public Integer get(RevealDrawable object) {
          return object.getColor();
        }
      };

  public static final Property<RevealDrawable, Float> PIVOT_X =
      new Property<RevealDrawable, Float>(Float.TYPE, "pivotX") {

        @Override public void set(RevealDrawable object, Float value) {
          object.setPivotX(value);
        }

        @Override public Float get(RevealDrawable object) {
          return object.getPivotX();
        }
      };

  public static final Property<RevealDrawable, Float> PIVOT_Y =
      new Property<RevealDrawable, Float>(Float.TYPE, "pivotY") {

        @Override public void set(RevealDrawable object, Float value) {
          object.setPivotY(value);
        }

        @Override public Float get(RevealDrawable object) {
          return object.getPivotY();
        }
      };

  public static final Property<RevealDrawable, Integer> ALPHA =
      new Property<RevealDrawable, Integer>(Integer.TYPE, "alpha") {

        @Override public void set(RevealDrawable object, Integer value) {
          object.setAlpha(value);
        }

        @Override public Integer get(RevealDrawable object) {
          return object.getAlpha();
        }
      };

  public static final Property<RevealDrawable, PointF> PIVOT =
      new Property<RevealDrawable, PointF>((Class<PointF>) PointF[].class.getComponentType(),
          "pivot") {
        @Override public void set(RevealDrawable object, PointF value) {
          object.setPivot(value);
        }

        @Override public PointF get(RevealDrawable object) {
          return new PointF(object.pivotX, object.pivotY);
        }
      };

  protected Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
  protected float radius = 0f;
  protected float pivotX, pivotY;

  public RevealDrawable() {
    onInitPaint(mPaint);
  }

  protected abstract void onInitPaint(Paint paint);

  @Override public void setAlpha(int alpha) {
    mPaint.setAlpha(alpha);
    invalidateSelf();
  }

  @Override public int getAlpha() {
    return mPaint.getAlpha();
  }

  @Override public void setColorFilter(ColorFilter cf) {
    mPaint.setColorFilter(cf);
    invalidateSelf();
  }

  @Override public int getOpacity() {
    return PixelFormat.TRANSPARENT;
  }

  @Override public int getIntrinsicHeight() {
    int h = getBounds() != null ? getBounds().height() : 0;
    return h > 0 ? h : 100;
  }

  @Override public int getIntrinsicWidth() {
    int w = getBounds() != null ? getBounds().width() : 0;
    return w > 0 ? w : 100;
  }

  public RevealDrawable setColor(int color) {
    mPaint.setColor(color);
    invalidateSelf();
    return this;
  }

  public int getColor() {
    return mPaint.getColor();
  }

  public RevealDrawable setPivotX(float pivotX) {
    this.pivotX = pivotX;
    invalidateSelf();
    return this;
  }

  public float getPivotX() {
    return pivotX;
  }

  public RevealDrawable setPivotY(float pivotY) {
    this.pivotY = pivotY;
    invalidateSelf();
    return this;
  }

  public float getPivotY() {
    return pivotY;
  }

  public RevealDrawable setPivot(float pivotX, float pivotY) {
    this.pivotX = pivotX;
    this.pivotY = pivotY;
    invalidateSelf();
    return this;
  }

  public RevealDrawable setPivot(PointF point) {
    this.pivotX = point.x;
    this.pivotY = point.y;
    invalidateSelf();
    return this;
  }

  public RevealDrawable setRadius(float radius) {
    this.radius = radius;
    invalidateSelf();
    return this;
  }

  public float getRadius() {
    return radius;
  }

  public RevealDrawable setPaint(Paint paint) {
    mPaint = paint;
    invalidateSelf();
    return this;
  }

  public Paint getPaint() {
    return mPaint;
  }

  private RevealAnimator animator;

  public RevealAnimator animat() {
    if (animator == null) {
      animator = new RevealAnimator(this);
    }
    return animator;
  }
}
