package francisco.visintini.pmaxml.presentation.extensions

import org.jdom2.Text

/**
 * Checks if [this] is a [String] or a [Text] and if so, returns the result from either
 * [Text.isAllWhitespace] or [String.isAllWhitespace], false otherwise.
 */
fun Any.isAllWhitespace(): Boolean {
    return when (this) {
        is String -> this.isAllWhitespace()
        is Text -> this.isAllWhitespace()
        else -> false
    }
}

/** Negation method from [Any.isAllWhitespace]. */
fun Any.isNotAllWhitespace(): Boolean {
    return !when (this) {
        is String -> this.isAllWhitespace()
        is Text -> this.isAllWhitespace()
        else -> false
    }
}
