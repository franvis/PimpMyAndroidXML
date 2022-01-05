package francisco.visintini.pmaxml.presentation.formatting.di

import dagger.Binds
import dagger.Module
import francisco.visintini.pmaxml.presentation.formatting.drawable.DrawableFileManager
import francisco.visintini.pmaxml.presentation.formatting.drawable.DrawableFileManagerImpl
import francisco.visintini.pmaxml.presentation.formatting.layout.*

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
