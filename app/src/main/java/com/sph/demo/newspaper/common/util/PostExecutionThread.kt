package com.sph.demo.newspaper.common.util

import io.reactivex.Scheduler


interface PostExecutionThread {
    val scheduler: Scheduler
}