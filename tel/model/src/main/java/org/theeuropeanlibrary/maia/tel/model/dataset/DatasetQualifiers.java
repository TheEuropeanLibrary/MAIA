package org.theeuropeanlibrary.maia.tel.model.dataset;

import org.theeuropeanlibrary.maia.common.FieldId;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.IdentifierType;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.NameType;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.Language;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.LinkType;
import org.theeuropeanlibrary.maia.tel.model.dataset.definitions.NoteType;

/**
 * This class holds all the keys and qualifiers defining an provider in The
 * European Library domain model
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 28.10.2014
 */
public final class DatasetQualifiers {

    private DatasetQualifiers() {
        // Constants class
    }

    @FieldId(1)
    public static final Class<Language> LANGUAGE = Language.class;

    @FieldId(2)
    public static final Class<IdentifierType> IDENTIFIER_TYPE = IdentifierType.class;

    @FieldId(3)
    public static final Class<NameType> NAME_TYPE = NameType.class;

    @FieldId(4)
    public static final Class<NoteType> NOTE_TYPE = NoteType.class;

    @FieldId(5)
    public static final Class<LinkType> LINK_TYPE = LinkType.class;
}
