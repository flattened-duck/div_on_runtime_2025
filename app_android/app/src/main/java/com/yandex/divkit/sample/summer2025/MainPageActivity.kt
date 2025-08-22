package com.yandex.divkit.sample.summer2025

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yandex.div.core.Div2Context
import com.yandex.div.core.DivConfiguration
import com.yandex.div.core.view2.Div2View
import com.yandex.div.picasso.PicassoDivImageLoader
import com.yandex.divkit.sample.summer2025.databinding.ActivityMainPageBinding

class MainPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainPageBinding
    private var updater: PerpetualUpdater? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title

        val divContext = Div2Context(
            baseContext = this@MainPageActivity,
            configuration = createDivConfiguration(),
            lifecycleOwner = this@MainPageActivity
        )

        val divView = Div2View(divContext).apply {
            PerpetualUpdater(this).startUpdating()
        }
        binding.list.addView(divView)
    }

    override fun onStart() {
        super.onStart()

        updater?.startUpdating()
    }

    override fun onStop() {
        updater?.stopUpdating()

        super.onStop()
    }

    override fun onDestroy() {
        updater?.stopUpdating()

        super.onDestroy()
    }

    private fun createDivConfiguration(): DivConfiguration {
        return DivConfiguration.Builder(PicassoDivImageLoader(this))
            .actionHandler(SampleDivActionHandler())
            .visualErrorsEnabled(true)
            .build()
    }
}
