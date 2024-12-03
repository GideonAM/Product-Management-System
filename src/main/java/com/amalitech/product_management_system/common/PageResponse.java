package com.amalitech.product_management_system.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class PageResponse <T> {
    private List<T> pageDataList;
    private boolean isFirst;
    private boolean isLast;
    private int totalPages;
    private Long totalElements;
}
