package francisco.visintini.pmaxml.presentation.extensions

import org.jdom2.Verifier

fun String?.startsWithWhite() = !isNullOrEmpty() && Verifier.isXMLWhitespace(toCharArray().first())

fun String?.endsWithWhite() = !isNullOrEmpty() && Verifier.isXMLWhitespace(toCharArray().last())

fun String.isAllWhitespace() = toCharArray().areAllCharsXMLWhitespaces()
