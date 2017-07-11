package com.mobile.androidtest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * author：hj
 * time: 2017/7/11 0011 14:12
 */


public class UIUtils {

    /**
     * 获取导航栏的高度.分版本获取
     * @param context
     * @return
     */
    public static int getNavHeight(Context context) {
        int navHeight = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            navHeight = getNavigationBarSize(context).y;
        } else {
            if (isHasNavigationBar(context)) {
                navHeight = getNavigationBarHeight(context);
            }
        }
        return navHeight;
    }


    /**
     * 获取导航栏的高度 需要先判断是否有导航栏
     *
     * @param context
     * @return
     */
    private static int getNavigationBarHeight(Context context) {
        try {
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            // 获取NavigationBar的高度
            int height = resources.getDimensionPixelSize(resourceId);
            return height;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 获取是否存在NavigationBar
     * 部分机型有问题：华为mate9
     * @param context
     * @return
     */
    private static boolean isHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;

    }

    /**
     * 根据屏幕可以使用的宽高来获取导航栏高度
     * @param context
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR2)
    private static Point getNavigationBarSize(Context context) {
        Point appUsableSize = getAppUsableScreenSize(context);
        Point realScreenSize = getRealScreenSize(context);

        // navigation bar on the right
        if (appUsableSize.x < realScreenSize.x) {
            return new Point(realScreenSize.x - appUsableSize.x, appUsableSize.y);
        }

        // navigation bar at the bottom
        if (appUsableSize.y < realScreenSize.y) {
            return new Point(appUsableSize.x, realScreenSize.y - appUsableSize.y);
        }

        // navigation bar is not present
        return new Point();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR2)
    private static Point getAppUsableScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    private static Point getRealScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();

        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealSize(size);
        } else if (Build.VERSION.SDK_INT >= 14) {
            try {
                size.x = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                size.y = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            } catch (NoSuchMethodException e) {
            }
        }
        return size;
    }
}
