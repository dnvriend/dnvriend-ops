// Copyright 2017 Dennis Vriend
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.github.dnvriend

import com.github.dnvriend.ops.AllOps
import com.sksamuel.avro4s.SchemaFor
import org.apache.avro.Schema
import org.scalatest.{ FlatSpec, Matchers, TryValues }
import org.typelevel.scalatest.{ DisjunctionMatchers, DisjunctionValues }

object v1 {
  case class Person(name: String = "Dennis")
  val personSchema: Schema = SchemaFor[Person]()
  case class Cat(name: String = "Elsa")
}

object v2 {
  case class Person(name: String = "Dennis", age: Int = 0)
  val personSchema: Schema = SchemaFor[Person]()
}

object v3 {
  case class Person(name: String = "Dennis", age: Int = 0, luckyNumbers: List[Int] = List.empty[Int])
  val personSchema: Schema = SchemaFor[Person]()
}

class TestSpec extends FlatSpec with Matchers with DisjunctionValues with DisjunctionMatchers with TryValues with AllOps {

}
