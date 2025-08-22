import pydivkit as dk
from pydivkit.core import Expr


def create_simple_text(text: str, size: int = 16, color: str = "#000000"):
    """Утилита для создания простого текста"""
    return dk.DivText(
        text=text,
        font_size=size,
        text_color=color,
        width=dk.DivMatchParentSize(),
        height=dk.DivWrapContentSize()
    )


def create_button(text: str, action_url: str, bg_color: str = "#3498db"):
    """Утилита для создания кнопки"""
    return dk.DivContainer(
        items=[
            dk.DivText(
                text=text,
                text_color="#ffffff",
                text_alignment_horizontal=dk.DivAlignmentHorizontal.CENTER,
                font_weight=dk.DivFontWeight.BOLD
            )
        ],
        background=[dk.DivSolidBackground(color=bg_color)],
        border=dk.DivBorder(corner_radius=8),
        paddings=dk.DivEdgeInsets(top=12, bottom=12, left=16, right=16),
        actions=[dk.DivAction(url=action_url, log_id="button_click")]
    )


def create_card(items: list, bg_color: str = "#ffffff", padding: int = 16):
    """Утилита для создания карточки"""
    return dk.DivContainer(
        items=items,
        orientation=dk.DivContainerOrientation.VERTICAL,
        width=dk.DivMatchParentSize(),
        height=dk.DivWrapContentSize(),
        background=[dk.DivSolidBackground(color=bg_color)],
        border=dk.DivBorder(corner_radius=12),
        paddings=dk.DivEdgeInsets(top=padding, bottom=padding, left=padding, right=padding)
    )
