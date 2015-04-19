package com.moshx.reveallibrary.property;

import android.animation.ObjectAnimator;
import android.util.Property;
import com.moshx.reveallibrary.RevealDrawable;

/**
 * Created by M.Ersan on 4/18/15.
 */
public class FloatAnimationProperty extends AnimationProperty<Float> {

  public FloatAnimationProperty(Property property, Float... floats) {
    super(property, floats);
  }

  @Override public ObjectAnimator build(RevealDrawable target) {
    Property<RevealDrawable, Float> pro = property;
    return ObjectAnimator.ofFloat(target, property, pro.get(target), values[0]);
  }
}
