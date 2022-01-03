package com.kiwi.data.repositories

import com.kiwi.data.di.IoDispatcher
import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher

@Reusable
class KiwiRepository @Inject constructor(
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
)
