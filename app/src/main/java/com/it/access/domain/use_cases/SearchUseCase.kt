package com.it.access.domain.use_cases

import androidx.core.util.rangeTo
import com.it.access.data.response.ItemResp
import com.it.access.domain.SearchState
import com.it.access.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import java.util.stream.Collectors

class SearchUseCase constructor(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(
        itemsFlow: Flow<List<ItemResp>>,
        eventFlow: Flow<SearchState>
    ) =
        eventFlow
            .combine(itemsFlow) { event, items ->

            delay(800) // fake api delay

            var stream = items.stream()

            event.functions.let { pred ->
                if(pred.isNotEmpty())
                    stream = stream.filter {
                        pred.any { param ->
                            param in it.functions
                        }
                    }
            }

            event.type.let { pred ->
                if(pred.isNotEmpty())
                    stream = stream.filter {
                        pred.any { param ->
                            param == it.type
                        }
                    }
            }

            event.location.let { pred ->
                if(pred.isNotEmpty())
                    stream = stream.filter {
                        pred.any { param ->
                            param == it.location
                        }
                    }
            }

            event.surface.let { pred ->
                if(pred.isNotEmpty())
                    stream = stream.filter {
                        pred.any { param ->
                            com.it.access.domain.check(param, it.surface)
                        }
                    }
            }

            event.power.let { pred ->
                if(pred.isNotEmpty())
                    stream = stream.filter {
                        pred.any { param ->
                            com.it.access.domain.check(param, it.power)
                        }
                    }
            }

            event.element.let { pred ->
                if(pred.isNotEmpty())
                    stream = stream.filter {
                        pred.any { param ->
                            param == it.element
                        }
                    }
            }

            event.price.let { pred ->
                stream = stream.filter {
                    when {
                        pred.from != null && pred.to != null ->
                            it.price in pred.from rangeTo pred.to
                        pred.to != null ->
                            it.price <= pred.to
                        pred.from != null ->
                            it.price >= pred.from
                        else -> true
                    }
                }
            }

            event.speed.let { pred ->
                if(pred.isNotEmpty())
                    stream = stream.filter {
                        pred.any { param ->
                            param.toInt() == it.speed
                        }
                    }
            }

            event.color.let { pred ->
                if(pred.isNotEmpty())
                    stream = stream.filter {
                        pred.any { param ->
                            param == it.color
                        }
                    }
            }

            event.length.let { pred ->
                stream = stream.filter {
                    when {
                        pred.from != null && pred.to != null ->
                            it.length in pred.from rangeTo pred.to
                        pred.to != null ->
                            it.length <= pred.to
                        pred.from != null ->
                            it.length >= pred.from
                        else -> true
                    }
                }
            }

            event.width.let { pred ->
                stream = stream.filter {
                    when {
                        pred.from != null && pred.to != null ->
                            it.width in pred.from rangeTo pred.to
                        pred.to != null ->
                            it.width <= pred.to
                        pred.from != null ->
                            it.width >= pred.from
                        else -> true
                    }
                }
            }

            event.weight.let { pred ->
                stream = stream.filter {
                    when {
                        pred.from != null && pred.to != null ->
                            it.weight in pred.from rangeTo pred.to
                        pred.to != null ->
                            it.weight <= pred.to
                        pred.from != null ->
                            it.weight >= pred.from
                        else -> true
                    }
                }
            }

            event.height.let { pred ->
                stream = stream.filter {
                    when {
                        pred.from != null && pred.to != null ->
                            it.height in pred.from rangeTo pred.to
                        pred.to != null ->
                            it.height <= pred.to
                        pred.from != null ->
                            it.height >= pred.from
                        else -> true
                    }
                }
            }

            Resource.Success(
                stream.collect(Collectors.toList())
            )
        }
        .flowOn(dispatcher)
}