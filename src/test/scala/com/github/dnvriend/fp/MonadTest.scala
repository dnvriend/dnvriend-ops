package com.github.dnvriend.fp

import com.github.dnvriend.TestSpec

class MonadTest extends TestSpec {
  it should "find a monad for Option" in {
    val optionMonad = Monad[Option]
    optionMonad.map(Option(1))(_ + 1) shouldBe Option(2)
  }

  it should "find a monad for List" in {
    val listMonad = Monad[List]
    listMonad.map(List(1, 2, 3))(_ + 1) shouldBe List(2, 3, 4)
  }
}
