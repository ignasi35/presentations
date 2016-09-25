package com.marimon.presentations.impl

import com.marimon.presentations.Impl

import scala.concurrent.Future


class PromiseImpl extends Impl[Future] {
  val tryImpl = new TryImpl

  override def parse[I, O](input: I): Future[O] =
    Future.fromTry(tryImpl.parse(input))

  override def authorize[I](input: I): Future[I] =
    Future.fromTry(tryImpl.authorize(input))

  override def persist[I](valid: I): Future[I] =
    Future.fromTry(tryImpl.persist(valid))

  override def forAUser[I, O](persisted: I): Future[O] =
    Future.fromTry(tryImpl.forAUser(persisted))
}


