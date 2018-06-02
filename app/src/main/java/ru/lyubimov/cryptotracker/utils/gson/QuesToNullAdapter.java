package ru.lyubimov.cryptotracker.utils.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Type;

public final class QuesToNullAdapter<T> implements JsonDeserializer<T> {

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if ( json.isJsonPrimitive() ) {
            final JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
            if ( jsonPrimitive.isString() && jsonPrimitive.getAsString().equals("?") ) {
                return null;
            }
        }
        return context.deserialize(json, typeOfT);
    }
}
