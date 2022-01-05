package francisco.visintini.pmaxml.presentation.formatting.layout

import francisco.visintini.pmaxml.presentation.formatting.FileFormatter
import francisco.visintini.pmaxml.presentation.formatting.utils.AndroidXmlConstants
import java.io.*
import javax.inject.Inject
import kotlin.system.exitProcess
import org.jdom2.input.SAXBuilder
import org.jdom2.output.Format
import org.jdom2.output.XMLOutputter

@Suppress("UNCHECKED_CAST")
class LayoutFormatter
@Inject
constructor(private val layoutOutputProcessor: LayoutOutputProcessor) : FileFormatter() {

    override fun formatFiles(files: List<File>) {
        try {
            files.forEach { format(it.absolutePath) }
        } catch (io: Exception) {
            println("An exception occurred while formating the file")
            println(io.localizedMessage)
            exitProcess(1)
        }
    }

    private fun format(filePath: String) {
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
