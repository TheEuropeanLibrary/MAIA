package org.theeuropeanlibrary.maia.converter.xml.util;

import org.theeuropeanlibrary.maia.converter.xml.common.XmlFieldConverter;
import org.theeuropeanlibrary.maia.converter.xml.common.XmlFieldSerializer;

/**
 * Holds the definition for the serialization of a data element
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @date 29 de Abr de 2011
 */
public class XmlElementDefinition {

    private String elementName;
    private XmlFieldSerializer serializer;

    /**
     * Creates a new instance of this class.
     *
     * @param elementName the name of XML element to use
     * @param serializer The XmlFieldConverter implementation to use
     */
    public XmlElementDefinition(String elementName, XmlFieldSerializer serializer) {
        super();
        this.elementName = elementName;
        this.serializer = serializer;
    }

    /**
     * Returns the elementName.
     *
     * @return the elementName
     */
    public String getElementName() {
        return elementName;
    }

    /**
     * Sets the elementName to the given value.
     *
     * @param elementName the elementName to set
     */
    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    /**
     * @return serializer
     */
    public XmlFieldSerializer getSerializer() {
        return serializer;
    }

    /**
     * @param serializer the serializer to set
     */
    public void setSerializer(XmlFieldSerializer serializer) {
        this.serializer = serializer;
    }
}
