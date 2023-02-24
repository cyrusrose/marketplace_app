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