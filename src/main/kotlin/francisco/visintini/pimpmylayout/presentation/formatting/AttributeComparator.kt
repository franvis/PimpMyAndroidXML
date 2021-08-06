package francisco.visintini.pimpmylayout.presentation.formatting

import francisco.visintini.pimpmylayout.presentation.formatting.layout.AndroidLayoutFormattingConfig
import org.jdom2.Attribute

class AttributeComparator {

    fun sortAttributes(attributes: List<Attribute>): List<Attribute> {
        val checkedAttributes = attributes.toMutableList()

        checkedAttributes.sortWith { a1, a2 ->
            if (a1.namespacePrefix != a2.namespacePrefix) {
                if (a1.name == "style") {
                    return@sortWith if (a2.namespacePrefix == "app") -1 else 1
                }
                AndroidLayoutFormattingConfig.NAMESPACE_ORDER.forEach { namespace ->
                    if (a1.namespacePrefix == namespace) {
                        return@sortWith -1
                    } else if (a2.namespacePrefix == namespace) {
                        return@sortWith 1
                    }
                }
                if (AndroidLayoutFormattingConfig.ORDER_NAMESPACES_ALPHABETICALLY) {
                    return@sortWith a1.namespacePrefix.compareTo(a2.namespacePrefix)
                }
            }
            AndroidLayoutFormattingConfig.ATTRIBUTES_NAME_ORDER.forEach { name ->
                if (a1.name == name) {
                    return@sortWith -1
                } else if (a2.name == name) {
                    return@sortWith 1
                }
            }

            if (AndroidLayoutFormattingConfig.ORDER_ATTRIBUTES_ALPHABETICALLY) {
                return@sortWith a1.name.compareTo(a2.name)
            } else {
                return@sortWith 0
            }
        }

        return checkedAttributes
    }
}
