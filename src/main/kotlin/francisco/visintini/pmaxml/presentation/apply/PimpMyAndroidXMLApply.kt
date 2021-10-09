package francisco.visintini.pmaxml.presentation.apply

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import francisco.visintini.pmaxml.presentation.formatting.*
import francisco.visintini.pmaxml.presentation.formatting.drawable.*
import francisco.visintini.pmaxml.presentation.formatting.layout.*
import java.io.IOException

class PimpMyAndroidXMLApply : CliktCommand() {
    private val rootPath: String by argument(
        name = "files to format",
        help =
        "The absolute path of the root directory where to start looking at for files to apply the code styling "
    )

    override fun run() {
        try {
            AndroidXmlFormatter(
                LayoutFormatter(
                    LayoutFileProvider(LayoutFileChecker(FileExtensionChecker())),
                    LayoutOutputProcessor(LayoutAttributeComparator(), ElementContentAnalyzer())
                ), DrawableFormatter(
                    DrawableFileProvider(DrawableFileChecker(FileExtensionChecker())),
                    DrawableOutputProcessor(DrawableAttributeComparator(), ElementContentAnalyzer())
                )
            )
                .formatDocuments(rootPath)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
