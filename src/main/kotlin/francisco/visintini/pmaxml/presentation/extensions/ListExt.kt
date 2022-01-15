package francisco.visintini.pmaxml.presentation.extensions

/**
 * Checks that all the items in a List<*> are from the type [T] and if so, returns the List casted
 * as List<T>. If not, it throws the corresponding class cast exception
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> List<*>.checkItemsType() =
    if (all { it is T }) this as List<T>
    else throw ClassCastException("Items should always be of type ${T::class.java}")

/**
 * Returns the index of the first T element matching the given predicate and starting to search from
 * the [startIndex], or the size of the list if the list does not contain such element.
 */
fun <T> List<T>.indexOfFirst(start: Int, condition: (obj: T) -> Boolean): Int {
    for (i in start until size) {
        if (condition.invoke(this[i])) return i
    }
    return size
}
