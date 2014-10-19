package org.theeuropeanlibrary.maia.converter.xml.schema;

import org.theeuropeanlibrary.maia.converter.xml.basetype.BaseTypeEncoder;
import org.theeuropeanlibrary.maia.converter.xml.basetype.BooleanEncoder;
import org.theeuropeanlibrary.maia.converter.xml.basetype.DateEncoder;
import org.theeuropeanlibrary.maia.converter.xml.basetype.DoubleEncoder;
import org.theeuropeanlibrary.maia.converter.xml.basetype.EnumEncoder;
import org.theeuropeanlibrary.maia.converter.xml.basetype.FloatEncoder;
import org.theeuropeanlibrary.maia.converter.xml.basetype.IntegerEncoder;
import org.theeuropeanlibrary.maia.converter.xml.basetype.LongEncoder;
import org.theeuropeanlibrary.maia.converter.xml.basetype.ShortEncoder;
import org.theeuropeanlibrary.maia.converter.xml.basetype.StringEncoder;
import org.w3c.dom.Element;

/**
 * Generates the schema entries for all base types.
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @since 23 de Fev de 2013
 */
public class BaseTypeEncoderSchemaGenerator {

    /**
     * Transforms the given encoder to the schema element.
     *
     * @param encoder
     * @param parentEl
     */
    @SuppressWarnings("rawtypes")
    public void toXmlSchema(BaseTypeEncoder encoder, Element parentEl) {
        if (encoder instanceof BooleanEncoder) {
            parentEl.setAttribute("type", "xs:boolean");
        } else if (encoder instanceof DateEncoder) {
            parentEl.setAttribute("type", "xs:string");
        } else if (encoder instanceof DoubleEncoder) {
            parentEl.setAttribute("type", "xs:double");
        } else if (encoder instanceof EnumEncoder) {
            Element simpleTypeEl = parentEl.getOwnerDocument().createElement("xs:simpleType");
            parentEl.appendChild(simpleTypeEl);
            Element restrictionEl = parentEl.getOwnerDocument().createElement("xs:restriction");
            simpleTypeEl.appendChild(restrictionEl);
            restrictionEl.setAttribute("base", "xs:string");

            for (Enum<?> e : ((EnumEncoder) encoder).getType().getEnumConstants()) {
                Element enumEl = parentEl.getOwnerDocument().createElement("xs:enumeration");
                restrictionEl.appendChild(enumEl);
                enumEl.setAttribute("value", e.toString());
            }
        } else if (encoder instanceof FloatEncoder) {
            parentEl.setAttribute("type", "xs:float");
        } else if (encoder instanceof IntegerEncoder) {
            parentEl.setAttribute("type", "xs:integer");
        } else if (encoder instanceof LongEncoder) {
            parentEl.setAttribute("type", "xs:long");
        } else if (encoder instanceof ShortEncoder) {
            parentEl.setAttribute("type", "xs:short");
        } else if (encoder instanceof StringEncoder) {
            parentEl.setAttribute("type", "xs:string");
        }
    }
}
