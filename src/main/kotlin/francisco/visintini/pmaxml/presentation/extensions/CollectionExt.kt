package francisco.visintini.pmaxml.presentation.extensions

fun <T> Collection<T>.isNotNullNorEmpty() = !this.isNullOrEmpty()
