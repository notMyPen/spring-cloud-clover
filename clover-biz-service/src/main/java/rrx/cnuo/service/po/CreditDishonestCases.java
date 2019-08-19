package rrx.cnuo.service.po;

public class CreditDishonestCases {
    private Long id;

    private Long uid;

    private Byte type;

    private String caseId;

    private String caseCreateTime;

    private String courtName;

    private String province;

    private String caseDocumentId;

    private String discreditPublishTime;

    private String executionTarget;

    private String leftTarget;

    private String executionDescription;

    private String caseEndTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId == null ? null : caseId.trim();
    }

    public String getCaseCreateTime() {
        return caseCreateTime;
    }

    public void setCaseCreateTime(String caseCreateTime) {
        this.caseCreateTime = caseCreateTime == null ? null : caseCreateTime.trim();
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName == null ? null : courtName.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCaseDocumentId() {
        return caseDocumentId;
    }

    public void setCaseDocumentId(String caseDocumentId) {
        this.caseDocumentId = caseDocumentId == null ? null : caseDocumentId.trim();
    }

    public String getDiscreditPublishTime() {
        return discreditPublishTime;
    }

    public void setDiscreditPublishTime(String discreditPublishTime) {
        this.discreditPublishTime = discreditPublishTime == null ? null : discreditPublishTime.trim();
    }

    public String getExecutionTarget() {
        return executionTarget;
    }

    public void setExecutionTarget(String executionTarget) {
        this.executionTarget = executionTarget == null ? null : executionTarget.trim();
    }

    public String getLeftTarget() {
        return leftTarget;
    }

    public void setLeftTarget(String leftTarget) {
        this.leftTarget = leftTarget == null ? null : leftTarget.trim();
    }

    public String getExecutionDescription() {
        return executionDescription;
    }

    public void setExecutionDescription(String executionDescription) {
        this.executionDescription = executionDescription == null ? null : executionDescription.trim();
    }

    public String getCaseEndTime() {
        return caseEndTime;
    }

    public void setCaseEndTime(String caseEndTime) {
        this.caseEndTime = caseEndTime == null ? null : caseEndTime.trim();
    }
}