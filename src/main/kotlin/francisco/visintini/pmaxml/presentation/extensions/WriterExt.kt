package francisco.visintini.pmaxml.presentation.extensions

import francisco.visintini.pmaxml.presentation.formatting.utils.AndroidXmlConstants.EMPTY_SPACE
import java.io.Writer
import org.jdom2.output.support.FormatStack

fun Writer.writeNewEmptyLine(formatStack: FormatStack) {
    write(formatStack.lineSeparator)
}

fun Writer.writeNewEmptyLine(formatStack: FormatStack, quantity: Int) {
    repeat(quantity) { write(formatStack.lineSeparator) }
}

fun Writer.writeIndent(formatStack: FormatStack, level: Int = 1) {
    repeat(level) { write(formatStack.indent) }
}

fun Writer.writeIndent(customIndentSize: Int, level: Int = 1) {
    repeat(level) { write(EMPTY_SPACE.repeat(customIndentSize)) }
}

fun Writer.writeEmptySpace() {
    write(EMPTY_SPACE)
}
