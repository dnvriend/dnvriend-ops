package com.github.dnvriend.ops

import java.io.{ ByteArrayInputStream, ByteArrayOutputStream }

import com.sksamuel.avro4s._
import org.apache.avro.Schema

import scalaz.{ @@, Tag }

object Converter {
  def apply[A, B](implicit conv: Converter[A, B]): Converter[A, B] = conv

  implicit def ToAvroBinaryEncoder[A <: Product: SchemaFor: ToRecord]: Converter[A, Array[Byte] @@ AvroBinary] = new Converter[A, Array[Byte] @@ AvroBinary] {
    override def apply(that: A): Array[Byte] @@ AvroBinary = {
      val baos = new ByteArrayOutputStream
      val output = AvroOutputStream.binary[A](baos)
      output.write(that)
      output.flush()
      output.close()
      baos.flush()
      baos.close()
      Tag(baos.toByteArray)
    }
  }

  implicit def ToAvroJsonEncoder[A <: Product: SchemaFor: ToRecord]: Converter[A, Array[Byte] @@ AvroJson] = new Converter[A, Array[Byte] @@ AvroJson] {
    override def apply(that: A): Array[Byte] @@ AvroJson = {
      val baos = new ByteArrayOutputStream
      val output = AvroOutputStream.json[A](baos)
      output.write(that)
      output.flush()
      output.close()
      baos.flush()
      baos.close()
      Tag(baos.toByteArray)
    }
  }

  implicit def AvroBinaryAtoBConverter[A <: Product: ToRecord, B <: Product: FromRecord](implicit schemaForA: SchemaFor[A], encoder: Converter[A, Array[Byte] @@ AvroBinary], decoder: Converter2[Schema, Array[Byte] @@ AvroBinary, B]): Converter[A, B] = new Converter[A, B] {
    override def apply(v1: A): B = encoder andThen decoder.curried(schemaForA()) apply v1
  }
}
trait Converter[A, B] extends Function1[A, B]

object Converter2 {
  def apply[A, B, C](implicit conv: Converter2[A, B, C]): Converter2[A, B, C] = conv
  implicit def AvroBinaryDecoder[R <: Product: FromRecord](implicit readerSchema: SchemaFor[R]): Converter2[Schema, Array[Byte] @@ AvroBinary, R] = new Converter2[Schema, Array[Byte] @@ AvroBinary, R] {
    override def apply(writerSchema: Schema, taggedBytes: @@[Array[Byte], AvroBinary]): R = {
      val bytes: Array[Byte] = Tag.unwrap(taggedBytes)
      val input = new AvroBinaryInputStream[R](new ByteArrayInputStream(bytes), Option(writerSchema), Option(readerSchema()))
      val result = input.iterator().next()
      input.close()
      result
    }
  }
}
trait Converter2[A, B, C] extends Function2[A, B, C]