package org.theeuropeanlibrary.maia.tel.model.provider;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.Country;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.Language;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.NameType;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.ConsortiumType;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.LibraryOrganization;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.LinkType;
import org.theeuropeanlibrary.maia.tel.model.common.PortalStatus;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.ProviderRelationType;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.ProviderType;

/**
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 04.12.2014
 */
public class ExportEnums {

    public static void main(String[] args) throws Exception {
        {
            File json = new File("/home/markus/Country.json");
            FileUtils.write(json, "{\n", true);
            Country[] countries = Country.values();
            for (Country country : countries) {
                FileUtils.write(json, "\"" + country.toString() + "\" : \"" + country.getName() + "\",\n", true);
            }
            FileUtils.write(json, "}", true);
        }

        {
            File json = new File("/home/markus/Language.json");
            FileUtils.write(json, "{\n", true);
            Language[] languages = Language.values();
            for (Language language : languages) {
                FileUtils.write(json, "\"" + language.toString() + "\" : \"" + language.getName() + "\",\n", true);
            }
            FileUtils.write(json, "}", true);
        }

        {
            File json = new File("/home/markus/ProviderType.json");
            FileUtils.write(json, "{\n", true);
            ProviderType[] values = ProviderType.values();
            for (ProviderType value : values) {
                FileUtils.write(json, "\"" + value.toString() + "\" : \"" + value.toString() + "\",\n", true);
            }
            FileUtils.write(json, "}", true);
        }

        {
            File json = new File("/home/markus/LibraryOrganization.json");
            FileUtils.write(json, "{\n", true);
            LibraryOrganization[] values = LibraryOrganization.values();
            for (LibraryOrganization value : values) {
                FileUtils.write(json, "\"" + value.toString() + "\" : \"" + value.toString() + "\",\n", true);
            }
            FileUtils.write(json, "}", true);
        }

        {
            File json = new File("/home/markus/ConsortiumType.json");
            FileUtils.write(json, "{\n", true);
            ConsortiumType[] values = ConsortiumType.values();
            for (ConsortiumType value : values) {
                FileUtils.write(json, "\"" + value.toString() + "\" : \"" + value.toString() + "\",\n", true);
            }
            FileUtils.write(json, "}", true);
        }

        {
            File json = new File("/home/markus/PortalStatus.json");
            FileUtils.write(json, "{\n", true);
            PortalStatus[] values = PortalStatus.values();
            for (PortalStatus value : values) {
                FileUtils.write(json, "\"" + value.toString() + "\" : \"" + value.toString() + "\",\n", true);
            }
            FileUtils.write(json, "}", true);
        }

        {
            File json = new File("/home/markus/NameType.json");
            FileUtils.write(json, "{\n", true);
            NameType[] values = NameType.values();
            for (NameType value : values) {
                FileUtils.write(json, "\"" + value.toString() + "\" : \"" + value.toString() + "\",\n", true);
            }
            FileUtils.write(json, "}", true);
        }

        {
            File json = new File("/home/markus/LinkType.json");
            FileUtils.write(json, "{\n", true);
            LinkType[] values = LinkType.values();
            for (LinkType value : values) {
                FileUtils.write(json, "\"" + value.toString() + "\" : \"" + value.toString() + "\",\n", true);
            }
            FileUtils.write(json, "}", true);
        }

        {
            File json = new File("/home/markus/ProviderRelationType.json");
            FileUtils.write(json, "{\n", true);
            ProviderRelationType[] values = ProviderRelationType.values();
            for (ProviderRelationType value : values) {
                FileUtils.write(json, "\"" + value.toString() + "\" : \"" + value.toString() + "\",\n", true);
            }
            FileUtils.write(json, "}", true);
        }

    }
}
