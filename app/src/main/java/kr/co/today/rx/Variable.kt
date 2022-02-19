package kr.co.today.rx

import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.BehaviorSubject

class Variable<T>(private var _value: T) : Consumer<T> {
  private val serializedSubject = BehaviorSubject.createDefault(_value)

  fun get(): T {
    return _value
  }

  fun set(value: T) {
    this._value = value
    serializedSubject.onNext(this._value)
  }

  var value: T
    @Synchronized get() {
      return this._value
    }
    @Synchronized set(value) {
      this._value = value
      serializedSubject.onNext(this._value)
    }

  fun asObservable(): Observable<T> {
    return serializedSubject
  }

  fun rx(): (T) -> Unit {
    return fun(value: T) {
      this.set(value)
    }
  }

  override fun accept(t: T) {
    this.set(t)
  }

  fun onComplete() {
    serializedSubject.onComplete()
  }
}
