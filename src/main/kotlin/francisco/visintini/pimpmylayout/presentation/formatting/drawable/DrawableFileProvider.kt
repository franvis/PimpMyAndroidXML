package francisco.visintini.pimpmylayout.presentation.formatting.drawable

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

class DrawableFileProvider(private val drawableFileChecker: DrawableFileChecker) {

    fun getDrawableFiles(rootPath: String): List<File> {
        val files = mutableListOf<File>()
        Files.walk(Paths.get(rootPath)).use { paths ->
            files.addAll(
                paths
                    .filter { drawableFileChecker.isDrawableFile(it) }
                    .map { it.toFile() }
                    .toList())
        }
        return files
    }
}
