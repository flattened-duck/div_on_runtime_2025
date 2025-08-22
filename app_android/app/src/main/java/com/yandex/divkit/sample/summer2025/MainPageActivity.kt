package com.yandex.divkit.sample.summer2025

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.yandex.div.DivDataTag
import com.yandex.div.core.Div2Context
import com.yandex.div.core.DivConfiguration
import com.yandex.div.core.view2.Div2View
import com.yandex.div.picasso.PicassoDivImageLoader
import com.yandex.divkit.sample.summer2025.databinding.ActivityMainPageBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainPageBinding
    private val assetReader = AssetReader(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title

        lifecycleScope.launch(Dispatchers.IO) {
            val divData = if (USE_LOCAL_ASSET) {
                val jsonObject = assetReader.read("sample.json")
                createDivData(jsonObject)
            } else {
                DataLoader.get()
            }

            withContext(Dispatchers.Main) {
                val divContext = Div2Context(
                    baseContext = this@MainPageActivity,
                    configuration = createDivConfiguration(),
                    lifecycleOwner = this@MainPageActivity
                )

                val divView = Div2View(divContext).apply {
                    setData(divData, DivDataTag("div2"))
                }
                binding.list.addView(divView)
            }
        }
    }

    private fun createDivConfiguration(): DivConfiguration {
        return DivConfiguration.Builder(PicassoDivImageLoader(this))
            .actionHandler(SampleDivActionHandler())
            .visualErrorsEnabled(true)
            .build()
    }

    private companion object {
        const val USE_LOCAL_ASSET = false
    }
}
