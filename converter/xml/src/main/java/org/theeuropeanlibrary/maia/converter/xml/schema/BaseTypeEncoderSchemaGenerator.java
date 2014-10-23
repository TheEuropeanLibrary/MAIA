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
     * @param elementName
     */
    @SuppressWarnings("rawtypes")
    public void toXmlSchema(BaseTypeEncoder encoder, Element parentEl, String elementName) {
        String type = null;
        if (encoder instanceof BooleanEncoder) {
            type = "xs:boolean";
        } else if (encoder instanceof DateEncoder) {
            type = "xs:string";
        } else if (encoder instanceof DoubleEncoder) {
            type = "xs:double";
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
            type = "xs:float";
        } else if (encoder instanceof IntegerEncoder) {
            type = "xs:integer";
        } else if (encoder instanceof LongEncoder) {
            type = "xs:long";
        } else if (encoder instanceof ShortEncoder) {
            type = "xs:short";
        } else if (encoder instanceof StringEncoder) {
            type = "xs:string";
        }

        if (type != null) {
            if (elementName == null || type.contains(elementName.toLowerCase())) {
                parentEl.setAttribute("type", type);
            } else {
                Element complexTypeEl = parentEl.getOwnerDocument().createElement("xs:complexType");
                parentEl.appendChild(complexTypeEl);

                Element simpleContentEl = parentEl.getOwnerDocument().createElement("xs:simpleContent");
                complexTypeEl.appendChild(simpleContentEl);

                Element extensionContentEl = parentEl.getOwnerDocument().createElement("xs:extension");
                simpleContentEl.appendChild(extensionContentEl);

                extensionContentEl.setAttribute("base", type);
            }
        }
    }
}
