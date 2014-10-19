package org.theeuropeanlibrary.maia.converter.xml;

import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.theeuropeanlibrary.maia.common.definitions.Dataset;
import org.theeuropeanlibrary.maia.common.definitions.Provider;
import org.theeuropeanlibrary.maia.converter.xml.common.XmlFieldConverterFactory;
import org.theeuropeanlibrary.maia.converter.xml.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This class converts pOroviders from and to xml.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public class ProviderEntityXmlConverter extends AbstractEntityXmlConverter<Provider> {

    public ProviderEntityXmlConverter(XmlFieldConverterFactory factory) {
        super(factory);
    }

    @Override
    protected Provider createEntity() {
        return new Provider();
    }

    @Override
    public Class<Provider> getDecodeType() {
        return Provider.class;
    }
    
    @Override
    public Element encode(Provider provider) throws ConverterException {
        Document newDocument = XmlUtil.newDocument();
        newDocument.appendChild(newDocument.createElement("Provider"));
        encode(provider, newDocument.getDocumentElement());
        return newDocument.getDocumentElement();
    }
}
