package com.youssef.GridPulse.configuration.graphql.pagination.cursorBased;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Connection<T> {
    private List<Edge<T>> edges;
    private PageInfo pageInfo;
    private long totalCount;
}