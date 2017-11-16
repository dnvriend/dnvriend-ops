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

package com.github.dnvriend.ops

import com.github.dnvriend.TestSpec

import scalaz.@@

class CryptoOpsTest extends TestSpec {
  final val KEY = "THIS_IS_A_SECRET"
  final val SALT = "THIS_IS_A_SALT"
  final val AES_CIPHER_TEXT: String @@ Hex = "08F848EBF55C1F6236EDBFAEEC9C8F24".tagHex
  final val PLAIN_TEXT = "Hello World!"

  "symmetric" should "encrypt aes" in {
    PLAIN_TEXT.arr.encrypt.symmetric.aes(KEY, SALT).hex shouldBe AES_CIPHER_TEXT
  }

  it should "decrypt aes" in {
    AES_CIPHER_TEXT.fromHex.decrypt.symmetric.aes(KEY, SALT).str shouldBe PLAIN_TEXT
  }
}
