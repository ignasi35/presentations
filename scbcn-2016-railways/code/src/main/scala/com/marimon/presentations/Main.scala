package com.marimon.presentations

import com.marimon.presentations.impl.{PromiseImpl, TryImpl}

import scala.concurrent.Future
import scala.language.higherKinds
import scala.util.Try

trait Impl[T[_]] {
  def parse[I, O](input: I): T[O]
  def authorize[I](input: I): T[I]
  def persist[I](valid: I): T[I]
  def forAUser[I, O](persisted: I): T[O]
}


object Main extends App {

  import scala.concurrent.ExecutionContext.Implicits.global

  //  def railwayOperation(impl: Impl[Try])(i: String) = {
  def railwayOperation(impl: Impl[Future])(i: String) = {
    for {
      valid <- impl.parse(i)
      parsed <- impl.authorize(valid)
      persisted <- impl.persist(parsed)
      response <- impl.forAUser(persisted)
    } yield response
  }

  val asyncImpl: Impl[Future] = new PromiseImpl
  val syncImpl: Impl[Try] = new TryImpl

  val inputs = List("2", "17", "18", "19", "200", "asdf")
//  inputs.map(railwayOperation(syncImpl)).foreach(println)
  inputs.map(railwayOperation(asyncImpl)).foreach(println)

}
