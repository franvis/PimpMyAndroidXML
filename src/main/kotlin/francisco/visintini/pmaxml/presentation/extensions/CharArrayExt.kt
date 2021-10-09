package francisco.visintini.pmaxml.presentation.extensions

import org.jdom2.Verifier

fun CharArray.areAllCharsXMLWhitespaces(): Boolean = none { !Verifier.isXMLWhitespace(it) }
