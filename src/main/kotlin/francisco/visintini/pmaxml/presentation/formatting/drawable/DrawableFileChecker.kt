package francisco.visintini.pmaxml.presentation.formatting.drawable

import francisco.visintini.pmaxml.presentation.formatting.FileExtensionChecker
import java.nio.file.Path
import kotlin.io.path.extension
import kotlin.io.path.name

class DrawableFileChecker(private val fileExtensionChecker: FileExtensionChecker) {

    private fun isDrawableFolder(folderName: String): Boolean {
        return DRAWABLE_REGULAR_EXPRESSION.toRegex().containsMatchIn(folderName)
    }

    fun isDrawableFile(path: Path) =
        with(path) {
            parent != null &&
                isDrawableFolder(parent.name) &&
                fileExtensionChecker.isXmlFile(extension)
        }

    companion object {
        private const val DRAWABLE_REGULAR_EXPRESSION = "drawable.*"
    }
}
