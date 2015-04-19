# AdvanceReveal
Simple Advanced Library for Android Reveal Effect

# Sample APK
https://github.com/MoshDev/AdvanceReveal/releases/download/SampleApk/app-debug.apk

# Sample Code
```
  
    RevealAnimator animator1 = new RevealAnimator(drawable);
    animator1.radius(100)
        .pivot(centerX, contentView.getHeight())
        .interpolator(new BounceInterpolator())
        .color(Color.CYAN);

    RevealAnimator animator2 = new RevealAnimator(drawable);
    animator2.radius(contentView.getHeight() + 100);

    RevealAnimator animator3 = new RevealAnimator(drawable);
    animator3.color(Color.YELLOW).duration(2000);

    animator1.withNextAnim(animator2.withNextAnim(animator3)).start();
```    