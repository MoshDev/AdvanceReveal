package com.moshx.reveallibrary.property;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.util.Property;
import com.moshx.reveallibrary.RevealDrawable;
import com.moshx.reveallibrary.property.AnimationProperty;

/**
 * Created by M.Ersan on 4/18/15.
 */
public class ColorAnimationProperty extends AnimationProperty<Integer> {

  public ColorAnimationProperty(Property property, Integer... integers) {
    super(property, integers);
  }

  @Override public ObjectAnimator build(RevealDrawable target) {
    Property<RevealDrawable, Integer> pro = property;
    ObjectAnimator animator =
        ObjectAnimator.ofObject(target, property, new ArgbEvaluator(), pro.get(target), values[0]);
    return animator;
  }
}
