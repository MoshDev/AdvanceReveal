package com.moshx.reveallibrary;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.util.Log;
import java.util.HashMap;

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

  private final RevealDrawable mDrawable;
  private long mDuration = 0;

  public RevealAnimator(RevealDrawable drawable) {
    mDrawable = drawable;
  }

  public RevealAnimator alpha(float fromValue, float toValue) {
    //TODO
    return this;
  }

  public RevealAnimator alpha(float alpha) {
    int nextValue = (int) Math.ceil(alpha * 255);
    int currentValue = mDrawable.getAlpha();

    Log.d("Mosh123", "Alpha:" + currentValue + "-->" + nextValue);

    ObjectAnimator animator =
        ObjectAnimator.ofInt(mDrawable, RevealDrawable.ALPHA, currentValue, nextValue);
    animators.put(ALPHA, animator);
    return this;
  }

  public RevealAnimator alphaBy(float alpha) {
    int nextValue = mDrawable.getAlpha() + (int) Math.ceil(alpha * 255);
    int currentValue = mDrawable.getAlpha();
    ObjectAnimator animator =
        ObjectAnimator.ofInt(mDrawable, RevealDrawable.ALPHA, nextValue, currentValue);
    animators.put(ALPHA, animator);
    return this;
  }

  public RevealAnimator radius(float radius) {
    float currentValue = mDrawable.getRadius();
    ObjectAnimator animator =
        ObjectAnimator.ofFloat(mDrawable, RevealDrawable.RADIUS, currentValue, radius);
    animators.put(RADIUS, animator);
    return this;
  }

  public RevealAnimator radiusBy(float radius) {
    float currentValue = mDrawable.getRadius();
    float nextValue = currentValue - radius;
    ObjectAnimator animator =
        ObjectAnimator.ofFloat(mDrawable, RevealDrawable.RADIUS, currentValue, nextValue);
    animators.put(RADIUS, animator);
    return this;
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

    Animator[] animatorsArray = animators.values().toArray(new Animator[] { });

    AnimatorSet set = new AnimatorSet();
    set.playTogether(animatorsArray);
    set.setDuration(mDuration);
    set.start();
  }
}
