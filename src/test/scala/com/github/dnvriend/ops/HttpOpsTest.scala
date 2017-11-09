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

class HttpOpsTest extends TestSpec {
  it should "get from httpbin.org" in {
    "https://httpbin.org/get".get() shouldBe right[HttpResponse]
  }

  it should "get from google" in {
    "http://www.google.nl".get() shouldBe right[HttpResponse]
  }

  it should "not find an unknown host" in {
    "http://foo.bar".get() shouldBe left[Throwable]
  }
}
