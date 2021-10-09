package francisco.visintini.pmaxml.presentation.extensions

import org.jdom2.Element

fun Element.depth(): Int {
    var depth = 0
    var root: Element? = this
    while (root != null) {
        depth++
        root = root.parentElement
    }
    return depth
}
