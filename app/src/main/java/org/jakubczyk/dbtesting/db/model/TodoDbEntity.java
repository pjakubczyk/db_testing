package org.jakubczyk.dbtesting.db.model;

import io.requery.Column;
import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;

@Entity
public interface TodoDbEntity {

    @Key
    @Generated
    int getId();

    @Column
    String getItemValue();

}
