package francisco.visintini.pmaxml.presentation.apply.formatting

import francisco.visintini.pmaxml.presentation.apply.formatting.drawable.DrawableFileManager
import francisco.visintini.pmaxml.presentation.apply.formatting.drawable.DrawableFormatter
import francisco.visintini.pmaxml.presentation.apply.formatting.layout.LayoutFileManager
import francisco.visintini.pmaxml.presentation.apply.formatting.layout.LayoutFormatter
import java.nio.file.Files
import java.nio.file.Path
import javax.inject.Inject
import kotlin.io.path.isDirectory
import kotlin.system.exitProcess

@Suppress("UNCHECKED_CAST")
class AllDocumentsFormatter
@Inject
constructor(
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
            io.printStackTrace()
            exitProcess(1)
        }
    }
}
