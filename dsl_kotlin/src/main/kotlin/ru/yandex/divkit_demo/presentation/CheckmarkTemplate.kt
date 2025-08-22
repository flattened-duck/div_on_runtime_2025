package ru.yandex.divkit_demo.presentation

import divkit.dsl.Action
import divkit.dsl.Container
import divkit.dsl.Template
import divkit.dsl.Url
import divkit.dsl.Variable
import divkit.dsl.Visibility
import divkit.dsl.action
import divkit.dsl.asList
import divkit.dsl.booleanVariable
import divkit.dsl.border
import divkit.dsl.center
import divkit.dsl.color
import divkit.dsl.container
import divkit.dsl.core.expression
import divkit.dsl.core.reference
import divkit.dsl.defer
import divkit.dsl.edgeInsets
import divkit.dsl.evaluate
import divkit.dsl.fixedSize
import divkit.dsl.horizontal
import divkit.dsl.stroke
import divkit.dsl.template
import divkit.dsl.text
import divkit.dsl.vertical

object CheckmarkTemplate {

    val titleRef = reference<String>("title")
    val actionUrlRef = reference<Url>("action_url")
    val isCheckedRef = reference<Visibility>("is_checked")

    val template: Template<Container> by lazy {
        template(name = "event_checkmark_default") {
            container(
                orientation = horizontal,
                contentAlignmentVertical = center,
                margins = edgeInsets(
                    top = 5,
                    bottom = 5,
                ),
                variables = listOf(
                    booleanVariable(
                        name = "is_checked",
                        value = false,
                    )
                ),
                actions = listOf(
                    action(
                        logId = "set_checkbox",
                    ).evaluate(
                        isEnabled = expression("@{!is_locked}")
                    ).defer(
                        url = actionUrlRef
                    )
                ),
                items = listOf(
                    container(
                        orientation = vertical,
                        width = fixedSize(20),
                        height = fixedSize(20),
                        contentAlignmentVertical = center,
                        margins = edgeInsets(
                            end = 7,
                        ),
                        border = border(
                            stroke = stroke(
                                color = color("#000"),
                                width = 1.0,
                            )
                        ),
                        items = listOf(
                            text(
                                text = "X",
                                fontSize = 14,
                                textColor = color("#000000"),
                                textAlignmentHorizontal = center,
                            ).defer(
                                visibility = isCheckedRef
                            )
                        )
                    ),
                    text(
                        text = "",
                        fontSize = 14,
                        textColor = color("#000000"),
                    ).defer(
                        text = titleRef
                    )
                )
            )
        }
    }
}
