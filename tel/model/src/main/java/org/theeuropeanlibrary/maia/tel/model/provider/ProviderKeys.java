package org.theeuropeanlibrary.maia.tel.model.provider;

import org.theeuropeanlibrary.maia.common.FieldId;
import org.theeuropeanlibrary.maia.common.TKey;
import org.theeuropeanlibrary.maia.tel.model.common.Coordinate;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.Country;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.Language;
import org.theeuropeanlibrary.maia.tel.model.common.Link;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.ConsortiumType;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.LibraryOrganization;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.PortalStatus;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.ProviderMembershipType;

/**
 * This class holds all the keys and qualifiers defining an provider in The
 * European Library domain model
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 28.10.2014
 */
public final class ProviderKeys {

    private ProviderKeys() {
        // constants class
    }

    // General 
    @FieldId(1)
    public static final TKey<ProviderKeys, String> NAME = TKey.register(
            ProviderKeys.class,
            "Name",
            String.class);

    @FieldId(2)
    public static final TKey<ProviderKeys, String> IDENTIFIER = TKey.register(
            ProviderKeys.class,
            "Identifier",
            String.class);

    @FieldId(3)
    public static final TKey<ProviderKeys, Language> LANGUAGE = TKey.register(
            ProviderKeys.class,
            "Language",
            Language.class);

    @FieldId(4)
    public static final TKey<ProviderKeys, Country> COUNTRY = TKey.register(
            ProviderKeys.class,
            "Country",
            Country.class);

    // Additional Information
    @FieldId(51)
    public static final TKey<ProviderKeys, String> PHONE = TKey.register(
            ProviderKeys.class,
            "Phone",
            String.class);

    @FieldId(52)
    public static final TKey<ProviderKeys, String> FAX = TKey.register(
            ProviderKeys.class,
            "Fax",
            String.class);

    @FieldId(53)
    public static final TKey<ProviderKeys, String> EMAIL = TKey.register(
            ProviderKeys.class,
            "E-Mail",
            String.class);

    @FieldId(54)
    public static final TKey<ProviderKeys, Boolean> DEA = TKey.register(
            ProviderKeys.class,
            "DEA Signed",
            Boolean.class);

    @FieldId(55)
    public static final TKey<ProviderKeys, Boolean> EOD = TKey.register(
            ProviderKeys.class,
            "EOD Partner",
            Boolean.class);

    @FieldId(56)
    public static final TKey<ProviderKeys, LibraryOrganization> LIBRARY_ORGANIZATION = TKey.register(
            ProviderKeys.class,
            "Library Organization",
            LibraryOrganization.class);

    @FieldId(57)
    public static final TKey<ProviderKeys, ConsortiumType> CONSORTIUM_TYPE = TKey.register(
            ProviderKeys.class,
            "Consortium Type",
            ConsortiumType.class);

    @FieldId(58)
    public static final TKey<ProviderKeys, String> NOTE = TKey.register(
            ProviderKeys.class,
            "Note",
            String.class);

    // Membership
    @FieldId(101)
    public static final TKey<ProviderKeys, Boolean> MEMBER = TKey.register(
            ProviderKeys.class,
            "Member",
            Boolean.class);

    @FieldId(102)
    public static final TKey<ProviderKeys, ProviderMembershipType> MEMBERSHIP_TYPE = TKey.register(
            ProviderKeys.class,
            "Membership Type",
            ProviderMembershipType.class);

    // Portal
    @FieldId(151)
    public static final TKey<ProviderKeys, PortalStatus> PORTAL_STATUS = TKey.register(
            ProviderKeys.class,
            "Portal Status",
            PortalStatus.class);

    @FieldId(152)
    public static final TKey<ProviderKeys, Link> LINK = TKey.register(
            ProviderKeys.class,
            "Link",
            Link.class);

    @FieldId(153)
    public static final TKey<ProviderKeys, Coordinate> COORDINATE = TKey.register(
            ProviderKeys.class,
            "Coordinate",
            Coordinate.class);

    //Relations
    @FieldId(201)
    public static final TKey<ProviderKeys, String> PROVIDER = TKey.register(
            ProviderKeys.class,
            "Provider",
            String.class);

    @FieldId(202)
    public static final TKey<ProviderKeys, String> CONTACT = TKey.register(
            ProviderKeys.class,
            "Contact",
            String.class);

    @FieldId(203)
    public static final TKey<ProviderKeys, String> PROJECT = TKey.register(
            ProviderKeys.class,
            "Contact",
            String.class);

    @FieldId(204)
    public static final TKey<ProviderKeys, String> CASE = TKey.register(
            ProviderKeys.class,
            "Case",
            String.class);

    @FieldId(205)
    public static final TKey<ProviderKeys, String> TICKET = TKey.register(
            ProviderKeys.class,
            "Ticket",
            String.class);

    @FieldId(206)
    public static final TKey<ProviderKeys, String> TASK = TKey.register(
            ProviderKeys.class,
            "Task",
            String.class);

}
