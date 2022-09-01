package net.imoya.android.util

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import java.lang.ref.WeakReference

/**
 * [SeekBar] と、整数値を入力可能な [EditText] を連動します。
 *
 * * ユーザーが [SeekBar] を操作した時、選択した数値を [EditText] へ表示します。
 * * ユーザーが [EditText] へ数値を入力した時、 [SeekBar] の位置を更新します。
 *     * 最小値, 最大値を超える値の場合は、最小値または最大値へ自動的に訂正します。
 *     * 不正な入力は自動的に訂正します。
 */
@Suppress("unused")
open class SeekBarAndIntegerInputBinder(
    /**
     * 動作設定
     */
    @Suppress("MemberVisibilityCanBePrivate")
    protected val properties: Properties = Properties(),
) : SeekBar.OnSeekBarChangeListener, TextWatcher {
    /**
     * 動作設定
     */
    open class Properties(
        /**
         * 最小値
         */
        var min: Int = 0,
        /**
         * 最大値
         */
        var max: Int = 1
    )

    /**
     * [SeekBar]
     */
    @Suppress("MemberVisibilityCanBePrivate")
    var seekBar: SeekBar?
        get() = seekBarRef?.get()
        set(value) {
            seekBarRef = if (value != null) WeakReference(value) else null
        }

    /**
     * [EditText]
     */
    @Suppress("MemberVisibilityCanBePrivate")
    var editText: EditText?
        get() = editTextRef?.get()
        set(value) {
            editTextRef = if (value != null) WeakReference(value) else null
        }

    /**
     * 入力値
     *
     * @throws IllegalStateException [seekBar], [editText] のうち、少なくとも片方が未設定です。
     */
    @Suppress("MemberVisibilityCanBePrivate")
    var value: Int = 0
        set(newValue) {
            field = newValue.coerceIn(properties.min, properties.max)

            // ビューを設定済みの場合は反映する
            val seekBar = this.seekBar
            val editText = this.editText
            if (seekBar != null && editText != null && !isInCorrection) {
                seekBar.setOnSeekBarChangeListener(null)
                editText.removeTextChangedListener(this)
                seekBar.progress = field - properties.min
                editText.setText(field.toString(10))
                seekBar.setOnSeekBarChangeListener(this)
                editText.addTextChangedListener(this)
            }
        }

    /**
     * [SeekBar] の参照
     */
    @Suppress("MemberVisibilityCanBePrivate")
    protected var seekBarRef: WeakReference<SeekBar>? = null

    /**
     * [EditText] の参照
     */
    @Suppress("MemberVisibilityCanBePrivate")
    protected var editTextRef: WeakReference<EditText>? = null

    /**
     * [editText] 訂正中フラグ
     */
    @Suppress("MemberVisibilityCanBePrivate")
    protected var isInCorrection = false

    @Suppress("unused")
    fun bind() {
        val seekBar = requireSeekBar()
        val editText = requireEditText()

        editText.setText(value.toString())
        editText.addTextChangedListener(this)

        seekBar.max = properties.max - properties.min
        seekBar.progress = value - properties.min
        seekBar.setOnSeekBarChangeListener(this)
    }

    @Suppress("unused")
    fun unbind() {
        seekBar?.setOnSeekBarChangeListener(null)
        editText?.removeTextChangedListener(this)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun requireSeekBar(): SeekBar {
        return seekBar ?: throw IllegalStateException("SeekBar is not set")
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun requireEditText(): EditText {
        return editText ?: throw IllegalStateException("EditText is not set")
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(s: Editable) {
        UtilLog.v(TAG) { "afterTextChanged: isInCorrection = $isInCorrection" }
        if (!isInCorrection) {
            isInCorrection = true
            UtilLog.v(TAG) { "afterTextChanged: s = $s" }
            val original = s.toString()
            val validated = validateString(original)
            if (validated.isEmpty()) {
                UtilLog.v(TAG, "afterTextChanged: isEmpty")
                // 空文字の場合は訂正せず、ProgressBarへ反映もしない
                s.clear()
            } else if (validated == "-") {
                // マイナス記号のみの入力は訂正せず、ProgressBarへ反映もしない
                UtilLog.v(TAG, "afterTextChanged: only '-'")
                if (original != validated) {
                    UtilLog.v(TAG, "afterTextChanged: replace")
                    s.clear()
                    s.append(validated)
                }
            } else {
                var value: Int = try {
                    validated.toInt(10)
                } catch (e: Exception) {
                    seekBar?.progress ?: properties.min
                }
                UtilLog.v(TAG) { "afterTextChanged: input number = $value" }
                if (value < properties.min) {
                    UtilLog.v(TAG, "afterTextChanged: input number < min")
                    value = properties.min
                } else if (value > properties.max) {
                    UtilLog.v(TAG, "afterTextChanged: input number > max")
                    value = properties.max
                }
                UtilLog.v(TAG) { "afterTextChanged: input number(after collection) = $value" }
                this.value = value
                seekBar?.progress = value - properties.min
                val stringAfter = value.toString()
                if (original != stringAfter) {
                    s.clear()
                    s.append(stringAfter)
                }
            }
            isInCorrection = false
            UtilLog.v(TAG, "afterTextChanged: end")
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        UtilLog.v(TAG) { "onProgressChanged: progress = $progress, fromUser = $fromUser" }
        if (fromUser) {
            editText?.setText((progress + properties.min).toString())
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {}

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun validateString(s: String): String {
        // 先頭と末尾の空白文字を取り除く
        val trimChars = " 　\t\r\n".toCharArray()
        val trimmed = s.trim { trimChars.contains(it) }

        // 文字列の先頭より、整数値として認識可能な文字列のみを取り出す
        val regex = Regex("^-?[0-9]*")
        val matches = regex.find(trimmed)
        return matches?.value ?: ""
    }

    companion object {
        /**
         * Tag for log
         */
        private const val TAG = "SeekBarAndNumberBinder"
    }
}