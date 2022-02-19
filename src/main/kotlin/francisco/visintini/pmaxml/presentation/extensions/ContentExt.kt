package francisco.visintini.pmaxml.presentation.extensions

import org.jdom2.Comment
import org.jdom2.Content
import org.jdom2.Text

/**
 * Returns true if [this] is the last content from the parent element and false otherwise or in the
 * case the parent element is null. This is calculated by comparing [this] to the last [Content] of
 * the parentElement which is not Text as usually Text is used for line breaks
 */
fun Content.isLastContentOfParent(): Boolean {
    return parentElement?.let { parentElement.content.last { it !is Text } == this } ?: false
}

fun Content.isNotLastCommentOfParent() = isLastContentOfParent().not()

fun Content.indexWithinParent() = parentElement?.content?.indexOf(this) ?: -1

fun Content.nextChildrenInParent(): Content? =
    parentElement?.let {
        if (indexWithinParent() == -1) return null

        it.content[indexWithinParent() + 1]
    }

fun Content.childrenInParent(index: Int): Content? =
    if (parentElement != null && parentElement.content.size > index) parentElement.content[index]
    else null

/** Checks that within the next 2 contents of the parent there's no [Comment] element. */
fun Content.nextChildrenIsComment(): Boolean {
    return nextChildrenInParent()?.let {
        val elementAfterNextOne = childrenInParent(indexWithinParent() + 2)
        val parentContainsAtLeast2MoreChildren =
            (parentElement.content.size > indexWithinParent() + 1)

        it is Comment ||
            (parentContainsAtLeast2MoreChildren &&
                it.isNotTextAndEmptyLine() &&
                it.isTextAndOnlyALineBreak() &&
                elementAfterNextOne is Comment)
    }
        ?: false
}

fun Content.nextChildrenIsNotComment() = nextChildrenIsComment().not()

fun Content.isTextAndOnlyALineBreak() = (this as? Text)?.text?.replace(" ", "") == "\n"

fun Content.isTextAndEmptyLine() = (this as? Text)?.text?.replace(" ", "") == "\n\n"

fun Content.isNotTextAndEmptyLine() = isTextAndEmptyLine().not()

fun Content.isLastContentOfDocument() = this == document.content.last()
