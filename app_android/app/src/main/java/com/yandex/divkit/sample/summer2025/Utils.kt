package com.yandex.divkit.sample.summer2025

import com.yandex.div.data.DivParsingEnvironment
import com.yandex.div.json.ParsingErrorLogger
import com.yandex.div2.DivData
import org.json.JSONObject

internal fun createDivData(json: JSONObject): DivData {
    val templatesJson = json.optJSONObject("templates")
    val cardJson = json.getJSONObject("card")

    val environment = DivParsingEnvironment(ParsingErrorLogger.ASSERT).apply {
        if (templatesJson != null) parseTemplates(templatesJson)
    }
    return DivData(environment, cardJson)
}
