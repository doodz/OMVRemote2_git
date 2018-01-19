package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by thiba on 31/08/2016.
 */
public class UpdateSettings {


    @SerializedName("proposed")
    @Expose
    private Boolean proposed;
    @SerializedName("partner")
    @Expose
    private Boolean partner;

    /**
     *
     * @return
     * The proposed
     */
    public Boolean getProposed() {
        return proposed;
    }

    /**
     *
     * @param proposed
     * The proposed
     */
    public void setProposed(Boolean proposed) {
        this.proposed = proposed;
    }

    /**
     *
     * @return
     * The partner
     */
    public Boolean getPartner() {
        return partner;
    }

    /**
     *
     * @param partner
     * The partner
     */
    public void setPartner(Boolean partner) {
        this.partner = partner;
    }
}
