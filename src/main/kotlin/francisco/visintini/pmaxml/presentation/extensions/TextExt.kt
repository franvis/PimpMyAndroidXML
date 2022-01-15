package francisco.visintini.pmaxml.presentation.extensions

import org.jdom2.Text

/** Returns true if the text is only xml whitespaces, false otherwise. */
fun Text.isAllWhitespace() = text.toCharArray().areAllCharsXMLWhitespaces()
