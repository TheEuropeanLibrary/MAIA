package org.theeuropeanlibrary.maia.tel.model.provider;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.theeuropeanlibrary.maia.common.AbstractEntity;
import org.theeuropeanlibrary.maia.common.Entity;
import org.theeuropeanlibrary.maia.common.Entity.QualifiedValue;
import org.theeuropeanlibrary.maia.common.TKey;
import org.theeuropeanlibrary.maia.common.definitions.Provider;
import org.theeuropeanlibrary.maia.common.filter.BaseEntityFilter;
import org.theeuropeanlibrary.maia.common.filter.BaseEntityFilterFactory;
import org.theeuropeanlibrary.maia.common.filter.EntityFilter;
import org.theeuropeanlibrary.maia.common.filter.EntityFilterFactory;
import org.theeuropeanlibrary.maia.common.registry.AbstractEntityRegistry;
import org.theeuropeanlibrary.maia.tel.model.common.qualifier.NameType;
import org.theeuropeanlibrary.maia.tel.model.provider.definitions.LinkType;

/**
 * This class provides registration of keys for the provider in The European
 * Library domain model.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 28.10.2014
 */
public final class ProviderRegistry extends AbstractEntityRegistry {

    private static ProviderRegistry INSTANCE;

    private final EntityFilterFactory<String, Provider<String>> filterFactory;

    private ProviderRegistry() {
        this(new BaseEntityFilterFactory());
    }

    private ProviderRegistry(EntityFilterFactory<String, Provider<String>> filterFactory) {
        INSTANCE = this;

        this.filterFactory = filterFactory;

        Set<TKey<?, ?>> generalKeys = setupGeneralKeys();
        EntityFilter generalFilter = new BaseEntityFilter(generalKeys);
        filterFactory.registerFilter("general", generalFilter);

        final Set<TKey<?, ?>> addressKeys = setupAddressKeys();
        filterFactory.registerFilter("address", new EntityFilter<String, Provider<String>>() {

            @Override
            public Provider<String> filter(Provider<String> entity) {
                AbstractEntity instance = entity.createInstance();
                instance.setId(entity.getId());
                Set<TKey<?, ?>> keys = entity.getAvailableKeys();
                for (TKey<?, ?> key : keys) {
                    if (addressKeys.contains(key)) {
                        List<?> qvs = entity.getQualifiedValues(key);
                        for (Object ob : qvs) {
                            Entity.QualifiedValue<?> qv = (Entity.QualifiedValue<?>) ob;
                            instance.addValue(key, qv.getValue(), qv.getQualifiers().toArray(new Enum[qv.getQualifiers().size()]));
                        }
                    }
                }
                List<QualifiedValue<String>> qvs = entity.getQualifiedValues(ProviderKeys.LINK, LinkType.WEBSITE);
                for (QualifiedValue<String> qv : qvs) {
                    instance.addValue(ProviderKeys.LINK, qv.getValue(), qv.getQualifiers().toArray(new Enum[qv.getQualifiers().size()]));
                }
                return (Provider<String>) instance;
            }

            @Override
            public void merge(Provider<String> merger, Provider<String> mergee) {
                Set<TKey<?, ?>> keys = mergee.getAvailableKeys();
                for (TKey key : keys) {
                    if (addressKeys.contains(key)) {
                        List<?> qvs = mergee.getQualifiedValues(key);
                        merger.deleteValues(key);
                        for (Object ob : qvs) {
                            Entity.QualifiedValue<?> qv = (Entity.QualifiedValue<?>) ob;
                            merger.addValue(key, qv.getValue(), qv.getQualifiers().toArray(new Enum[qv.getQualifiers().size()]));
                        }
                    }
                }
                List<QualifiedValue<String>> qvs = mergee.getQualifiedValues(ProviderKeys.LINK, LinkType.WEBSITE);
                merger.deleteValues(ProviderKeys.LINK, LinkType.WEBSITE);
                for (QualifiedValue<String> qv : qvs) {
                    merger.addValue(ProviderKeys.LINK, qv.getValue(), qv.getQualifiers().toArray(new Enum[qv.getQualifiers().size()]));
                }
            }

        });

        Set<TKey<?, ?>> membershipKeys = setupMembershipKeys();
        EntityFilter membershipFilter = new BaseEntityFilter(membershipKeys);
        filterFactory.registerFilter("membership", membershipFilter);

        Set<TKey<?, ?>> portalKeys = setupPortalKeys();
        EntityFilter portalFilter = new BaseEntityFilter(portalKeys);
        filterFactory.registerFilter("portal", portalFilter);

        Set<TKey<?, ?>> relKeys = new LinkedHashSet<>();
        relKeys.add(ProviderKeys.PROVIDER);
        filterFactory.registerFilter("provider", new BaseEntityFilter(relKeys));
        validQualifiers.put(ProviderKeys.PROVIDER, new LinkedHashSet<Class<? extends Enum<?>>>() {
            {
                add(ProviderQualifiers.PROVIDER_RELATIONSHIP_TYPE);
            }
        });
        qualifiers.add(ProviderQualifiers.PROVIDER_RELATIONSHIP_TYPE);

        relKeys = new LinkedHashSet<>();
        relKeys.add(ProviderKeys.DATASET);
        filterFactory.registerFilter("dataset", new BaseEntityFilter(relKeys));

        relKeys = new LinkedHashSet<>();
        relKeys.add(ProviderKeys.CONTACT);
        filterFactory.registerFilter("contact", new BaseEntityFilter(relKeys));

        relKeys = new LinkedHashSet<>();
        relKeys.add(ProviderKeys.IMAGE);
        filterFactory.registerFilter("image", new BaseEntityFilter(relKeys));

        relKeys = new LinkedHashSet<>();
        relKeys.add(ProviderKeys.PROJECT);
        filterFactory.registerFilter("project", new BaseEntityFilter(relKeys));

        relKeys = new LinkedHashSet<>();
        relKeys.add(ProviderKeys.CASE);
        filterFactory.registerFilter("case", new BaseEntityFilter(relKeys));

        relKeys = new LinkedHashSet<>();
        relKeys.add(ProviderKeys.TICKET);
        filterFactory.registerFilter("ticket", new BaseEntityFilter(relKeys));

        relKeys = new LinkedHashSet<>();
        relKeys.add(ProviderKeys.TASK);
        filterFactory.registerFilter("task", new BaseEntityFilter(relKeys));

        keys.addAll(generalKeys);
        keys.addAll(addressKeys);
        keys.addAll(membershipKeys);
        keys.addAll(portalKeys);
        keys.add(ProviderKeys.PROVIDER);
        keys.add(ProviderKeys.DATASET);
        keys.add(ProviderKeys.CONTACT);
        keys.add(ProviderKeys.IMAGE);
        keys.add(ProviderKeys.PROJECT);
        keys.add(ProviderKeys.CASE);
        keys.add(ProviderKeys.TICKET);
        keys.add(ProviderKeys.TASK);

        Set<TKey<?, ?>> providerUserKeys = new LinkedHashSet<>();
        providerUserKeys.addAll(generalKeys);
        providerUserKeys.addAll(addressKeys);
        providerUserKeys.addAll(membershipKeys);
        providerUserKeys.addAll(portalKeys);
        providerUserKeys.add(ProviderKeys.PROVIDER);
        providerUserKeys.add(ProviderKeys.DATASET);
        providerUserKeys.add(ProviderKeys.CONTACT);
        providerUserKeys.add(ProviderKeys.IMAGE);
        providerUserKeys.add(ProviderKeys.PROJECT);
        providerUserKeys.add(ProviderKeys.CASE);
        providerUserKeys.add(ProviderKeys.TICKET);
        providerUserKeys.add(ProviderKeys.TASK);
        EntityFilter providerUserFilter = new BaseEntityFilter(providerUserKeys);
        filterFactory.registerFilter("provider", providerUserFilter);

        Set<TKey<?, ?>> officeUserKeys = new LinkedHashSet<>();
        officeUserKeys.addAll(generalKeys);
        officeUserKeys.addAll(addressKeys);
        officeUserKeys.addAll(membershipKeys);
        officeUserKeys.addAll(portalKeys);
        officeUserKeys.add(ProviderKeys.PROVIDER);
        officeUserKeys.add(ProviderKeys.DATASET);
        officeUserKeys.add(ProviderKeys.CONTACT);
        officeUserKeys.add(ProviderKeys.IMAGE);
        officeUserKeys.add(ProviderKeys.PROJECT);
        officeUserKeys.add(ProviderKeys.CASE);
        officeUserKeys.add(ProviderKeys.TICKET);
        officeUserKeys.add(ProviderKeys.TASK);
        EntityFilter officeUserFilter = new BaseEntityFilter(officeUserKeys);
        filterFactory.registerFilter("office", officeUserFilter);

        filterFactory.registerFilter("simple", new EntityFilter<String, Provider<String>>() {

            @Override
            public Provider<String> filter(Provider<String> entity) {
                Provider<String> simpleProvider = (Provider<String>) entity.createInstance();
                simpleProvider.setId(entity.getId());
                String name = entity.getFirstValue(ProviderKeys.NAME, NameType.MAIN);
                if (name != null) {
                    simpleProvider.addValue(ProviderKeys.NAME, name, NameType.MAIN);
                }
                String identifier = entity.getFirstValue(ProviderKeys.IDENTIFIER);
                if (identifier != null) {
                    simpleProvider.addValue(ProviderKeys.IDENTIFIER, identifier);
                }
                String logo = entity.getFirstValue(ProviderKeys.LINK, LinkType.LOGO);
                if (logo != null) {
                    simpleProvider.addValue(ProviderKeys.LINK, logo, LinkType.LOGO);
                }
                return simpleProvider;
            }

            @Override
            public void merge(Provider<String> merger, Provider<String> mergee) {
                throw new UnsupportedOperationException("This filter is only for view, it cannot be merged back.");
            }

        });
    }

    private Set<TKey<?, ?>> setupGeneralKeys() {
        Set<TKey<?, ?>> generalKeys = new LinkedHashSet<>();

        generalKeys.add(ProviderKeys.PROVIDER_TYPE);
        generalKeys.add(ProviderKeys.COUNTRY);
        generalKeys.add(ProviderKeys.IDENTIFIER);
        generalKeys.add(ProviderKeys.LANGUAGE);
        generalKeys.add(ProviderKeys.NAME);

        validQualifiers.put(ProviderKeys.NAME, new LinkedHashSet<Class<? extends Enum<?>>>() {
            {
                add(ProviderQualifiers.NAME_TYPE);
                add(ProviderQualifiers.LANGUAGE);
            }
        });

        qualifiers.add(ProviderQualifiers.NAME_TYPE);
        qualifiers.add(ProviderQualifiers.LANGUAGE);

        uniqueKeys.add(ProviderKeys.PROVIDER_TYPE);
        uniqueKeys.add(ProviderKeys.COUNTRY);
        uniqueKeys.add(ProviderKeys.IDENTIFIER);

        return generalKeys;
    }

    private Set<TKey<?, ?>> setupAddressKeys() {
        Set<TKey<?, ?>> additionalKeys = new LinkedHashSet<>();

        additionalKeys.add(ProviderKeys.ADDRESS);
        additionalKeys.add(ProviderKeys.PHONE);
        additionalKeys.add(ProviderKeys.FAX);
        additionalKeys.add(ProviderKeys.EMAIL);

        return additionalKeys;
    }

    private Set<TKey<?, ?>> setupMembershipKeys() {
        Set<TKey<?, ?>> membershipKeys = new LinkedHashSet<>();

        membershipKeys.add(ProviderKeys.MEMBER);
        membershipKeys.add(ProviderKeys.MEMBERSHIP_TYPE);
        membershipKeys.add(ProviderKeys.DEA);
        membershipKeys.add(ProviderKeys.EOD);
        membershipKeys.add(ProviderKeys.LIBRARY_ORGANIZATION);
        membershipKeys.add(ProviderKeys.CONSORTIUM_TYPE);
        membershipKeys.add(ProviderKeys.NOTE);

        uniqueKeys.add(ProviderKeys.DEA);
        uniqueKeys.add(ProviderKeys.EOD);
        uniqueKeys.add(ProviderKeys.CONSORTIUM_TYPE);
        uniqueKeys.add(ProviderKeys.MEMBER);
        uniqueKeys.add(ProviderKeys.MEMBERSHIP_TYPE);

        return membershipKeys;
    }

    private Set<TKey<?, ?>> setupPortalKeys() {
        Set<TKey<?, ?>> portalKeys = new LinkedHashSet<>();

        portalKeys.add(ProviderKeys.PORTAL_STATUS);
        portalKeys.add(ProviderKeys.COORDINATE);
        portalKeys.add(ProviderKeys.LINK);

        validQualifiers.put(ProviderKeys.LINK, new LinkedHashSet<Class<? extends Enum<?>>>() {
            {
                add(ProviderQualifiers.LINK_TYPE);
            }
        });

        qualifiers.add(ProviderQualifiers.LINK_TYPE);

        uniqueKeys.add(ProviderKeys.PORTAL_STATUS);
        uniqueKeys.add(ProviderKeys.COORDINATE);

        return portalKeys;
    }

    public EntityFilterFactory<String, Provider<String>> getFilterFactory() {
        return filterFactory;
    }

    public static synchronized ProviderRegistry getInstance() {
        if (INSTANCE == null) {
            new ProviderRegistry();
        }
        return INSTANCE;
    }
}
