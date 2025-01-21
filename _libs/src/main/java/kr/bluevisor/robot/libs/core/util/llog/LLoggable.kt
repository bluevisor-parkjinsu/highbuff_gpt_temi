package enn.libs.and.llog

interface LLoggable {
    fun v()
    fun v(message: String)
    fun v(throwable: Throwable?)
    fun v(throwable: Throwable?, message: String)
    fun v(messageFormat: String, vararg messageArguments: Any?)
    fun v(throwable: Throwable?, messageFormat: String, vararg messageArguments: Any?)

    fun d()
    fun d(message: String)
    fun d(throwable: Throwable?)
    fun d(throwable: Throwable?, message: String)
    fun d(messageFormat: String, vararg messageArguments: Any?)
    fun d(throwable: Throwable?, messageFormat: String, vararg messageArguments: Any?)

    fun i()
    fun i(message: String)
    fun i(throwable: Throwable?)
    fun i(throwable: Throwable?, message: String)
    fun i(messageFormat: String, vararg messageArguments: Any?)
    fun i(throwable: Throwable?, messageFormat: String, vararg messageArguments: Any?)

    fun w()
    fun w(message: String)
    fun w(throwable: Throwable?)
    fun w(throwable: Throwable?, message: String)
    fun w(messageFormat: String, vararg messageArguments: Any?)
    fun w(throwable: Throwable?, messageFormat: String, vararg messageArguments: Any?)

    fun e()
    fun e(message: String)
    fun e(throwable: Throwable?)
    fun e(throwable: Throwable?, message: String)
    fun e(messageFormat: String, vararg messageArguments: Any?)
    fun e(throwable: Throwable?, messageFormat: String, vararg messageArguments: Any?)

    fun print(priority: Int)
    fun print(priority: Int, message: String)
    fun print(priority: Int, throwable: Throwable?)
    fun print(priority: Int, throwable: Throwable?, message: String)
    fun print(priority: Int, messageFormat: String, vararg messageArguments: Any?)
    fun print(priority: Int, throwable: Throwable?, messageFormat: String, vararg messageArguments: Any?)
}