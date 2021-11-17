package com.alone.suit.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class TestWork(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    companion object {
        const val TAG = "TestWork"
    }
    override fun doWork(): Result {
        Log.d(TAG, "doWork: ")
        return Result.success()
    }
}