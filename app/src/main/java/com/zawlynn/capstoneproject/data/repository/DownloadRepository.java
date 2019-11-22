package com.zawlynn.capstoneproject.data.repository;

public class DownloadRepository {
    private static DownloadRepository instance;
    private String downloading_id;
    private Boolean download_started;
    public static DownloadRepository getInstance(){
        if(instance==null){
            instance=new DownloadRepository();
        }
        return instance;
    }
    public String getDownloading_id() {
        return downloading_id;
    }

    public void setDownloading_id(String downloading_id) {
        this.downloading_id = downloading_id;
    }

    public Boolean getDownload_started() {
        return download_started;
    }

    public void setDownload_started(Boolean download_started) {
        this.download_started = download_started;
    }
}
