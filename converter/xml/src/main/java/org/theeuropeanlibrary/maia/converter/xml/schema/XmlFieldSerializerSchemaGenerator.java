/* XmlFieldSerializerSchemaGenerator.java - created on 23 de Fev de 2013, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.xml.schema;

import java.util.Set;

import org.theeuropeanlibrary.maia.converter.xml.serializer.BaseTypeXmlSerializer;
import org.theeuropeanlibrary.maia.converter.xml.serializer.CollectionXmlFieldSerializer;
import org.theeuropeanlibrary.maia.converter.xml.serializer.MultiClassXmlFieldSerializer;
import org.theeuropeanlibrary.maia.converter.xml.serializer.ObjectToXmlFieldSerializer;
import org.theeuropeanlibrary.maia.converter.xml.serializer.XmlFieldConverter;
import org.theeuropeanlibrary.maia.converter.xml.serializer.XmlFieldSerializer;
import org.w3c.dom.Element;

/**
 * Generates the schema entries for all xml field serializer.
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @since 23 de Fev de 2013
 */
@SuppressWarnings("rawtypes")
public class XmlFieldSerializerSchemaGenerator {

    private final XmlFieldConverterSchemaGenerator fieldSchemaGenerator;
    private final BaseTypeEncoderSchemaGenerator baseTypeGenerator;

    /**
     * Creates a new instance of this class.
     *
     * @param fieldSchemaGenerator
     * @param baseTypeGenerator
     */
    public XmlFieldSerializerSchemaGenerator(XmlFieldConverterSchemaGenerator fieldSchemaGenerator,
            BaseTypeEncoderSchemaGenerator baseTypeGenerator) {
        super();
        this.fieldSchemaGenerator = fieldSchemaGenerator;
        this.baseTypeGenerator = baseTypeGenerator;
    }

    /**
     * Generate the XML schema definition for the serialized field
     *
     * @param serializer serializer
     * @param schemaEl the parent schema element
     * @param elementName the name of the XML element used for serializing this
     * field
     * @param minOccurs the minimum occurrences of the element
     * @param maxOccurs the maximum occurrences of the element
     * @param parentClasses classes above in the schema - these should not be
     * recursively part of the schema
     */
    public void toXmlSchema(XmlFieldSerializer serializer, Element schemaEl,
            String elementName, int minOccurs, int maxOccurs, Set<Class<?>> parentClasses) {
        if (serializer instanceof ObjectToXmlFieldSerializer) {
            toXmlSchema((ObjectToXmlFieldSerializer) serializer, schemaEl, elementName, minOccurs,
                    maxOccurs, parentClasses);
        } else if (serializer instanceof MultiClassXmlFieldSerializer) {
            toXmlSchema((MultiClassXmlFieldSerializer) serializer, schemaEl, elementName, minOccurs,
                    maxOccurs, parentClasses);
        } else if (serializer instanceof CollectionXmlFieldSerializer) {
            toXmlSchema((CollectionXmlFieldSerializer) serializer, schemaEl, elementName, minOccurs,
                    maxOccurs, parentClasses);
        } else if (serializer instanceof BaseTypeXmlSerializer) {
            toXmlSchema((BaseTypeXmlSerializer) serializer, schemaEl, elementName, minOccurs,
                    maxOccurs, parentClasses);
        } else {
            throw new IllegalArgumentException("serializer implementation not supported: "
                    + serializer.getClass().getName());
        }
    }

    /**
     * @param serializer
     * @param schemaEl
     * @param elementName
     * @param minOccurs
     * @param maxOccurs
     * @param parentClasses
     */
    public void toXmlSchema(ObjectToXmlFieldSerializer serializer, Element schemaEl,
            String elementName, int minOccurs, int maxOccurs, Set<Class<?>> parentClasses) {
        fieldSchemaGenerator.toXmlSchema(serializer.getObjectSerializer(), schemaEl, elementName,
                minOccurs, maxOccurs, parentClasses);
    }

    /**
     * @param serializer
     * @param parentEl
     * @param elementName
     * @param minOccurs
     * @param maxOccurs
     * @param parentClasses
     */
    public void toXmlSchema(MultiClassXmlFieldSerializer serializer, Element parentEl,
            String elementName, int minOccurs, int maxOccurs, Set<Class<?>> parentClasses) {
        Element elementEl = parentEl.getOwnerDocument().createElement("xs:element");
        parentEl.appendChild(elementEl);
        elementEl.setAttribute("name", elementName);

        if (minOccurs != 1) {
            elementEl.setAttribute("minOccurs", String.valueOf(minOccurs));
        }
        if (maxOccurs != 1) {
            if (maxOccurs == -1) {
                elementEl.setAttribute("maxOccurs", "unbounded");
            } else {
                elementEl.setAttribute("maxOccurs", String.valueOf(maxOccurs));
            }
        }

        Element complexTypeEl = parentEl.getOwnerDocument().createElement("xs:complexType");
        elementEl.appendChild(complexTypeEl);
        Element choiceEl = parentEl.getOwnerDocument().createElement("xs:choice");
        complexTypeEl.appendChild(choiceEl);

        for (Class convClass : serializer.getConverters().keySet()) {
            if (parentClasses.contains(convClass)) {
                continue;
            }
            XmlFieldConverter conv = serializer.getConverters().get(convClass);
            fieldSchemaGenerator.toXmlSchema(conv, choiceEl, convClass.getSimpleName(), minOccurs,
                    maxOccurs, parentClasses);
        }
    }

    /**
     * @param serializer
     * @param parentEl
     * @param elementName
     * @param minOccurs
     * @param maxOccurs
     * @param parentClasses
     */
    public void toXmlSchema(CollectionXmlFieldSerializer serializer, Element parentEl,
            String elementName, int minOccurs, int maxOccurs, Set<Class<?>> parentClasses) {
        fieldSchemaGenerator.toXmlSchema(serializer.getItemSerializer(), parentEl, elementName,
                minOccurs, -1, parentClasses);
    }

    /**
     * @param serializer
     * @param parentEl
     * @param elementName
     * @param minOccurs
     * @param maxOccurs
     * @param parentClasses
     */
    public void toXmlSchema(BaseTypeXmlSerializer serializer, Element parentEl, String elementName,
            int minOccurs, int maxOccurs, Set<Class<?>> parentClasses) {
        Element elementEl = parentEl.getOwnerDocument().createElement("xs:element");
        parentEl.appendChild(elementEl);
        elementEl.setAttribute("name", elementName);
        if (minOccurs != 1) {
            elementEl.setAttribute("minOccurs", String.valueOf(minOccurs));
        }
        if (maxOccurs != 1) {
            if (maxOccurs == -1) {
                elementEl.setAttribute("maxOccurs", "unbounded");
            } else {
                elementEl.setAttribute("maxOccurs", String.valueOf(maxOccurs));
            }
        }
        baseTypeGenerator.toXmlSchema(serializer.getEncoder(), elementEl);
    }
}
