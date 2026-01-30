package com.temp.demo.component.clazz;

import com.temp.demo.component.ComponentResolver;

import java.util.List;

public interface ClazzComponentResolver<T> extends ComponentResolver<Class<T>> {

    List<ClazzComponent<?>> getAllSuperClass();

}
