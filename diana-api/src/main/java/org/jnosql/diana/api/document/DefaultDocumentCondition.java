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

package org.jnosql.diana.api.document;


import org.jnosql.diana.api.Condition;
import org.jnosql.diana.api.TypeReference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;
import static org.jnosql.diana.api.Condition.AND;
import static org.jnosql.diana.api.Condition.NOT;
import static org.jnosql.diana.api.Condition.OR;
import static org.jnosql.diana.api.Condition.SUBQUERY;

/**
 * The default implementation of {@link DocumentCondition}
 */
class DefaultDocumentCondition implements DocumentCondition {

    private final Document document;

    private final Condition condition;

    private DefaultDocumentCondition(Document document, Condition condition) {
        this.document = document;
        this.condition = condition;
    }

    public static DefaultDocumentCondition of(Document document, Condition condition) {
        return new DefaultDocumentCondition(Objects.requireNonNull(document, "Document is required"), condition);
    }

    static DocumentCondition between(Document document) {
        Objects.requireNonNull(document, "document is required");
        Object value = document.get();
        checkIterableClause(value);
        return new DefaultDocumentCondition(document, Condition.BETWEEN);
    }

    private static void checkIterableClause(Object value) {
        if (Iterable.class.isInstance(value)) {
            int count = 0;
            Iterator iterator = Iterable.class.cast(value).iterator();
            while (iterator.hasNext()) {
                iterator.next();
                count++;
                if (count > 2) {
                    throw new IllegalArgumentException("On Documentcondition#between you must use an iterable" +
                            " with two elements");
                }
            }
            if (count != 2) {
                throw new IllegalArgumentException("On Documentcondition#between you must use an iterable" +
                        " with two elements");
            }

        } else {
            throw new IllegalArgumentException("On Documentcondition#between you must use an iterable" +
                    " with two elements istead of class: " + value.getClass().getName());
        }
    }

    static DefaultDocumentCondition and(DocumentCondition... conditions) throws NullPointerException {
        requireNonNull(conditions, "condition is required");
        Document document = Document.of(AND.getNameField(), asList(conditions));
        return DefaultDocumentCondition.of(document, AND);
    }


    static DefaultDocumentCondition or(DocumentCondition... conditions) throws NullPointerException {
        requireNonNull(conditions, "condition is required");
        Document document = Document.of(OR.getNameField(), asList(conditions));
        return DefaultDocumentCondition.of(document, OR);
    }

    static DefaultDocumentCondition subquery(DocumentQuery query) throws NullPointerException {
        requireNonNull(query, "query is required");
        Document document = Document.of(SUBQUERY.getNameField(), query);
        return DefaultDocumentCondition.of(document, SUBQUERY);
    }


    public Document getDocument() {
        return document;
    }

    public Condition getCondition() {
        return condition;
    }

    @Override
    public DocumentCondition and(DocumentCondition condition) throws NullPointerException {
        requireNonNull(condition, "Conditions is required");
        if (AND.equals(this.condition)) {
            Document column = getConditions(condition, AND);
            return new DefaultDocumentCondition(column, AND);
        }
        return DefaultDocumentCondition.and(this, condition);
    }

    @Override
    public DocumentCondition negate() {
        if (NOT.equals(this.condition)) {
            return this.document.get(DocumentCondition.class);
        } else {
            Document document = Document.of(NOT.getNameField(), this);
            return new DefaultDocumentCondition(document, NOT);
        }
    }

    @Override
    public DocumentCondition or(DocumentCondition condition) {
        requireNonNull(condition, "Condition is required");
        if (OR.equals(this.condition)) {
            Document document = getConditions(condition, OR);
            return new DefaultDocumentCondition(document, OR);
        }
        return DefaultDocumentCondition.or(this, condition);
    }

    private Document getConditions(DocumentCondition columnCondition, Condition condition) {
        List<DocumentCondition> conditions = new ArrayList<>();
        conditions.addAll(document.get(new TypeReference<List<DocumentCondition>>() {
        }));
        conditions.add(columnCondition);
        return Document.of(condition.getNameField(), conditions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefaultDocumentCondition that = (DefaultDocumentCondition) o;
        return Objects.equals(document, that.document) &&
                condition == that.condition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(document, condition);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DefaultDocumentCondition{");
        sb.append("document=").append(document);
        sb.append(", condition=").append(condition);
        sb.append('}');
        return sb.toString();
    }


}
