package com.yxlh.permission.core

import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.atomic.AtomicInteger
import kotlin.properties.Delegates

private val nextLocalRequestCode = AtomicInteger()

private val nextKey: String
    get() = "activity_rq#${nextLocalRequestCode.getAndIncrement()}"

fun ComponentActivity.requestPermission(
    permission: String,
    onPermit: () -> Unit,
    onDeny: (shouldShowCustomRequest: Boolean) -> Unit
) {
    if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
        onPermit()
        return
    }
    var launcher by Delegates.notNull<ActivityResultLauncher<String>>()
    launcher = activityResultRegistry.register(
        nextKey,
        ActivityResultContracts.RequestPermission()
    ) { result ->
        if (result) {
            onPermit()
        } else {
            onDeny(!ActivityCompat.shouldShowRequestPermissionRationale(this, permission))
        }
        launcher.unregister()
    }
    lifecycle.addObserver(object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == Lifecycle.Event.ON_DESTROY) {
                launcher.unregister()
                lifecycle.removeObserver(this)
            }
        }
    })
    launcher.launch(permission)
}

fun ComponentActivity.requestPermissions(
    permissions: Array<String>,
    onPermit: () -> Unit,
    onDeny: (shouldShowCustomRequest: Boolean) -> Unit
) {
    var hasPermissions = true
    for (permission in permissions) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            hasPermissions = false
            break
        }
    }
    if (hasPermissions) {
        onPermit()
        return
    }
    var launcher by Delegates.notNull<ActivityResultLauncher<Array<String>>>()
    launcher = activityResultRegistry.register(
        nextKey,
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        var allAllow = true
        for (allow in result.values) {
            if (!allow) {
                allAllow = false
                break
            }
        }
        if (allAllow) {
            onPermit()
        } else {
            var shouldShowCustomRequest = false
            for (permission in permissions) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    shouldShowCustomRequest = true
                    break
                }
            }
            onDeny(shouldShowCustomRequest)
        }
        launcher.unregister()
    }
    lifecycle.addObserver(object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == Lifecycle.Event.ON_DESTROY) {
                launcher.unregister()
                lifecycle.removeObserver(this)
            }
        }
    })
    launcher.launch(permissions)
}