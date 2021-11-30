package com

import android.app.Application
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig

/**
 * @description  描述这个类的作用
 *
 * **********************************
 * @date          @2021/11/30 14:01
 * **********************************
 **/
class App: Application(),CameraXConfig.Provider {
    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }
}