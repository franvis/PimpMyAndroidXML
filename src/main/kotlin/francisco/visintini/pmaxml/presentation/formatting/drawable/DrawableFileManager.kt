package francisco.visintini.pmaxml.presentation.formatting.drawable

import java.io.File
import java.nio.file.Path

interface DrawableFileManager {

    fun getDrawableFiles(rootDirectory: Path): List<File>
}