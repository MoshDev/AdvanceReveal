package com.moshx.reveallibrary;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.util.Log;
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
  static final int SCALE_Y = 0x0010;
  static final int COLOR = 0x0020;
  static final int ALPHA = 0x0040;

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
    float next = from - radius;
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

  public void start() {
    if (animators.size() <= 0) {
      return;
    }

    Animator[] animatorsArray = animators.values().toArray(new Animator[] {});

    AnimatorSet set = new AnimatorSet();
    set.playTogether(animatorsArray);
    set.setDuration(mDuration);
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
}
