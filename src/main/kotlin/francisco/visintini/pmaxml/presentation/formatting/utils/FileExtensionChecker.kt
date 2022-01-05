package francisco.visintini.pmaxml.presentation.formatting.utils

import javax.inject.Inject

class FileExtensionChecker @Inject constructor() {

    fun isXmlFile(fileExtension: String) = fileExtension == XML_FILE_EXTENSION

    companion object {
        private const val XML_FILE_EXTENSION = "xml"
    }
}
