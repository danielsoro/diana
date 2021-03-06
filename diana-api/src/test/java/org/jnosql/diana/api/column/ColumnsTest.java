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

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;


public class ColumnsTest {

    @Test
    public void shouldCreateColumn() {
        Column column = Columns.of("name", "Ada");
        assertEquals("name", column.getName());
        assertEquals("Ada", column.get());
    }

    @Test
    public void shouldCreateColumnsFromMap() {
        Map<String, String> map = singletonMap("name", "Ada");
        List<Column> columns = Columns.of(map);
        assertFalse(columns.isEmpty());
        assertThat(columns, Matchers.contains(Column.of("name", "Ada")));
    }
}