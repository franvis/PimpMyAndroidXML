package francisco.visintini.pmaxml.presentation.formatting.layout

import francisco.visintini.pmaxml.presentation.formatting.AndroidXmlConstants
import java.io.*
import kotlin.system.exitProcess
import org.jdom2.input.SAXBuilder
import org.jdom2.output.Format
import org.jdom2.output.XMLOutputter
import org.jdom2.output.support.AbstractXMLOutputProcessor

@Suppress("UNCHECKED_CAST")
class LayoutFormatter(
    private val layoutFileProvider: LayoutFileProvider,
    private val layoutOutputProcessor: LayoutOutputProcessor
) : AbstractXMLOutputProcessor() {

    /**
     * Formats all layout xml documents within a directory. If no directory named 'layout' is found
     * when navigating recursively the directoryPath parameter, no file will be formatted.
     *
     * @param directoryPath root directory where the examination and search for documents to be
     * formatted will start.
     */
    fun formatDocuments(directoryPath: String) {
        try {
            layoutFileProvider.getLayoutFiles(directoryPath).forEach {
                formatLayoutDocument(it.absolutePath)
            }
        } catch (io: Exception) {
            println("An exception occurred while formating the file")
            println(io.localizedMessage)
            exitProcess(1)
        }
    }

    private fun formatLayoutDocument(filePath: String) {
        val doc = SAXBuilder().build(FileInputStream(filePath))
        val outputter =
            XMLOutputter(
                Format.getPrettyFormat().apply {
                    indent = LayoutFormattingConfig.INDENT_SPACE
                    lineSeparator = AndroidXmlConstants.LINE_BREAK
                    encoding = AndroidXmlConstants.UTF_8
                },
                layoutOutputProcessor)
        val stream = ByteArrayOutputStream()
        outputter.output(doc, stream)
        val content = stream.toByteArray()
        FileOutputStream(filePath).write(content, 0, content.size - 2)
    }
}
