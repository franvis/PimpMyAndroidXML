package francisco.visintini.pmaxml.presentation

import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.subcommands
import francisco.visintini.pmaxml.presentation.apply.PimpMyAndroidXmlApply
import francisco.visintini.pmaxml.presentation.check.PimpMyAndroidXmlCheck
import francisco.visintini.pmaxml.presentation.di.DaggerPimpMyAndroidXml
import javax.inject.Inject

fun main(args: Array<String>) {
    DaggerPimpMyAndroidXml.builder().build().createMain().main(args)
}

class Main
@Inject
constructor(
    pimpMyAndroidXmlCheck: PimpMyAndroidXmlCheck,
    pimpMyAndroidXmlApply: PimpMyAndroidXmlApply
) : NoOpCliktCommand() {
    init {
        subcommands(pimpMyAndroidXmlCheck, pimpMyAndroidXmlApply)
    }
}
