package com.valtech.tp.app1.domain.model.commun;

public class EntityAlreadyExist extends RuntimeException {

    public EntityAlreadyExist(String s) {
        super(s);
    }
}
