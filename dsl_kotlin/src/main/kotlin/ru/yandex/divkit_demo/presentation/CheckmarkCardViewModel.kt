package ru.yandex.divkit_demo.presentation

data class CheckmarkCardViewModel(
    val checkmarkItems: List<CheckmarkItemData>
)

data class CheckmarkItemData(
    val title: String,
    val variableName: String,
    val isChecked: Boolean = false
)
