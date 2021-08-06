package francisco.visintini.pimpmylayout.presentation.extensions

fun Int.normalizeToPositive(): Int =
    if (this < 0) {
        0
    } else this

fun Int.normalizeToMinimum(minimum: Int): Int =
    if (this < minimum) {
        minimum
    } else this
