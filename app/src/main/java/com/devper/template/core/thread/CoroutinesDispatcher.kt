package com.devper.template.core.thread

import com.devper.template.domain.core.thread.CoroutineThreadDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CoroutinesDispatcher : CoroutineThreadDispatcher {

    override fun ui(): CoroutineDispatcher = Dispatchers.Main

    override fun compute(): CoroutineDispatcher = Dispatchers.Default

    override fun io(): CoroutineDispatcher = Dispatchers.IO
}