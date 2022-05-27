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

import net.imoya.android.log.LogUtil as NewLogUtil

/**
 * ログユーティリティ
 *
 * The logging function at ImoyaAndroidUtil has been moved to ImoyaAndroidLog library.
 *
 * Methods at this class are deprecated. Use net.imoya.android.log.LogUtil class in ImoyaAndroidLog instead.
 *
 * @author IceImo-P
 */
@Suppress("unused")
object LogUtil {
    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil.logString(BooleanArray) at ImoyaAndroidLog.",
        replaceWith = ReplaceWith(
            "LogUtil.logString(array)", "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun logString(array: BooleanArray): String = NewLogUtil.logString(array)

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil.logString(IntArray) at ImoyaAndroidLog.",
        replaceWith = ReplaceWith(
            "LogUtil.logString(array)", "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun logString(array: IntArray): String = NewLogUtil.logString(array)

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil.logString(LongArray) at ImoyaAndroidLog.",
        replaceWith = ReplaceWith(
            "LogUtil.logString(array)", "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun logString(array: LongArray): String = NewLogUtil.logString(array)

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil.logString(ShortArray) at ImoyaAndroidLog.",
        replaceWith = ReplaceWith(
            "LogUtil.logString(array)", "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun logString(array: ShortArray): String = NewLogUtil.logString(array)

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil.logString(ByteArray) at ImoyaAndroidLog.",
        replaceWith = ReplaceWith(
            "LogUtil.logString(array)", "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun logString(array: ByteArray): String = NewLogUtil.logString(array)

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil.logString(FloatArray) at ImoyaAndroidLog.",
        replaceWith = ReplaceWith(
            "LogUtil.logString(array)", "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun logString(array: FloatArray): String = NewLogUtil.logString(array)

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil.logString(DoubleArray) at ImoyaAndroidLog.",
        replaceWith = ReplaceWith(
            "LogUtil.logString(array)", "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun logString(array: DoubleArray): String = NewLogUtil.logString(array)

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil.logString(CharArray) at ImoyaAndroidLog.",
        replaceWith = ReplaceWith(
            "LogUtil.logString(array)", "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun logString(array: CharArray): String = NewLogUtil.logString(array)

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil.logString(Any?) at ImoyaAndroidLog.",
        replaceWith = ReplaceWith(
            "LogUtil.logString(obj)", "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun logString(obj: Any?): String = NewLogUtil.logString(obj)

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Use net.imoya.android.log.LogUtil.logString(Array<*>) at ImoyaAndroidLog.",
        replaceWith = ReplaceWith(
            "LogUtil.logString(obj)", "net.imoya.android.log.LogUtil"
        )
    )
    @JvmStatic
    fun logString(array: Array<*>): String = NewLogUtil.logString(array)
}