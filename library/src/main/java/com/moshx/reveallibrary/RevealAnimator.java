package com.moshx.reveallibrary;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.graphics.Path;
import android.os.Build;
import android.view.animation.LinearInterpolator;
import com.moshx.reveallibrary.property.AnimationProperty;
import com.moshx.reveallibrary.property.ColorAnimationProperty;
import com.moshx.reveallibrary.property.FloatAnimationProperty;
import com.moshx.reveallibrary.property.PathAnimationProperty;
import java.util.ArrayList;
import java.util.HashMap;

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
  static final int PATH = 0x0030;

  static final boolean IS_KITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

  private HashMap<Integer, AnimationProperty> properties = new HashMap<>();
  private final RevealDrawable mDrawable;
  private AnimatorSet animatorSet = new AnimatorSet();
  private Runnable endAction, startAction;
  private RevealAnimator nextAnimator;
  private TimeInterpolator interpolator = new LinearInterpolator();

  public RevealAnimator(RevealDrawable drawable) {
    mDrawable = drawable;
    animatorSet.setDuration(800);
    animatorSet.addListener(internalAnimatorListener);
  }

  public RevealAnimator alpha(float alpha) {
    FloatAnimationProperty property = new FloatAnimationProperty(RevealDrawable.ALPHA, alpha);
    properties.put(ALPHA, property);
    return this;
  }

  public RevealAnimator alphaBy(float alpha) {
    float next = mDrawable.getAlphaFloat() + alpha;
    return alpha(next);
  }

  public RevealAnimator radius(float radius) {
    FloatAnimationProperty property = new FloatAnimationProperty(RevealDrawable.RADIUS, radius);
    properties.put(RADIUS, property);
    return this;
  }

  public RevealAnimator radiusBy(float radius) {
    float next = mDrawable.getRadius() + radius;
    return radius(next);
  }

  public RevealAnimator color(int color) {
    ColorAnimationProperty property = new ColorAnimationProperty(RevealDrawable.COLOR, color);
    properties.put(COLOR, property);
    return this;
  }

  public RevealAnimator pivotX(float x) {
    properties.remove(PATH);
    FloatAnimationProperty property = new FloatAnimationProperty(RevealDrawable.PIVOT_X, x);
    properties.put(PIVOT_X, property);
    return this;
  }

  public RevealAnimator pivotXBy(float x) {
    float next = mDrawable.getPivotX() + x;
    return pivotX(next);
  }

  public RevealAnimator pivotY(float y) {
    properties.remove(PATH);
    FloatAnimationProperty property = new FloatAnimationProperty(RevealDrawable.PIVOT_Y, y);
    properties.put(PIVOT_Y, property);
    return this;
  }

  public RevealAnimator pivotYBy(float y) {
    float next = mDrawable.getPivotY() + y;
    return pivotY(next);
  }

  public RevealAnimator pivot(float x, float y) {
    pivotX(x);
    return pivotY(y);
  }

  public RevealAnimator path(Path path) {
    properties.remove(PIVOT_X);
    properties.remove(PIVOT_Y);
    PathAnimationProperty property = new PathAnimationProperty(RevealDrawable.PATH, path);
    properties.put(PATH, property);
    return this;
  }

  public void build() {

    Animator[] animatorsArray = new Animator[properties.size()];
    AnimationProperty[] propertiesArray = properties.values().toArray(new AnimationProperty[] {});
    for (int i = 0; i < animatorsArray.length; i++) {
      ObjectAnimator anim = propertiesArray[i].build(mDrawable);
      anim.setInterpolator(interpolator);
      animatorsArray[i] = anim;
    }
    animatorSet.playTogether(animatorsArray);

    properties.clear();
  }

  public void start() {
    if (properties.size() <= 0) {
      return;
    }
    build();
    animatorSet.start();
  }

  public long getStartDelay() {
    return animatorSet.getStartDelay();
  }

  public RevealAnimator startDelay(long startDelay) {
    animatorSet.setStartDelay(startDelay);
    return this;
  }

  public RevealAnimator duration(long duration) {
    animatorSet.setDuration(duration);
    return this;
  }

  @TargetApi(Build.VERSION_CODES.KITKAT) public void pause() {
    if (IS_KITKAT) {
      animatorSet.pause();
    }
  }

  @TargetApi(Build.VERSION_CODES.KITKAT) public void resume() {
    if (IS_KITKAT) {
      animatorSet.resume();
    }
  }

  public long getDuration() {
    return animatorSet.getDuration();
  }

  public RevealAnimator interpolator(TimeInterpolator value) {
    if (value == null) {
      value = new LinearInterpolator();
    }
    interpolator = value;
    return this;
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2) public TimeInterpolator getInterpolator() {
    return animatorSet.getInterpolator();
  }

  public void cancel() {
    animatorSet.cancel();
  }

  public void end() {
    animatorSet.end();
  }

  public boolean isRunning() {
    return animatorSet.isRunning();
  }

  public boolean isStarted() {
    return animatorSet.isStarted();
  }

  public RevealAnimator withStartAction(Runnable startAction) {
    this.startAction = startAction;
    return this;
  }

  public RevealAnimator withEndAction(Runnable endAction) {
    this.endAction = endAction;
    return this;
  }

  public RevealAnimator withNextAnim(RevealAnimator anim) {
    this.nextAnimator = anim;
    return this;
  }

  private Animator.AnimatorListener internalAnimatorListener = new Animator.AnimatorListener() {

    public void onAnimationStart(Animator animation) {
      if (startAction != null) {
        startAction.run();
      }
    }

    public void onAnimationEnd(Animator animation) {
      if (nextAnimator != null) {
        nextAnimator.start();
      }
      if (endAction != null) {
        endAction.run();
      }
    }

    public void onAnimationCancel(Animator animation) {

    }

    public void onAnimationRepeat(Animator animation) {

    }
  };

  public void addListener(Animator.AnimatorListener listener) {
    animatorSet.addListener(listener);
  }

  public void removeListener(Animator.AnimatorListener listener) {
    animatorSet.removeListener(listener);
  }

  public ArrayList<Animator.AnimatorListener> getListeners() {
    return animatorSet.getListeners();
  }

  public void removeAllListeners() {
    animatorSet.removeAllListeners();
  }
}
