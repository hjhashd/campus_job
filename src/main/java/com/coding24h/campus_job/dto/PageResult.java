package com.coding24h.campus_job.dto;

import java.util.List;

public class PageResult<T> {
    private long totalItems;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private List<T> items;

    // 修改后的构造函数
    public PageResult(long totalItems, int totalPages, int currentPage, int pageSize, List<T> items) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.items = items;
    }

    // Getters
    public long getTotalItems() {
        return totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<T> getItems() {
        return items;
    }
}