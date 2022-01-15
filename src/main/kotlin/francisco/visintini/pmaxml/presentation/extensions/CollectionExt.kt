package francisco.visintini.pmaxml.presentation.extensions

/** Negation method of [isNullOrEmpty] */
fun <T> Collection<T>.isNotNullNorEmpty() = isNullOrEmpty().not()
