package org.jakubczyk.dbtesting.data.entity;

import java.util.Date;

public class TodoEntity {

    String value;
    Date createdAt;

    public TodoEntity(String value, Date createdAt) {
        this.value = value;
        this.createdAt = createdAt;
    }

    public String getValue() {
        return value;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
