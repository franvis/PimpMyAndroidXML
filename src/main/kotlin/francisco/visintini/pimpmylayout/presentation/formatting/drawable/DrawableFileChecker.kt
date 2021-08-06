package francisco.visintini.pimpmylayout.presentation.formatting.drawable

import francisco.visintini.pimpmylayout.presentation.formatting.FileExtensionChecker
import java.nio.file.Path
import kotlin.io.path.extension
import kotlin.io.path.name

class DrawableFileChecker(private val fileExtensionChecker: FileExtensionChecker) {

    private fun isDrawableFolder(folderName: String): Boolean {
        return LAYOUT_REGULAR_EXPRESSION.toRegex().containsMatchIn(folderName)
    }

    fun isDrawableFile(path: Path) =
        with(path) {
            parent != null &&
                isDrawableFolder(parent.name) &&
                fileExtensionChecker.isXmlFile(extension)
        }

    companion object {
        private const val LAYOUT_REGULAR_EXPRESSION = "drawable.*"
    }
}
