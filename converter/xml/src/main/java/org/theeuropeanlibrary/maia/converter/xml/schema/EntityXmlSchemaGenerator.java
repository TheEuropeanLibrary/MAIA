package org.theeuropeanlibrary.maia.converter.xml.schema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.theeuropeanlibrary.maia.common.TKey;
import org.theeuropeanlibrary.maia.common.registry.EntityRegistry;
import org.theeuropeanlibrary.maia.converter.xml.serializer.XmlFieldConverter;
import org.theeuropeanlibrary.maia.converter.xml.factory.XmlFieldConverterFactory;
import org.w3c.dom.DOMException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Handles the xml serialization of Object Model objects in MetadataRecords
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @date 9 de Mai de 2011
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class EntityXmlSchemaGenerator {

    private final EntityRegistry registry;
    private final XmlFieldConverterFactory factory;
    private final XmlFieldConverterSchemaGenerator fieldStandaloneGenerator;
    private final Class<?> entity;

    public EntityXmlSchemaGenerator(EntityRegistry registry, XmlFieldConverterFactory factory, Class<?> entity) {
        this.registry = registry;
        this.factory = factory;
        this.fieldStandaloneGenerator = new XmlFieldConverterSchemaGenerator();
        this.entity = entity;
    }

    /**
     * @param schemaEl
     * @param elementName
     * @param minOccurs
     * @param maxOccurs
     * @param parentClasses
     * @return xml schema
     */
    public Element toXmlSchema(Element schemaEl, String elementName, int minOccurs, int maxOccurs,
            Set<Class<?>> parentClasses) {
        if (parentClasses.contains(entity)) {
            return null;
        }
        parentClasses.add(entity);

        try {
            Set<TKey> complexKeys = new HashSet<>();
            Set<TKey> baseKeys = new HashSet<>();
            Set<Class<? extends Enum<?>>> allQualifiers = new HashSet<>();
            for (TKey key : registry.getAvailableKeys()) {
                Set<Class<? extends Enum<?>>> validEnums = registry.getQualifiers(key);
                if (validEnums != null) {
                    allQualifiers.addAll(validEnums);
                }
                if (Enum.class.isAssignableFrom(key.getType())) {
                    allQualifiers.add(key.getType());
                }
                if (factory.getBaseTypeConverter(key.getType()) != null) {
                    baseKeys.add(key);
                } else {
                    complexKeys.add(key);
                }
            }
            List<Class<? extends Enum<?>>> allQualifiersSorted = new ArrayList<>(
                    allQualifiers);
            Collections.sort(allQualifiersSorted, new Comparator<Class<? extends Enum<?>>>() {
                @Override
                public int compare(Class<? extends Enum<?>> o1, Class<? extends Enum<?>> o2) {
                    return o1.getSimpleName().compareTo(o2.getSimpleName());
                }
            });

            addQualifiers(allQualifiersSorted, schemaEl);

            addComplexKeys(complexKeys, schemaEl, parentClasses);

            addBaseTypeKeys(baseKeys, schemaEl, parentClasses);

            addGenericType(schemaEl, baseKeys);

            addEntityCollectionElement(schemaEl);

            Element recordElementEl = addEntityElement(schemaEl, complexKeys, baseKeys);
            return recordElementEl;
        } finally {
            parentClasses.remove(entity);
        }
    }

    private void addEntityCollectionElement(Element schemaEl) throws DOMException {
        Element metadataElementEl = schemaEl.getOwnerDocument().createElement("xs:element");
        schemaEl.appendChild(metadataElementEl);
        metadataElementEl.setAttribute("name", entity.getSimpleName() + "Collection");
        Element complexTypeEl = schemaEl.getOwnerDocument().createElement("xs:complexType");
        metadataElementEl.appendChild(complexTypeEl);
        Element sequenceEl = schemaEl.getOwnerDocument().createElement("xs:sequence");
        complexTypeEl.appendChild(sequenceEl);
        sequenceEl.setAttribute("minOccurs", "0");
        sequenceEl.setAttribute("maxOccurs", "unbounded");
        Element subElementEl = schemaEl.getOwnerDocument().createElement("xs:element");
        sequenceEl.appendChild(subElementEl);
        subElementEl.setAttribute("ref", entity.getSimpleName());
    }

    private Element addEntityElement(Element schemaEl, Set<TKey> complexKeys, Set<TKey> baseKeys) throws DOMException {
        Element recordElementEl = schemaEl.getOwnerDocument().createElement("xs:element");

        schemaEl.appendChild(recordElementEl);
        recordElementEl.setAttribute("name", entity.getSimpleName());
        Element complexTypeEl = schemaEl.getOwnerDocument().createElement("xs:complexType");
        recordElementEl.appendChild(complexTypeEl);
        Element topSequenceEl = schemaEl.getOwnerDocument().createElement("xs:sequence");
        complexTypeEl.appendChild(topSequenceEl);

        Element modelSequenceEl = schemaEl.getOwnerDocument().createElement("xs:sequence");
        topSequenceEl.appendChild(modelSequenceEl);
        modelSequenceEl.setAttribute("minOccurs", "0");
        modelSequenceEl.setAttribute("maxOccurs", "unbounded");
        Element choiceEl = schemaEl.getOwnerDocument().createElement("xs:choice");
        modelSequenceEl.appendChild(choiceEl);
        for (TKey key : complexKeys) {
            Element subElementEl = schemaEl.getOwnerDocument().createElement("xs:element");
            choiceEl.appendChild(subElementEl);
            subElementEl.setAttribute("ref", factory.getElementName(key));
        }
        for (TKey key : baseKeys) {
            Element subElementEl = schemaEl.getOwnerDocument().createElement("xs:element");
            choiceEl.appendChild(subElementEl);
            subElementEl.setAttribute("ref", factory.getElementName(key));
        }
        {// allow also the Generic element
            Element subElementEl = schemaEl.getOwnerDocument().createElement("xs:element");
            choiceEl.appendChild(subElementEl);
            subElementEl.setAttribute("ref", "Generic");
        }
        {// allow also the fieldRelation element
//                    Element fieldRelationsEl = schemaEl.getOwnerDocument().createElement("xs:element");
//                    fieldRelationsEl.setAttribute("name", "FieldRelations");
//                    fieldRelationsEl.setAttribute("minOccurs", "0");
//                    fieldRelationsEl.setAttribute("maxOccurs", "1");
//                    Element frCcomplexTypeEl = schemaEl.getOwnerDocument().createElement("xs:complexType");
//                    fieldRelationsEl.appendChild(frCcomplexTypeEl);

            Element relEl = schemaEl.getOwnerDocument().createElement("xs:element");
            relEl.setAttribute("name", "FieldRelation");
            relEl.setAttribute("minOccurs", "1");
            relEl.setAttribute("maxOccurs", "unbounded");
            choiceEl.appendChild(relEl);

            Element relElComplexTypeEl = schemaEl.getOwnerDocument().createElement("xs:complexType");
            relEl.appendChild(relElComplexTypeEl);

            Element relElSequenceEl = schemaEl.getOwnerDocument().createElement("xs:sequence");
            relElComplexTypeEl.appendChild(relElSequenceEl);
            relElSequenceEl.setAttribute("minOccurs", "0");
            relElSequenceEl.setAttribute("maxOccurs", "unbounded");

//                    relElComplexTypeEl
            {
                Element qualifierEl = schemaEl.getOwnerDocument().createElement("xs:element");
                relElSequenceEl.appendChild(qualifierEl);
                qualifierEl.setAttribute("name", "Qualifier");
                qualifierEl.setAttribute("minOccurs", "0");
                qualifierEl.setAttribute("maxOccurs", "unbounded");

                Element qualComplexTypeEl = schemaEl.getOwnerDocument().createElement(
                        "xs:complexType");
                qualifierEl.appendChild(qualComplexTypeEl);

                Element attrSimpleContentEl = schemaEl.getOwnerDocument().createElement(
                        "xs:simpleContent");
                qualComplexTypeEl.appendChild(attrSimpleContentEl);
                Element attrSimpleContentExtensionEl = schemaEl.getOwnerDocument().createElement(
                        "xs:extension");
                attrSimpleContentEl.appendChild(attrSimpleContentExtensionEl);
                attrSimpleContentExtensionEl.setAttribute("base", "xs:string");

                Element attributeEl = schemaEl.getOwnerDocument().createElement("xs:attribute");
                attrSimpleContentExtensionEl.appendChild(attributeEl);
                attributeEl.setAttribute("name", "ClassName");
                attributeEl.setAttribute("type", "xs:string");
            }
            Element attributeEl = schemaEl.getOwnerDocument().createElement("xs:attribute");
            relElComplexTypeEl.appendChild(attributeEl);
            attributeEl.setAttribute("name", "source");
            attributeEl.setAttribute("type", "xs:long");

            attributeEl = schemaEl.getOwnerDocument().createElement("xs:attribute");
            relElComplexTypeEl.appendChild(attributeEl);
            attributeEl.setAttribute("name", "target");
            attributeEl.setAttribute("type", "xs:long");
//                    topSequenceEl.appendChild(fieldRelationsEl);
        }

        Element attributeEl = schemaEl.getOwnerDocument().createElement("xs:attribute");
        complexTypeEl.appendChild(attributeEl);
        attributeEl.setAttribute("name", "ID");
        attributeEl.setAttribute("type", "xs:string");
//                <xs:element maxOccurs="unbounded" minOccurs="0" name="Qualifier"><xs:complexType><xs:simpleContent><xs:extension base="xs:string"><xs:attribute name="ClassName" type="xs:string"/></xs:extension></xs:simpleContent></xs:complexType></xs:element>

        // Element
        // originalEl=schemaEl.getOwnerDocument().createElement("xs:element");
        // originalEl.setAttribute("name", "SourceMetadataRecord");
        // originalEl.setAttribute("minOccurs", "0");
        // topSequenceEl.appendChild(originalEl);
        //
        // Element
        // complexTypeEl2=schemaEl.getOwnerDocument().createElement("xs:complexType");
        // originalEl.appendChild(complexTypeEl2);
        // Element
        // sequenceEl2=schemaEl.getOwnerDocument().createElement("xs:sequence");
        // complexTypeEl2.appendChild(sequenceEl2);
        // sequenceEl2.setAttribute("minOccurs", "1");
        // sequenceEl2.setAttribute("maxOccurs", "1");
        //
        // Element
        // anyEl=schemaEl.getOwnerDocument().createElement("xs:any");
        // anyEl.setAttribute("minOccurs", "1");
        // anyEl.setAttribute("maxOccurs", "1");
        // anyEl.setAttribute("namespace", "##other");
        // anyEl.setAttribute("processContents", "strict");
        // sequenceEl2.appendChild(anyEl);
        return recordElementEl;
    }

    private void addGenericType(Element schemaEl, Set<TKey> baseKeys) throws DOMException {
        // A generic element that may contain any base type with any TKey
        Element otherElementEl = schemaEl.getOwnerDocument().createElement("xs:element");
        {
            schemaEl.appendChild(otherElementEl);
            otherElementEl.setAttribute("name", "Generic");
            Element complexTypeEl = schemaEl.getOwnerDocument().createElement("xs:complexType");
            otherElementEl.appendChild(complexTypeEl);
            Element sequenceEl = schemaEl.getOwnerDocument().createElement("xs:sequence");
            complexTypeEl.appendChild(sequenceEl);

            Element attrEl = schemaEl.getOwnerDocument().createElement("xs:attribute");
            complexTypeEl.appendChild(attrEl);
            attrEl.setAttribute("name", "OrderIndex");
            attrEl.setAttribute("type", "xs:decimal");
// sequenceEl.setAttribute("minOccurs", "0");
// sequenceEl.setAttribute("maxOccurs", "unbounded");

            {
                Element tKeyEl = schemaEl.getOwnerDocument().createElement("xs:element");
                sequenceEl.appendChild(tKeyEl);
                tKeyEl.setAttribute("name", "TKey");

                Element qualComplexTypeEl = schemaEl.getOwnerDocument().createElement(
                        "xs:complexType");
                tKeyEl.appendChild(qualComplexTypeEl);

                Element attrSimpleContentEl = schemaEl.getOwnerDocument().createElement(
                        "xs:simpleContent");
                qualComplexTypeEl.appendChild(attrSimpleContentEl);
                Element attrSimpleContentExtensionEl = schemaEl.getOwnerDocument().createElement(
                        "xs:extension");
                attrSimpleContentEl.appendChild(attrSimpleContentExtensionEl);
                attrSimpleContentExtensionEl.setAttribute("base", "xs:string");

                Element attributeEl = schemaEl.getOwnerDocument().createElement("xs:attribute");
                attrSimpleContentExtensionEl.appendChild(attributeEl);
                attributeEl.setAttribute("name", "Namespace");
                attributeEl.setAttribute("type", "xs:string");
// attributeEl = schemaEl.getOwnerDocument().createElement("xs:attribute");
// qualComplexTypeEl.appendChild(attributeEl);
// attributeEl.setAttribute("name", "Type");
// attributeEl.setAttribute("type", "xs:string");
                attributeEl = schemaEl.getOwnerDocument().createElement("xs:attribute");
                attrSimpleContentExtensionEl.appendChild(attributeEl);
                attributeEl.setAttribute("name", "Name");
                attributeEl.setAttribute("type", "xs:string");
            }

            {
                Element qualifierEl = schemaEl.getOwnerDocument().createElement("xs:element");
                sequenceEl.appendChild(qualifierEl);
                qualifierEl.setAttribute("name", "Qualifier");
                qualifierEl.setAttribute("minOccurs", "0");
                qualifierEl.setAttribute("maxOccurs", "unbounded");

                Element qualComplexTypeEl = schemaEl.getOwnerDocument().createElement(
                        "xs:complexType");
                qualifierEl.appendChild(qualComplexTypeEl);

                Element attrSimpleContentEl = schemaEl.getOwnerDocument().createElement(
                        "xs:simpleContent");
                qualComplexTypeEl.appendChild(attrSimpleContentEl);
                Element attrSimpleContentExtensionEl = schemaEl.getOwnerDocument().createElement(
                        "xs:extension");
                attrSimpleContentEl.appendChild(attrSimpleContentExtensionEl);
                attrSimpleContentExtensionEl.setAttribute("base", "xs:string");

                Element attributeEl = schemaEl.getOwnerDocument().createElement("xs:attribute");
                attrSimpleContentExtensionEl.appendChild(attributeEl);
                attributeEl.setAttribute("name", "ClassName");
                attributeEl.setAttribute("type", "xs:string");
            }

            Element choiceEl = schemaEl.getOwnerDocument().createElement("xs:choice");
            sequenceEl.appendChild(choiceEl);
            Set<String> uniques = new HashSet<>();
            for (TKey key : baseKeys) {
                uniques.add(key.getType().getSimpleName());
            }
            for (String unique : uniques) {
                Element subElementEl = schemaEl.getOwnerDocument().createElement("xs:element");
                choiceEl.appendChild(subElementEl);
                subElementEl.setAttribute("ref", unique);
            }
        }
    }

    private void addBaseTypeKeys(Set<TKey> baseKeys, Element schemaEl, Set<Class<?>> parentClasses) {
        Set<Class<?>> bases = new HashSet<>();
        for (TKey key : baseKeys) {
            XmlFieldConverter serializer = factory.getBaseTypeConverter(key.getType());
            Element elementEl = fieldStandaloneGenerator.toXmlSchema(serializer, schemaEl, factory.getElementName(key), 1,
                    1, parentClasses);

            Set<Class<? extends Enum<?>>> validEnums = registry.getQualifiers(key);
            Node typeEl = elementEl.getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0);
            if (validEnums != null) {
                for (Class<? extends Enum<?>> qualClass : validEnums) {
                    Element attrEl = schemaEl.getOwnerDocument().createElement("xs:attribute");
                    typeEl.appendChild(attrEl);
                    attrEl.setAttribute("name", qualClass.getSimpleName());
                    attrEl.setAttribute("type", qualClass.getSimpleName() + "Enum");
                }
            }
            Element attrEl = schemaEl.getOwnerDocument().createElement("xs:attribute");
            typeEl.appendChild(attrEl);
            attrEl.setAttribute("name", "OrderIndex");
            attrEl.setAttribute("type", "xs:decimal");

            bases.add(key.getType());
        }
        for (Class<?> base : bases) {
            XmlFieldConverter serializer = factory.getBaseTypeConverter(base);
            fieldStandaloneGenerator.toXmlSchema(serializer, schemaEl, base.getSimpleName(), 1,
                    1, parentClasses);
        }
    }

    private void addComplexKeys(Set<TKey> complexKeys, Element schemaEl, Set<Class<?>> parentClasses) throws DOMException {
        for (TKey key : complexKeys) {
            Set<Class<? extends Enum<?>>> validEnums = registry.getQualifiers(key);

            if (key.getType().isEnum()) {
                Element subElementEl = schemaEl.getOwnerDocument().createElement("xs:element");
                schemaEl.appendChild(subElementEl);
                subElementEl.setAttribute("name", factory.getElementName(key));
                Element complexTypeEl = schemaEl.getOwnerDocument().createElement(
                        "xs:complexType");
                subElementEl.appendChild(complexTypeEl);
                Element simpleContentEl = schemaEl.getOwnerDocument().createElement(
                        "xs:simpleContent");
                complexTypeEl.appendChild(simpleContentEl);
                Element extensionEl = schemaEl.getOwnerDocument().createElement("xs:extension");
                simpleContentEl.appendChild(extensionEl);
                extensionEl.setAttribute("base", factory.getElementName(key) + "Enum");
                if (validEnums != null) {
                    for (Class<? extends Enum<?>> qualClass : validEnums) {
                        Element attrEl = schemaEl.getOwnerDocument().createElement("xs:attribute");
                        extensionEl.appendChild(attrEl);
                        attrEl.setAttribute("name", qualClass.getSimpleName());
                        attrEl.setAttribute("type", qualClass.getSimpleName() + "Enum");
                    }
                }
                Element attrEl = schemaEl.getOwnerDocument().createElement("xs:attribute");
                extensionEl.appendChild(attrEl);
                attrEl.setAttribute("name", "OrderIndex");
                attrEl.setAttribute("type", "xs:decimal");

                // <xs:element name="Language">
                // <xs:complexType>
                // <xs:simpleContent>
                // <xs:extension base="LanguageType">
                // <xs:attribute name="LanguageRelation"
                // type="LanguageRelationQualifier"/>
                // </xs:extension>
                // </xs:simpleContent>
                // </xs:complexType>
            } else {
                XmlFieldConverter serializer = factory.getConverter(key.getType());
                Element elementEl = fieldStandaloneGenerator.toXmlSchema(serializer, schemaEl,
                        factory.getElementName(key), 1, 1, parentClasses);

                Node typeEl = elementEl.getChildNodes().item(0);
                if (validEnums != null) {
                    for (Class<? extends Enum<?>> qualClass : validEnums) {
                        Element attrEl = schemaEl.getOwnerDocument().createElement("xs:attribute");
                        typeEl.appendChild(attrEl);
                        attrEl.setAttribute("name", qualClass.getSimpleName());
                        attrEl.setAttribute("type", qualClass.getSimpleName() + "Enum");
                    }
                }
                Element attrEl = schemaEl.getOwnerDocument().createElement("xs:attribute");
                typeEl.appendChild(attrEl);
                attrEl.setAttribute("name", "OrderIndex");
                attrEl.setAttribute("type", "xs:decimal");
            }
        }
    }

    private void addQualifiers(List<Class<? extends Enum<?>>> allQualifiersSorted, Element schemaEl) throws DOMException {
        for (Class<? extends Enum<?>> qualClass : allQualifiersSorted) {
            Element simpleTypeEl = schemaEl.getOwnerDocument().createElement("xs:simpleType");
            schemaEl.appendChild(simpleTypeEl);
            simpleTypeEl.setAttribute("name", qualClass.getSimpleName() + "Enum");
            Element restrictionEl = schemaEl.getOwnerDocument().createElement("xs:restriction");
            simpleTypeEl.appendChild(restrictionEl);
            restrictionEl.setAttribute("base", "xs:string");

            for (Enum e : qualClass.getEnumConstants()) {
                Element enumEl = schemaEl.getOwnerDocument().createElement("xs:enumeration");
                restrictionEl.appendChild(enumEl);
                enumEl.setAttribute("value", e.toString());
            }
        }
    }
}
