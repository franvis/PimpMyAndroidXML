package francisco.visintini.pmaxml.presentation.extensions

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> List<*>.checkItemsType() =
    if (all { it is T }) this as List<T>
    else throw ClassCastException("Items should always be of type ${T::class.java}")

fun List<Any>.firstIndexForCondition(start: Int, condition: (obj: Any) -> Boolean): Int {
    for (i in start until size) {
        if (condition.invoke(this[i])) return i
    }
    return size
}
