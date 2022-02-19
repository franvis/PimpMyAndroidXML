package francisco.visintini.pmaxml.presentation.apply.formatting.drawable

import francisco.visintini.pmaxml.presentation.apply.formatting.utils.FileExtensionChecker
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import javax.inject.Inject
import kotlin.io.path.isDirectory
import kotlin.io.path.name
import kotlin.streams.toList

class DrawableFileManagerImpl
@Inject
constructor(private val fileExtensionChecker: FileExtensionChecker) : DrawableFileManager {

    override fun getDrawableFiles(drawableDirectory: Path): List<File> {
        Files.walk(drawableDirectory).use { paths ->
            return paths.filter { isDrawableFile(it) }.map { it.toFile() }.toList()
        }
    }

    override fun isDrawableDirectory(directory: Path) =
        with(directory) {
            isDirectory() && DRAWABLE_REGULAR_EXPRESSION.toRegex().containsMatchIn(name)
        }

    private fun isDrawableFile(path: Path) =
        with(path) {
            parent != null && isDrawableDirectory(parent) && fileExtensionChecker.isXmlFile(path)
        }

    private companion object {
        private const val DRAWABLE_REGULAR_EXPRESSION = "drawable.*"
    }
}
