package com.tfxk.framework;

/**
 *
 */
public interface Callback<T extends Object> {
    void call(T... values);
}
