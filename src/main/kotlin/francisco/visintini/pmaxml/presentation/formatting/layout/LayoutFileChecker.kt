package francisco.visintini.pmaxml.presentation.formatting.layout

import francisco.visintini.pmaxml.presentation.formatting.FileExtensionChecker
import java.nio.file.Path
import kotlin.io.path.extension
import kotlin.io.path.isDirectory
import kotlin.io.path.name

class LayoutFileChecker(private val fileExtensionChecker: FileExtensionChecker) {

    private fun isLayoutFolder(path: Path): Boolean {
        return with(path) {
            isDirectory() && LAYOUT_REGULAR_EXPRESSION.toRegex().containsMatchIn(name)
        }
    }

    fun isLayoutFile(path: Path) =
        with(path) {
            parent != null && isLayoutFolder(parent) && fileExtensionChecker.isXmlFile(extension)
        }

    companion object {
        private const val LAYOUT_REGULAR_EXPRESSION = "layout.*"
    }
}
