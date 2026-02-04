package com.fresh.temp.demo.generics;

public interface FtMapper<T> {
    int updateById(T entity);
}
