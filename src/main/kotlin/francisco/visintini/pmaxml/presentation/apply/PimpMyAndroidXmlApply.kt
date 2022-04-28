package francisco.visintini.pmaxml.presentation.apply

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.choice
import francisco.visintini.pmaxml.presentation.apply.formatting.*
import javax.inject.Inject

class PimpMyAndroidXmlApply @Inject constructor(private val filesFormatter: FilesFormatter) :
    CliktCommand() {
    private val fileType: String by option()
        .choice(choices = FileType.values().map { it.name }.toTypedArray())
        .default(FileType.ALL.name)
    private val rootPath: String by argument(
            name = "files to format",
            help =
                "The absolute path of the root directory where to start looking at for files to apply the code styling. By " +
                    "default it will use the current directory where is being run.")
        .default(".")

    override fun run() {
        filesFormatter.formatFiles(rootPath, FileType.valueOf(fileType))
    }

    enum class FileType {
        ALL,
        DRAWABLE,
        LAYOUT
    }
}
