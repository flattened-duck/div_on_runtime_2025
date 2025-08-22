import pydivkit as dk
from pydivkit.core import Expr
from .templates import EventCheckmarkDefault


def create_hello_world_div():
    return dk.DivText(
        text="Hello, World!",
        width=dk.DivMatchParentSize(),
        height=dk.DivWrapContentSize(),
        text_alignment_horizontal=dk.DivAlignmentHorizontal.CENTER,
        text_alignment_vertical=dk.DivTextAlignmentVertical.CENTER,
        font_size=24,
        text_color="#000000",
        background=[dk.DivSolidBackground(color="#f0f8ff")],
        margins=dk.DivEdgeInsets(top=20, bottom=20, left=20, right=20),
        paddings=dk.DivEdgeInsets(top=16, bottom=16, left=16, right=16),
        border=dk.DivBorder(corner_radius=12)
    )


def create_welcome_card():
    return dk.DivContainer(
        items=[
            dk.DivText(
                text="Добро пожаловать в PyDivKit!",
                width=dk.DivMatchParentSize(),
                height=dk.DivWrapContentSize(),
                text_alignment_horizontal=dk.DivAlignmentHorizontal.CENTER,
                font_size=28,
                font_weight=dk.DivFontWeight.BOLD,
                text_color="#2c3e50",
                margins=dk.DivEdgeInsets(bottom=16)
            ),
            dk.DivText(
                text="Это пример работы с DSL для создания интерфейсов DivKit на Python.",
                width=dk.DivMatchParentSize(),
                height=dk.DivWrapContentSize(),
                text_alignment_horizontal=dk.DivAlignmentHorizontal.CENTER,
                font_size=16,
                text_color="#34495e",
                margins=dk.DivEdgeInsets(bottom=24)
            ),
            create_hello_world_div(),
            dk.DivText(
                text="Сервер работает на localhost:8080",
                width=dk.DivMatchParentSize(),
                height=dk.DivWrapContentSize(),
                text_alignment_horizontal=dk.DivAlignmentHorizontal.CENTER,
                font_size=14,
                text_color="#7f8c8d",
                margins=dk.DivEdgeInsets(top=24)
            )
        ],
        orientation=dk.DivContainerOrientation.VERTICAL,
        width=dk.DivMatchParentSize(),
        height=dk.DivWrapContentSize(),
        background=[dk.DivSolidBackground(color="#ffffff")],
        paddings=dk.DivEdgeInsets(top=20, bottom=20, left=20, right=20)
    )


def create_checkbox_layout():
    variables = [
        dk.BooleanVariable(name="is_locked", value=False),
        dk.StringVariable(name="is_locked_state", value="unlocked"),
        dk.BooleanVariable(name="coderun_check", value=False),
        dk.BooleanVariable(name="divkit_check", value=False),
        dk.BooleanVariable(name="workshop_check", value=False),
        dk.BooleanVariable(name="quiz_check", value=False),
        dk.BooleanVariable(name="hookah_check", value=False),
        dk.StringVariable(name="custom_checkmark_input_text", value=""),
        dk.BooleanVariable(name="custom_checkmark_check", value=False)
    ]
    
    checkbox_items = [
        {
            "title": "Покодил на CodeRun",
            "action_url": "div-action://set_variable?name=coderun_check&value=@{!coderun_check}",
            "is_checked": Expr("@{coderun_check ? 'visible' : 'gone'}")
        },
        {
            "title": "Написал вёрстку на скорость", 
            "action_url": "div-action://set_variable?name=divkit_check&value=@{!divkit_check}",
            "is_checked": Expr("@{divkit_check ? 'visible' : 'gone'}")
        },
        {
            "title": "Сходил на воркшоп",
            "action_url": "div-action://set_variable?name=workshop_check&value=@{!workshop_check}",
            "is_checked": Expr("@{workshop_check ? 'visible' : 'gone'}")
        },
        {
            "title": "Угадал все ответы на квизе",
            "action_url": "div-action://set_variable?name=quiz_check&value=@{!quiz_check}",
            "is_checked": Expr("@{quiz_check ? 'visible' : 'gone'}")
        },
        {
            "title": "Посмотрел на кальян",
            "action_url": "div-action://set_variable?name=hookah_check&value=@{!hookah_check}",
            "is_checked": Expr("@{hookah_check ? 'visible' : 'gone'}")
        }
    ]
    
    checkbox_components = [
        EventCheckmarkDefault(
            title=item["title"],
            action_url=item["action_url"],
            is_checked=item["is_checked"]
        )
        for item in checkbox_items
    ]
    
    custom_checkbox = dk.DivContainer(
        items=[
            dk.DivContainer(
                items=[
                    dk.DivText(
                        text="X",
                        font_size=14,
                        text_color="#000000",
                        text_alignment_horizontal=dk.DivAlignmentHorizontal.CENTER,
                        visibility=Expr("@{custom_checkmark_check ? 'visible' : 'gone'}")
                    )
                ],
                orientation=dk.DivContainerOrientation.VERTICAL,
                actions=[
                    dk.DivAction(
                        url="div-action://set_variable?name=custom_checkmark_check&value=@{!custom_checkmark_check}",
                        log_id="set_checkbox",
                        is_enabled=Expr("@{!is_locked}")
                    )
                ],
                width=dk.DivFixedSize(value=20),
                height=dk.DivFixedSize(value=20),
                border=dk.DivBorder(
                    stroke=dk.DivStroke(
                        color="#000",
                        width=1,
                        style=dk.DivStrokeStyleSolid()
                    )
                ),
                margins=dk.DivEdgeInsets(end=7),
                content_alignment_vertical=dk.DivAlignmentVertical.CENTER
            ),
            dk.DivState(
                div_id="custom_checkmark",
                state_id_variable="is_locked_state",
                states=[
                    dk.DivStateState(
                        state_id="locked",
                        div=dk.DivText(
                            text=Expr("@{custom_checkmark_input_text}"),
                            font_size=14,
                            text_color="#000000"
                        )
                    ),
                    dk.DivStateState(
                        state_id="unlocked",
                        div=dk.DivInput(
                            text_variable="custom_checkmark_input_text",
                            hint_text="",
                            keyboard_type=dk.DivInputKeyboardType.SINGLE_LINE_TEXT,
                            width=dk.DivFixedSize(value=200),
                            border=dk.DivBorder(
                                stroke=dk.DivStroke(
                                    color="#000",
                                    width=1,
                                    style=dk.DivStrokeStyleSolid()
                                )
                            )
                        )
                    )
                ]
            )
        ],
        orientation=dk.DivContainerOrientation.HORIZONTAL,
        content_alignment_vertical=dk.DivAlignmentVertical.CENTER,
        margins=dk.DivEdgeInsets(top=5, bottom=5),
        variables=[dk.BooleanVariable(name="is_checked", value=False)]
    )
    
    main_container = dk.DivContainer(
        items=checkbox_components + [custom_checkbox],
        orientation=dk.DivContainerOrientation.VERTICAL
    )
    
    triggers = [
        dk.DivTrigger(
            mode=dk.DivTriggerMode.ON_VARIABLE,
            condition=Expr("@{is_locked || !is_locked}"),
            actions=[
                dk.DivAction(
                    url="div-action://set_variable?name=is_locked_state&value=@{is_locked ? 'locked' : 'unlocked'}",
                    log_id="change_lock_state"
                )
            ]
        )
    ]
    
    return dk.DivData(
        log_id="div2_sample_card",
        variables=variables,
        variable_triggers=triggers,
        states=[
            dk.DivDataState(
                state_id=0,
                div=main_container
            )
        ]
    )
