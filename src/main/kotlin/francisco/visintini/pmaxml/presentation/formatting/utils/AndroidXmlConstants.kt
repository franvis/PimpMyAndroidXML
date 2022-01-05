package francisco.visintini.pmaxml.presentation.formatting.utils

import org.jdom2.Namespace

object AndroidXmlConstants {
    const val ANDROID_AAPT_NS_PREFIX = "aapt"
    const val EMPTY_SPACE = " "
    const val ELEMENT_CLOSURE_WITHOUT_QUALIFIED_NAME = " />"
    const val QUALIFIED_NAME_OPENING_BEGINNING = "<"
    const val QUALIFIED_NAME_CLOSURE_BEGINNING = "</"
    const val QUALIFIED_NAME_CLOSURE_END = ">"
    const val UTF_8 = "utf-8"
    const val BEGINNING_OF_VALUE = "=\""
    const val END_OF_VALUE = "\""
    const val XML_NAMESPACE_ABBREVIATION = "xmlns"
    const val LINE_BREAK = "\n"
    const val EMPTY_STRING = ""
    const val AMPERSAND = "&"
    const val COLON = ":"
    const val SEMI_COLON = ";"
    val androidNamespace: Namespace = Namespace.getNamespace("android","http://schemas.android.com/apk/res/android")
}
