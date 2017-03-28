package com.bottomsheetbehavior;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;

import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

public class ScrollingAppBarLayoutManager extends ViewGroupManager<AppBarLayout> {

    private final static String REACT_CLASS = "RCTScrollingAppBarLayout";

    private ScrollingAppBarLayoutBehavior scrollingBehavior;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public AppBarLayout createViewInstance(ThemedReactContext context) {
        AppBarLayout view = new AppBarLayout(context);
        int width = CoordinatorLayout.LayoutParams.MATCH_PARENT;
        int height = CoordinatorLayout.LayoutParams.WRAP_CONTENT;
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(width, height);
        params.setBehavior(new ScrollingAppBarLayoutBehavior(context, null));

        if (Build.VERSION.SDK_INT >= 21) {
            int statusBarHeight = getStatusBarHeight(context);
            params.setMargins(0, statusBarHeight, 0, 0);
        }

        view.setLayoutParams(params);
        scrollingBehavior = ScrollingAppBarLayoutBehavior.from(view);
        return view;
    }

    @ReactProp(name = "statusBarColor")
    public void setStatusBarColor(AppBarLayout view, String color) {
        scrollingBehavior.setStatusBarColor(Color.parseColor(color));
    }

    @ReactProp(name = "height")
    public void setHeight(AppBarLayout view, int height) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
        params.height = (int) PixelUtil.toPixelFromDIP(height);
        view.setLayoutParams(params);
    }

    private int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }

        return 0;
    }
}
