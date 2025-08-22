package ru.yandex.divkit_demo.presentation

import divkit.dsl.Div
import divkit.dsl.State
import divkit.dsl.action
import divkit.dsl.booleanVariable
import divkit.dsl.border
import divkit.dsl.center
import divkit.dsl.color
import divkit.dsl.container
import divkit.dsl.core.bind
import divkit.dsl.core.expression
import divkit.dsl.edgeInsets
import divkit.dsl.evaluate
import divkit.dsl.fixedSize
import divkit.dsl.horizontal
import divkit.dsl.input
import divkit.dsl.render
import divkit.dsl.scope.DivScope
import divkit.dsl.single_line_text
import divkit.dsl.state
import divkit.dsl.stateItem
import divkit.dsl.stroke
import divkit.dsl.text
import divkit.dsl.url
import divkit.dsl.vertical

object CheckmarkCardRenderer {

    fun DivScope.renderMainContainer(data: CheckmarkCardViewModel): Div {
        return container(
            orientation = vertical,
            items = buildList {
                // Добавляем обычные чекмарки из данных
                data.checkmarkItems.forEach { item ->
                    add(renderCheckmarkItem(item))
                }
                // Добавляем кастомный чекмарк с input
                add(renderCustomCheckmarkItem())
            }
        )
    }

    private fun DivScope.renderCheckmarkItem(item: CheckmarkItemData): Div {
        return render(
            CheckmarkTemplate.template,
            CheckmarkTemplate.titleRef bind item.title,
            CheckmarkTemplate.actionUrlRef bind  url("div-action://set_variable?name=${item.variableName}&value=@{!${item.variableName}}"),
            CheckmarkTemplate.isCheckedRef bind expression("@{${item.variableName} ? 'visible' : 'gone'}")
        )
    }

    private fun DivScope.renderCustomCheckmarkItem(): Div {
        return container(
            orientation = horizontal,
            contentAlignmentVertical = center,
            margins = edgeInsets(
                top = 5,
                bottom = 5
            ),
            variables = listOf(
                booleanVariable(name = "is_checked", value = false)
            ),
            items = listOf(
                renderCustomCheckbox(),
                renderCustomStateComponent()
            )
        )
    }

    private fun DivScope.renderCustomCheckbox(): Div {
        return container(
            orientation = vertical,
            width = fixedSize(20),
            height = fixedSize(20),
            contentAlignmentVertical = center,
            margins = edgeInsets(end = 7),
            border = border(
                stroke = stroke(
                    color = color("#000"),
                    width = 1.0
                )
            ),
            actions = listOf(
                action(
                    url = url("div-action://set_variable?name=custom_checkmark_check&value=@{!custom_checkmark_check}"),
                    logId = "set_checkbox",
                ).evaluate(
                    isEnabled = expression("@{!is_locked}")
                )
            ),
            items = listOf(
                text(
                    text = "X",
                    fontSize = 14,
                    textColor = color("#000000"),
                    textAlignmentHorizontal = center,
                ).evaluate(
                    visibility = expression("@{custom_checkmark_check ? 'visible' : 'gone'}")
                )
            )
        )
    }

    private fun DivScope.renderCustomStateComponent(): Div {
        return state(
            id = "cutom_checkmark",
            stateIdVariable = "is_locked_state",
            states = listOf(
                stateItem(
                    stateId = "locked",
                    div = text(
                        fontSize = 14,
                        textColor = color("#000000")
                    ).evaluate(
                        text = expression("@{custom_checkmark_input_text}"),
                    )
                ),
                stateItem(
                    stateId = "unlocked",
                    div = input(
                        textVariable = "custom_checkmark_input_text",
                        hintText = "",
                        keyboardType = single_line_text,
                        width = fixedSize(200),
                        border = border(
                            stroke = stroke(
                                color = color("#000"),
                                width = 1.0
                            )
                        )
                    )
                )
            )
        )
    }
}
