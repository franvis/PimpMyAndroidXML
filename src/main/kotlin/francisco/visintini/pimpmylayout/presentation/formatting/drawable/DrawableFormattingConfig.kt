package francisco.visintini.pimpmylayout.presentation.formatting.drawable

import francisco.visintini.pimpmylayout.presentation.formatting.AndroidXmlConstants

internal object DrawableFormattingConfig {
    /**
     * Specifies the space indention for an attribute within an element. Should be always bigger
     * than 0.
     */
    const val ATTRIBUTE_INDENTION = 2

    /**
     * Specifies the space indention for an element within the xml file. Should be always bigger
     * than 0.
     */
    private const val ELEMENT_INDENTION = 2

    /** Specifies if we want our namespaces to be order alphabetically */
    const val ORDER_NAMESPACES_ALPHABETICALLY = true
    /** Specifies if we want our attributes to be order alphabetically */
    const val ORDER_ATTRIBUTES_ALPHABETICALLY = true

    /** Provides the space indention for an attribute within an element as a String */
    val INDENT_SPACE = AndroidXmlConstants.EMPTY_SPACE.repeat(ELEMENT_INDENTION)

    /**
     * Provides the order in which we want to organise our attributes namespaces within elements as
     * an ordered array. "" is meant for attribute that don't have a namespace such as style
     */
    val NAMESPACE_ORDER = arrayOf("android", "", "app", "tools")

    /**
     * Provides the order in which we want to organise our attributes names within elements as an
     * ordered array
     */
    val ATTRIBUTES_NAME_ORDER = arrayOf("width", "height")
}
