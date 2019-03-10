package com.sph.demo.newspaper.common.util

import android.util.Log
import android.util.SparseArray
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.internal.schedulers.ExecutorScheduler
import java.util.concurrent.TimeUnit

abstract class UseCase {
    protected val TAG: String

    protected val subscription: MutableList<Disposable> = ArrayList<Disposable>()

    private var timerSubscriptions: SparseArray<Disposable>? = null

    private var threadExecutor: Scheduler
    private var postExecutionThread: PostExecutionThread

    init {

        TAG = this.javaClass.simpleName
        this.threadExecutor = ExecutorScheduler(JobExecutor())
        this.postExecutionThread = BackgroundThread()

    }

    /**
     * Execute (subscribe) an observable to a subscriber and enable auto un-subscribe
     *
     * @param observable   Observable object
     * @param ucSubscriber Subscriber object
     */
    protected fun <T> execute(observable: Observable<T>, ucSubscriber: DefaultSubscriber<T>): Disposable? {
        return execute(observable, ucSubscriber, true)
    }

    /**
     * Execute (subscribe) an observable to a subscriber
     *
     * @param observable      Observable object
     * @param subscriber      Subscriber object
     * @param autoUnsubscribe Flag to enable/disable auto un-subscribe
     */
    protected fun <T> execute(observable: Observable<T>, subscriber: DefaultSubscriber<T>, autoUnsubscribe: Boolean): Disposable? {
        var subs: Disposable? = null
        try {
            subs = observable
                    .subscribeOn(threadExecutor)
                    .observeOn(postExecutionThread.scheduler)
                    .doOnSubscribe {
                        subscriber.onStart()
                    }
                    .subscribe(object : Consumer<T> {
                        override fun accept(t: T) {
                            subscriber.onNext(t)
                        }
                    }, object : Consumer<Throwable> {
                        override fun accept(t: Throwable) {
                            subscriber.onError(t)
                        }
                    }, object : Action {
                        override fun run() {
                            subscriber.onComplete()
                        }
                    })
        } catch (ex: Exception) {
            // no-op
        }

        if (autoUnsubscribe && subs != null) {
            try {
                synchronized(subscription) {
                    this.subscription.add(subs!!)
                }

                subs = null
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        return subs
    }

    /**
     * Un-subscribe from current @[Disposable]
     *
     * @param async True for asynchronous and false for synchronous mode
     */
    @JvmOverloads
    fun unsubscribe(async: Boolean = true) {
        do {
            val worker = object : Thread() {
                override fun run() {
                    synchronized(subscription) {
                        for (sub in subscription) {
                            if (!sub.isDisposed)
                                sub.dispose()
                        }
                    }

                    // TODO: un-subscribe all timer subscriptions ???

                    Log.i(TAG, "Un-subscribed all subscriptions ...")
                }
            }

            if (async) {
                worker.start()
                break
            }

            worker.run()
        } while (false)
    }

    /**
     * Start a timer
     *
     * @param id         Timer ID
     * @param duration   Timeout duration (milliseconds)
     * @param subscriber Subscriber
     * @return true if success otherwise false
     */
    @Synchronized
    fun startTimer(id: Int, duration: Long, subscriber: DefaultSubscriber<in Long>): Boolean {
        var blRet = false

        do {
            if (duration <= 0) break

            // check id
            var subscription = getTimerSubscription(id)
            if (subscription != null) {
                // trying to stop previous timer
                if (subscription.isDisposed)
                    subscription.dispose()

                timerSubscriptions?.remove(id)
            }

            if (timerSubscriptions == null) timerSubscriptions = SparseArray<Disposable>()

            try {
                subscription = Observable.timer(duration, TimeUnit.MILLISECONDS)
                        .subscribeOn(threadExecutor)
                        .observeOn(postExecutionThread.scheduler)
                        .subscribe(object : Consumer<Long> {
                            override fun accept(t: Long) {
                                subscriber.onNext(t)
                            }
                        }, object : Consumer<Throwable> {
                            override fun accept(t: Throwable) {
                                subscriber.onError(t)
                            }
                        }, object : Action {
                            override fun run() {
                                subscriber.onComplete()
                            }
                        })

                timerSubscriptions?.put(id, subscription)
                blRet = true
            } catch (ex: Exception) {
                // no-op
                break
            }

        } while (false)

        return blRet
    }

    /**
     * Stop a timer
     *
     * @param id Timer ID
     * @return true if success otherwise false
     */
    @Synchronized
    fun stopTimer(id: Int): Boolean {
        var blRet = false

        do {
            val subscription = getTimerSubscription(id) ?: break

            try {
                subscription.dispose()
            } catch (ex: Exception) {
                // no-op
            }

            timerSubscriptions?.remove(id)

            blRet = true
        } while (false)

        return blRet
    }

    @Synchronized
    private fun getTimerSubscription(id: Int): Disposable? {
        var subscription: Disposable? = null

        if (timerSubscriptions != null)
            subscription = timerSubscriptions?.get(id)

        return subscription
    }
}
/**
 * Un-subscribe from current @[Subscription] (asynchronously)
 */
