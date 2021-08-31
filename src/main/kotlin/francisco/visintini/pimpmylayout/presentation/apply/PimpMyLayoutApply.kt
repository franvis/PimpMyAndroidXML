package francisco.visintini.pimpmylayout.presentation.apply

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import francisco.visintini.pimpmylayout.presentation.formatting.*
import francisco.visintini.pimpmylayout.presentation.formatting.drawable.AndroidDrawableFormatter
import francisco.visintini.pimpmylayout.presentation.formatting.drawable.AndroidDrawableOutputProcessor
import francisco.visintini.pimpmylayout.presentation.formatting.drawable.DrawableFileChecker
import francisco.visintini.pimpmylayout.presentation.formatting.drawable.DrawableFileProvider
import francisco.visintini.pimpmylayout.presentation.formatting.layout.AndroidLayoutFormatter
import francisco.visintini.pimpmylayout.presentation.formatting.layout.AndroidLayoutOutputProcessor
import francisco.visintini.pimpmylayout.presentation.formatting.layout.LayoutFileChecker
import francisco.visintini.pimpmylayout.presentation.formatting.layout.LayoutFileProvider
import java.io.IOException

class PimpMyLayoutApply : CliktCommand() {
    private val rootPath: String by argument(
        name = "files to format",
        help =
        "The absolute path of the root directory where to start looking at for files to apply the code styling "
    )

    override fun run() {
        try {
            AndroidXmlFormatter(
                AndroidLayoutFormatter(
                    LayoutFileProvider(LayoutFileChecker(FileExtensionChecker())),
                    AndroidLayoutOutputProcessor(AttributeComparator(), ElementContentAnalyzer())
                ), AndroidDrawableFormatter(
                    DrawableFileProvider(DrawableFileChecker(FileExtensionChecker())),
                    AndroidDrawableOutputProcessor(AttributeComparator(), ElementContentAnalyzer())
                )
            )
                .formatDocuments(rootPath)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
