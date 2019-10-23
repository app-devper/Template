package com.devper.template.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.user.User
import com.devper.template.domain.usecase.device.RegisterDeviceUseCase
import com.devper.template.domain.usecase.user.GetCurrentUserUseCase

class MainViewModel(
    private val getCurrentUseCase: GetCurrentUserUseCase,
    private val registerDeviceUseCase: RegisterDeviceUseCase
) : ViewModel() {

    private val result: MutableLiveData<ResultState<String>> = MutableLiveData()
    private var _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> = _user

    var badge: MutableLiveData<String> = MutableLiveData()

    private fun getCurrentUser() {
        getCurrentUseCase.execute(null) {
            onComplete { _user.value = it }
        }
    }

    fun registerDevice() {
        registerDeviceUseCase.execute(null) {
            onStart { result.value = ResultState.Loading() }
            onComplete {
                result.value = ResultState.Success(it)
                getCurrentUser()
            }
            onError { result.value = ResultState.Error(it) }
        }
    }

    fun getUser(): User? {
        return _user.value
    }

    fun setUser(user: User) {
        _user.value = user
    }

    override fun onCleared() {
        super.onCleared()
        getCurrentUseCase.unsubscribe()
        registerDeviceUseCase.unsubscribe()
    }
}
