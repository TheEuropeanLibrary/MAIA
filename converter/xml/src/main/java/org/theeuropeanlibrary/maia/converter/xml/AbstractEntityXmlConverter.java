package org.theeuropeanlibrary.maia.converter.xml;

import org.theeuropeanlibrary.maia.common.Converter;
import org.theeuropeanlibrary.maia.common.ConverterException;
import org.theeuropeanlibrary.maia.common.definitions.AbstractEntity;
import org.w3c.dom.Element;

/**
 * Handles the xml serialization of Entity to xml and back.
 *
 * @param <T> generic type of entity
 * @author Nuno Freire (nuno.freire@theeuropeanlibrary.org)
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public abstract class AbstractEntityXmlConverter<T extends AbstractEntity> implements Converter<Element, T> {

    @Override
    public Class<Element> getEncodeType() {
        return Element.class;
    }

    @Override
    public Element encode(T data) throws ConverterException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T decode(Element data) throws ConverterException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
