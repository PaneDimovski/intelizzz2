package uk.co.intelitrack.intelizzz.common.data.remote;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class Pagination {
    private String sortParams;
    private String startRecord;
    private String nextPage;
    private boolean hasNextPage;
    private String pageRecords;
    private String hasPreviousPage;
    private String totalRecords;
    private String currentPage;
    private String previousPage;
    private String totalPages;

    public String getSortParams() {
        return sortParams;
    }

    public String getStartRecord() {
        return startRecord;
    }

    public String getNextPage() {
        return nextPage;
    }

    public boolean getHasNextPage() {
        return hasNextPage;
    }

    public String getPageRecords() {
        return pageRecords;
    }

    public String getHasPreviousPage() {
        return hasPreviousPage;
    }

    public String getTotalRecords() {
        return totalRecords;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public String getPreviousPage() {
        return previousPage;
    }

    public String getTotalPages() {
        return totalPages;
    }
}
