package com.sph.demo.newspaper.common.util

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class BackgroundThread : PostExecutionThread {

    override val scheduler: Scheduler
        get() = Schedulers.computation()
}