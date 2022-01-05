package francisco.visintini.pmaxml.presentation.formatting

import org.jdom2.output.support.AbstractXMLOutputProcessor
import java.io.File

abstract class FileFormatter {

    abstract fun formatFiles(files: List<File>)
}