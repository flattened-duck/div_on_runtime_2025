import pydivkit as dk
from pydivkit.core import Expr


class EventCheckmarkDefault(dk.DivContainer):
    title: str = dk.Field()
    action_url: str = dk.Field()
    is_checked: str = dk.Field()
    
    variables = [
        dk.BooleanVariable(name="is_checked", value=False)
    ]
    
    orientation = dk.DivContainerOrientation.HORIZONTAL
    content_alignment_vertical = dk.DivAlignmentVertical.CENTER
    margins = dk.DivEdgeInsets(top=5, bottom=5)
    
    actions = [
        dk.DivAction(
            log_id="set_checkbox",
            is_enabled=Expr("@{!is_locked}"),
            url=dk.Ref(action_url)
        )
    ]
    
    items = [
        dk.DivContainer(
            items=[
                dk.DivText(
                    text="X",
                    font_size=14,
                    text_color="#000000",
                    text_alignment_horizontal=dk.DivAlignmentHorizontal.CENTER,
                    visibility=dk.Ref(is_checked)
                )
            ],
            orientation=dk.DivContainerOrientation.VERTICAL,
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
        dk.DivText(
            text=dk.Ref(title),
            font_size=14,
            text_color="#000000"
        )
    ]
