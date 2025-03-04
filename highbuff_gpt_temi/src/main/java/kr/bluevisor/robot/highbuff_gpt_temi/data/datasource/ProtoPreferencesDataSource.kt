package kr.bluevisor.robot.highbuff_gpt_temi.data.datasource

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import dagger.hilt.android.qualifiers.ApplicationContext
import enn.libs.and.llog.LLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kr.bluevisor.robot.highbuff_gpt_temi.CompanyInfo
import kr.bluevisor.robot.highbuff_gpt_temi.ProtoGptPreferences
import kr.bluevisor.robot.highbuff_gpt_temi.UserDefinedQuestionAndAnswer
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton


private object ProtoPreferencesSerializer : Serializer<ProtoGptPreferences> {
    override val defaultValue: ProtoGptPreferences = ProtoGptPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): ProtoGptPreferences {
        return runCatching {
            ProtoGptPreferences.parseFrom(input)
        }.onFailure { exception ->
            LLog.w(exception)
            if (exception is InvalidProtocolBufferException) {
                throw CorruptionException("Cannot read proto.", exception)
            }
            throw exception
        }.getOrThrow()
    }

    override suspend fun writeTo(t: ProtoGptPreferences, output: OutputStream) {
        t.writeTo(output)
    }
}

private val Context.protoDataStore: DataStore<ProtoGptPreferences> by dataStore(
    fileName = "proto_preferences.pb",
    serializer = ProtoPreferencesSerializer
)

@Singleton
class ProtoPreferencesDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    val preferencesFlow = context.protoDataStore.data
        .flowOn(Dispatchers.IO)
}
