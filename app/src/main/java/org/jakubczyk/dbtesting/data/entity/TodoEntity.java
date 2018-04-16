package org.jakubczyk.dbtesting.data.entity;

import java.util.Date;

public class TodoEntity {

    Integer id;
    String value;
    Date createdAt;

    public TodoEntity(Integer id, String value, Date createdAt) {
        this.id = id;
        this.value = value;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
