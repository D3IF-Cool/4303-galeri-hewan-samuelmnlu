package org.d3if4003.galerihewan.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.d3if4003.galerihewan.Hewan
import org.d3if4003.galerihewan.network.HewanApiService

class MainViewModel : ViewModel() {

    private val data = MutableLiveData<List<Hewan>>()
    private val status = MutableLiveData<HewanApiService.HewanApi.ApiStatus>()

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch {
            status.value = HewanApiService.HewanApi.ApiStatus.LOADING
            try {
                data.value = HewanApiService.HewanApi.service.getHewan()
                status.value = HewanApiService.HewanApi.ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.value = HewanApiService.HewanApi.ApiStatus.FAILED
            }
        }
    }

    fun getData(): LiveData<List<Hewan>> = data

    fun getStatus(): LiveData<HewanApiService.HewanApi.ApiStatus> = status
}