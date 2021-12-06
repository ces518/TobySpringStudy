package me.june.mvc.ajax;

public class Result {

    boolean duplicated;
    String availableId;

    public boolean isDuplicated() {
        return duplicated;
    }

    public void setDuplicated(boolean duplicated) {
        this.duplicated = duplicated;
    }

    public String getAvailableId() {
        return availableId;
    }

    public void setAvailableId(String availableId) {
        this.availableId = availableId;
    }
}
