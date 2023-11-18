package com.example.simpletodoapp.di.modules.serializables

import com.example.simpletodoapp.data.dataStore.model.ToDo
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Suppress("EXTERNAL_SERIALIZER_USELESS")
@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = PersistentList::class)
class ToDoPersistantListSerializer(
    private val serializer: KSerializer<ToDo>,
) : KSerializer<PersistentList<ToDo>> {

    private class PersistentListDescriptor :
        SerialDescriptor by serialDescriptor<List<ToDo>>() {
        @ExperimentalSerializationApi
        override val serialName: String = "kotlinx.serialization.immutable.persistentList"
    }

    override val descriptor: SerialDescriptor = PersistentListDescriptor()

    override fun serialize(encoder: Encoder, value: PersistentList<ToDo>) {
        return ListSerializer(serializer).serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder): PersistentList<ToDo> {
        return ListSerializer(serializer).deserialize(decoder).toPersistentList()
    }
}