package org.theeuropeanlibrary.maia.converter.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.theeuropeanlibrary.maia.common.converter.Converter;
import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.theeuropeanlibrary.maia.common.Entity.QualifiedRelation;
import org.theeuropeanlibrary.maia.common.Entity.QualifiedValue;
import org.theeuropeanlibrary.maia.common.TKey;
import org.theeuropeanlibrary.maia.common.AbstractEntity;
import org.theeuropeanlibrary.maia.converter.xml.serializer.EntityOrderedField;
import org.theeuropeanlibrary.maia.converter.xml.serializer.FieldRelation;
import org.theeuropeanlibrary.maia.converter.xml.serializer.XmlFieldConverter;
import org.theeuropeanlibrary.maia.converter.xml.factory.XmlFieldConverterFactory;
import org.theeuropeanlibrary.maia.converter.xml.util.XmlUtil;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Handles the xml serialization of Entity to xml and back.
 *
 * @param <T> generic type of entity
 * @author Nuno Freire (nuno.freire@theeuropeanlibrary.org)
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public abstract class AbstractEntityXmlConverter<T extends AbstractEntity> implements Converter<Element, T>, XmlFieldConverter<T> {

    public static final String XML_NAMESPACE = "http://theeuropeanlibrary.org/internal_object_model";

    private final XmlFieldConverterFactory converterFactory;

    public AbstractEntityXmlConverter(XmlFieldConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    protected abstract T createEntity();

    @Override
    public Class<Element> getEncodeType() {
        return Element.class;
    }

    @Override
    public T decode(Element data) throws ConverterException {
        T entity = createEntity();
        decode(data, entity);
        return entity;
    }

    /**
     * Refills an existing entity with the contents from the XML file
     *
     * @param xmlElement the XML representation of the object
     * @param entity the target
     * @throws ConverterException
     */
    public void decode(Element xmlElement, T entity) throws ConverterException {
        entity.setId(Long.parseLong(xmlElement.getAttribute("ID")));
        Iterable<Element> childNodes = XmlUtil.elements(xmlElement);

        truncateEntity(entity);

        List<EntityOrderedField> mdrFields = new ArrayList<>();
        List<EntityOrderedField> mdrFieldsWithoutIndex = new ArrayList<>();
        List<FieldRelation> mdrRelations = new ArrayList<>();

        for (Element el : childNodes) {
            if (!el.getLocalName().equals("FieldRelation")) {
                String orderIndex = el.getAttribute("OrderIndex");

                if (!el.getLocalName().equals("Generic")) {
                    TKey<?, ?> tkey = converterFactory.getKey(el.getLocalName());
                    XmlFieldConverter fieldDef = converterFactory.getConverter(tkey.getType());
                    if (fieldDef == null) {
                        fieldDef = converterFactory.getBaseTypeConverter(tkey.getType());
                    }
                    Object fldVal = fieldDef.decode(el);

                    HashSet<Enum<?>> qualifiers = new HashSet<>(3);
                    NamedNodeMap attributes = el.getAttributes();
                    for (int i = 0; i < attributes.getLength(); i++) {
                        Node attribute = attributes.item(i);
                        Class qualClass = converterFactory.getQualifier(attribute.getNodeName());
                        if (qualClass != null) {
                            Enum enumVal = Enum.valueOf(qualClass, attribute.getNodeValue());
                            qualifiers.add(enumVal);
                        }
                    }

                    if (orderIndex == null || orderIndex.isEmpty()) {
                        mdrFieldsWithoutIndex.add(new EntityOrderedField(tkey, fldVal,
                                qualifiers.toArray(new Enum<?>[qualifiers.size()]), null));
                    } else {
                        mdrFields.add(new EntityOrderedField(tkey, fldVal,
                                qualifiers.toArray(new Enum<?>[qualifiers.size()]),
                                Double.valueOf(orderIndex)));
                    }
                } else {// a Generic element
                    TKey tkey;
                    HashSet<Enum<?>> qualifiers = new HashSet<>(3);
                    NodeList subChildNodes = el.getChildNodes();
                    for (int j = 0; j < subChildNodes.getLength(); j++) {
                        Node genericItem = subChildNodes.item(j);
                        if (genericItem instanceof Element) {
                            Element subEl = (Element) genericItem;
                            switch (subEl.getLocalName()) {
                                case "TKey":
                                    try {
                                        Class namespace = Class.forName(subEl.getAttribute("Namespace"));
                                        tkey = TKey.resolve(namespace, subEl.getAttribute("Name"));
                                    } catch (ClassNotFoundException e) {
                                        throw new RuntimeException(e.getMessage(), e);
                                    }
                                    break;
                                case "Qualifier":
                                    String value = subEl.getTextContent();
                                    String className = subEl.getAttribute("ClassName");
                                    if (value != null && !value.isEmpty()) {
                                        Class qualClass;
                                        try {
                                            qualClass = Class.forName(className);
                                        } catch (ClassNotFoundException e) {
                                            throw new RuntimeException(e.getMessage(), e);
                                        }
                                        Enum enumVal = Enum.valueOf(qualClass, value);
                                        qualifiers.add(enumVal);
                                    }
                                    break;
                                default:
                                    tkey = converterFactory.getKey(subEl.getLocalName());
                                    XmlFieldConverter fieldDef = converterFactory.getBaseTypeConverter(tkey.getType());
                                    Object fldVal = fieldDef.decode(subEl);
                                    if (orderIndex == null || orderIndex.isEmpty()) {
                                        mdrFieldsWithoutIndex.add(new EntityOrderedField(tkey, fldVal,
                                                qualifiers.toArray(new Enum<?>[qualifiers.size()]),
                                                null));
                                    } else {
                                        mdrFields.add(new EntityOrderedField(tkey, fldVal,
                                                qualifiers.toArray(new Enum<?>[qualifiers.size()]),
                                                Double.valueOf(orderIndex)));
                                    }
                                    break;
                            }
                        }
                    }
                }
            } else {
                int sourceIdx = Integer.parseInt(el.getAttribute("source"));
                int targetIdx = Integer.parseInt(el.getAttribute("target"));

                FieldRelation rel = new FieldRelation(sourceIdx, targetIdx);

                for (Element qEl : XmlUtil.elements(el)) {
                    String value = qEl.getTextContent();
                    String className = qEl.getAttribute("ClassName");
                    if (value != null && !value.isEmpty()) {
                        Class qualClass;
                        try {
                            qualClass = Class.forName(className);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e.getMessage(), e);
                        }
                        Enum enumVal = Enum.valueOf(qualClass, value);
                        rel.getQualifiers().add(enumVal);
                    }
                }

                mdrRelations.add(rel);
            }
        }

        HashMap<Integer, QualifiedValue<?>> fieldRefsByOrder = new HashMap<>(
                mdrFields.size());

        Collections.sort(mdrFields);

        for (EntityOrderedField f : mdrFields) {
            QualifiedValue<?> qv = entity.addValue(f.getTkey(), f.getValue(), f.getQualifiers());
            fieldRefsByOrder.put(f.getOrderIndex().intValue(), qv);
        }

        for (EntityOrderedField f : mdrFieldsWithoutIndex) {
            entity.addValue(f.getTkey(), f.getValue(), f.getQualifiers());
        }

        for (FieldRelation fr : mdrRelations) {
            QualifiedValue<?> src = fieldRefsByOrder.get(fr.getSourceOrdIndex());
            QualifiedValue<?> trg = fieldRefsByOrder.get(fr.getTargetOrdIndex());
            if (src != null && trg != null) {
                entity.addRelation(src, trg, fr.getQualifierArray());
            }
        }
    }

    private void truncateEntity(T bean) {
        // deletes every key in the MDR at the moment
        // can't iterate and delete in one go for concurrency reasons
        Set<TKey<?, ?>> keysToDelete = new LinkedHashSet<>();
        keysToDelete.addAll(bean.getAvailableKeys());
        while (!(keysToDelete.isEmpty())) {
            TKey<?, ?> key = keysToDelete.iterator().next();
            bean.deleteValues(key);
            keysToDelete.remove(key);
        }
    }

    @Override
    public void encode(T mdr, Element xmlParentElement) throws ConverterException {
        xmlParentElement.setAttribute("ID", String.valueOf(mdr.getId()));
        xmlParentElement.setAttribute("xmlns",
                "http://theeuropeanlibrary.org/internal_object_model");

        Set<TKey<?, ?>> keys = mdr.getAvailableKeys();
        if (keys != null && keys.size() > 0) {
            for (TKey<?, ?> key : keys) {
                XmlFieldConverter fieldDef = converterFactory.getConverter(key.getType());
                if (fieldDef == null) {
                    fieldDef = converterFactory.getBaseTypeConverter(key.getType());
                }
                final String elementName = converterFactory.getElementName(key);

                List<?> fieldValues = mdr.getQualifiedValues(key);
                for (Object fieldValue : fieldValues) {
                    QualifiedValue<?> qv = (QualifiedValue<?>) fieldValue;

                    if (fieldDef != null) {
                        Element xmlEl = xmlParentElement.getOwnerDocument().createElementNS(
                                XML_NAMESPACE, elementName);
                        xmlParentElement.appendChild(xmlEl);
                        fieldDef.encode(qv.getValue(), xmlEl);

                        for (Enum<?> q : qv.getQualifiers()) {
                            xmlEl.setAttribute(q.getClass().getSimpleName(), q.toString());
                        }
                        xmlEl.setAttribute("OrderIndex", String.valueOf(qv.getOrderIndex()));
                    } else {// it is a base type
                        Element genericEl = xmlParentElement.getOwnerDocument().createElementNS(
                                XML_NAMESPACE, "Generic");
                        xmlParentElement.appendChild(genericEl);
                        Element tkeyEl = xmlParentElement.getOwnerDocument().createElementNS(
                                XML_NAMESPACE, "TKey");
                        genericEl.appendChild(tkeyEl);
                        tkeyEl.setAttribute("Namespace", key.getNamespace().getName());
// tkeyEl.setAttribute("Type", key.getType().getName());
                        tkeyEl.setAttribute("Name", key.getName());

                        for (Enum<?> q : qv.getQualifiers()) {
                            Element qualEl = xmlParentElement.getOwnerDocument().createElementNS(
                                    XML_NAMESPACE, "Qualifier");
                            genericEl.appendChild(qualEl);
                            qualEl.setTextContent(q.toString());
                            qualEl.setAttribute("ClassName", q.getClass().getName());
                        }
                        genericEl.setAttribute("OrderIndex", String.valueOf(qv.getOrderIndex()));

                        Element valueEl = xmlParentElement.getOwnerDocument().createElementNS(
                                XML_NAMESPACE, converterFactory.getElementName(key));
                        genericEl.appendChild(valueEl);
                        fieldDef.encode(qv.getValue(), valueEl);
                    }
                }
            }
        }

        Set<QualifiedRelation<?, ?>> relations = mdr.getAvailableRelations();
        if (!relations.isEmpty()) {
// Element relationsEl = xmlParentElement.getOwnerDocument().createElementNS(
// XML_NAMESPACE, "FieldRelations");
// xmlParentElement.appendChild(relationsEl);
            for (QualifiedRelation<?, ?> rel : relations) {
                Element relEl = xmlParentElement.getOwnerDocument().createElementNS(
                        XML_NAMESPACE, "FieldRelation");
                xmlParentElement.appendChild(relEl);
                relEl.setAttribute("source", String.valueOf(rel.getSource().getOrderIndex()));
                relEl.setAttribute("target", String.valueOf(rel.getTarget().getOrderIndex()));
                for (Enum<?> q : rel.getQualifiers()) {
                    Element qualEl = xmlParentElement.getOwnerDocument().createElementNS(
                            XML_NAMESPACE, "Qualifier");
                    relEl.appendChild(qualEl);
                    qualEl.setTextContent(q.toString());
                    qualEl.setAttribute("ClassName", q.getClass().getName());
                }
            }
        }

    }

//    private static String getElementName(Class aClass) {
//        return aClass.getSimpleName() + "Object";
//    }
    
    /**
     * Indicates if it is able to convert a given class
     *
     * @param cls
     * @return can or cannot
     */
    public boolean canConvert(Class<?> cls) {
        XmlFieldConverter converter;
        converter = converterFactory.getConverter(cls);
        if (converter != null) {
            return true;
        } else {
            converter = converterFactory.getBaseTypeConverter(cls);
            return converter != null;
        }
    }
}
