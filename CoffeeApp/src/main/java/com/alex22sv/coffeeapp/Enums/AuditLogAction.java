package com.alex22sv.coffeeapp.Enums;

public enum AuditLogAction {
    // Auth
    LOGGED_IN,
    LOGGED_OUT,
    // Open pages
    OPENED_CONSUMED_COFFEE,
    OPENED_COFFEE_BRAND,
    OPENED_PREPARATION_METHOD,
    OPENED_DOWNLOAD_DATABASE,
    OPENED_AUDIT_LOG,
    // Consumed Coffee
    ADDED_CONSUMED_COFFEE,
    FAILED_TO_ADD_CONSUMED_COFFEE,
    UPDATED_CONSUMED_COFFEE,
    FAILED_TO_UPDATE_CONSUMED_COFFEE,
    DELETED_CONSUMED_COFFEE,
    FAILED_TO_DELETE_CONSUMED_COFFEE,
    // Coffee Brand
    ADDED_COFFEE_BRAND,
    FAILED_TO_ADD_COFFEE_BRAND,
    UPDATED_COFFEE_BRAND,
    FAILED_TO_UPDATE_COFFEE_BRAND,
    DELETED_COFFEE_BRAND,
    FAILED_TO_DELETE_COFFEE_BRAND,
    // Preparation Method
    ADDED_PREPARATION_METHOD,
    FAILED_TO_ADD_PREPARATION_METHOD,
    UPDATED_PREPARATION_METHOD,
    FAILED_TO_UPDATE_PREPARATION_METHOD,
    DELETED_PREPARATION_METHOD,
    FAILED_TO_DELETE_PREPARATION_METHOD,
    // Audit log
    FAILED_TO_READ_AUDIT_LOG_TABLE
}
