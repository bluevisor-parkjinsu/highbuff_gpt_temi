package enn.libs.and.llog

import android.util.Log

class LLoggerDefault(tag: String) : LLogger(tag, -1) {

    override fun getTargetAndCallerStackTraceElements()
            : Pair<StackTraceElement, StackTraceElement> {

        val stackTrace = Thread.currentThread().stackTrace
        fun makeTracePair(index: Int) = Pair(stackTrace[index], stackTrace[index + 1])

        when {
            isTracingFromKotlinFile(stackTrace[INDEX__TARGET_STACK_TRACE__KOTLIN]) ->
                return makeTracePair(INDEX__TARGET_STACK_TRACE__KOTLIN)
            isTracingFromJavaFile(stackTrace[INDEX__TARGET_STACK_TRACE__JAVA]) ->
                return makeTracePair(INDEX__TARGET_STACK_TRACE__JAVA)
        }

        Log.w(tag, "Unknown target source file extension.")
        stackTrace.forEach { Log.w(tag, "$it") }
        return makeTracePair(INDEX__TARGET_STACK_TRACE__KOTLIN)
    }

    private fun isTracingFromKotlinFile(stackTraceElement: StackTraceElement) =
        stackTraceElement.fileName?.endsWith(".kt", true) ?: false

    private fun isTracingFromJavaFile(stackTraceElement: StackTraceElement) =
        stackTraceElement.fileName?.endsWith(".java", true) ?: false

    companion object {
        private const val INDEX__TARGET_STACK_TRACE__KOTLIN = 5
        private const val INDEX__TARGET_STACK_TRACE__JAVA = INDEX__TARGET_STACK_TRACE__KOTLIN + 1
    }
}