package com.moshx.reveallibrary;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PointFEvaluator;
import android.animation.TypeConverter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by M.Ersan on 4/5/15.
 */
public class RevealAnimator {

  static final int NONE = 0x0000;
  static final int PIVOT_X = 0x0001;
  static final int PIVOT_Y = 0x0002;
  static final int RADIUS = 0x0008;
  static final int COLOR = 0x0010;
  static final int ALPHA = 0x0020;

  private RevealAnimator nextAnimation;

  private HashMap<Integer, Animator> animators = new HashMap<>();
  private final List<Animator.AnimatorListener> listeners = new ArrayList<>();

  private final RevealDrawable mDrawable;
  private long mDuration = 0;

  public RevealAnimator(RevealDrawable drawable) {
    mDrawable = drawable;
  }

  public RevealAnimator alpha(float fromValue, float toValue) {
    int currentValue = (int) Math.ceil(fromValue * 255f);
    int nextValue = (int) Math.ceil(toValue * 255);
    ObjectAnimator animator =
        ObjectAnimator.ofInt(mDrawable, RevealDrawable.ALPHA, currentValue, nextValue);
    animators.put(ALPHA, animator);
    return this;
  }

  public RevealAnimator alpha(float alpha) {
    float from = mDrawable.getAlpha() / 255f;
    float next = alpha;
    return alpha(from, next);
  }

  public RevealAnimator alphaBy(float alpha) {
    float from = mDrawable.getAlpha() / 255f;
    float next = (mDrawable.getAlpha() / 255f) + alpha;
    return alpha(from, next);
  }

  public RevealAnimator radius(float from, float to) {
    ObjectAnimator animator = ObjectAnimator.ofFloat(mDrawable, RevealDrawable.RADIUS, from, to);
    animators.put(RADIUS, animator);
    return this;
  }

  public RevealAnimator radius(float radius) {
    float currentValue = mDrawable.getRadius();
    return radius(currentValue, radius);
  }

  public RevealAnimator radiusBy(float radius) {
    float from = mDrawable.getRadius();
    float next = from + radius;
    return radius(from, next);
  }

  public RevealAnimator color(int color) {
    int currentColor = mDrawable.getColor();
    ObjectAnimator animator =
        ObjectAnimator.ofObject(mDrawable, RevealDrawable.COLOR, new ArgbEvaluator(), currentColor,
            color);
    animators.put(COLOR, animator);
    return this;
  }

  public RevealAnimator duration(long duration) {
    mDuration = duration;
    return this;
  }

  public RevealAnimator pivotX(float x) {
    float from = mDrawable.getPivotX();
    return pivotX(from, x);
  }

  public RevealAnimator pivotXBy(float x) {
    float from = mDrawable.getPivotX();
    float next = from + x;
    return pivotX(from, next);
  }

  public RevealAnimator pivotX(float from, float to) {
    animators.remove(PIVOT_X);
    ObjectAnimator animator = ObjectAnimator.ofFloat(mDrawable, RevealDrawable.PIVOT_X, from, to);
    animators.put(PIVOT_X, animator);
    return this;
  }

  public RevealAnimator pivotY(float y) {
    float from = mDrawable.getPivotY();
    return pivotY(from, y);
  }

  public RevealAnimator pivotYBy(float y) {
    float from = mDrawable.getPivotY();
    float next = from + y;
    return pivotY(from, next);
  }

  public RevealAnimator pivotY(float from, float to) {
    animators.remove(PIVOT_Y);
    ObjectAnimator animator = ObjectAnimator.ofFloat(mDrawable, RevealDrawable.PIVOT_Y, from, to);
    animators.put(PIVOT_Y, animator);
    return this;
  }

  public RevealAnimator pivot(float x, float y) {
    pivotX(x);
    return pivotY(y);
  }

  public RevealAnimator path(Path path) {

    animators.remove(PIVOT_X);
    animators.remove(PIVOT_Y);

    ObjectAnimator animator =
        ObjectAnimator.ofObject(mDrawable, RevealDrawable.PIVOT, new PathEvaluator(path),
            new PointF(0, 0), new PointF(1, 1));
    animators.put(PIVOT_X, animator);
    return this;
  }

  public void start() {
    if (animators.size() <= 0) {
      return;
    }

    Animator[] animatorsArray = animators.values().toArray(new Animator[] {});

    AnimatorSet set = new AnimatorSet();
    set.playTogether(animatorsArray);
    set.setDuration(mDuration);
    set.setInterpolator(new LinearInterpolator());
    if (!listeners.isEmpty()) {
      for (Animator.AnimatorListener ls : listeners) {
        set.addListener(ls);
      }
    }
    animators.clear();
    listeners.clear();
    set.start();
  }

  public RevealAnimator addListener(Animator.AnimatorListener listener) {
    listeners.add(listener);
    return this;
  }

  public RevealAnimator afterEnd(RevealAnimator revealAnimator) {
    nextAnimation = revealAnimator;
    return this;
  }
}
