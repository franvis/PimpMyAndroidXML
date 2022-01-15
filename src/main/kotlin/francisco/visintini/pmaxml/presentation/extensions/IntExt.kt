package francisco.visintini.pmaxml.presentation.extensions

/** Normalizes an Int by returning 0 if the value of [this] is minor than 0. */
fun Int.normalizeToPositive(): Int =
    if (this < 0) {
        0
    } else this
