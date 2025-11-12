package com.youssef.GridPulse.configuration.graphql.pagination.offsetBased;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestInput {
    private int page = 0;
    private int size = 10;
    private String sortBy = "createdAt";
    private boolean desc = true;
}
