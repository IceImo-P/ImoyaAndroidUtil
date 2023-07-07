package net.imoya.android.util.sample

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.os.BundleCompat
import net.imoya.android.util.BundleUtil
import net.imoya.android.util.IntentUtil

class IntentTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.intent_test)

        addLog("onCreate")

        if (savedInstanceState == null) {
            IntentCompat.getParcelableExtra(
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
            IntentCompat.getParcelableArrayListExtra(
                intent,
                KEY_LIST,
                SampleDataContainer::class.java
            )?.let {
                it.forEachIndexed { index, item -> addLog("$KEY_LIST[$index] : $item") }
            }
            IntentUtil.getSerializableExtra(
                intent,
                KEY_SERIALIZABLE,
                SampleDataSerializable::class.java
            )?.let {
                addLog("$KEY_SERIALIZABLE : $it")
            }
        } else {
            printSavedInstanceState(savedInstanceState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable(KEY_SINGLE, SampleDataContainer("1"))
        outState.putParcelableArray(KEY_ARRAY, Array(2) { SampleDataContainer(it.toString()) })
        val list = ArrayList<SampleDataContainer>()
        list.add(SampleDataContainer("1"))
        list.add(SampleDataContainer("2"))
        list.add(SampleDataContainer("3"))
        list[0].subContainer = SampleDataContainer.SubContainer("1-1")
        list[2].subContainer = SampleDataContainer.SubContainer("3-1")
        outState.putParcelableArrayList(KEY_LIST, list)
        outState.putSerializable(KEY_SERIALIZABLE, SampleDataSerializable("serializable"))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        addLog("onRestoreInstanceState")

        printSavedInstanceState(savedInstanceState)
    }

    private fun printSavedInstanceState(savedInstanceState: Bundle) {
        BundleCompat.getParcelable(
            savedInstanceState,
            KEY_SINGLE,
            SampleDataContainer::class.java
        )?.let {
            addLog("$KEY_SINGLE : $it")
        }
        addLog("----")
        BundleUtil.getParcelableArray(
            savedInstanceState,
            KEY_ARRAY,
            SampleDataContainer::class.java
        )?.let {
            it.forEachIndexed { index, item -> addLog("$KEY_ARRAY[$index] : $item") }
        }
        addLog("----")
        BundleCompat.getParcelableArrayList(
            savedInstanceState,
            KEY_LIST,
            SampleDataContainer::class.java
        )?.let {
            it.forEachIndexed { index, item -> addLog("$KEY_LIST[$index] : $item") }
        }
        addLog("----")
        BundleUtil.getSerializable(
            savedInstanceState,
            KEY_SERIALIZABLE,
            SampleDataSerializable::class.java
        )?.let {
            addLog("$KEY_SERIALIZABLE : $it")
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
        const val KEY_SERIALIZABLE = "serializable"
    }
}