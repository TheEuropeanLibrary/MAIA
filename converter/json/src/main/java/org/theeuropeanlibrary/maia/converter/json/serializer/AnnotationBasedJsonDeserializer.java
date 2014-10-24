/* AnnotationBasedByteConverter.java - created on 5 de Abr de 2011, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.json.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.theeuropeanlibrary.maia.common.FieldId;

/**
 * Performs XML serialization of object in the Object Model, using java
 * reflection and the FieldId annotations on the class fields.
 *
 * @param <T> class to be serialized
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @since 9 de Mai de 2011
 */
public class AnnotationBasedJsonDeserializer<T> extends JsonDeserializer<T> {

    private final Class<T> deserializeClass;
    private final List<JsonFieldDeserializer> idIndexedFieldArray;
    private final Map<String, Integer> jsonNameToField;

    /**
     * Creates a new instance of this class.
     *
     * @param deserializeClass The class to be Serialized
     * @param customSerializers serializers for particular fields of the class
     */
    public AnnotationBasedJsonDeserializer(Class<T> deserializeClass,
            Map<Integer, JsonFieldDeserializer> customSerializers) {
        this.deserializeClass = deserializeClass;

        List<JsonFieldDeserializer> fieldsWithAnnotatoins = new ArrayList<>();
        Map<Integer, JsonFieldDeserializer> idToFieldMap = new HashMap<>();
        Map<Integer, String> nameToFieldMap = new HashMap<>();
        int maxFieldId = initFieldsFromClass(deserializeClass, customSerializers, fieldsWithAnnotatoins,
                idToFieldMap, nameToFieldMap);

        jsonNameToField = new HashMap<>();
        idIndexedFieldArray = new ArrayList<>(maxFieldId + 1);
        for (int i = 0; i <= maxFieldId; i++) {
            idIndexedFieldArray.add(idToFieldMap.get(i));
            jsonNameToField.put(nameToFieldMap.get(i), i);
        }
    }

    private int initFieldsFromClass(Class<?> initClass,
            Map<Integer, JsonFieldDeserializer> customEncoders,
            List<JsonFieldDeserializer> fieldsWithAnnotatoins,
            Map<Integer, JsonFieldDeserializer> idToFieldMap, Map<Integer, String> nameToFieldMap) {
        int maxFieldId = 0;
        if (!initClass.getSuperclass().equals(Object.class)) {
            maxFieldId = initFieldsFromClass(initClass.getSuperclass(), customEncoders,
                    fieldsWithAnnotatoins, idToFieldMap, nameToFieldMap);
        }

        for (Field f : initClass.getDeclaredFields()) {
            FieldId ann = f.getAnnotation(FieldId.class);
            if (ann != null) {
                JsonFieldDeserializer fldConv = null;
                maxFieldId = Math.max(maxFieldId, ann.value());
                String capFieldName = Character.toUpperCase(f.getName().charAt(0))
                        + f.getName().substring(1);
                Method setMethod;
                try {
                    setMethod = initClass.getDeclaredMethod("set" + capFieldName, f.getType());
                    if (customEncoders != null) {
                        fldConv = customEncoders.get(ann.value());
                    }
                    if (fldConv == null) {
                        fldConv = new BaseTypeJsonFieldDeserializer(f.getType());
                    }
                    fldConv.configure(setMethod);
                } catch (SecurityException e) {
                    throw new RuntimeException("No access to get/set method for field "
                            + f.getName(), e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException("Missing get/set method for field " + f.getName(), e);
                }

                // fieldToIdMap.put(f, ann.id());
                idToFieldMap.put(ann.value(), fldConv);
                nameToFieldMap.put(ann.value(), capFieldName);
                fieldsWithAnnotatoins.add(fldConv);
            }
        }
        return maxFieldId;
    }

    @Override
    public T deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        try {
            T obj = deserializeClass.newInstance();

            while (true) {
                JsonToken token = jp.nextValue();

                String name = jp.getCurrentName();
                Integer val = jsonNameToField.get(name);
                if (val == null || val < 0) {
                    break;
                }
                JsonFieldDeserializer deserializer = idIndexedFieldArray.get(val);
                deserializer.deserialize(obj, jp, dc);
            }

            return obj;
        } catch (InstantiationException e) {
            throw new RuntimeException("Class default contructor thrown exception", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Class does not provide an accesible default contructor", e);
        }
    }

    /**
     * @return serialized class
     */
    @SuppressWarnings("rawtypes")
    public Class getDeserializedClass() {
        return deserializeClass;
    }

    /**
     * @return elements
     */
    public List<JsonFieldDeserializer> getElements() {
        return idIndexedFieldArray;
    }
}
