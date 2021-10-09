package francisco.visintini.pmaxml.presentation.extensions

import org.jdom2.Text

fun Any.isAllWhitespace(): Boolean {
    return when (this) {
        is String -> this.isAllWhitespace()
        is Text -> this.isAllWhitespace()
        else -> false
    }
}

fun Any.isNotAllWhitespace(): Boolean {
    return !when (this) {
        is String -> this.isAllWhitespace()
        is Text -> this.isAllWhitespace()
        else -> false
    }
}
