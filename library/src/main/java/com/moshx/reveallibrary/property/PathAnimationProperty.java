package com.moshx.reveallibrary.property;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Property;
import com.moshx.reveallibrary.PathEvaluator;
import com.moshx.reveallibrary.RevealDrawable;

/**
 * Created by M.Ersan on 4/18/15.
 */
public class PathAnimationProperty extends AnimationProperty<Path> {

  public PathAnimationProperty(Property property, Path... paths) {
    super(property, paths);
  }

  @Override public ObjectAnimator build(RevealDrawable target) {
    ObjectAnimator animator =
        ObjectAnimator.ofObject(target, property, new PathEvaluator(values[0]), new PointF(0, 0),
            new PointF(1, 1));
    return animator;
  }
}
