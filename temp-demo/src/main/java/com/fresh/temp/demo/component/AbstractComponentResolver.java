package com.fresh.temp.demo.component;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AbstractComponentResolver<E> implements ComponentResolver<E> {
    protected Component<E> component;

    public AbstractComponentResolver(Component<E> component) {
        this.component = Optional.ofNullable(component).orElse(new EmptyComponent());
    }

    @Override
    public boolean isLeaf() {
        //return component.getAllChild()==null || component.getAllChild().size()==0;
        return component.isLeaf();
    }

    @Override
    public E getEntity() {
        return component.getEntity();
    }

    @Override
    public List<Component<?>> getAllChild() {
        return component.getAllChild();
    }

    public class EmptyComponent extends AbstractComponent<E> {
        public EmptyComponent() {
            super(null, true);
        }

        @Override
        public List<Component<?>> getAllChild() {
            return new ArrayList<>();
        }

        @Override
        public void addChild(Component<?> child) {
            //do nothing
        }
    }

}
