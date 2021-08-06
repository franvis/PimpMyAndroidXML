package francisco.visintini.pimpmylayout.presentation.extensions

fun <T> Collection<T>.isNotNullNorEmpty() = !this.isNullOrEmpty()
