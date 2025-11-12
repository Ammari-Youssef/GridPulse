package com.youssef.GridPulse.configuration.graphql.pagination.cursorBased;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Edge<T> {
    private String cursor;
    private T node;
}
