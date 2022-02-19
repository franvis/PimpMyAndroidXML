package francisco.visintini.pmaxml.presentation.extensions

import francisco.visintini.pmaxml.presentation.apply.formatting.utils.AndroidXmlConstants.EMPTY_SPACE
import java.io.Writer
import org.jdom2.output.support.FormatStack

fun Writer.writeLineBreak(formatStack: FormatStack) {
    write(formatStack.lineSeparator)
}

fun Writer.writeEmptyLine(formatStack: FormatStack) {
    writeLineBreak(formatStack, 2)
}

fun Writer.writeLineBreak(formatStack: FormatStack, quantity: Int) {
    repeat(quantity) { write(formatStack.lineSeparator) }
}

fun Writer.writeIndent(formatStack: FormatStack, level: Int = 1) {
    repeat(level) { write(formatStack.indent) }
}

fun Writer.writeIndent(customIndentSize: Int, level: Int = 1) {
    repeat(level) { write(EMPTY_SPACE.repeat(customIndentSize)) }
}

/** Writes an [EMPTY_SPACE] */
fun Writer.writeEmptySpace() {
    write(EMPTY_SPACE)
}
