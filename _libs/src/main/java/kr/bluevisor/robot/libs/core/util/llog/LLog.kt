package enn.libs.and.llog

class LLog {

    companion object : LLoggable {

        private val logger = LLoggerDefault("LLog")


        @JvmStatic override fun v() =
            logger.v()

        @JvmStatic override fun v(message: String) =
            logger.v(message)

        @JvmStatic override fun v(throwable: Throwable?) =
            logger.v(throwable)

        @JvmStatic override fun v(throwable: Throwable?, message: String) =
            logger.v(throwable, message)

        @JvmStatic override fun v(messageFormat: String, vararg messageArguments: Any?) =
            logger.v(messageFormat, *messageArguments)

        @JvmStatic override fun v(
            throwable: Throwable?, messageFormat: String, vararg messageArguments: Any?
        ) = logger.v(throwable, messageFormat, *messageArguments)


        @JvmStatic override fun d() =
            logger.d()

        @JvmStatic override fun d(message: String) =
            logger.d(message)

        @JvmStatic override fun d(throwable: Throwable?) =
            logger.d(throwable)

        @JvmStatic override fun d(throwable: Throwable?, message: String) =
            logger.d(throwable, message)

        @JvmStatic override fun d(messageFormat: String, vararg messageArguments: Any?) =
            logger.d(messageFormat, *messageArguments)

        @JvmStatic override fun d(
            throwable: Throwable?, messageFormat: String, vararg messageArguments: Any?
        ) = logger.d(throwable, messageFormat, *messageArguments)


        @JvmStatic override fun i() =
            logger.i()

        @JvmStatic override fun i(message: String) =
            logger.i(message)

        @JvmStatic override fun i(throwable: Throwable?) =
            logger.i(throwable)

        @JvmStatic override fun i(throwable: Throwable?, message: String) =
            logger.i(throwable, message)

        @JvmStatic override fun i(messageFormat: String, vararg messageArguments: Any?) =
            logger.i(messageFormat, *messageArguments)

        @JvmStatic override fun i(
            throwable: Throwable?, messageFormat: String, vararg messageArguments: Any?
        ) = logger.i(throwable, messageFormat, *messageArguments)


        @JvmStatic override fun w() =
            logger.w()

        @JvmStatic override fun w(message: String) =
            logger.w(message)

        @JvmStatic override fun w(throwable: Throwable?) =
            logger.w(throwable)

        @JvmStatic override fun w(throwable: Throwable?, message: String) =
            logger.w(throwable, message)

        @JvmStatic override fun w(messageFormat: String, vararg messageArguments: Any?) =
            logger.w(messageFormat, *messageArguments)

        @JvmStatic override fun w(
            throwable: Throwable?, messageFormat: String, vararg messageArguments: Any?
        ) = logger.w(throwable, messageFormat, *messageArguments)


        @JvmStatic override fun e() =
            logger.e()

        @JvmStatic override fun e(message: String) =
            logger.e(message)

        @JvmStatic override fun e(throwable: Throwable?) =
            logger.e(throwable)

        @JvmStatic override fun e(throwable: Throwable?, message: String) =
            logger.e(throwable, message)

        @JvmStatic override fun e(messageFormat: String, vararg messageArguments: Any?) =
            logger.e(messageFormat, *messageArguments)

        @JvmStatic override fun e(
            throwable: Throwable?, messageFormat: String, vararg messageArguments: Any?
        ) = logger.e(throwable, messageFormat, *messageArguments)


        @JvmStatic override fun print(
            priority: Int
        ) = logger.print(priority)

        @JvmStatic override fun print(
            priority: Int,
            message: String
        ) = logger.print(priority, message)

        @JvmStatic override fun print(
            priority: Int,
            throwable: Throwable?
        ) = logger.print(priority, throwable)

        @JvmStatic override fun print(
            priority: Int,
            throwable: Throwable?,
            message: String
        ) = logger.print(priority, throwable, message)

        @JvmStatic override fun print(
            priority: Int,
            messageFormat: String,
            vararg messageArguments: Any?
        ) = logger.print(priority, messageFormat, *messageArguments)

        @JvmStatic override fun print(
            priority: Int,
            throwable: Throwable?,
            messageFormat: String,
            vararg messageArguments: Any?
        ) = logger.print(priority, throwable, messageFormat, *messageArguments)
    }
}