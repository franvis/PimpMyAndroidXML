package francisco.visintini.pmaxml.presentation.apply.formatting.layout

import javax.inject.Inject
import org.jdom2.Attribute

class LayoutAttributeComparator @Inject constructor() {

    fun sortAttributes(attributes: List<Attribute>): List<Attribute> {
        val checkedAttributes = attributes.toMutableList()

        checkedAttributes.sortWith { a1, a2 ->
            if (a1.namespacePrefix != a2.namespacePrefix) {
                LayoutFormattingConfig.NAMESPACE_ORDER.forEach { namespace ->
                    if (a1.namespacePrefix == namespace) {
                        return@sortWith -1
                    } else if (a2.namespacePrefix == namespace) {
                        return@sortWith 1
                    }
                }
                if (LayoutFormattingConfig.ORDER_NAMESPACES_ALPHABETICALLY) {
                    return@sortWith a1.namespacePrefix.compareTo(a2.namespacePrefix)
                }
            }
            LayoutFormattingConfig.ATTRIBUTES_NAME_ORDER.forEach { name ->
                if (a1.name == name) {
                    return@sortWith -1
                } else if (a2.name == name) {
                    return@sortWith 1
                }
            }

            if (LayoutFormattingConfig.ORDER_ATTRIBUTES_ALPHABETICALLY) {
                return@sortWith a1.name.compareTo(a2.name)
            } else {
                return@sortWith 0
            }
        }

        return checkedAttributes
    }
}
