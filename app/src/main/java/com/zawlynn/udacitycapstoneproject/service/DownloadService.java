package com.zawlynn.udacitycapstoneproject.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.zawlynn.udacitycapstoneproject.data.repository.DownloadRepository;
import com.zawlynn.udacitycapstoneproject.pojo.Episode;

import com.zawlynn.udacitycapstoneproject.ui.podcast.fragment.PlaylistFragment;
import com.zawlynn.udacitycapstoneproject.utils.Constant;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadService extends IntentService {
    private static final String TAG = "DownloadService";

    public DownloadService() {
        super(DownloadService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        DownloadRepository.getInstance().setDownload_started(true);
        Episode episode = intent.getParcelableExtra(Constant.DATA);
        if (episode != null) {

            File PATH = new File(getExternalCacheDir(), "downloaded");
            Log.v("LOG_TAG", "PATH: " + PATH);
            PATH.mkdirs();
            File outputFile = new File(PATH, episode.getId() + ".mp3");
            downloadFile(episode, outputFile);
        }
        stopSelf();
    }

    private void downloadFile(Episode episode, File outputFile) {
        boolean status = false;
        try {
            URL u = new URL(episode.getAudio());
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            DataInputStream stream = new DataInputStream(u.openStream());

            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();

            DataOutputStream fos = new DataOutputStream(new FileOutputStream(outputFile));
            fos.write(buffer);
            fos.flush();
            fos.close();
            Log.d(TAG, "DOWNLOAD COMPLETE: ." + outputFile.getAbsolutePath());
            episode.setLocal_file_cache(outputFile.getAbsolutePath());
            episode.setSaved(true);
            status=true;
        } catch (Exception e) {
            status = false;
            Log.d(TAG, "DOWNLOAD COMPLETE: " + e.getMessage());
        }
        Intent i = new Intent(PlaylistFragment.class.getName());
        i.putExtra(Constant.COMPLETED, status);
        i.putExtra(Constant.DATA, episode);
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
        DownloadRepository.getInstance().setDownload_started(false);
    }
}
