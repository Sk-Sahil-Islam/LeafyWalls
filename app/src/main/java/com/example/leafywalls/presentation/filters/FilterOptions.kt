package com.example.leafywalls.presentation.filters

enum class SortingOption(val value: String) {
    LATEST("latest"),
    RELEVANT("relevant")
}

enum class OrientationOption(val value: String) {
    LANDSCAPE("landscape"),
    PORTRAIT("portrait"),
    SQUARISH("squarish")
}

enum class ColorOption(val value: String) {
    BLACK_AND_WHITE("black_and_white"),
    BLACK("black"),
    WHITE("white"),
    YELLOW("yellow"),
    ORANGE("orange"),
    RED("red"),
    PURPLE("purple"),
    MAGENTA("magenta"),
    GREEN("green"),
    TEAL("teal"),
    BLUE("blue")
}

enum class SafeSearchOption(val value: String) {
    LOW("low"),
    HIGH("high")
}

