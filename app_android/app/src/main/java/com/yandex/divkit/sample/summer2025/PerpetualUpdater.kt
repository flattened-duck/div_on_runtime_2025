package com.yandex.divkit.sample.summer2025

import com.yandex.div.DivDataTag
import com.yandex.div.core.view2.Div2View
import com.yandex.div2.DivData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PerpetualUpdater(
    private val divView: Div2View,
) {
    private var previousResult: DivData? = null
    private var updateJob: Job? = null

    private val assetReader by lazy(LazyThreadSafetyMode.NONE) {
        AssetReader(divView.context)
    }

    private val tag = DivDataTag("div2")

    fun startUpdating() {
        updateJob?.cancel()

        if (USE_LOCAL_ASSET) {
            val jsonObject = assetReader.read("sample.json")
            val data = createDivData(jsonObject)
            divView.setData(data, tag)
        } else {
            updateJob = CoroutineScope(Dispatchers.IO).launch {
                continueUpdate()
            }
        }
    }

    fun stopUpdating() {
        updateJob?.cancel()
    }

    private suspend fun continueUpdate() {
        val newData = DataLoader.get()

        if (previousResult?.hash() != newData?.hash()) {
            previousResult = newData

            withContext(Dispatchers.Main) {
                divView.setData(newData, tag)
            }
        }

        delay(WAIT_BETWEEN_UPDATES_MILLIS)
        continueUpdate()
    }

    private companion object {
        const val USE_LOCAL_ASSET = false
        const val WAIT_BETWEEN_UPDATES_MILLIS = 1000L
    }
}