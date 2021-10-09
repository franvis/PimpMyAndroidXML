package francisco.visintini.pmaxml.presentation.formatting

import francisco.visintini.pmaxml.presentation.formatting.drawable.DrawableFormatter
import francisco.visintini.pmaxml.presentation.formatting.layout.LayoutFormatter
import kotlin.system.exitProcess
import org.jdom2.output.support.AbstractXMLOutputProcessor

@Suppress("UNCHECKED_CAST")
class AndroidXmlFormatter(
    private val layoutFormatter: LayoutFormatter,
    private val drawableFormatter: DrawableFormatter
) :
    AbstractXMLOutputProcessor() {

    fun formatDocuments(rootPath: String) {
        try {
            layoutFormatter.formatDocuments(rootPath)
            drawableFormatter.formatDocuments(rootPath)
        } catch (io: Exception) {
            println("An exception occurred while formating the file")
            println(io.localizedMessage)
            exitProcess(1)
        }
    }
}
