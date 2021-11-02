package francisco.visintini.pmaxml.presentation.formatting.layout

import francisco.visintini.pmaxml.presentation.extensions.*
import francisco.visintini.pmaxml.presentation.formatting.AndroidXmlConstants
import francisco.visintini.pmaxml.presentation.formatting.ElementContentAnalyzer
import java.io.IOException
import java.io.Writer
import org.jdom2.*
import org.jdom2.output.Format.TextMode.*
import org.jdom2.output.support.AbstractXMLOutputProcessor
import org.jdom2.output.support.FormatStack
import org.jdom2.util.NamespaceStack

@Suppress("UNCHECKED_CAST")
class LayoutOutputProcessor(
    private val layoutAttributeComparator: LayoutAttributeComparator,
    private val elementContentAnalyzer: ElementContentAnalyzer
) : AbstractXMLOutputProcessor() {

    override fun printDocument(
        writer: Writer,
        fstack: FormatStack,
        nstack: NamespaceStack,
        doc: Document
    ) {
        super.printDocument(writer, fstack, nstack, doc)
        // Write a line at the end of file. In the future we should make it configurable (disable it)
        writer.writeNewEmptyLine(fstack)
    }

    @Throws(IOException::class)
    override fun printElement(
        writer: Writer,
        formatStack: FormatStack,
        namespaces: NamespaceStack,
        element: Element
    ) {
        // Represents the attributes of the current element
        val attributes = element.attributes
        // Represents the content of the element (within the tags)
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
        // Print ending of element
        if (start >= size) {
            // Write the closure of content elements (TextView, ImageView, etc)
            writer.write(AndroidXmlConstants.ELEMENT_CLOSURE_WITHOUT_QUALIFIED_NAME)
        } else {
            // Write the closure of group layout elements (LinearLayout, ConstraintLayout, etc)
            writer.write(AndroidXmlConstants.QUALIFIED_NAME_CLOSURE_END)
            writer.writeNewEmptyLine(formatStack)
            if (elementContentAnalyzer.nextNonText(content, start) < size) {
                writer.writeNewEmptyLine(formatStack)
                printContentRange(
                    formatStack, writer, content, start, size, element.depth(), namespaces
                )
                writer.writeNewEmptyLine(formatStack)
                writer.writeIndent(formatStack, element.depth() - 1)
            } else {
                printTextRange(formatStack, writer, content, start, size)
            }
            writer.write(AndroidXmlConstants.QUALIFIED_NAME_CLOSURE_BEGINNING)
            printQualifiedName(writer, element)
            writer.write(AndroidXmlConstants.QUALIFIED_NAME_CLOSURE_END)
        }
        /**
         * If the current element is the root element or is not the last element of the parent we add a line break.
         * This is to avoid having line breaks between closures of elements, for example:
         * <pre>{@code
         *     </androidx.constraintlayout.widget.ConstraintLayout>
         * </androidx.coordinatorlayout.widget.CoordinatorLayout>
         * }</pre>
         * instead of
         * <pre>{@code
         *     </androidx.constraintlayout.widget.ConstraintLayout>
         *
         * </androidx.coordinatorlayout.widget.CoordinatorLayout>
         * }</pre>
         */
        if(element.isRootElement || element.isNotLastElementOfParent()) {
            writer.writeNewEmptyLine(formatStack)
        }
    }

    private fun printElementNamespace(writer: Writer, formatStack: FormatStack, element: Element) {
        with(element) {
            if (namespace != Namespace.XML_NAMESPACE && (namespace != Namespace.NO_NAMESPACE)) {
                this@LayoutOutputProcessor.printNamespace(writer, formatStack, namespace)
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
        element.additionalNamespaces?.forEach {
            with(writer) {
                if (LayoutFormattingConfig.ATTRIBUTE_INDENTION > 0) {
                    writeNewEmptyLine(formatStack)
                    writeIndent(formatStack, element.depth() - 1)
                    write(LayoutFormattingConfig.INDENT_SPACE)
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
            val isFirstNode = index == start
            val next = content[index]
            if (next !is Text && next !is EntityRef) {
                if (!isFirstNode) {
                    writer.writeNewEmptyLine(formatStack)
                }

                writer.writeIndent(formatStack, level)
                printNode(formatStack, writer, next, namespaces)

                ++index
            } else {
                val first =
                    elementContentAnalyzer.getStartOfContentSkippingLeadingWhite(content, index)
                index = elementContentAnalyzer.nextNonText(content, first)
                if (first < index) {
                    if (!isFirstNode) {
                        writer.writeNewEmptyLine(formatStack)
                    }

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
                    formatStack.textMode, content, end
                )
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
                        (previous.endsWithWhite() || next.startsWithWhite())
                    ) {
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

    private fun printAttributes(
        writer: Writer,
        fstack: FormatStack,
        attribs: List<Attribute>,
        elementDepth: Int
    ) {
        layoutAttributeComparator.sortAttributes(attribs.checkItemsType()).forEach { attrib ->
            with(writer) {
                // Write indention
                writeNewEmptyLine(fstack)
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
