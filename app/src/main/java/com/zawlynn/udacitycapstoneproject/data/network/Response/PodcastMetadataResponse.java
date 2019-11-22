package com.zawlynn.udacitycapstoneproject.data.network.Response;

import com.zawlynn.udacitycapstoneproject.pojo.Episode;

import java.util.ArrayList;
import java.util.List;

public class PodcastMetadataResponse {
    private String thumbnail;
    private String rss;
    private String id;
    private String next_episode_pub_date;
    private List<Episode> episodes;
    private String language;
    private String website;
    private String total_episodes;
    private String lastest_pub_date_ms;
    private String country;
    private String listennotes_url;
    private String title;
    private String description;
    private String earliest_pub_date_ms;
    private String itunes_id;
    private String publisher;
    private String email;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getRss() {
        return rss;
    }

    public void setRss(String rss) {
        this.rss = rss;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNext_episode_pub_date() {
        return next_episode_pub_date;
    }

    public void setNext_episode_pub_date(String next_episode_pub_date) {
        this.next_episode_pub_date = next_episode_pub_date;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(ArrayList<Episode> episodes) {
        this.episodes = episodes;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTotal_episodes() {
        return total_episodes;
    }

    public void setTotal_episodes(String total_episodes) {
        this.total_episodes = total_episodes;
    }

    public String getLastest_pub_date_ms() {
        return lastest_pub_date_ms;
    }

    public void setLastest_pub_date_ms(String lastest_pub_date_ms) {
        this.lastest_pub_date_ms = lastest_pub_date_ms;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getListennotes_url() {
        return listennotes_url;
    }

    public void setListennotes_url(String listennotes_url) {
        this.listennotes_url = listennotes_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEarliest_pub_date_ms() {
        return earliest_pub_date_ms;
    }

    public void setEarliest_pub_date_ms(String earliest_pub_date_ms) {
        this.earliest_pub_date_ms = earliest_pub_date_ms;
    }

    public String getItunes_id() {
        return itunes_id;
    }

    public void setItunes_id(String itunes_id) {
        this.itunes_id = itunes_id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
