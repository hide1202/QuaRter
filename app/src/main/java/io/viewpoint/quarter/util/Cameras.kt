package io.viewpoint.quarter.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.UseCase
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.lifecycle.LifecycleOwner
import io.viewpoint.quarter.extensions.await
import timber.log.Timber

object Cameras {
    suspend fun bindCamera(
        activity: AppCompatActivity,
        vararg useCases: UseCase
    ) = bindCamera(activity, activity, *useCases)

    suspend fun bindCamera(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        vararg useCases: UseCase
    ) {
        val cameraProvider = ProcessCameraProvider
            .getInstance(context)
            .await(MainThreadExecutor)

        try {
            cameraProvider.unbindAll()

            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                CameraSelector.DEFAULT_BACK_CAMERA,
                *useCases
            )
        } catch (t: Exception) {
            Timber.e(t, "Use case binding failed")
        }
    }
}