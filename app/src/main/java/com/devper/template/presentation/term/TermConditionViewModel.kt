package com.devper.template.presentation.term

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devper.template.core.platform.SingleLiveEvent
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.termcondition.TermCondition
import com.devper.template.domain.usecase.termcondition.GetTermConditionUseCase
import com.devper.template.presentation.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TermConditionViewModel @ViewModelInject constructor(
    private val getTermConditionUseCase: GetTermConditionUseCase
) : BaseViewModel() {

     val resultLiveData = SingleLiveEvent<ResultState<TermCondition>>()
     val termCondition = MutableLiveData<TermCondition>()

    fun getTermCondition() {
        getTermConditionUseCase(Unit).onEach {
            resultLiveData.value = it
        }.launchIn(viewModelScope)
    }

    fun setTermCondition(it:TermCondition){
        termCondition.value =it
    }
}
