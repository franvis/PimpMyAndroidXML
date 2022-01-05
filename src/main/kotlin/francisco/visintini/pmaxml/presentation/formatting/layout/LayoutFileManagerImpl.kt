package francisco.visintini.pmaxml.presentation.formatting.layout

import francisco.visintini.pmaxml.presentation.formatting.utils.FileExtensionChecker
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import javax.inject.Inject
import kotlin.io.path.extension
import kotlin.io.path.isDirectory
import kotlin.io.path.name
import kotlin.streams.toList

class LayoutFileManagerImpl
@Inject
constructor(private val fileExtensionChecker: FileExtensionChecker) : LayoutFileManager {

    override fun getLayoutFiles(rootDirectory: Path): List<File> {
        if (isLayoutDirectory(rootDirectory)) {
            Files.walk(rootDirectory).use { paths ->
                return paths.filter { isLayoutFile(it) }.map { it.toFile() }.toList()
            }
        } else {
            return emptyList()
        }
    }

    private fun isLayoutDirectory(directory: Path): Boolean {
        return with(directory) {
            isDirectory() && LAYOUT_REGULAR_EXPRESSION.toRegex().containsMatchIn(name)
        }
    }

    private fun isLayoutFile(path: Path) =
        with(path) {
            parent != null && isLayoutDirectory(parent) && fileExtensionChecker.isXmlFile(extension)
        }

    private companion object {
        private const val LAYOUT_REGULAR_EXPRESSION = "layout.*"
    }
}
