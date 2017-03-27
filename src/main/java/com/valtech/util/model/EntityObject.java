package com.valtech.util.model;

import java.io.Serializable;

public abstract class EntityObject implements Serializable {

    public abstract Object getNaturalId();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityObject that = (EntityObject) o;
        return getNaturalId() != null ? getNaturalId().equals(that.getNaturalId()) : that.getNaturalId() == null;
    }

    @Override
    public int hashCode() {
        return getNaturalId() != null ? getNaturalId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return getNaturalId().toString();
    }
}
