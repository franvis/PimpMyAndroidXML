package francisco.visintini.pmaxml.presentation.apply.formatting.layout

import francisco.visintini.pmaxml.presentation.apply.formatting.utils.AndroidXmlConstants
import java.io.*
import javax.inject.Inject
import org.jdom2.input.SAXBuilder
import org.jdom2.output.Format
import org.jdom2.output.XMLOutputter

@Suppress("UNCHECKED_CAST")
class LayoutFormatter
@Inject
constructor(private val layoutOutputProcessor: LayoutOutputProcessor) : LayoutFileFormatter {

    override fun formatFiles(files: List<File>) {
        files.forEach { format(it.absolutePath) }
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
