package com.sph.demo.newspaper.common.util

import java.util.concurrent.*

class JobExecutor : Executor {
    private val INITIAL_POOL_SIZE = 3
    private val MAX_POOL_SIZE = 10

    // Sets the amount of time an idle thread waits before terminating
    private val KEEP_ALIVE_TIME = 10

    // Sets the Time Unit to seconds
    private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS

    private var workQueue: BlockingQueue<Runnable>

    private var threadPoolExecutor: ThreadPoolExecutor

    private var threadFactory: ThreadFactory

    init {
        this.workQueue = LinkedBlockingQueue()
        this.threadFactory = JobThreadFactory()
        this.threadPoolExecutor = ThreadPoolExecutor(INITIAL_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE_TIME.toLong(), KEEP_ALIVE_TIME_UNIT, this.workQueue, this.threadFactory)
    }

    override fun execute(runnable: Runnable?) {
        if (runnable == null) {
            throw IllegalArgumentException("Runnable to execute cannot be null")
        }
        if (!threadPoolExecutor.isShutdown && !threadPoolExecutor.isTerminating) {
            this.threadPoolExecutor.execute(runnable)
        }
    }

    private class JobThreadFactory : ThreadFactory {
        private val counter = 0

        override fun newThread(runnable: Runnable): Thread {
            return Thread(runnable, THREAD_NAME + counter)
        }

        companion object {
            private val THREAD_NAME = "android_"
        }
    }
}