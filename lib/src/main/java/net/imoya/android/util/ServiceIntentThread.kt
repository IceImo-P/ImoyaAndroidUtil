package net.imoya.android.util

import android.app.Service
import android.content.Context
import android.content.Intent
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * [Service] の起動 [Intent] を逐次処理するスレッドです。
 */
abstract class ServiceIntentThread(
    /**
     * [Context]
     */
    @JvmField
    protected val context: Context
) : Thread() {
    /**
     * 内部で使用する Queue の項目になるコンテナです。
     *
     * @author IceImo-P
     */
    open class Task(
        /**
         * [Service] が受信した [Intent]
         */
        @JvmField
        val intent: Intent?,
        /**
         * [Service] 起動ID
         */
        @JvmField
        val startId: Int
    )

    /**
     * 終了フラグ
     *
     * 値が true であることをスレッドが検出した場合、スレッドは直ちに終了すべきである。
     */
    @Volatile
    @JvmField
    protected var exitFlag = false

    /**
     * [Task] の Queue
     */
    @Suppress("MemberVisibilityCanBePrivate")
    @JvmField
    protected val tasks: ConcurrentLinkedQueue<Task> = ConcurrentLinkedQueue()

    private val lock = ReentrantLock()
    private val condition = lock.newCondition()

    /**
     * 終了中フラグ
     *
     * true if this thread is on exit process, otherwise false.
     */
    @Volatile
    @JvmField
    var isOnExit = false

    /**
     * スレッドを安全に終了します。
     */
    fun quitSafely() {
        lock.withLock {
            exitFlag = true
            condition.signalAll()
        }
    }

    /**
     * 指定のIntentをスレッドに処理させます。
     *
     * @param intent  処理させるIntent
     * @param startId Service#onStartで取得したID値
     */
    fun addAction(intent: Intent?, startId: Int) {
        lock.withLock {
            // QueueにIntentを追加する。
            tasks.add(createTask(intent, startId))

            // スレッドの動作を再開させ、Queueの内容を処理してもらう。
            condition.signalAll()
        }
    }

    override fun run() {
        // PARTIAL_WAKE_LOCKを取得する
        val wl = PowerUtil.getPartialWakeLock(
            context, this.javaClass.name
        )
        try {
//            if (wl != null) {
            wl.acquire(90000)
//            }
            Log.v(TAG, "start")
            runLoop()
        } finally {
            Log.v(TAG, "end")

            // PARTIAL_WAKE_LOCKを解放する
//            if (wl != null) {
            wl.release()
//            }
        }
    }

    private fun runLoop() {
        var lastItem: Task? = null
        while (!exitFlag) {
            if (tasks.isEmpty()) {
                // Queueが空になった時の処理を行う。
                if (processOnIdle(lastItem)) {
                    break
                }
            }

            // 終了指示がされていたら、直ちに終了する。
            if (exitFlag) {
                isOnExit = true
                break
            }
            while (!tasks.isEmpty()) {
                // 次に処理すべきIntentをキューから取り出す。
                val item: Task?
                this.lock.withLock {
                    item = tasks.poll()

                }
                item?.let {
                    try {
                        // Intentの内容に応じた処理。
                        processTask(it)
                    } catch (ex: Exception) {
                        Log.w(TAG, ex)
                    } finally {
                        // try { Thread.sleep(1000); } // for DEBUG
                        // catch (InterruptedException ex) {}
                        // WakefulBroadcastReceiver.completeWakefulIntent(it.intent);
                        lastItem = it
                    }
                } ?: break
            }

            // 終了指示がされていたら、直ちに終了する。
            if (exitFlag) {
                isOnExit = true
                break
            }
        }
        isOnExit = true
    }

    /**
     * 処理すべきタスクが無くなった時の処理を行います。
     *
     * @param lastItem 最後に処理したタスクを含むQueueItem。
     * @return サービスの終了が確定した時はtrue, それ以外の時はfalse。
     */
    @Suppress("MemberVisibilityCanBePrivate")
    protected open fun processOnIdle(lastItem: Task?): Boolean {
        while (tasks.isEmpty()) {
            // 過去に処理したタスクがあったら、一旦サービスの終了を指示する。
            // (スレッド起動直後、最初のタスクが登録される前に終了することを防ぐ)
            if (lastItem != null) {
                // Log.d(TAG, "processOnIdle: Stopping if service is in idle state.");
                return if (stopSelfResult(lastItem.startId)) {
                    // サービス停止が確定したら、このスレッドは終了する。
                    // Log.d(TAG, "processOnIdle: Service will be stopped.");
                    isOnExit = true
                    true
                } else {
                    // サービスが停止しなかったら、まだタスクがあるはずである。
                    // Log.d(TAG, "processOnIdle: Service will not be stopped.");
                    false
                }
            }

            // 他のメソッド呼び出しに起因する指示があるまで待つ。
            // Log.d(TAG, "processOnIdle: Waiting for next task...");
            lock.withLock {
                if (tasks.isEmpty()) {
                    try {
                        this.condition.await(250, TimeUnit.MILLISECONDS)
                    } catch (ex: InterruptedException) {
                        Log.d(TAG, ex)
                    }
                }
            }
        }
        Log.d(TAG, "PST: New task found.")
        return false
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected open fun createTask(intent: Intent?, startId: Int): Task {
        // デフォルトの実装では、単純な Task オブジェクトを返す
        return Task(intent, startId)
    }

    /**
     * Queueに入れられたTaskに応じた、各種処理を行います。
     *
     * @param task [Task]
     */
    protected abstract fun processTask(task: Task)

    /**
     * このスレッドを使用する [Service] の [Service.stopSelfResult]
     * メソッドを呼び出し、結果を返します。
     *
     * @param startId [Service.onStartCommand] で受け取った startId 値
     * @return [Service.stopSelfResult] の戻り値
     */
    protected abstract fun stopSelfResult(startId: Int): Boolean

    companion object {
        private const val TAG = "ServiceIntentThread"
    }
}