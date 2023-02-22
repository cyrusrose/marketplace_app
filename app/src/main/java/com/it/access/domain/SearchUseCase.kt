package com.it.access.domain

import androidx.core.util.rangeTo
import com.it.access.data.repository.ItemRepository
import com.it.access.data.response.ItemResp
import com.it.access.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import java.util.stream.Collectors

class SearchUseCase constructor(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(items: Flow<List<ItemResp>>, event: Flow<SearchState>) =
        combine(items, event) { items, event ->
            var stream = items.stream()

            event.isHeatingProtected?.let { pred ->
                stream = stream.filter { it.isHeatingProtected == pred }
            }

            event.isRemote?.let { pred ->
                stream = stream.filter { it.isRemote == pred }
            }

            event.isRolloverProtected?.let { pred ->
                stream = stream.filter { it.isRolloverProtected == pred }
            }

            event.type?.let { pred ->
                stream = stream.filter {
                    pred.any { param ->
                        param == it.type
                    }
                }
            }

            event.location?.let { pred ->
                if(pred.isNotEmpty())
                    stream = stream.filter {
                        pred.any { param ->
                            param == it.location
                        }
                    }
            }

            event.surface?.let { pred ->
                if(pred.isNotEmpty())
                    stream = stream.filter {
                        pred.any { param ->
                            check(param, it.surface)
                        }
                    }
            }

            event.power?.let { pred ->
                if(pred.isNotEmpty())
                    stream = stream.filter {
                        pred.any { param ->
                            check(param, it.power)
                        }
                    }
            }

            event.element?.let { pred ->
                if(pred.isNotEmpty())
                    stream = stream.filter {
                        pred.any { param ->
                            param == it.element
                        }
                    }
            }

            event.price?.let { pred ->
                stream = stream.filter {
                    it.price in pred.from rangeTo pred.to
                }
            }

            event.speed?.let { pred ->
                if(pred.isNotEmpty())
                    stream = stream.filter {
                        pred.any { param ->
                            param == it.speed.toString()
                        }
                    }
            }

            event.color?.let { pred ->
                if(pred.isNotEmpty())
                    stream = stream.filter {
                        pred.any { param ->
                            param == it.color
                        }
                    }
            }

            event.length?.let { pred ->
                stream = stream.filter {
                    it.length in pred.from rangeTo pred.to
                }
            }

            event.width?.let { pred ->
                stream = stream.filter {
                    it.width in pred.from rangeTo pred.to
                }
            }

            event.weight?.let { pred ->
                stream = stream.filter {
                    it.weight in pred.from rangeTo pred.to
                }
            }

            event.height?.let { pred ->
                stream = stream.filter {
                    it.height in pred.from rangeTo pred.to
                }
            }

            Resource.Success(
                stream.collect(Collectors.toList())
            )
        }
        .flowOn(dispatcher)
}