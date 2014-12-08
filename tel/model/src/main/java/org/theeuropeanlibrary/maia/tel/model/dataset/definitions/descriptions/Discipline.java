package org.theeuropeanlibrary.maia.tel.model.dataset.definitions.descriptions;



/**
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 */
public enum Discipline {

    HUMANITIES("H000", "Humanities....");

    private final String code;

    private final String name;

    private Discipline(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
