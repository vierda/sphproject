package com.sph.demo.newspaper.common.util

open class DefaultSubscriber<T> {
    open fun onStart() {
        // for sub-classes to override
    }

    open fun onComplete() {
        // for sub-classes to override
    }

    open fun onError(t: Throwable?) {
        t?.printStackTrace()
        // for sub-classes to override
    }

    open fun onNext(t: T) {
        // for sub-classes to override
    }

    open fun unsubscribe() {
        // for sub-classes to override
    }
}