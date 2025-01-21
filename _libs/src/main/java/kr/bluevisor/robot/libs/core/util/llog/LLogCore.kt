package enn.libs.and.llog

import android.util.Log

class LLogCore {
    companion object {
        @JvmStatic fun v(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.VERBOSE
            )
        }

        @JvmStatic fun v(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            message: String
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.VERBOSE,
                message
            )
        }

        @JvmStatic fun v(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            throwable: Throwable?
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.VERBOSE,
                throwable
            )
        }

        @JvmStatic fun v(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            throwable: Throwable?,
            message: String
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.VERBOSE,
                throwable,
                message
            )
        }

        @JvmStatic fun v(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            messageFormat: String,
            vararg messageArguments: Any?
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.VERBOSE,
                messageFormat,
                *messageArguments
            )
        }

        @JvmStatic fun v(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            throwable: Throwable?,
            messageFormat: String,
            vararg messageArguments: Any?
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.VERBOSE,
                throwable,
                messageFormat,
                *messageArguments
            )
        }


        @JvmStatic fun d(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.DEBUG
            )
        }

        @JvmStatic fun d(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            message: String
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.DEBUG,
                message
            )
        }

        @JvmStatic fun d(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            throwable: Throwable?
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.DEBUG,
                throwable
            )
        }

        @JvmStatic fun d(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            throwable: Throwable?,
            message: String
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.DEBUG,
                throwable,
                message
            )
        }

        @JvmStatic fun d(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            messageFormat: String,
            vararg messageArguments: Any?
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.DEBUG,
                messageFormat,
                *messageArguments
            )
        }

        @JvmStatic fun d(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            throwable: Throwable?,
            messageFormat: String,
            vararg messageArguments: Any?
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.DEBUG,
                throwable,
                messageFormat,
                *messageArguments
            )
        }


        @JvmStatic fun i(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.INFO
            )
        }

        @JvmStatic fun i(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            message: String
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.INFO,
                message
            )
        }

        @JvmStatic fun i(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            throwable: Throwable?
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.INFO,
                throwable
            )
        }

        @JvmStatic fun i(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            throwable: Throwable?,
            message: String
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.INFO,
                throwable,
                message
            )
        }

        @JvmStatic fun i(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            messageFormat: String,
            vararg messageArguments: Any?
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.INFO,
                messageFormat,
                *messageArguments
            )
        }

        @JvmStatic fun i(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            throwable: Throwable?,
            messageFormat: String,
            vararg messageArguments: Any?
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.INFO,
                throwable,
                messageFormat,
                *messageArguments
            )
        }


        @JvmStatic fun w(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.WARN
            )
        }

        @JvmStatic fun w(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            message: String
        ): String {
             return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.WARN,
                message
            )
        }

        @JvmStatic fun w(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            throwable: Throwable?
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.WARN,
                throwable
            )
        }

        @JvmStatic fun w(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            throwable: Throwable?,
            message: String
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.WARN,
                throwable,
                message
            )
        }

        @JvmStatic fun w(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            messageFormat: String,
            vararg messageArguments: Any?
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.WARN,
                messageFormat,
                *messageArguments
            )
        }

        @JvmStatic fun w(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            throwable: Throwable?,
            messageFormat: String,
            vararg messageArguments: Any?
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.WARN,
                throwable,
                messageFormat,
                *messageArguments
            )
        }


        @JvmStatic fun e(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.ERROR
            )
        }

        @JvmStatic fun e(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            message: String
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.ERROR,
                message
            )
        }

        @JvmStatic fun e(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            throwable: Throwable?
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.ERROR,
                throwable
            )
        }

        @JvmStatic fun e(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            throwable: Throwable?,
            message: String
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.ERROR,
                throwable,
                message
            )
        }

        @JvmStatic fun e(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            messageFormat: String,
            vararg messageArguments: Any?
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.ERROR,
                messageFormat,
                *messageArguments
            )
        }

        @JvmStatic fun e(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            throwable: Throwable?,
            messageFormat: String,
            vararg messageArguments: Any?
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                Log.ERROR,
                throwable,
                messageFormat,
                *messageArguments
            )
        }


        @JvmStatic fun print(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            priority: Int
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                priority
            )
        }

        @JvmStatic fun print(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            priority: Int,
            message: String
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                priority,
                message
            )
        }

        @JvmStatic fun print(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            priority: Int,
            throwable: Throwable?
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                priority,
                throwable
            )
        }

        @JvmStatic fun print(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            priority: Int,
            throwable: Throwable?,
            message: String
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                priority,
                throwable,
                message
            )
        }

        @JvmStatic fun print(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            priority: Int,
            messageFormat: String,
            vararg messageArguments: Any?
        ): String {
             return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                priority,
                messageFormat,
                *messageArguments
            )
        }

        @JvmStatic fun print(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            priority: Int,
            throwable: Throwable?,
            messageFormat: String,
            vararg messageArguments: Any?
        ): String {
            return printLog(
                tag,
                targetStackTraceElement,
                callerStackTraceElement,
                priority,
                throwable,
                messageFormat,
                *messageArguments
            )
        }


        private fun printLog(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            priority: Int
        ): String {
            return printLogInner(
                tag,
                priority,
                targetStackTraceElement,
                callerStackTraceElement,
                null,
                null
            )
        }

        private fun printLog(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            priority: Int,
            message: String
        ): String {
            return printLogInner(
                tag,
                priority,
                targetStackTraceElement,
                callerStackTraceElement,
                null,
                message
            )
        }

        private fun printLog(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            priority: Int,
            throwable: Throwable?
        ): String {
            return printLogInner(
                tag,
                priority,
                targetStackTraceElement,
                callerStackTraceElement,
                throwable,
                null
            )
        }

        private fun printLog(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            priority: Int,
            throwable: Throwable?,
            message: String
        ): String {
            return printLogInner(
                tag,
                priority,
                targetStackTraceElement,
                callerStackTraceElement,
                throwable,
                message
            )
        }

        private fun printLog(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            priority: Int,
            messageFormat: String,
            vararg messageArguments: Any?
        ): String {
            return printLogInner(
                tag,
                priority,
                targetStackTraceElement,
                callerStackTraceElement,
                null,
                String.format(messageFormat, *messageArguments)
            )
        }

        private fun printLog(
            tag: String,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            priority: Int,
            throwable: Throwable?,
            messageFormat: String,
            vararg messageArguments: Any?
        ): String {
            return printLogInner(
                tag,
                priority,
                targetStackTraceElement,
                callerStackTraceElement,
                throwable,
                String.format(messageFormat, *messageArguments)
            )
        }


        private fun printLogInner(
            tag: String,
            priority: Int,
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            throwable: Throwable?,
            message: String?
        ): String {
            return makeLog(
                targetStackTraceElement,
                callerStackTraceElement,
                message ?: "",
                throwable
            ).also {
                Log.println(priority, tag, it)
            }
        }

        private fun makeLog(
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            message: String
        ): String {
            fun makeSimpleClassName(className: String) = className.let {
                it.substring(it.lastIndexOf(".") + 1)
            }
            return  "____" +
                    " ${makeSimpleClassName(targetStackTraceElement.className)}" +
                    ".${targetStackTraceElement.methodName}()" +
                    (if (message.isNotBlank()) ": $message" else ".") +
                    " (${targetStackTraceElement.fileName}:${targetStackTraceElement.lineNumber})" +
                    " ::" +
                    " <caller: ${makeSimpleClassName(callerStackTraceElement.className)}" +
                    ".${callerStackTraceElement.methodName}()," +
                    " thread: ${Thread.currentThread().name}>"
        }

        private fun makeLog(
            targetStackTraceElement: StackTraceElement,
            callerStackTraceElement: StackTraceElement,
            message: String,
            throwable: Throwable?
        ): String {
            return makeLog(targetStackTraceElement, callerStackTraceElement, message) +
                    (throwable?.let { "%n${Log.getStackTraceString(throwable)}" } ?: "")
        }

        fun getTargetAndCallerStackTraceElements(
            targetMethodStackTraceIndex: Int
        ): Pair<StackTraceElement, StackTraceElement> {
            val stackTrace = Thread.currentThread().stackTrace
            return Pair(
                stackTrace[targetMethodStackTraceIndex],
                stackTrace[targetMethodStackTraceIndex + 1]
            )
        }
    }
}