package com.moshx.reveallibrary;

import android.animation.TypeEvaluator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;

/**
 * Created by M.Ersan on 4/16/15.
 */
public class PathEvaluator implements TypeEvaluator<PointF> {

  private final PathMeasure pathMeasure;
  private final float length;

  public PathEvaluator(Path path) {
    pathMeasure = new PathMeasure(path, false);
    length = pathMeasure.getLength();
  }

  @Override public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
    float[] pos = new float[2];
    pathMeasure.getPosTan(fraction * length, pos, null);
    return new PointF(pos[0], pos[1]);
  }
}
