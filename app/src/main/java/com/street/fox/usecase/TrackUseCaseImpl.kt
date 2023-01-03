package com.street.fox.usecase

import com.soywiz.klock.DateTime
import com.soywiz.klock.milliseconds
import com.street.fox.StateData
import com.street.fox.extensions.mapData
import com.street.fox.repository.TrackRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class TrackUseCaseImpl(private val trackRepository: TrackRepository) : TrackUseCase {
    override fun getRecentlyPlayed(activityViewData: ActivityViewData): Flow<StateData<RecentlyPlayedViewData>> =
        trackRepository.getRecentlyPlayed(activityViewData.endDate.local).mapData {
            val tracks = it.items
                .map {
                    RecentlyPlayedViewData.Track(
                        it.track.name,
                        it.track.durationMs,
                        DateTime.parse(it.playedAt)
                    )
                }

            RecentlyPlayedViewData(
                tracks
                    .filterIndexed { index, item ->
                        val next = tracks.getOrNull(index - 1)

                        val startedBeforeActivity = item.playedAt < activityViewData.startDate &&
                            (item.playedAt + item.durationMs.milliseconds) >= activityViewData.startDate &&
                            next != null &&
                            next.playedAt > activityViewData.startDate

                        val startedDuringActivity = item.playedAt >= activityViewData.startDate
                            && item.playedAt <= activityViewData.endDate

                        startedBeforeActivity || startedDuringActivity
                    }
            )
        }
}
