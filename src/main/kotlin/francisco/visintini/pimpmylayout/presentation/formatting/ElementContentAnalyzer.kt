package francisco.visintini.pimpmylayout.presentation.formatting

import francisco.visintini.pimpmylayout.presentation.extensions.*
import org.jdom2.EntityRef
import org.jdom2.Text
import org.jdom2.output.Format

class ElementContentAnalyzer {

    fun getEndOfContentSkippingTrailingWhite(
        textMode: Format.TextMode,
        content: List<Any>,
        start: Int
    ): Int {
        with(start.normalizeToMinimum(content.size)) {
            val isTrimOrNormalizeTextMode =
                with(textMode) {
                    this == Format.TextMode.TRIM_FULL_WHITE ||
                        this == Format.TextMode.NORMALIZE ||
                        this == Format.TextMode.TRIM
                }
            return if (isTrimOrNormalizeTextMode) {
                content.firstIndexForCondition(this) { it.isAllWhitespace() }
            } else this
        }
    }

    fun getStartOfContentSkippingLeadingWhite(content: List<Any>, start: Int): Int =
        content.firstIndexForCondition(start.normalizeToPositive()) { it.isNotAllWhitespace() }

    fun nextNonText(content: List<Any>, start: Int): Int =
        content.firstIndexForCondition(start.normalizeToPositive()) {
            it !is Text && it !is EntityRef
        }
}
