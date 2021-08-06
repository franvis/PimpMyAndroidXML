package francisco.visintini.pimpmylayout.presentation.extensions

import org.jdom2.Text

fun Text.isAllWhitespace() = text.toCharArray().areAllCharsXMLWhitespaces()
