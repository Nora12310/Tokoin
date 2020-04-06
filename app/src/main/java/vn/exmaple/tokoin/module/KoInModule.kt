package vn.exmaple.tokoin.module

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vn.exmaple.tokoin.data.remote.NewsAPIGenerator
import vn.exmaple.tokoin.data.remote.INewsRepository
import vn.exmaple.tokoin.data.remote.NewsRepositoryImpl
import vn.exmaple.tokoin.ui.home.HomeViewModel

val appModule = module {
    single { NewsAPIGenerator(androidContext()) }
    single<INewsRepository> { NewsRepositoryImpl(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
}