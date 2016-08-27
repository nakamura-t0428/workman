package com.loadtester.util.helper

import org.apache.commons.codec.binary.Base64
import java.util.UUID
import java.nio.ByteBuffer
import java.nio.ByteOrder

object UUIDHelper {
  def uuidAsBase64:String = {
    val uuid = java.util.UUID.randomUUID
    uuidToBase64(uuid)
  }
  def uuidToBase64(str:String):String = {
    val base64 = new Base64()
    val uuid = UUID.fromString(str)
    uuidToBase64(uuid)
  }
  def uuidToBase64(uuid:UUID):String = {
    val bb = ByteBuffer.allocate(16)
    bb.order(ByteOrder.BIG_ENDIAN)
    bb.putLong(uuid.getMostSignificantBits())
    bb.putLong(uuid.getLeastSignificantBits())
    bb.flip()
    Base64.encodeBase64URLSafeString(bb.array())
  }
  def uuidFromBase64(str:String):UUID = {
    val base64 = new Base64()
    val bytes = Base64.decodeBase64(str)
    val bb = ByteBuffer.wrap(bytes)
    val uuid = new UUID(bb.getLong(), bb.getLong())
    uuid
  }
}