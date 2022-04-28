package francisco.visintini.pmaxml.presentation.apply.formatting.drawable

import francisco.visintini.pmaxml.presentation.apply.formatting.jdom.ElementContentAnalyzer
import francisco.visintini.pmaxml.presentation.apply.formatting.utils.AndroidXmlConstants
import francisco.visintini.pmaxml.presentation.extensions.*
import java.io.IOException
import java.io.Writer
import javax.inject.Inject
import org.jdom2.*
import org.jdom2.output.Format.TextMode.*
import org.jdom2.output.support.AbstractXMLOutputProcessor
import org.jdom2.output.support.FormatStack
import org.jdom2.util.NamespaceStack

@Suppress("UNCHECKED_CAST")
class DrawableOutputProcessor
@Inject
constructor(
    private val drawableAttributeComparator: DrawableAttributeComparator,
    private val elementContentAnalyzer: ElementContentAnalyzer
) : AbstractXMLOutputProcessor() {

    override fun printDocument(
        writer: Writer,
        fstack: FormatStack,
        nstack: NamespaceStack,
        doc: Document
    ) {
        super.printDocument(writer, fstack, nstack, doc)
        // TODO Write a line at the end of file.
        //  In the future we should make it configurable (disable it)
        writer.writeLineBreak(fstack)
    }

    @Throws(IOException::class)
    override fun printElement(
        writer: Writer,
        formatStack: FormatStack,
        namespaces: NamespaceStack,
        element: Element
    ) {
        // Represents the content of the element (within the tags)
        val content = element.content as List<Any>
        // Represents the attributes of the current element
        val attributes = element.attributes

        printElementOpeningTag(writer, element)

        printAdditionalNamespaces(writer, formatStack, element)

        if (attributes.isNotNullNorEmpty()) {
            printElementAttributes(writer, formatStack, attributes, element.depth())
        }

        val contentStartIndex =
            elementContentAnalyzer.getStartOfContentSkippingLeadingWhite(content, 0)
        val contentSize = content.size
        // Print ending of element
        if (contentStartIndex >= contentSize) {
            // Write the closure of content elements (TextView, ImageView, etc)
            writer.write(AndroidXmlConstants.OPENING_TAG_WITHOUT_CHILDREN_CLOSURE)
            writer.writeLineBreak(formatStack)
        } else {
            // Write the closure of group layout elements (LinearLayout, ConstraintLayout, etc) that
            // contain elements
            writer.write(AndroidXmlConstants.OPENING_TAG_WITH_CHILDREN_CLOSURE)
            writer.writeLineBreak(formatStack)

            printContentRange(
                formatStack,
                writer,
                content,
                contentStartIndex,
                contentSize,
                element.depth(),
                namespaces)
            printElementClosingTag(writer, formatStack, element)
        }
    }

    override fun printComment(writer: Writer, formatStack: FormatStack, comment: Comment) {
        super.printComment(writer, formatStack, comment)
        if (comment.parentElement != null) writer.writeLineBreak(formatStack)
    }

    private fun printElementOpeningTag(writer: Writer, element: Element) {
        // Example: "<"
        writer.write(AndroidXmlConstants.OPENING_TAG_BEGINNING)
        // Example: androidx.coordinatorlayout.widget.CoordinatorLayout
        printQualifiedName(writer, element)
    }

    private fun printElementClosingTag(writer: Writer, formatStack: FormatStack, element: Element) {
        writer.writeIndent(formatStack, element.depth() - 1)
        writer.write(AndroidXmlConstants.CLOSING_TAG_BEGINNING)
        printQualifiedName(writer, element)
        writer.write(AndroidXmlConstants.CLOSING_TAG_CLOSURE)
        if (element.isNotRootElement()) writer.writeLineBreak(formatStack)
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
        element.additionalNamespaces?.forEach {
            with(writer) {
                if (DrawableFormattingConfig.ATTRIBUTE_INDENTION > 0) {
                    writeLineBreak(formatStack)
                    writeIndent(formatStack, element.depth() - 1)
                    write(DrawableFormattingConfig.INDENT_SPACE)
                } else {
                    write(AndroidXmlConstants.EMPTY_SPACE)
                }

                printNamespace(writer, formatStack, it)
            }
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
            val next = content[index]
            if (next !is Text && next !is EntityRef) {
                writer.writeIndent(formatStack, level)
                printNode(formatStack, writer, next, namespaces)

                ++index
            } else {
                val first =
                    elementContentAnalyzer.getStartOfContentSkippingLeadingWhite(content, index)
                index = elementContentAnalyzer.nextNonText(content, first)
                if (first < index) {
                    writer.writeIndent(formatStack, level)
                    printTextRange(formatStack, writer, content, first, index)
                }
            }
        }
    }

    private fun printNode(
        formatStack: FormatStack,
        writer: Writer,
        node: Any,
        namespaces: NamespaceStack
    ) {
        when (node) {
            is Comment -> {
                printComment(writer, formatStack, node)
            }
            is Element -> {
                printElement(writer, formatStack, namespaces, node)
            }
            is ProcessingInstruction -> {
                printProcessingInstruction(writer, formatStack, node)
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
        elementContentItems: List<Any>,
        startIndex: Int,
        endIndex: Int
    ) {
        var previous: String? = null
        val actualStart =
            elementContentAnalyzer.getStartOfContentSkippingLeadingWhite(
                elementContentItems, startIndex)

        if (actualStart < elementContentItems.size) {
            val actualEnd =
                elementContentAnalyzer.getEndOfContentSkippingTrailingWhite(
                    elementContentItems, endIndex)
            for (i in actualStart..actualEnd) {
                val node = elementContentItems[i]
                val next: String =
                    if (node is Text) {
                        node.text
                    } else {
                        if (node is EntityRef) {
                            AndroidXmlConstants.AMPERSAND +
                                node.value +
                                AndroidXmlConstants.SEMI_COLON
                        } else {
                            throw IllegalStateException(
                                "$node Should see only CDATA, Text, or EntityRef")
                        }
                    }

                if (next.isNotEmpty()) {
                    if (previous != null &&
                        (formatStack.textMode == NORMALIZE || formatStack.textMode == TRIM) &&
                        (previous.endsWithWhite() || next.startsWithWhite())) {
                        writer.writeEmptySpace()
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

    private fun printElementAttributes(
        writer: Writer,
        fstack: FormatStack,
        attribs: List<Attribute>,
        elementDepth: Int
    ) {
        drawableAttributeComparator.sortAttributes(attribs.checkItemsType()).forEach { attrib ->
            with(writer) {
                // Write indention
                writeLineBreak(fstack)
                writeIndent(fstack, elementDepth)
                // Write qualified name
                printQualifiedName(writer, attrib)
                // Write attribute value
                write(AndroidXmlConstants.BEGINNING_OF_VALUE)
                attributeEscapedEntitiesFilter(writer, fstack, attrib.value)
                write(AndroidXmlConstants.END_OF_VALUE)
            }
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
