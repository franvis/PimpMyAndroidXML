package francisco.visintini.pmaxml.presentation.check

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import java.io.IOException

class PimpMyAndroidXMLCheck : CliktCommand() {
    private val rootPath: String by argument(
        name = "Tag",
        help =
            "The absolute path of the root directory where to start looking at for files to check the code styling ")

    override fun run() {
        try {
            // TODO Make the file checker
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
