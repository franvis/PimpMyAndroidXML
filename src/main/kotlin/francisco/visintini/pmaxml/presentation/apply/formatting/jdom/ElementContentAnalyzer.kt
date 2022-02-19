package francisco.visintini.pmaxml.presentation.apply.formatting.jdom

import francisco.visintini.pmaxml.presentation.extensions.*
import javax.inject.Inject
import org.jdom2.EntityRef
import org.jdom2.Text

class ElementContentAnalyzer @Inject constructor() {

    /**
     * Returns the index of the last element content skipping trailing white lines.
     *
     * @param elementContent list of content of the element
     * @param startIndex start index where to start checking for the end of the content
     *
     * @return index of content that is all whitespaces, last content index otherwise
     */
    fun getEndOfContentSkippingTrailingWhite(elementContent: List<Any>, startIndex: Int): Int {
        val index = elementContent.indexOfFirst(startIndex) { it.isAllWhitespace() }
        return if (index == elementContent.size) elementContent.size - 1 else index
    }

    fun getStartOfContentSkippingLeadingWhite(content: List<Any>, start: Int): Int =
        content.indexOfFirst(start.normalizeToPositive()) { it.isNotAllWhitespace() }

    fun nextNonText(content: List<Any>, start: Int): Int =
        content.indexOfFirst(start.normalizeToPositive()) { it !is Text && it !is EntityRef }
}
