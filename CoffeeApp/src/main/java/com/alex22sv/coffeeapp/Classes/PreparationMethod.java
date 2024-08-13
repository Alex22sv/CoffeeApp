package com.alex22sv.coffeeapp.Classes;

public class PreparationMethod {
    private Integer preparationMethodId;
    private String preparationMethodName;
    public PreparationMethod(Integer preparationMethodId, String preparationMethodName) {
        this.preparationMethodId = preparationMethodId;
        this.preparationMethodName = preparationMethodName;
    }

    public Integer getPreparationMethodId() {
        return preparationMethodId;
    }

    public void setPreparationMethodId(Integer preparationMethodId) {
        this.preparationMethodId = preparationMethodId;
    }

    public String getPreparationMethodName() {
        return preparationMethodName;
    }

    public void setPreparationMethodName(String preparationMethodName) {
        this.preparationMethodName = preparationMethodName;
    }
}
