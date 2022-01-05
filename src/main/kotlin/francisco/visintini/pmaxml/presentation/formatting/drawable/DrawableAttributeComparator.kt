package francisco.visintini.pmaxml.presentation.formatting.drawable

import org.jdom2.Attribute
import javax.inject.Inject

class DrawableAttributeComparator @Inject constructor() {

    fun sortAttributes(attributes: List<Attribute>): List<Attribute> {
        val checkedAttributes = attributes.toMutableList()

        checkedAttributes.sortWith { a1, a2 ->
            if (a1.namespacePrefix != a2.namespacePrefix) {
                if (DrawableFormattingConfig.ORDER_NAMESPACES_ALPHABETICALLY) {
                    return@sortWith a1.namespacePrefix.compareTo(a2.namespacePrefix)
                }
            }
            DrawableFormattingConfig.ATTRIBUTES_NAME_ORDER.forEach { name ->
                if (a1.name == name) {
                    return@sortWith -1
                } else if (a2.name == name) {
                    return@sortWith 1
                }
            }

            if (DrawableFormattingConfig.ORDER_ATTRIBUTES_ALPHABETICALLY) {
                return@sortWith a1.name.compareTo(a2.name)
            } else {
                return@sortWith 0
            }
        }

        return checkedAttributes
    }
}
