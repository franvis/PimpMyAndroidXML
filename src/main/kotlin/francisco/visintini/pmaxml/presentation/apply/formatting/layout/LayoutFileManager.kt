package francisco.visintini.pmaxml.presentation.apply.formatting.layout

import java.io.File
import java.nio.file.Path

interface LayoutFileManager {

    fun getLayoutFiles(rootDirectory: Path): List<File>

    fun isLayoutDirectory(directory: Path): Boolean
}
