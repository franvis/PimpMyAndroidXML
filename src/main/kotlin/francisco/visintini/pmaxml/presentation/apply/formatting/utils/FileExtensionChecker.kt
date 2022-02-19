package francisco.visintini.pmaxml.presentation.apply.formatting.utils

import java.nio.file.Path
import javax.inject.Inject
import kotlin.io.path.extension

/** Class in charge of checking file extensions. */
class FileExtensionChecker @Inject constructor() {

    /**
     * Returns true if the extension from the [filePath] is [XML_FILE_EXTENSION], false otherwise.
     */
    fun isXmlFile(filePath: Path) = filePath.extension == XML_FILE_EXTENSION

    private companion object {
        private const val XML_FILE_EXTENSION = "xml"
    }
}
