package francisco.visintini.pimpmylayout.presentation.formatting

class FileExtensionChecker {

    fun isXmlFile(fileExtension: String) = fileExtension == XML_FILE_EXTENSION

    companion object {
        private const val XML_FILE_EXTENSION = "xml"
    }
}
