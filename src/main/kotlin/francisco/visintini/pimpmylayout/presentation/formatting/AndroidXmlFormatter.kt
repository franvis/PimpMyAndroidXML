package francisco.visintini.pimpmylayout.presentation.formatting

import francisco.visintini.pimpmylayout.presentation.formatting.drawable.AndroidDrawableFormatter
import francisco.visintini.pimpmylayout.presentation.formatting.layout.AndroidLayoutFormatter
import java.io.*
import kotlin.system.exitProcess
import org.jdom2.output.support.AbstractXMLOutputProcessor

@Suppress("UNCHECKED_CAST")
class AndroidXmlFormatter(
    private val androidLayoutFormatter: AndroidLayoutFormatter,
    private val androidDrawableFormatter: AndroidDrawableFormatter
) :
    AbstractXMLOutputProcessor() {

    fun formatDocuments(rootPath: String) {
        try {
            androidLayoutFormatter.formatDocuments(rootPath)
            androidDrawableFormatter.formatDocuments(rootPath)
        } catch (io: Exception) {
            println("An exception occurred while formating the file")
            println(io.localizedMessage)
            exitProcess(1)
        }
    }
}
