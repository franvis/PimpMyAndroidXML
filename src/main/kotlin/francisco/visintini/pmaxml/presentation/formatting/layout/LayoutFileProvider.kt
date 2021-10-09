package francisco.visintini.pmaxml.presentation.formatting.layout

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

class LayoutFileProvider(private val layoutFileChecker: LayoutFileChecker) {

    fun getLayoutFiles(rootPath: String): List<File> {
        val files = mutableListOf<File>()
        Files.walk(Paths.get(rootPath)).use { paths ->
            files.addAll(
                paths.filter { layoutFileChecker.isLayoutFile(it) }.map { it.toFile() }.toList())
        }
        return files
    }
}
