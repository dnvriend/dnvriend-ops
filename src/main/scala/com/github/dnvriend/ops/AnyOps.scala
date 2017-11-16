package com.github.dnvriend.ops

import scalaz.{ @@, Tag }

trait AnyOps {
  implicit def ToUnwrapAnyOps[A, TAG](that: A @@ TAG): ToUnwrapAnyOps[A, TAG] = new ToUnwrapAnyOps[A, TAG](that)
}
class ToUnwrapAnyOps[A, TAG](that: A @@ TAG) {
  def unwrap: A = Tag.unwrap(that)
}
