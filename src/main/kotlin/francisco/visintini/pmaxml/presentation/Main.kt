package francisco.visintini.pmaxml.presentation

import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.subcommands
import francisco.visintini.pmaxml.presentation.apply.PimpMyAndroidXmlApply
import francisco.visintini.pmaxml.presentation.check.PimpMyAndroidXmlCheck

fun main(args: Array<String>) {
    Main().subcommands(PimpMyAndroidXmlCheck(), PimpMyAndroidXmlApply()).main(args)
}

class Main : NoOpCliktCommand()
