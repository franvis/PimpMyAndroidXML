package francisco.visintini.pmaxml.presentation

import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.subcommands
import francisco.visintini.pmaxml.presentation.apply.PimpMyAndroidXMLApply
import francisco.visintini.pmaxml.presentation.check.PimpMyAndroidXMLCheck

fun main(args: Array<String>) {
    Main().subcommands(PimpMyAndroidXMLCheck(), PimpMyAndroidXMLApply()).main(args)
}

class Main : NoOpCliktCommand()
