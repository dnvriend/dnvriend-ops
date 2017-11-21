package com.github.dnvriend.fp

import scala.concurrent.{ ExecutionContext, Future }

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

trait Applicative[F[_]] extends Functor[F] {
  def pure[A](a: => A): F[A]
  def ap[A, B](fa: F[A])(f: F[A => B]): F[B]
  override def map[A, B](fa: F[A])(f: A => B): F[B] = {
    // to go to F[B]
    // we can just call 'ap', that returns an F[B]
    // ap accepts an fa, and an F[A => B]
    // we can create an F[A => B] from the 'f'
    // by 'lifting' the function 'f' into the context by means of 'pure'
    ap(fa)(pure(f)) // returns F[B]
  }
}

trait Monad[F[_]] extends Applicative[F] {
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
  override def ap[A, B](fa: F[A])(f: F[A => B]): F[B] = {
    // we can create an F[B] by calling flatMap
    // by leveraging the A => F[B] contract of flatMap,
    // we can apply flatMap by calling it with 'f' which is 'F[A => B]'
    // which gives us access to 'A => B',
    // we can now call 'map' with 'fa', and giving it 'A => B'
    // which results in the 'A => F[B]' contract
    flatMap(f)(atob => map(fa)(atob))
  }
  override def map[A, B](fa: F[A])(f: A => B): F[B] = {
    // we can create an F[B] by calling flatMap
    // by leveraging the A => F[B] contract of flatMap,
    // we can apply flatMap by calling it with 'F[A]',
    // which gives us access to 'A'
    // we can now apply 'f-with-a', which gives us 'B',
    // if we lift B in the context, it gives us F[B],
    // which results in the 'A => F[B]' contract
    flatMap(fa)(a => pure(f(a)))
  }
}

object Monad {
  def apply[F[_]](implicit m: Monad[F]): Monad[F] = m
  implicit val OptionMonad: Monad[Option] = new Monad[Option] {
    override def pure[A](a: => A): Option[A] = Option.apply(a)
    override def flatMap[A, B](fa: Option[A])(f: A => Option[B]) = {
      fa.flatMap(f)
    }
  }
  implicit val ListMonad: Monad[List] = new Monad[List] {
    override def pure[A](a: => A): List[A] = List(a)
    override def flatMap[A, B](fa: List[A])(f: A => List[B]) = {
      fa.flatMap(f)
    }
  }
  implicit def FutureMonad(implicit ec: ExecutionContext): Monad[Future] = new Monad[Future] {
    override def pure[A](a: => A) = Future(a)
    override def flatMap[A, B](fa: Future[A])(f: A => Future[B]) = {
      fa.flatMap(f)
    }
  }
}
