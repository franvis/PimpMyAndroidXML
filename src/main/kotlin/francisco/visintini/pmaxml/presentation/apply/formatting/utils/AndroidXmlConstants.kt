package francisco.visintini.pmaxml.presentation.apply.formatting.utils

import org.jdom2.Namespace

/** Container object that holds all the android xml constants used by this project. */
object AndroidXmlConstants {
    const val EMPTY_SPACE = " "
    const val OPENING_TAG_BEGINNING = "<"
    const val OPENING_TAG_WITHOUT_CHILDREN_CLOSURE = " />"
    const val OPENING_TAG_WITH_CHILDREN_CLOSURE = ">"
    const val CLOSING_TAG_BEGINNING = "</"
    const val CLOSING_TAG_CLOSURE = ">"
    const val UTF_8 = "utf-8"
    const val BEGINNING_OF_VALUE = "=\""
    const val END_OF_VALUE = "\""
    const val XML_NAMESPACE_ABBREVIATION = "xmlns"
    const val LINE_BREAK = "\n"
    const val EMPTY_STRING = ""
    const val AMPERSAND = "&"
    const val COLON = ":"
    const val SEMI_COLON = ";"
    val androidNamespace: Namespace =
        Namespace.getNamespace("android", "http://schemas.android.com/apk/res/android")
}
