package com.codahale.jerkson.ser

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.{SerializerProvider, JsonSerializer}

class MapSerializer extends JsonSerializer[collection.Map[_ ,_]] {
  def serialize(map: collection.Map[_,_], json: JsonGenerator, provider: SerializerProvider) {
    json.writeStartObject()

    // attempt #1. doesn't compile, but worked fine in 2.9.1
    // C:\Projects\SublimeScala\Jerkson\src\main\scala\com\codahale\jerkson\ser\MapSerializer.scala:10: error: missing parameter type for expanded function
    // The argument types of an anonymous function must be fully known. (SLS 8.5)
    // Expected type was: ?
    // for ((key, value) <- map) {

    // attempt #2. doesn't compile, fails with a weird message
    // C:\Projects\SublimeScala\Jerkson\src\main\scala\com\codahale\jerkson\ser\MapSerializer.scala:14: error: erroneous or inaccessible type
    // for ((key: Any, value: Any) <- map) {

    // attempt #3. finally compiles
    map foreach { (kv: (Any, Any)) =>
      val (key, value) = kv
      provider.defaultSerializeField(key.toString, value, json)
    }

    json.writeEndObject()
  }
}
