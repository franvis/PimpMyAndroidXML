package francisco.visintini.pmaxml.presentation.di

import dagger.Component
import francisco.visintini.pmaxml.presentation.Main
import francisco.visintini.pmaxml.presentation.formatting.di.FormattingModule

@Component(modules = [FormattingModule::class])
interface PimpMyAndroidXml {
    fun createMain(): Main
}
