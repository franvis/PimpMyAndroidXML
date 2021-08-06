package francisco.visintini.pimpmylayout.presentation

import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.subcommands
import francisco.visintini.pimpmylayout.presentation.apply.PimpMyLayoutApply
import francisco.visintini.pimpmylayout.presentation.check.PimpMyLayoutCheck
import francisco.visintini.pimpmylayout.presentation.formatting.*

fun main(args: Array<String>) {
    Main().subcommands(PimpMyLayoutCheck(), PimpMyLayoutApply()).main(args)
}

class Main : NoOpCliktCommand()
