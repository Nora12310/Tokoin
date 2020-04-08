package vn.exmaple.tokoin.module

import org.akd.muxic.data.local.AppDatabase
import org.akd.support.adapter.lists.FlexibleAdapter
import org.akd.support.adapter.lists.PageListAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vn.exmaple.tokoin.data.remote.NewsAPIGenerator
import vn.exmaple.tokoin.data.remote.INewsRepository
import vn.exmaple.tokoin.data.remote.NewsRepositoryImpl
import vn.exmaple.tokoin.dialog.AddProfileDialogViewModel
import vn.exmaple.tokoin.ui.filterable.FilterableViewModel
import vn.exmaple.tokoin.ui.home.HomeViewModel
import vn.exmaple.tokoin.ui.profile.ProfileViewModel

val appModule = module {
    single { NewsAPIGenerator(androidContext()) }
    single<INewsRepository> { NewsRepositoryImpl(get()) }
    single { AppDatabase.getInstance(androidContext()) }

    factory { FlexibleAdapter() }
    factory { PageListAdapter() }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { FilterableViewModel(get(), get(), get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { AddProfileDialogViewModel(get(), get()) }
}