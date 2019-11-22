package com.zawlynn.udacitycapstoneproject.data.network.Response;

import com.zawlynn.udacitycapstoneproject.pojo.CuratedPodcast;
import com.zawlynn.udacitycapstoneproject.pojo.Podcast;

import java.util.List;

public class BestPodcastResponse {
    private String id;
    private String name;
    private String parent_id;
    private boolean has_next;
    private boolean has_previous;
    private long total;
    private long page_number;
    private long previous_page_number;
    private long next_page_number;
    private String listennotes_url;
    private List<Podcast> channels;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public boolean isHas_next() {
        return has_next;
    }

    public void setHas_next(boolean has_next) {
        this.has_next = has_next;
    }

    public boolean isHas_previous() {
        return has_previous;
    }

    public void setHas_previous(boolean has_previous) {
        this.has_previous = has_previous;
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

    public long getPrevious_page_number() {
        return previous_page_number;
    }

    public void setPrevious_page_number(long previous_page_number) {
        this.previous_page_number = previous_page_number;
    }

    public long getNext_page_number() {
        return next_page_number;
    }

    public void setNext_page_number(long next_page_number) {
        this.next_page_number = next_page_number;
    }

    public String getListennotes_url() {
        return listennotes_url;
    }

    public void setListennotes_url(String listennotes_url) {
        this.listennotes_url = listennotes_url;
    }

    public List<Podcast> getChannels() {
        return channels;
    }

    public void setChannels(List<Podcast> channels) {
        this.channels = channels;
    }
}
