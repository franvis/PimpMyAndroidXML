package francisco.visintini.pmaxml.presentation.extensions

import org.jdom2.Text

fun Text.isAllWhitespace() = text.toCharArray().areAllCharsXMLWhitespaces()
