# SnowEffect

## Demo
<img src="https://github.com/xavier0507/SnowEffect/blob/master/gif/params_and_rotation.gif" height="400">

# Usage(xml)
```
 <com.xy.snoweffect.view.SnowEffectFrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/color_FFFFFFFF"
    app:dropAverageDuration="20000"
    app:isRotation="false"
    app:snowBasicCount="5"
    tools:context="com.xy.snoweffect.activity.MainActivity">
</com.xy.snoweffect.view.SnowEffectFrameLayout>
```

# Usage(java)
```
SnowEffectFrameLayout snowEffectFrameLayout;
this.snowEffectFrameLayout = (SnowEffectFrameLayout) this.findViewById(R.id.activity_main);
this.snowEffectFrameLayout.setSnowBasicCount(Integer.parseInt(snowBasicCount));
this.snowEffectFrameLayout.setDropAverageDuration(Integer.parseInt(dropDuration));
this.snowEffectFrameLayout.startEffect();
```
