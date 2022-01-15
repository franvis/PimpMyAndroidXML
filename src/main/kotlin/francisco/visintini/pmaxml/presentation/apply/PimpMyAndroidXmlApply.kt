package francisco.visintini.pmaxml.presentation.apply

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import francisco.visintini.pmaxml.presentation.apply.formatting.*
import java.io.IOException
import javax.inject.Inject

class PimpMyAndroidXmlApply
@Inject
constructor(private val allDocumentsFormatter: AllDocumentsFormatter) : CliktCommand() {
    private val rootPath: String by argument(
        name = "files to format",
        help =
            "The absolute path of the root directory where to start looking at for files to apply the code styling ")

    override fun run() {
        try {
            allDocumentsFormatter.formatAllDocuments(rootPath)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
