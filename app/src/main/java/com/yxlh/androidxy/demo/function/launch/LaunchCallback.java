package com.yxlh.androidxy.demo.function.launch;

import android.content.Intent;

/**
 * @author zwl
 */
public interface LaunchCallback {
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
