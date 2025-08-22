package ru.yandex.divkit_demo

import divkit.dsl.Divan
import divkit.dsl.Url
import divkit.dsl.action
import divkit.dsl.booleanVariable
import divkit.dsl.core.expression
import divkit.dsl.data
import divkit.dsl.divan
import divkit.dsl.evaluate
import divkit.dsl.on_variable
import divkit.dsl.stringVariable
import divkit.dsl.timer
import divkit.dsl.trigger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.yandex.divkit_demo.presentation.CheckmarkCardRenderer
import ru.yandex.divkit_demo.presentation.CheckmarkCardViewModel
import ru.yandex.divkit_demo.presentation.CheckmarkItemData

@RestController
@RequestMapping("/divruntime") // Listening at localhost:8080/divruntime
class DemoScreenController {

    @GetMapping
    fun getDemoScreen(
        @RequestHeader(name = "Accept-Language") lang: String?,
        @RequestParam(name = "username") username: String?,
    ): ResponseEntity<Divan> {
        val translations = Translations.fromLang(lang)
        return ResponseEntity(
            renderDemoScreen(translations, username),
            HttpStatus.OK
        )
    }

    private fun renderDemoScreen(translations: Translations, username: String?): Divan {
        val message = mapMessage(username, translations)
        val checkmarkItems = createCheckmarkItems()
        val data = CheckmarkCardViewModel(checkmarkItems = checkmarkItems)

        return divan {
            data(
                logId = "div2_sample_card",
                div = with(CheckmarkCardRenderer) {
                    renderMainContainer(data)
                },
                variables = listOf(
                    booleanVariable(name = "is_locked", value = false),
                    stringVariable(name = "is_locked_state", value = "unlocked"),
                    booleanVariable(name = "coderun_check", value = false),
                    booleanVariable(name = "divkit_check", value = false),
                    booleanVariable(name = "workshop_check", value = false),
                    booleanVariable(name = "quiz_check", value = false),
                    booleanVariable(name = "hookah_check", value = false),
                    stringVariable(name = "custom_checkmark_input_text", value = ""),
                    booleanVariable(name = "custom_checkmark_check", value = false),
                ),
                variableTriggers = listOf(
                    trigger(
                        mode = on_variable,
                        actions = listOf(
                            action(
                                url = Url.Companion.create("div-action://set_variable?name=is_locked_state&value=@{is_locked ? 'locked' : 'unlocked'}"),
                                logId = "change_lock_state"
                            )
                        )
                    ).evaluate(
                        condition = expression("@{is_locked || !is_locked}")
                    )
                ),
                timers = listOf(
                    timer(
                        id = "update_timer",
                        tickInterval = 3000,
                        tickActions = listOf(
                            action(
                                logId = "update_page",
                                url = Url.Companion.create("sample-action://update"),
                            )
                        )
                    )
                )
            )
        }
    }

    private fun mapMessage(
        username: String?,
        translations: Translations,
    ): String {
        return if (username != null) {
            translations["hello"].format(username)
        } else {
            translations["who"]
        }
    }

    private fun createCheckmarkItems(): List<CheckmarkItemData> {
        return listOf(
            CheckmarkItemData(
                title = "Покодил на CodeRun",
                variableName = "coderun_check",
                isChecked = false
            ),
            CheckmarkItemData(
                title = "Написал вёрстку на скорость",
                variableName = "divkit_check",
                isChecked = false
            ),
            CheckmarkItemData(
                title = "Сходил на воркшоп",
                variableName = "workshop_check",
                isChecked = false
            ),
            CheckmarkItemData(
                title = "Угадал все ответы на квизе",
                variableName = "quiz_check",
                isChecked = false
            ),
            CheckmarkItemData(
                title = "Посмотрел на кальян",
                variableName = "hookah_check",
                isChecked = false
            )
        )
    }
}