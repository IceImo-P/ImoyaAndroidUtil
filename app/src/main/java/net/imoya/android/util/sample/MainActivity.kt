package net.imoya.android.util.sample

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main)

        findViewById<Button>(R.id.intent_test).setOnClickListener {
            intentTest()
        }
    }

    private fun intentTest() {
        val intent = Intent(this.applicationContext, IntentTestActivity::class.java)
        intent.putExtra(IntentTestActivity.KEY_SINGLE, SampleDataContainer("1"))
        intent.putExtra(IntentTestActivity.KEY_ARRAY, Array(2) { SampleDataContainer(it.toString()) })
        val list = ArrayList<SampleDataContainer>()
        list.add(SampleDataContainer("1"))
        list.add(SampleDataContainer("2"))
        list.add(SampleDataContainer("3"))
        list[0].subContainer = SampleDataContainer.SubContainer("1-1")
        list[2].subContainer = SampleDataContainer.SubContainer("3-1")
        intent.putExtra(IntentTestActivity.KEY_LIST, list)
        startActivity(intent)
    }
}