package com.github.dnvriend.ops

import com.github.dnvriend.{ TestSpec, v1, v2, v3 }
import org.apache.avro.Schema

import scalaz.@@

class AvroOpsTest extends TestSpec with AllOps {
  val AvroBinaryByteArray: Array[Byte] @@ AvroBinary = "0C44656E6E6973".tagHex.fromHex.tagAvroBinary
  val AvroJsonByteArray: Array[Byte] @@ AvroJson = "7B226E616D65223A2244656E6E6973227D".tagHex.fromHex.tagAvroJson

  val v1Person = v1.Person("Dennis")
  val v2Person = v2.Person("Dennis", 0)
  val v3Person = v3.Person("Dennis", 0, Nil)

  it should "find person to avro json type class" in {
    Converter[v1.Person, Array[Byte] @@ AvroJson].apply(v1Person)
      .unwrap.hex shouldBe "7B226E616D65223A2244656E6E6973227D"
  }

  it should "find person to avro binary type class" in {
    Converter[v1.Person, Array[Byte] @@ AvroBinary].apply(v1Person)
      .unwrap.hex shouldBe "0C44656E6E6973"
  }

  it should "find AVRO a-to-b type class" in {
    Converter2[Schema, Array[Byte] @@ AvroBinary, v1.Person]
      .apply(v1.personSchema, AvroBinaryByteArray) shouldBe v1Person
  }

  it should "convert person v1 to v1" in {
    Converter[v1.Person, v1.Person].apply(v1Person) shouldBe v1Person
  }

  it should "convert person v1 to v2" in {
    Converter[v1.Person, v2.Person].apply(v1Person) shouldBe v2Person
  }

  it should "convert person v1 to v3" in {
    Converter[v1.Person, v3.Person].apply(v1Person) shouldBe v3Person
  }

  it should "convert person v1 to cat v1" in {
    Converter[v1.Person, v1.Cat].apply(v1Person) shouldBe v1.Cat("Dennis")
  }
}
