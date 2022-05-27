/*
 * Copyright (C) 2022 IceImo-P
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    protected val context: Context,

    /**
     * If set true, use Partial WakeLock while thread is running.
     */
    @JvmField
    protected val usePartialWakeLock: Boolean = true
) : Thread() {
    /**
     * 内部で使用する queue の項目になるコンテナです。
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
     * [Task] の queue
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
     * 指定の [Intent] をスレッドに処理させます。
     *
     * @param intent  処理させる [Intent]
     * @param startId [Service.onStart] で取得したID値
     */
    fun addAction(intent: Intent?, startId: Int) {
        lock.withLock {
            // Add Intent to queue
            tasks.add(createTask(intent, startId))

            // スレッドの動作を再開させ、queue の内容を処理してもらう
            condition.signalAll()
        }
    }

    override fun run() {
        // PARTIAL_WAKE_LOCK を取得する
        val wl = if (usePartialWakeLock) PowerUtil.getPartialWakeLock(
            context, this.javaClass.name
        ) else null
        try {
            wl?.acquire(90000)
            UtilLog.v(TAG, "start")
            runLoop()
        } finally {
            UtilLog.v(TAG, "end")

            // PARTIAL_WAKE_LOCK を解放する
            wl?.release()
        }
    }

    private fun runLoop() {
        var lastItem: Task? = null
        while (!exitFlag) {
            if (tasks.isEmpty()) {
                // Queue が空になった時の処理を実行する
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
                // 次に処理すべき Intent を queue より取り出す
                val item: Task?
                this.lock.withLock {
                    item = tasks.poll()

                }
                item?.let {
                    try {
                        // Intent の内容に応じた処理
                        processTask(it)
                    } catch (ex: Exception) {
                        UtilLog.w(TAG, ex)
                    } finally {
                        // try { Thread.sleep(1000); } // for DEBUG
                        // catch (InterruptedException ex) {}
                        // WakefulBroadcastReceiver.completeWakefulIntent(it.intent);
                        lastItem = it
                    }
                } ?: break
            }

            // 外部より終了を指示された場合は、直ちに終了する
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
            // 過去に処理したタスクがあったら、一旦サービスの終了を指示する
            // (スレッド起動直後、最初のタスクが登録される前に終了することを防ぐ)
            if (lastItem != null) {
                UtilLog.v(TAG, "processOnIdle: Stopping if service is in idle state.")
                return if (stopSelfResult(lastItem.startId)) {
                    // サービス停止が確定したら、このスレッドは終了する
                    UtilLog.v(TAG, "processOnIdle: Service will be stopped.")
                    isOnExit = true
                    true
                } else {
                    // サービスが停止しなかったら、まだタスクがあるはずである
                    UtilLog.v(TAG, "processOnIdle: Service will not be stopped.")
                    false
                }
            }

            // 他のメソッド呼び出しに起因する指示があるまで待つ
            UtilLog.v(TAG, "processOnIdle: Waiting for next task...")
            lock.withLock {
                if (tasks.isEmpty()) {
                    try {
                        this.condition.await(250, TimeUnit.MILLISECONDS)
                    } catch (ex: InterruptedException) {
                        UtilLog.d(TAG, ex)
                    }
                }
            }
        }
        UtilLog.v(TAG, "New task found.")
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