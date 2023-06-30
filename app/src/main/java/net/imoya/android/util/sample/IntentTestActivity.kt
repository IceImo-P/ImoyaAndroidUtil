package net.imoya.android.util.sample

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.imoya.android.util.IntentUtil

class IntentTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.intent_test)

        if (savedInstanceState == null) {
            IntentUtil.getParcelableExtra(
                intent,
                KEY_SINGLE,
                SampleDataContainer::class.java
            )?.let {
                addLog("$KEY_SINGLE : $it")
            }
            addLog("----")
            IntentUtil.getParcelableArrayExtra(
                intent,
                KEY_ARRAY,
                SampleDataContainer::class.java
            )?.let {
                it.forEachIndexed { index, item -> addLog("$KEY_ARRAY[$index] : $item") }
            }
            addLog("----")
            IntentUtil.getParcelableArrayListExtra(
                intent,
                KEY_LIST,
                SampleDataContainer::class.java
            )?.let {
                it.forEachIndexed { index, item -> addLog("$KEY_LIST[$index] : $item") }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addLog(text: String) {
        val log = findViewById<TextView>(R.id.log)
        log.text = log.text.toString() + text + "\n"
    }

    companion object {
        const val KEY_SINGLE = "single"
        const val KEY_ARRAY = "array"
        const val KEY_LIST = "list"
    }
}