/*
 * Copyright 2017 Otavio Santana and others
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jnosql.diana.api.key;


import java.io.Serializable;

import org.jnosql.diana.api.TypeSupplier;
import org.jnosql.diana.api.Value;

/**
 * A bucket unit, it's a tuple that contains key its respective value.
 *
 * @param <T> the key type
 */
@SuppressWarnings("unchecked")
public interface KeyValueEntity<T> extends Serializable {


    /**
     * Creates a Key value instance
     *
     * @param key   the key
     * @param value the value
     * @param <T>   the key type
     * @return a {@link KeyValueEntity} instance
     * @throws NullPointerException when either key or value are null
     */

    static <T> KeyValueEntity<T> of(T key, Value value) throws NullPointerException {
        return new DefaultKeyValueEntity(key, value);
    }

    /**
     * Creates a Key value instance
     *
     * @param key   the key
     * @param value the value
     * @param <T>   the key type
     * @return a {@link KeyValueEntity} instance
     * @throws NullPointerException when either key or value are null
     */
    static <T> KeyValueEntity<T> of(T key, Object value) throws NullPointerException {
        return of(key, Value.of(value));
    }

    /**
     * the key
     *
     * @return the value
     */
    T getKey();

    /**
     * The value
     *
     * @return the value
     * @see Value
     */
    Value getValue();

    /**
     * Alias to {@link org.jnosql.diana.api.Value#get(Class)}
     * @param clazz {@link org.jnosql.diana.api.Value#get(Class)}
     * @param <T> {@link org.jnosql.diana.api.Value#get(Class)}
     * @return {@link org.jnosql.diana.api.Value#get(Class)}
     * @throws NullPointerException {@link org.jnosql.diana.api.Value#get(Class)}
     * @throws UnsupportedOperationException {@link org.jnosql.diana.api.Value#get(Class)}
     */
    <T> T get(Class<T> clazz) throws NullPointerException, UnsupportedOperationException;

    /**
     * Alias to {@link org.jnosql.diana.api.Value#get(TypeSupplier)}
     * @param typeSupplier {@link org.jnosql.diana.api.Value#get(TypeSupplier)}
     * @param <T> {@link org.jnosql.diana.api.Value#get(TypeSupplier)}
     * @return {@link org.jnosql.diana.api.Value#get(TypeSupplier)}
     * @throws NullPointerException {@link org.jnosql.diana.api.Value#get(TypeSupplier)}
     * @throws UnsupportedOperationException {@link org.jnosql.diana.api.Value#get(TypeSupplier)}
     */
    <T> T get(TypeSupplier<T> typeSupplier) throws NullPointerException, UnsupportedOperationException;

    /**
     * Alias to {@link org.jnosql.diana.api.Value#get()}
     * @return {@link org.jnosql.diana.api.Value#get()}
     */
    Object get();

}
