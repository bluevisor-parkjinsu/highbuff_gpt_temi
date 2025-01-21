package enn.libs.and.llog

import android.util.Log

open class LLogger(
    val tag: String,
    val targetMethodStackTraceIndex: Int
) : LLoggable {

    override fun v() {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.v(
            tag,
            targetStackTraceElement,
            callerStackTraceElement
        ).also {
            onLogged(Log.VERBOSE, it)
        }
    }

    override fun v(message: String) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.v(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            message
        ).also {
            onLogged(Log.VERBOSE, it)
        }
    }

    override fun v(throwable: Throwable?) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.v(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            throwable
        ).also {
            onLogged(Log.VERBOSE, it, throwable)
        }
    }

    override fun v(throwable: Throwable?, message: String) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.v(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            throwable,
            message
        ).also {
            onLogged(Log.VERBOSE, it, throwable)
        }
    }

    override fun v(messageFormat: String, vararg messageArguments: Any?) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.v(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            messageFormat,
            *messageArguments
        ).also {
            onLogged(Log.VERBOSE, it)
        }
    }

    override fun v(
        throwable: Throwable?,
        messageFormat: String,
        vararg messageArguments: Any?) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.v(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            throwable,
            messageFormat,
            *messageArguments
        ).also {
            onLogged(Log.VERBOSE, it, throwable)
        }
    }


    override fun d() {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.d(
            tag,
            targetStackTraceElement,
            callerStackTraceElement
        ).also {
            onLogged(Log.DEBUG, it)
        }
    }

    override fun d(message: String) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.d(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            message
        ).also {
            onLogged(Log.DEBUG, it)
        }
    }

    override fun d(throwable: Throwable?) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.d(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            throwable
        ).also {
            onLogged(Log.DEBUG, it, throwable)
        }
    }

    override fun d(throwable: Throwable?, message: String) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.d(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            throwable,
            message
        ).also {
            onLogged(Log.DEBUG, it, throwable)
        }
    }

    override fun d(messageFormat: String, vararg messageArguments: Any?) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.d(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            messageFormat,
            *messageArguments
        ).also {
            onLogged(Log.DEBUG, it)
        }
    }

    override fun d(
        throwable: Throwable?,
        messageFormat: String,
        vararg messageArguments: Any?
    ) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.d(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            throwable,
            messageFormat,
            *messageArguments
        ).also {
            onLogged(Log.DEBUG, it, throwable)
        }
    }


    override fun i() {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.i(
            tag,
            targetStackTraceElement,
            callerStackTraceElement
        ).also {
            onLogged(Log.INFO, it)
        }
    }

    override fun i(message: String) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.i(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            message
        ).also {
            onLogged(Log.INFO, it)
        }
    }

    override fun i(throwable: Throwable?) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.i(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            throwable
        ).also {
            onLogged(Log.INFO, it, throwable)
        }
    }

    override fun i(throwable: Throwable?, message: String) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.i(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            throwable,
            message
        ).also {
            onLogged(Log.INFO, it, throwable)
        }
    }

    override fun i(messageFormat: String, vararg messageArguments: Any?) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.i(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            messageFormat,
            *messageArguments
        ).also {
            onLogged(Log.INFO, it)
        }
    }

    override fun i(
        throwable: Throwable?,
        messageFormat: String,
        vararg messageArguments: Any?
    ) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.i(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            throwable,
            messageFormat,
            *messageArguments
        ).also {
            onLogged(Log.INFO, it, throwable)
        }
    }


    override fun w() {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.w(
            tag,
            targetStackTraceElement,
            callerStackTraceElement
        ).also {
            onLogged(Log.WARN, it)
        }
    }

    override fun w(message: String) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.w(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            message
        ).also {
            onLogged(Log.WARN, it)
        }
    }

    override fun w(throwable: Throwable?) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.w(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            throwable
        ).also {
            onLogged(Log.WARN, it, throwable)
        }
    }

    override fun w(throwable: Throwable?, message: String) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.w(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            throwable,
            message
        ).also {
            onLogged(Log.WARN, it, throwable)
        }
    }

    override fun w(messageFormat: String, vararg messageArguments: Any?) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.w(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            messageFormat,
            *messageArguments
        ).also {
            onLogged(Log.WARN, it)
        }
    }

    override fun w(
        throwable: Throwable?,
        messageFormat: String,
        vararg messageArguments: Any?
    ) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.w(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            throwable,
            messageFormat,
            *messageArguments
        ).also {
            onLogged(Log.WARN, it, throwable)
        }
    }


    override fun e() {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.e(
            tag,
            targetStackTraceElement,
            callerStackTraceElement
        ).also {
            onLogged(Log.ERROR, it)
        }
    }

    override fun e(message: String) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.e(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            message
        ).also {
            onLogged(Log.ERROR, it)
        }
    }

    override fun e(throwable: Throwable?) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.e(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            throwable
        ).also {
            onLogged(Log.ERROR, it, throwable)
        }
    }

    override fun e(throwable: Throwable?, message: String) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.e(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            throwable,
            message
        ).also {
            onLogged(Log.ERROR, it, throwable)
        }
    }

    override fun e(messageFormat: String, vararg messageArguments: Any?) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.e(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            messageFormat,
            *messageArguments
        ).also {
            onLogged(Log.ERROR, it)
        }
    }

    override fun e(
        throwable: Throwable?,
        messageFormat: String,
        vararg messageArguments: Any?
    ) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.e(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            throwable,
            messageFormat,
            *messageArguments
        ).also {
            onLogged(Log.ERROR, it, throwable)
        }
    }


    override fun print(priority: Int) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.print(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            priority
        ).also {
            onLogged(priority, it)
        }
    }

    override fun print(priority: Int, message: String) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.print(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            priority,
            message
        ).also {
            onLogged(priority, it)
        }
    }

    override fun print(priority: Int, throwable: Throwable?) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.print(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            priority,
            throwable
        ).also {
            onLogged(priority, it, throwable)
        }
    }

    override fun print(priority: Int, throwable: Throwable?, message: String) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.print(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            priority,
            throwable,
            message
        ).also {
            onLogged(priority, it, throwable)
        }
    }

    override fun print(priority: Int, messageFormat: String, vararg messageArguments: Any?) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.print(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            priority,
            messageFormat,
            *messageArguments
        ).also {
            onLogged(priority, it)
        }
    }

    override fun print(
        priority: Int,
        throwable: Throwable?,
        messageFormat: String,
        vararg messageArguments: Any?,
    ) {
        val (targetStackTraceElement, callerStackTraceElement) =
            getTargetAndCallerStackTraceElements()
        LLogCore.print(
            tag,
            targetStackTraceElement,
            callerStackTraceElement,
            priority,
            throwable,
            messageFormat,
            *messageArguments
        ).also {
            onLogged(priority, it, throwable)
        }
    }


    protected open fun onLogged(
        priority: Int, logMessage: String, throwable: Throwable? = null) {
    }

    protected open fun getTargetAndCallerStackTraceElements()
    : Pair<StackTraceElement, StackTraceElement> {
        return LLogCore.getTargetAndCallerStackTraceElements(targetMethodStackTraceIndex)
    }
}