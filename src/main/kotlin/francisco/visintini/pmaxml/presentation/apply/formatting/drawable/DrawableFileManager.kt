package francisco.visintini.pmaxml.presentation.apply.formatting.drawable

import java.io.File
import java.nio.file.Path

interface DrawableFileManager {

    fun getDrawableFiles(drawableDirectory: Path): List<File>

    fun isDrawableDirectory(directory: Path): Boolean
}
