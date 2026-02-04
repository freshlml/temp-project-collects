package com.fresh.temp.demo.component;

import com.fresh.common.utils.AssertUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractComponent<E> implements Component<E> {
    private boolean isLeaf;
    private E entity;
    private final List<Component<?>> composites = new ArrayList<>();

    public AbstractComponent(E entity) {
        this(entity, false);
    }
    public AbstractComponent(E entity, boolean isLeaf) {
        //AssertUtils.ifNull(entity, () -> "参数entity不能为null", null);
        this.entity = entity;
        this.isLeaf = isLeaf;
    }


    @Override
    public boolean isLeaf() {
        return isLeaf;
    }

    @Override
    public E getEntity() {
        return entity;
    }

    @Override
    public List<Component<?>> getAllChild() {
        return composites;
    }

    @Override
    public void addChild(Component<?> child) {
        AssertUtils.notNull(child, "参数child不能为null");
        this.composites.add(child);
    }

}
