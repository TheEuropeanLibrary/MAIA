package org.theeuropeanlibrary.maia.tel.model.dataset.definitions;

import org.theeuropeanlibrary.maia.common.FieldId;

/**
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 08.12.2014
 */
public class Agreement {

    @FieldId(1)
    private AgreementStatus status;

    @FieldId(2)
    private String signed;

    @FieldId(3)
    private String appendix;

    public Agreement() {
        // default constructor
    }

    public AgreementStatus getStatus() {
        return status;
    }

    public void setStatus(AgreementStatus status) {
        this.status = status;
    }

    public String getSigned() {
        return signed;
    }

    public void setSigned(String signed) {
        this.signed = signed;
    }

    public String getAppendix() {
        return appendix;
    }

    public void setAppendix(String appendix) {
        this.appendix = appendix;
    }

    @Override
    public String toString() {
        return "Aggreement{" + "status=" + status + ", signed=" + signed + ", appendix=" + appendix + '}';
    }

}
