package com.marimon.presentations.impl

import com.marimon.presentations.Impl

import scala.util.Try


class TryImpl extends Impl[Try] {

  override def parse[String, Int](input: String): Try[Int] = ???

  override def authorize[X](input: X): Try[X] = ???

  override def persist[I](valid: I): Try[I] = ???

  override def forAUser[I, O](persisted: I): Try[O] = ???
}

