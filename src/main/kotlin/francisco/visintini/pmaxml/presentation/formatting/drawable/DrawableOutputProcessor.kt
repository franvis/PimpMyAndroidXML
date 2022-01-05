package francisco.visintini.pmaxml.presentation.formatting.drawable

import francisco.visintini.pmaxml.presentation.extensions.*
import francisco.visintini.pmaxml.presentation.formatting.utils.AndroidXmlConstants
import francisco.visintini.pmaxml.presentation.formatting.jdom.ElementContentAnalyzer
import francisco.visintini.pmaxml.presentation.extensions.depth
import java.io.IOException
import java.io.Writer
import org.jdom2.*
import org.jdom2.output.Format.TextMode.*
import org.jdom2.output.support.AbstractXMLOutputProcessor
import org.jdom2.output.support.FormatStack
import org.jdom2.util.NamespaceStack
import javax.inject.Inject

class DrawableOutputProcessor @Inject constructor(
    private val DrawableAttributeComparator: DrawableAttributeComparator,
    private val elementContentAnalyzer: ElementContentAnalyzer
) : AbstractXMLOutputProcessor() {

    override fun printDocument(
        out: Writer,
        fstack: FormatStack,
        nstack: NamespaceStack,
        doc: Document
    ) {
        super.printDocument(out, fstack, nstack, doc)
        newline(fstack, out)
    }

    @Throws(IOException::class)
    override fun printElement(
        writer: Writer,
        formatStack: FormatStack,
        namespaces: NamespaceStack,
        element: Element
    ) {
        val attributes = element.attributes
        val content = element.content as List<Any>

        writer.write(AndroidXmlConstants.QUALIFIED_NAME_OPENING_BEGINNING)
        printQualifiedName(writer, element)

        printElementNamespace(writer, formatStack, element)
        printAdditionalNamespaces(writer, formatStack, element)
        if (attributes.isNotNullNorEmpty()) {
            printAttributes(writer, formatStack, attributes, element.depth())
        }

        val start = elementContentAnalyzer.getStartOfContentSkippingLeadingWhite(content, 0)
        val size = content.size
        if (start >= size) {
            writer.write(AndroidXmlConstants.ELEMENT_CLOSURE_WITHOUT_QUALIFIED_NAME)
        } else {
            writer.write(AndroidXmlConstants.QUALIFIED_NAME_CLOSURE_END)
            if (elementContentAnalyzer.nextNonText(content, start) < size) {
                newline(formatStack, writer)
                printContentRange(
                    formatStack, writer, content, start, size, element.depth(), namespaces)
                newline(formatStack, writer)
                indent(formatStack, writer, element.depth() - 1)
            } else {
                printTextRange(formatStack, writer, content, start, size)
            }

            writer.write(AndroidXmlConstants.QUALIFIED_NAME_CLOSURE_BEGINNING)
            printQualifiedName(writer, element)
            writer.write(AndroidXmlConstants.QUALIFIED_NAME_CLOSURE_END)
        }
    }

    private fun printElementNamespace(writer: Writer, formatStack: FormatStack, element: Element) {
        with(element) {
            if (namespace != Namespace.XML_NAMESPACE && namespace.prefix != AndroidXmlConstants.ANDROID_AAPT_NS_PREFIX && (namespace != Namespace.NO_NAMESPACE)) {
                this@DrawableOutputProcessor.printNamespace(writer, formatStack, namespace)
            }
        }
    }

    override fun printNamespace(writer: Writer, formatStack: FormatStack, namespace: Namespace) {
        with(writer) {
            write(AndroidXmlConstants.XML_NAMESPACE_ABBREVIATION)
            if (!namespace.prefix.equals(AndroidXmlConstants.EMPTY_STRING)) {
                write(AndroidXmlConstants.COLON)
                write(namespace.prefix)
            }

            write(AndroidXmlConstants.BEGINNING_OF_VALUE)
            attributeEscapedEntitiesFilter(writer, formatStack, namespace.uri)
            write(AndroidXmlConstants.END_OF_VALUE)
        }
    }

    private fun printAdditionalNamespaces(
        writer: Writer,
        formatStack: FormatStack,
        element: Element
    ) {
        val list = element.additionalNamespaces
        list?.forEach {
            if (DrawableFormattingConfig.ATTRIBUTE_INDENTION > 0) {
                newline(formatStack, writer)
                indent(formatStack, writer, element.depth() - 1)
                writer.write(DrawableFormattingConfig.INDENT_SPACE)
            } else {
                writer.write(AndroidXmlConstants.EMPTY_SPACE)
            }

            printNamespace(writer, formatStack, it)
        }
    }

    private fun printContentRange(
        formatStack: FormatStack,
        writer: Writer,
        content: List<Any>,
        start: Int,
        end: Int,
        level: Int,
        namespaces: NamespaceStack
    ) {
        var index = start

        while (index < end) {
            val isFirstNode = index == start
            val next = content[index]
            if (next !is Text && next !is EntityRef) {
                if (!isFirstNode) {
                    newline(formatStack, writer)
                }

                indent(formatStack, writer, level)
                printNode(formatStack, writer, next, namespaces)

                ++index
            } else {
                val first =
                    elementContentAnalyzer.getStartOfContentSkippingLeadingWhite(content, index)
                index = elementContentAnalyzer.nextNonText(content, first)
                if (first < index) {
                    if (!isFirstNode) {
                        newline(formatStack, writer)
                    }

                    indent(formatStack, writer, level)
                    printTextRange(formatStack, writer, content, first, index)
                }
            }
        }
    }

    private fun printNode(
        formatStack: FormatStack,
        writer: Writer,
        next: Any,
        namespaces: NamespaceStack
    ) {
        when (next) {
            is Comment -> {
                printComment(writer, formatStack, next)
            }
            is Element -> {
                printElement(writer, formatStack, namespaces, next)
            }
            is ProcessingInstruction -> {
                printProcessingInstruction(writer, formatStack, next)
            }
        }
    }

    private fun printString(formatStack: FormatStack, writer: Writer, str: String) {
        val lineToPrint: String? =
            when (formatStack.textMode) {
                NORMALIZE -> {
                    Text.normalizeString(str)
                }
                TRIM -> {
                    str.trim()
                }
                else -> null
            }

        lineToPrint?.let { attributeEscapedEntitiesFilter(writer, formatStack, it) }
    }

    private fun printTextRange(
        formatStack: FormatStack,
        writer: Writer,
        content: List<Any>,
        start: Int,
        end: Int
    ) {
        var previous: String? = null
        val actualStart =
            elementContentAnalyzer.getStartOfContentSkippingLeadingWhite(content, start)

        if (actualStart < content.size) {
            val actualEnd =
                elementContentAnalyzer.getEndOfContentSkippingTrailingWhite(
                    formatStack.textMode, content, end)
            for (i in actualStart..actualEnd) {
                val node = content[i]
                val next: String =
                    if (node is Text) {
                        node.text
                    } else {
                        if (node is EntityRef) {
                            AndroidXmlConstants.AMPERSAND +
                                node.value +
                                AndroidXmlConstants.SEMI_COLON
                        } else {
                            throw IllegalStateException("Should see only CDATA, Text, or EntityRef")
                        }
                    }

                if (next.isNotEmpty()) {
                    if (previous != null &&
                        (formatStack.textMode == NORMALIZE || formatStack.textMode == TRIM) &&
                        (previous.endsWithWhite() || next.startsWithWhite())) {
                        writer.write(AndroidXmlConstants.EMPTY_SPACE)
                    }

                    when (node) {
                        is CDATA -> {
                            this.printCDATA(writer, formatStack, node)
                        }
                        is EntityRef -> {
                            this.printEntityRef(writer, formatStack, node)
                        }
                        else -> {
                            this.printString(formatStack, writer, next)
                        }
                    }
                    previous = next
                }
            }
        }
    }

    private fun newline(formatStack: FormatStack, writer: Writer) {
        writer.write(formatStack.lineSeparator)
    }

    private fun indent(formatStack: FormatStack, writer: Writer, level: Int) {
        repeat(level) { writer.write(formatStack.indent) }
    }

    override fun printAttribute(out: Writer, fstack: FormatStack, attribute: Attribute) {
        super.printAttribute(out, fstack, attribute)
    }

    private fun printAttributes(
        writer: Writer,
        fstack: FormatStack,
        attribs: List<Attribute>,
        elementDepth: Int
    ) {
        DrawableAttributeComparator.sortAttributes(attribs.checkItemsType()).forEach { attrib ->
            // Write indention
            if (DrawableFormattingConfig.ATTRIBUTE_INDENTION > 0) {
                newline(fstack, writer)
                indent(fstack, writer, elementDepth - 1)
                writer.write(DrawableFormattingConfig.INDENT_SPACE)
            } else {
                writer.write(AndroidXmlConstants.EMPTY_SPACE)
            }
            // Write qualified name
            printQualifiedName(writer, attrib)
            // Write attribute
            writer.write(AndroidXmlConstants.BEGINNING_OF_VALUE)
            attributeEscapedEntitiesFilter(writer, fstack, attrib.value)
            writer.write(AndroidXmlConstants.END_OF_VALUE)
        }
    }

    private fun printQualifiedName(writer: Writer, namespace: Namespace, name: String) {
        with(writer) {
            if (!namespace.prefix.isNullOrEmpty()) {
                write(namespace.prefix)
                write(AndroidXmlConstants.COLON)
            }
            write(name)
        }
    }

    private fun printQualifiedName(writer: Writer, a: Attribute) {
        printQualifiedName(writer, a.namespace, a.name)
    }

    private fun printQualifiedName(writer: Writer, e: Element) {
        printQualifiedName(writer, e.namespace, e.name)
    }
}
