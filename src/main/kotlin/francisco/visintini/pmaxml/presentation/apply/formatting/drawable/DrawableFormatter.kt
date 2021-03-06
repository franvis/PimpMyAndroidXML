package francisco.visintini.pmaxml.presentation.apply.formatting.drawable

import francisco.visintini.pmaxml.presentation.apply.formatting.utils.AndroidXmlConstants
import java.io.*
import javax.inject.Inject
import kotlin.system.exitProcess
import org.jdom2.input.SAXBuilder
import org.jdom2.output.Format
import org.jdom2.output.XMLOutputter

@Suppress("UNCHECKED_CAST")
class DrawableFormatter
@Inject
constructor(private val drawableOutputProcessor: DrawableOutputProcessor) : DrawableFileFormatter {

    override fun formatFiles(files: List<File>) {
        try {
            files.forEach { formatDocument(it.absolutePath) }
        } catch (io: Exception) {
            println("An exception occurred while formating the file")
            io.printStackTrace()
            exitProcess(1)
        }
    }

    private fun formatDocument(filePath: String) {
        val doc = SAXBuilder().build(FileInputStream(filePath))
        val outputter =
            XMLOutputter(
                Format.getPrettyFormat().apply {
                    this.indent = DrawableFormattingConfig.INDENT_SPACE
                    lineSeparator = AndroidXmlConstants.LINE_BREAK
                    encoding = AndroidXmlConstants.UTF_8
                },
                drawableOutputProcessor)
        val stream = ByteArrayOutputStream()
        outputter.output(doc, stream)
        val content = stream.toByteArray()
        FileOutputStream(filePath).write(content, 0, content.size - 1)
    }
}
