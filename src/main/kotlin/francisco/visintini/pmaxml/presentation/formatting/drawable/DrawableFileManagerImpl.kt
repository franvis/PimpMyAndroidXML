package francisco.visintini.pmaxml.presentation.formatting.drawable

import francisco.visintini.pmaxml.presentation.formatting.utils.FileExtensionChecker
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import javax.inject.Inject
import kotlin.io.path.extension
import kotlin.io.path.isDirectory
import kotlin.io.path.name
import kotlin.streams.toList

class DrawableFileManagerImpl
@Inject
constructor(private val fileExtensionChecker: FileExtensionChecker) : DrawableFileManager {

    override fun getDrawableFiles(rootDirectory: Path): List<File> {
        if (isDrawableDirectory(rootDirectory)) {
            Files.walk(rootDirectory).use { paths ->
                return paths.filter { isDrawableFile(it) }.map { it.toFile() }.toList()
            }
        } else {
            return emptyList()
        }
    }

    private fun isDrawableDirectory(directory: Path) =
        with(directory) {
            isDirectory() && DRAWABLE_REGULAR_EXPRESSION.toRegex().containsMatchIn(name)
        }

    private fun isDrawableFile(path: Path) =
        with(path) {
            parent != null &&
                isDrawableDirectory(parent) &&
                fileExtensionChecker.isXmlFile(extension)
        }

    private companion object {
        private const val DRAWABLE_REGULAR_EXPRESSION = "drawable.*"
    }
}
