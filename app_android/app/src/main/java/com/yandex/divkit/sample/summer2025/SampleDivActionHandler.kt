package com.yandex.divkit.sample.summer2025

import android.net.Uri
import com.yandex.div.DivDataTag
import com.yandex.div.core.DivActionHandler
import com.yandex.div.core.DivViewFacade
import com.yandex.div.core.view2.Div2View
import com.yandex.div.json.expressions.ExpressionResolver
import com.yandex.div2.DivAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class SampleDivActionHandler() : DivActionHandler() {
    private var updateJob: Job? = null

    override fun handleAction(
        action: DivAction,
        view: DivViewFacade,
        resolver: ExpressionResolver
    ): Boolean {
        val url = action.url?.evaluate(resolver) ?: return super.handleAction(action, view, resolver)

        return if (url.scheme == SCHEME_SAMPLE && handleSampleAction(url, view.view as Div2View)) {
            true
        } else {
            super.handleAction(action, view, resolver)
        }
    }

    private fun handleSampleAction(action: Uri, divView: Div2View): Boolean {
        return when (action.host) {
            "update" -> {
                updateJob?.cancel()
                updateJob = CoroutineScope(Dispatchers.IO).launch {
                    val data = DataLoader.get() ?: return@launch
                    withContext(Dispatchers.Main) {
                        divView.setData(data, DivDataTag("div2"))
                    }
                }

                true
            }
            else -> false
        }
    }

    companion object {
        const val SCHEME_SAMPLE = "sample-action"
    }
}
