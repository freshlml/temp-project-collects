package com.fresh.temp.demo.component;

import java.util.List;

public interface ComponentResolver<E> {
    boolean isLeaf();
    E getEntity();
    List<Component<?>> getAllChild();
}
