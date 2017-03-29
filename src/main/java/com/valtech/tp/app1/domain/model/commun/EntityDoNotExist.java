package com.valtech.tp.app1.domain.model.commun;

public class EntityDoNotExist extends RuntimeException {

    public EntityDoNotExist(String message) {
        super(message);
    }
}
