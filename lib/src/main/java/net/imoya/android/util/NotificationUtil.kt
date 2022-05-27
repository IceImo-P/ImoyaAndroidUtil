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

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

/**
 * 通知ユーティリティ
 *
 * @author IceImo-P
 */
@Suppress("unused")
object NotificationUtil {
    /**
     * 通知チャンネルを作成します。
     *
     * @param context [Context]
     * @param channelId 通知チャンネルのID
     * @param name 通知チャンネルの表示名(ユーザーへ表示されます)
     * @param description 通知チャンネルの説明(ユーザーへ表示されます)
     * @param importance 重要度(NotificationManager.IMPORTANCE_*)
     */
    @RequiresApi(Build.VERSION_CODES.O)
    @JvmStatic
    fun createChannel(
        context: Context,
        channelId: String,
        name: String,
        description: String,
        importance: Int
    ) {
        val channel = NotificationChannel(channelId, name, importance)
        channel.description = description
        val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    /**
     * 通知を生成します。
     *
     * @param context        [Context]
     * @param channelId      通知チャンネルID
     * @param iconResourceId アイコンのリソースID
     * @param time           通知日時
     * @param title          通知ビューのタイトル文言
     * @param text           通知ビューのテキスト文言
     * @param contentIntent  通知ビュータップ時に実行するIntent指定
     * @return [Notification]
     */
    @JvmStatic
    fun makeNotification(
        context: Context, channelId: String, iconResourceId: Int, time: Long,
        title: String, text: String, contentIntent: PendingIntent
    ): Notification {
        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(iconResourceId)
            .setWhen(time)
            .setContentTitle(title)
            .setContentText(text)
            .setContentIntent(contentIntent)
            .build()
    }
}
