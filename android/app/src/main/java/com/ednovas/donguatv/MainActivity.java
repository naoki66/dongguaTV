package com.ednovas.donguatv;

import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 强制解决状态栏遮挡：让内容不延伸到状态栏区域
        try {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);

            View decorView = window.getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
            decorView.setSystemUiVisibility(uiOptions);

            // 【终极方案】手动获取状态栏高度，并强行给根布局加 Padding
            // 无论系统 Flags 如何变化，这个 Padding 是 View 级别的，不会被覆盖
            View content = findViewById(android.R.id.content);
            if (content != null) {
                int statusBarHeight = getStatusBarHeight();
                // 设置 PaddingTop = 状态栏高度
                content.setPadding(0, statusBarHeight, 0, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取状态栏高度的辅助方法
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        // 如果获取失败，给一个兜底值 (24dp)
        if (result == 0) {
            float density = getResources().getDisplayMetrics().density;
            result = (int) (24 * density);
        }
        return result;
    }
}
