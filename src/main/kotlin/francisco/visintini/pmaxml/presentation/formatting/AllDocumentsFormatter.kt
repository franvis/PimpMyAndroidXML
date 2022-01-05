package francisco.visintini.pmaxml.presentation.formatting

import francisco.visintini.pmaxml.presentation.formatting.drawable.DrawableFileManager
import francisco.visintini.pmaxml.presentation.formatting.drawable.DrawableFormatter
import francisco.visintini.pmaxml.presentation.formatting.layout.LayoutFileManager
import francisco.visintini.pmaxml.presentation.formatting.layout.LayoutFormatter
import kotlin.system.exitProcess
import org.jdom2.output.support.AbstractXMLOutputProcessor
import java.nio.file.Files
import java.nio.file.Path
import javax.inject.Inject
import kotlin.io.path.isDirectory

@Suppress("UNCHECKED_CAST")
class AllDocumentsFormatter @Inject constructor(
    private val layoutFormatter: LayoutFormatter,
    private val drawableFormatter: DrawableFormatter,
    private val layoutFileManager: LayoutFileManager,
    private val drawableFileManager: DrawableFileManager,
) {

    fun formatAllDocuments(rootPath: String) {
        try {
            Files.walk(Path.of(rootPath)).use { paths ->
                paths.filter { it.isDirectory() }.forEach {
                    drawableFormatter.formatFiles(drawableFileManager.getDrawableFiles(it))
                    layoutFormatter.formatFiles(layoutFileManager.getLayoutFiles(it))
                }
            }
        } catch (io: Exception) {
            println("An exception occurred while formating the file")
            println(io.localizedMessage)
            exitProcess(1)
        }
    }
}
