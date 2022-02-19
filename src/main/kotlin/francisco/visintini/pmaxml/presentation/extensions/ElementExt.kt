package francisco.visintini.pmaxml.presentation.extensions

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

fun Element.isNotRootElement() = isRootElement.not()
