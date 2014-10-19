/* XmlFieldStandaloneSerializerSchemaGenerator.java - created on 23 de Fev de 2013, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.maia.converter.xml.schema;

import java.util.Set;
import org.theeuropeanlibrary.maia.converter.xml.base.AnnotationBasedXmlConverter;

import org.theeuropeanlibrary.maia.converter.xml.base.BaseTypeEncoderBasedXmlSerializer;
import org.theeuropeanlibrary.maia.converter.xml.base.MetadataXmlFieldSerializer;
import org.theeuropeanlibrary.maia.converter.xml.base.ProxyXmlFieldStandaloneSerializer;
import org.theeuropeanlibrary.maia.converter.xml.common.XmlFieldConverter;
import org.theeuropeanlibrary.maia.converter.xml.util.XmlElementDefinition;
import org.w3c.dom.Element;

/**
 * Generates the schema entries for all xml field serializer.
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @since 23 de Fev de 2013
 */
@SuppressWarnings("rawtypes")
public class XmlFieldConverterSchemaGenerator {

    private final XmlFieldSerializerSchemaGenerator fieldSchemaGenerator;
    private final BaseTypeEncoderSchemaGenerator baseTypeGenerator;

    /**
     * Creates a new instance of this class.
     */
    public XmlFieldConverterSchemaGenerator() {
        baseTypeGenerator = new BaseTypeEncoderSchemaGenerator();
        fieldSchemaGenerator = new XmlFieldSerializerSchemaGenerator(this, baseTypeGenerator);
    }

    /**
     * Generate the XML schema definition for the serialized object type
     *
     * @param serializer serializer
     * @param schemaEl the parent schema element
     * @param elementName the name of the XML element used for serializing this
     * field
     * @param minOccurs the minimum occurrences of the element
     * @param maxOccurs the maximum occurrences of the element
     * @param parentClasses parent class for limiting recursions
     * @return The XML Element with the schema definition for this element
     */
    public Element toXmlSchema(XmlFieldConverter serializer, Element schemaEl,
            String elementName, int minOccurs, int maxOccurs, Set<Class<?>> parentClasses) {
        if (serializer instanceof AnnotationBasedXmlConverter) {
            return toXmlSchema((AnnotationBasedXmlConverter) serializer, schemaEl, elementName,
                    minOccurs, maxOccurs, parentClasses);
        }
        if (serializer instanceof BaseTypeEncoderBasedXmlSerializer) {
            return toXmlSchema((BaseTypeEncoderBasedXmlSerializer) serializer, schemaEl,
                    elementName, minOccurs, maxOccurs, parentClasses);
        }
        if (serializer instanceof MetadataXmlFieldSerializer) {
            return toXmlSchema((MetadataXmlFieldSerializer) serializer, schemaEl, elementName,
                    minOccurs, maxOccurs, parentClasses);
        }
        if (serializer instanceof ProxyXmlFieldStandaloneSerializer) {
            return toXmlSchema((ProxyXmlFieldStandaloneSerializer) serializer, schemaEl,
                    elementName, minOccurs, maxOccurs, parentClasses);
        } else {
            throw new IllegalArgumentException("serializer implementation not supported: "
                    + serializer.getClass().getName());
        }
    }

    /**
     * Generate the XML schema definition for the serialized object type
     *
     * @param schemaEl the parent schema element
     * @param elementName the name of the XML element used for serializing this
     * field
     * @param minOccurs the minimum occurrences of the element
     * @param maxOccurs the maximum occurrences of the element
     * @param parentClasses parent class for limiting recursions
     * @return The XML Element with the schema definition for this element
     */
    private Element toXmlSchema(AnnotationBasedXmlConverter serializer, Element parentEl,
            String elementName, int minOccurs, int maxOccurs, Set<Class<?>> parentClasses) {
        Class theClass = serializer.getSerializedClass();
        if (parentClasses.contains(theClass)) {
            return null;
        }
        parentClasses.add(theClass);

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
        Element seqEl = parentEl.getOwnerDocument().createElement("xs:sequence");
        complexTypeEl.appendChild(seqEl);

        for (int i = 1; i < serializer.getElements().size(); i++) {
            XmlElementDefinition conv = (XmlElementDefinition) serializer.getElements().get(i);
            if (conv != null) {
                fieldSchemaGenerator.toXmlSchema(conv.getSerializer(), seqEl,
                        conv.getElementName(), 0, 1, parentClasses);
            }
        }

        parentClasses.remove(theClass);
        return elementEl;
    }

    /**
     * Generate the XML schema definition for the serialized object type
     *
     * @param schemaEl the parent schema element
     * @param elementName the name of the XML element used for serializing this
     * field
     * @param minOccurs the minimum occurrences of the element
     * @param maxOccurs the maximum occurrences of the element
     * @param parentClasses parent class for limiting recursions
     * @return The XML Element with the schema definition for this element
     */
    private Element toXmlSchema(BaseTypeEncoderBasedXmlSerializer serializer, Element parentEl,
            String elementName, int minOccurs, int maxOccurs, Set<Class<?>> parentClasses) {
        boolean isTopElement = parentEl.getChildNodes().getLength() > 0;
        Element elementEl = parentEl.getOwnerDocument().createElement("xs:element");
        baseTypeGenerator.toXmlSchema(serializer.getEncoder(), elementEl);

        if (isTopElement) {
            Element simpleTypeEl = (Element) elementEl.getChildNodes().item(0);
            if (simpleTypeEl != null) {
                parentEl.appendChild(simpleTypeEl);
                simpleTypeEl.setAttribute("name", elementName + "Type");
                return simpleTypeEl;
            } else {
// encoder.toXmlSchema(parentEl);
                parentEl.appendChild(elementEl);
                elementEl.setAttribute("name", elementName);
                return elementEl;
            }
        } else {
            parentEl.appendChild(elementEl);
            elementEl.setAttribute("name", elementName);
            return elementEl;
        }
    }

    /**
     * Generate the XML schema definition for the serialized object type
     *
     * @param schemaEl the parent schema element
     * @param elementName the name of the XML element used for serializing this
     * field
     * @param minOccurs the minimum occurrences of the element
     * @param maxOccurs the maximum occurrences of the element
     * @param parentClasses parent class for limiting recursions
     * @return The XML Element with the schema definition for this element
     */
    private Element toXmlSchema(ProxyXmlFieldStandaloneSerializer serializer, Element schemaEl,
            String elementName, int minOccurs, int maxOccurs, Set<Class<?>> parentClasses) {
        return toXmlSchema(serializer.getProxiedSerializer(), schemaEl, elementName, minOccurs,
                maxOccurs, parentClasses);
    }

    /**
     * Generate the XML schema definition for the serialized object type
     *
     * @param schemaEl the parent schema element
     * @param elementName the name of the XML element used for serializing this
     * field
     * @param minOccurs the minimum occurrences of the element
     * @param maxOccurs the maximum occurrences of the element
     * @param parentClasses parent class for limiting recursions
     * @return The XML Element with the schema definition for this element
     */
    private Element toXmlSchema(MetadataXmlFieldSerializer serializer, Element schemaEl,
            String elementName, int minOccurs, int maxOccurs, Set<Class<?>> parentClasses) {
        Element originalEl = schemaEl.getOwnerDocument().createElement("xs:element");
        originalEl.setAttribute("name", elementName);
        schemaEl.appendChild(originalEl);

        Element complexTypeEl2 = schemaEl.getOwnerDocument().createElement("xs:complexType");
        originalEl.appendChild(complexTypeEl2);
        Element sequenceEl2 = schemaEl.getOwnerDocument().createElement("xs:sequence");
        complexTypeEl2.appendChild(sequenceEl2);
// sequenceEl2.setAttribute("minOccurs", "1");
// sequenceEl2.setAttribute("maxOccurs", "1");

        Element anyEl = schemaEl.getOwnerDocument().createElement("xs:any");
        anyEl.setAttribute("minOccurs", "1");
        anyEl.setAttribute("maxOccurs", "1");
// anyEl.setAttribute("namespace", "##other");
        anyEl.setAttribute("processContents", "skip");
        sequenceEl2.appendChild(anyEl);

        Element attributeEl = schemaEl.getOwnerDocument().createElement("xs:attribute");
        attributeEl.setAttribute("name", "Format");
        attributeEl.setAttribute("type", "xs:string");
        complexTypeEl2.appendChild(attributeEl);

        return originalEl;
    }

}
