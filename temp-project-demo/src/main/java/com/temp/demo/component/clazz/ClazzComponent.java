package com.temp.demo.component.clazz;

import com.temp.demo.component.AbstractComponent;

public class ClazzComponent<T> extends AbstractComponent<Class<T>> {

    public ClazzComponent(Class<T> entity) {
        super(entity);
    }

    public ClazzComponent(Class<T> entity, boolean isLeaf) {
        super(entity, isLeaf);
    }

}
