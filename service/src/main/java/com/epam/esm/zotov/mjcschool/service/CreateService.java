package com.epam.esm.zotov.mjcschool.service;

/**
 * Defines service methods related to creating new objects of type
 * <code>T</code>
 */
public interface CreateService<T> {
    /**
     * Saves <code>T</code> object. Returns saved object
     * 
     * @param object - object to be saved.
     * @return <code>true</code> if object successfuly saved.
     */
    T save(T object);

}