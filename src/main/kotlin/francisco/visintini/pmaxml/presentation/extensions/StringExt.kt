package francisco.visintini.pmaxml.presentation.extensions

import org.jdom2.Verifier

/**
 * Returns true if the first character in the String is an XML whitespace, false otherwise or when
 * [this] is null or empty.
 */
fun String?.startsWithWhite() = !isNullOrEmpty() && Verifier.isXMLWhitespace(toCharArray().first())

/**
 * Returns true if the last character in the String is an XML whitespace, false otherwise or when
 * [this] is null or empty.
 */
fun String?.endsWithWhite() = !isNullOrEmpty() && Verifier.isXMLWhitespace(toCharArray().last())

/** Returns true if the entire String is only xml whitespaces, false otherwise. */
fun String.isAllWhitespace() = toCharArray().areAllCharsXMLWhitespaces()
