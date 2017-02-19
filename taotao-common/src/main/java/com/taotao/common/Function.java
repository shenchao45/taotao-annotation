package com.taotao.common;

/**
 * Created by shenchao on 2017/2/16.
 */
public interface Function<T,E> {
    T callback(E e);
}
