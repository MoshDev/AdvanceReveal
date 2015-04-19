package com.moshx.reveallibrary.property;

import android.animation.ObjectAnimator;
import android.util.Property;
import com.moshx.reveallibrary.RevealDrawable;

/**
 * Created by M.Ersan on 4/18/15.
 */
public abstract class AnimationProperty<Value> {

  protected Value[] values;
  protected Property property;

  public AnimationProperty(Property property, Value... values) {
    this.values = values;
    this.property = property;
  }

  public AnimationProperty<Value> setProperty(Property property) {
    this.property = property;
    return this;
  }

  public AnimationProperty<Value> setValues(Value... values) {
    this.values = values;
    return this;
  }

  public abstract ObjectAnimator build(RevealDrawable target);
}
