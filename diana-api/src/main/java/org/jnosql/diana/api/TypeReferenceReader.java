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
package org.jnosql.diana.api;

/**
 * The reader to {@link TypeReference}
 *
 * @see Value#get(TypeSupplier)
 */
public interface TypeReferenceReader {


    /**
     * verifies if the reader has support of instance from this class.
     *
     * @param <T>  the type
     * @param type the type
     * @return true if is compatible otherwise false
     */
    <T> boolean isCompatible(TypeSupplier<T> type);

    /**
     * converts to defined type on {@link TypeReference}
     *
     * @param typeReference the typeReference
     * @param value         the value
     * @param <T>           the typeReference type
     * @return the instance converted
     */
    <T> T convert(TypeSupplier<T> typeReference, Object value);
}
