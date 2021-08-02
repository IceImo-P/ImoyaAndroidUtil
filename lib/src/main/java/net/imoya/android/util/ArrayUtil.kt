package net.imoya.android.util

import java.util.*

/**
 * 配列を取り扱うためのユーティリティメソッド集です。
 *
 * @author IceImo-P
 */
@Suppress("unused")
object ArrayUtil {
    /**
     * 整数値を含む [ArrayList] を、プリミティブ型の配列に変換します。
     *
     * @param source [ArrayList]
     * @return [IntArray]
     */
    @JvmStatic
    fun toIntArray(source: ArrayList<Int>): IntArray {
        return source.toIntArray()
    }

    /**
     * boolean の配列のうち、値が true である要素の数を返します。
     *
     * @param array 配列
     * @return 値が true である要素の数, 又は array が null の場合0
     */
    @JvmStatic
    fun getTrueCount(array: BooleanArray?): Int {
        return array?.count { it } ?: 0
    }
}