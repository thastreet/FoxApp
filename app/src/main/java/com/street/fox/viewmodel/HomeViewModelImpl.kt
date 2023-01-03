package com.street.fox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.street.fox.StateData
import com.street.fox.extensions.data
import com.street.fox.usecase.ActivityUseCase
import com.street.fox.usecase.ActivityViewData
import com.street.fox.usecase.HomeUseCase
import com.street.fox.usecase.HomeViewData
import com.street.fox.usecase.TrackUseCase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModelImpl(
    homeUseCase: HomeUseCase,
    private val trackUseCase: TrackUseCase,
    private val activityUseCase: ActivityUseCase
) : ViewModel(), HomeViewModel {
    private val internalViewData = MutableStateFlow(HomeViewData("", "", emptyList()))
    override val viewData: StateFlow<HomeViewData> = internalViewData

    init {
        viewModelScope.launch {
            homeUseCase.viewData.data().collect {
                internalViewData.value = it
            }
        }
    }

    override fun onActivityTapped(activityViewData: ActivityViewData) {
        viewModelScope.launch {
            launch {
                trackUseCase.getRecentlyPlayed(activityViewData)
                    .filter { it is StateData.Data }
                    .collectLatest {
                        val tracks = it.value?.tracks

                        if (!tracks.isNullOrEmpty()) {
                            val description = "\n\uD83C\uDFB5 Spotify Playlist \uD83C\uDFB5\n\n" + tracks.joinToString("\n") {
                                it.name + " - " + it.artist
                            }

                            activityUseCase.updateActivity(activityViewData.id, description)
                                .filter { it is StateData.Data }
                                .collectLatest {
                                    cancel()
                                }
                        } else {
                            cancel()
                        }
                    }
            }
        }
    }
}
