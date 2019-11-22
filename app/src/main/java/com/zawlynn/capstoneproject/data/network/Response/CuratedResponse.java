package com.zawlynn.capstoneproject.data.network.Response;

import com.zawlynn.capstoneproject.pojo.CuratedPodcast;

import java.util.List;

public class CuratedResponse {
    private boolean has_next;
    private long total;
    private long page_number;
    private boolean has_previous;
    private long next_page_number;
    private long previous_page_number;
    private List<CuratedPodcast> curated_lists;

    public boolean isHas_next() {
        return has_next;
    }

    public void setHas_next(boolean has_next) {
        this.has_next = has_next;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPage_number() {
        return page_number;
    }

    public void setPage_number(long page_number) {
        this.page_number = page_number;
    }

    public boolean isHas_previous() {
        return has_previous;
    }

    public void setHas_previous(boolean has_previous) {
        this.has_previous = has_previous;
    }

    public long getNext_page_number() {
        return next_page_number;
    }

    public void setNext_page_number(long next_page_number) {
        this.next_page_number = next_page_number;
    }

    public long getPrevious_page_number() {
        return previous_page_number;
    }

    public void setPrevious_page_number(long previous_page_number) {
        this.previous_page_number = previous_page_number;
    }

    public List<CuratedPodcast> getCurated_lists() {
        return curated_lists;
    }

    public void setCurated_lists(List<CuratedPodcast> curated_lists) {
        this.curated_lists = curated_lists;
    }
}
