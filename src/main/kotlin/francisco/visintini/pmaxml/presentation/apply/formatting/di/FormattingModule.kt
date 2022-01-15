package francisco.visintini.pmaxml.presentation.apply.formatting.di

import dagger.Binds
import dagger.Module
import francisco.visintini.pmaxml.presentation.apply.formatting.drawable.DrawableFileManager
import francisco.visintini.pmaxml.presentation.apply.formatting.drawable.DrawableFileManagerImpl
import francisco.visintini.pmaxml.presentation.apply.formatting.layout.*

@Module
abstract class FormattingModule {

    @Binds
    abstract fun bindDrawableFileManager(
        drawableFileManagerImpl: DrawableFileManagerImpl
    ): DrawableFileManager

    @Binds
    abstract fun bindLayoutFileManager(
        layoutFileManagerImpl: LayoutFileManagerImpl
    ): LayoutFileManager
}
