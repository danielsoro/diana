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
package org.jnosql.diana.api.column;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

/**
 * the default implementation of {@link ColumnDeleteQuery}
 */
class DefaultColumnDeleteQuery implements ColumnDeleteQuery {

    private final String columnFamily;

    private ColumnCondition condition;

    private final List<String> columns = new ArrayList<>();

    private DefaultColumnDeleteQuery(String columnFamily, ColumnCondition condition) {
        this.columnFamily = columnFamily;
        this.condition = condition;
    }

    private DefaultColumnDeleteQuery(String columnFamily) {
        this.columnFamily = columnFamily;
    }

    public String getColumnFamily() {
        return columnFamily;
    }

    public Optional<ColumnCondition> getCondition() {
        return Optional.ofNullable(condition);
    }

    @Override
    public List<String> getColumns() {
        return unmodifiableList(columns);
    }

    @Override
    public void add(String column) throws NullPointerException {
        this.columns.add(requireNonNull(column, "column null is required"));
    }

    @Override
    public void addAll(Iterable<String> columns) throws NullPointerException {
        requireNonNull(columns, "columns is required");
        columns.forEach(this::add);
    }

    @Override
    public void remove(String column) throws NullPointerException {
        requireNonNull(column, "column is required");
        this.columns.remove(column);
    }

    @Override
    public void removeAll(Iterable<String> columns) throws NullPointerException {
        requireNonNull(columns, "columns is required");
        columns.forEach(this::remove);
    }

    @Override
    public ColumnDeleteQuery and(ColumnCondition condition) {
        requireNonNull(condition, "condition is required");
        if (Objects.isNull(this.condition)) {
            this.condition = condition;
        } else {
            this.condition = this.condition.and(condition);
        }
        return this;
    }

    @Override
    public ColumnDeleteQuery or(ColumnCondition condition) throws NullPointerException {
        requireNonNull(condition, "condition is required");
        if (Objects.isNull(this.condition)) {
            this.condition = condition;
        } else {
            this.condition = this.condition.or(condition);
        }
        return this;
    }

    static DefaultColumnDeleteQuery of(String columnFamily, ColumnCondition condition) throws NullPointerException {
        requireNonNull(columnFamily, "columnFamily is required");
        requireNonNull(condition, "condition is required");
        return new DefaultColumnDeleteQuery(columnFamily, condition);
    }

    static DefaultColumnDeleteQuery of(String columnFamily) throws NullPointerException {
        requireNonNull(columnFamily, "columnFamily is required");
        return new DefaultColumnDeleteQuery(columnFamily);
    }
}
