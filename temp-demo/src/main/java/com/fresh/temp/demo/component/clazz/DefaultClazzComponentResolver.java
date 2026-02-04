package com.fresh.temp.demo.component.clazz;

import com.fresh.temp.demo.component.AbstractComponentResolver;
import com.fresh.temp.demo.component.Component;

import java.util.ArrayList;
import java.util.List;

public class DefaultClazzComponentResolver<T>
        extends AbstractComponentResolver<Class<T>>
        implements ClazzComponentResolver<T> {

    public DefaultClazzComponentResolver(ClazzComponent<T> component) {
        super(component);
    }

    @Override
    public List<ClazzComponent<?>> getAllSuperClass() {
        List<ClazzComponent<?>> result = new ArrayList<>();
        List<Component<?>> childs = getAllChild();
        getAllSuperClass(result, childs);
        return result;
    }

    private void getAllSuperClass(List<ClazzComponent<?>> listResult, List<Component<?>> childs) {
        for(Component<?> child : childs) {
            Object entity_raw = child.getEntity();
            if(entity_raw instanceof Class) {
                Class<?> entity = (Class<?>) entity_raw;
                if(!entity.isInterface()) {
                    listResult.add((ClazzComponent<?>) child);  //ClazzComponent<?> <- Component<Class<?>> <- Component<?>
                    getAllSuperClass(listResult, child.getAllChild());
                    //break;
                }
            }
        }
    }


}
