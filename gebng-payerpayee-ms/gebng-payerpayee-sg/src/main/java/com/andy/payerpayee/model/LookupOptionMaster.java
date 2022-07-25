package com.andy.payerpayee.model;


import javax.persistence.*;

@Entity
@Table(name="LOOKUP_OPTION_MASTER")
public class LookupOptionMaster {

    @Id
    @Column(name="LOOKUP_OPTION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lookupOptionId;

    @Column(name = "TIER")
    private String tier;

    @Column(name = "ATTRIBUTE")
    private String attribute;

    @Column(name = "LOOKUP_OPTION_NAME")
    private String lookupOptionName;

    @Column(name = "LOOKUP_OPTION_DESCRIPTION")
    private String lookupOptionDescription;

    @Column(name = "LOOKUP_OPTION_ORDER")
    private String lookupOptionOrder;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;


    public int getLookupOptionId() {
        return lookupOptionId;
    }

    public void setLookupOptionId(int lookupOptionId) {
        this.lookupOptionId = lookupOptionId;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getLookupOptionName() {
        return lookupOptionName;
    }

    public void setLookupOptionName(String lookupOptionName) {
        this.lookupOptionName = lookupOptionName;
    }

    public String getLookupOptionDescription() {
        return lookupOptionDescription;
    }

    public void setLookupOptionDescription(String lookupOptionDescription) {
        this.lookupOptionDescription = lookupOptionDescription;
    }

    public String getLookupOptionOrder() {
        return lookupOptionOrder;
    }

    public void setLookupOptionOrder(String lookupOptionOrder) {
        this.lookupOptionOrder = lookupOptionOrder;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
