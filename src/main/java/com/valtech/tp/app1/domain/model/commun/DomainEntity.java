package com.valtech.tp.app1.domain.model.commun;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;

@MappedSuperclass
public abstract class DomainEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    protected static final int DEFAULT_LENGTH = 255;
    protected static final int STRING_MAX_LENGTH = 254;

    /**
     * @return an object or an Object[] (not other thing)
     */
    public abstract Object getNaturalId();

    @Override
    public int hashCode() {
        Object naturalId = getNaturalId();
        return naturalId instanceof Object[]
                ? Objects.hash((Object[]) naturalId)
                : Objects.hashCode(naturalId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(getClass().isAssignableFrom(obj.getClass()) || obj.getClass().isAssignableFrom(getClass()))) {
            return false;
        }
        final DomainEntity other = (DomainEntity) obj;
        Object naturalId = getNaturalId();
        return naturalId instanceof Object[]
                ? Objects.deepEquals(naturalId, other.getNaturalId())
                : Objects.equals(naturalId, other.getNaturalId());
    }

    @Override
    public String toString() {
        return "[" + getClass().getSimpleName() + ": {" + getNaturalId().toString() + "}]";
    }
}
