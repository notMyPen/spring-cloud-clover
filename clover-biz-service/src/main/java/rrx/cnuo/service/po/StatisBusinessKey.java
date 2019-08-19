package rrx.cnuo.service.po;

public class StatisBusinessKey {
    private Short businessType;

    private Long businessId;

    private Short statisItemKey;

    public Short getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Short businessType) {
        this.businessType = businessType;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Short getStatisItemKey() {
        return statisItemKey;
    }

    public void setStatisItemKey(Short statisItemKey) {
        this.statisItemKey = statisItemKey;
    }
}