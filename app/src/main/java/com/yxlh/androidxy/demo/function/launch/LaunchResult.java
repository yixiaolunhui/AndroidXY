package com.yxlh.androidxy.demo.function.launch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zwl
 */
public class LaunchResult {
    private static final String TAG = "LaunchResult";
    private LaunchResultProxy launchResultProxy;


    public LaunchResult(Activity activity) {
        launchResultProxy = getResultFragment(activity);
    }

    private LaunchResultProxy getResultFragment(Activity activity) {
        LaunchResultProxy launchResultProxy;
        if (activity instanceof FragmentActivity) {
            launchResultProxy = (SupportLaunchResultFragment) ((FragmentActivity) activity).getSupportFragmentManager().findFragmentByTag(TAG);
            if (launchResultProxy == null) {
                launchResultProxy = getSupportFragment((FragmentActivity) activity);
            }
        } else {
            launchResultProxy = (LaunchResultFragment) activity.getFragmentManager().findFragmentByTag(TAG);
            if (launchResultProxy == null) {
                launchResultProxy = getFragment(activity);
            }
        }
        return launchResultProxy;
    }


    private LaunchResultProxy getFragment(Activity activity) {
        LaunchResultProxy launchResultProxy = new LaunchResultFragment();
        android.app.FragmentManager fragmentManager = activity.getFragmentManager();
        try {
            fragmentManager.beginTransaction()
                    .add((android.app.Fragment) launchResultProxy, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        } catch (Exception e) {
            e.printStackTrace();
            launchResultProxy = null;
        }
        return launchResultProxy;
    }


    private LaunchResultProxy getSupportFragment(FragmentActivity activity) {
        LaunchResultProxy launchResultProxy = new SupportLaunchResultFragment();
        androidx.fragment.app.FragmentManager fragmentManager = activity.getSupportFragmentManager();
        try {
            fragmentManager.beginTransaction()
                    .add((androidx.fragment.app.Fragment) launchResultProxy, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        } catch (Exception e) {
            e.printStackTrace();
            launchResultProxy = null;
        }
        return launchResultProxy;
    }


    public void startForResult(Intent intent, LaunchCallback callback) {
        if (launchResultProxy != null) {
            launchResultProxy.startForResult(intent, callback);
        }
    }


    public static class SupportLaunchResultFragment extends androidx.fragment.app.Fragment implements LaunchResultProxy {
        private Map<Integer, LaunchCallback> mCallbacks = new HashMap<>();

        public SupportLaunchResultFragment() {
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            LaunchCallback callback = mCallbacks.remove(requestCode);
            if (callback != null) {
                callback.onActivityResult(requestCode, resultCode, data);
            }
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        @Override
        public void startForResult(Intent intent, LaunchCallback callback) {
            int requestCode = requestCode(mCallbacks, callback);
            mCallbacks.put(requestCode, callback);
            startActivityForResult(intent, requestCode);
        }
    }


    public static class LaunchResultFragment extends android.app.Fragment implements LaunchResultProxy {
        private Map<Integer, LaunchCallback> mCallbacks = new HashMap<>();

        public LaunchResultFragment() {
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            LaunchCallback callback = mCallbacks.remove(requestCode);
            if (callback != null) {
                callback.onActivityResult(requestCode, resultCode, data);
            }
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        @Override
        public void startForResult(Intent intent, LaunchCallback callback) {
            int requestCode = requestCode(mCallbacks, callback);
            mCallbacks.put(requestCode, callback);
            startActivityForResult(intent, requestCode);
        }
    }

    //最大请求码 requestCode 不能超过16bits
    private static int requestCode(Map<Integer, LaunchCallback> mCallbacks, LaunchCallback callback) {
        int requestCode = callback.hashCode() % 1024;
        while (mCallbacks.get(requestCode) != null) {
            requestCode += 1;
            if (requestCode > 65530) {
                requestCode %= 1024;
            }
        }
        return requestCode;
    }


    public interface LaunchResultProxy {
        void startForResult(Intent intent, LaunchCallback callback);
    }
}
