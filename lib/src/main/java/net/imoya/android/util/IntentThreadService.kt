package net.imoya.android.util

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * [ServiceIntentThread] を利用し、 [Intent] を逐次処理する [Service] です。
 */
abstract class IntentThreadService : Service() {
    /**
     * 現在 [Intent] を処理しているスレッド
     */
    private var currentThread: ServiceIntentThread? = null

    /**
     * [Intent] を逐次処理するスレッドオブジェクトを生成します。
     *
     * @return [ServiceIntentThread]
     */
    protected abstract fun createServiceThread(): ServiceIntentThread

    @Suppress("MemberVisibilityCanBePrivate")
    protected var serviceTimeout: Long = 90000

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        handleStartCommand(intent, startId)
        return START_NOT_STICKY
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun handleStartCommand(intent: Intent?, startId: Int) {
        // PARTIAL_WAKE_LOCKを取得する
        val wl = PowerUtil.getPartialWakeLock(
            this, this.javaClass.name + "#onStart"
        )
        wl.acquire(serviceTimeout)
        try {

            Log.v(TAG, "onStart")

            // タスク処理用スレッド未起動時は、新しいスレッドを開始する
            if (currentThread == null || currentThread!!.isOnExit) {
                // Log.v(TAG, "onStart: Starting ServiceThread.");
                currentThread = createServiceThread()
                currentThread?.start()
            }

            // タスク処理用スレッドにタスクを処理してもらう
            currentThread?.addAction(intent, startId)
            // Log.v(TAG, "onStart: Set up task to ServiceThread.");
        } finally {
            // PARTIAL_WAKE_LOCKを解放する
            wl.release()
        }
    }

    override fun onDestroy() {
        // PARTIAL_WAKE_LOCKを取得する
        val wl = PowerUtil.getPartialWakeLock(
            this, this.javaClass.name + "#onDestroy"
        )
        wl.acquire(60 * 1000L /*1 minute*/)
        Log.v(TAG, "onDestroy")
        try {
            super.onDestroy()

            // タスク処理用スレッドを終了する
            try {
                currentThread?.quitSafely()
            } catch (ex: Exception) {
                Log.d(TAG, ex)
            }
        } finally {
            // PARTIAL_WAKE_LOCKを解放する
            wl.release()
        }
    }

    companion object {
        private const val TAG = "IntentThreadService"
    }
}