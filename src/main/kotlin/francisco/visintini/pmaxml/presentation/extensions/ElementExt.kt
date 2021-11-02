package francisco.visintini.pmaxml.presentation.extensions

import francisco.visintini.pmaxml.presentation.formatting.AndroidXmlConstants.androidNamespace
import org.jdom2.Element
import org.jdom2.Namespace

fun Element.depth(): Int {
    var depth = 0
    var root: Element? = this
    while (root != null) {
        depth++
        root = root.parentElement
    }
    return depth
}

fun Element.isLastElementOfParent(): Boolean {
    return parentElement?.let {
        parentElement.children.filterIsInstance<Element>().last()
            .getAttributeValue("id", androidNamespace) == this.getAttributeValue("id", androidNamespace)
    } ?: false
}

fun Element.isNotLastElementOfParent(): Boolean = isLastElementOfParent().not()
