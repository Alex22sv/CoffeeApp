package com.alex22sv.coffeeapp.Classes;

public class AuditLog {
    private Integer auditLogId;
    private String auditLogAction;
    private String auditLogUser;
    private String auditLogDate;

    public AuditLog(Integer auditLogId, String auditLogAction, String auditLogUser, String auditLogDate) {
        this.auditLogId = auditLogId;
        this.auditLogAction = auditLogAction;
        this.auditLogUser = auditLogUser;
        this.auditLogDate = auditLogDate;
    }

    public Integer getAuditLogId() {
        return auditLogId;
    }

    public void setAuditLogId(Integer auditLogId) {
        this.auditLogId = auditLogId;
    }

    public String getAuditLogAction() {
        return auditLogAction;
    }

    public void setAuditLogAction(String auditLogAction) {
        this.auditLogAction = auditLogAction;
    }

    public String getAuditLogUser() {
        return auditLogUser;
    }

    public void setAuditLogUser(String auditLogUser) {
        this.auditLogUser = auditLogUser;
    }

    public String getAuditLogDate() {
        return auditLogDate;
    }

    public void setAuditLogDate(String auditLogDate) {
        this.auditLogDate = auditLogDate;
    }
}
