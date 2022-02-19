package francisco.visintini.pmaxml.presentation.apply.formatting

import francisco.visintini.pmaxml.presentation.apply.formatting.drawable.DrawableFileManager
import francisco.visintini.pmaxml.presentation.apply.formatting.drawable.DrawableFormatter
import francisco.visintini.pmaxml.presentation.apply.formatting.layout.LayoutFileManager
import francisco.visintini.pmaxml.presentation.apply.formatting.layout.LayoutFormatter
import java.nio.file.Files
import java.nio.file.Path
import javax.inject.Inject
import kotlin.io.path.isDirectory
import kotlin.io.path.pathString
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

    /** Formats all possible documents (Drawables and Layout files for now). */
    // TODO Add here the option to skip certain directories/files
    fun formatAllDocuments(rootPath: String) {
        try {
            Files.walk(Path.of(rootPath)).use { paths ->
                paths.filter { isSafeDirectory(it) }.forEach {
                    when {
                        drawableFileManager.isDrawableDirectory(it) ->
                            drawableFormatter.formatFiles(drawableFileManager.getDrawableFiles(it))
                        layoutFileManager.isLayoutDirectory(it) -> {
                            layoutFormatter.formatFiles(layoutFileManager.getLayoutFiles(it))
                        }
                    }
                }
            }
        } catch (io: Exception) {
            println("An exception occurred while formating the file")
            io.printStackTrace()
            exitProcess(1)
        }
    }

    private fun isSafeDirectory(path: Path): Boolean {
        return path.isDirectory() &&
            path.pathString.startsWith(BUILD_FOLDER_NAME).not() && // Avoids build folders
            path.pathString.startsWith(GRADLE_FOLDER_NAME).not() && // Avoids gradle folders
            path.pathString.startsWith(IDEA_FOLDER_NAME).not() && // Avoids idea folders
            path.pathString.startsWith(GIT_FOLDER_NAME).not() // Avoids git folders
    }

    private companion object {
        private const val BUILD_FOLDER_NAME = "./build"
        private const val GRADLE_FOLDER_NAME = "./gradle"
        private const val IDEA_FOLDER_NAME = "./idea"
        private const val GIT_FOLDER_NAME = "./git"
    }
}
