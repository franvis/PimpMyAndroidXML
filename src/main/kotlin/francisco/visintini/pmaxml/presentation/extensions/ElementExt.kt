package francisco.visintini.pmaxml.presentation.extensions

import francisco.visintini.pmaxml.presentation.apply.formatting.utils.AndroidXmlConstants.androidNamespace
import org.jdom2.Element

/**
 * Returns the depth of the element within the xml. This is calculated by iterating backwards
 * through the parent elements starting from [this] as a root
 */
fun Element.depth(): Int {
    var depth = 0
    var root: Element? = this
    while (root != null) {
        depth++
        root = root.parentElement
    }
    return depth
}

/**
 * Returns true if [this] is the last element from the parent element and false otherwise or in the
 * case the parent element is null. This is calculated by iterating backwards through the parent
 * elements starting from [this] as a root
 */
fun Element.isLastElementOfParent(): Boolean {
    return parentElement?.let {
        parentElement
            .children
            .filterIsInstance<Element>()
            .last()
            .getAttributeValue("id", androidNamespace) ==
            this.getAttributeValue("id", androidNamespace)
    }
        ?: false
}

/** Negation method of [isLastElementOfParent]. */
fun Element.isNotLastElementOfParent() = isLastElementOfParent().not()
