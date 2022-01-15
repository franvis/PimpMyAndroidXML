package francisco.visintini.pmaxml.presentation.extensions

import org.jdom2.Verifier

/** Returns true if all chars contained in the array are xml whitespaces, false otherwise. */
fun CharArray.areAllCharsXMLWhitespaces(): Boolean = none { !Verifier.isXMLWhitespace(it) }
