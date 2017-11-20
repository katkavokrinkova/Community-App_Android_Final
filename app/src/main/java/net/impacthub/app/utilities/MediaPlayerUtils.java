/*
 * Copyright (c) 2017 Lightful. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Lightful.
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 */

package net.impacthub.app.utilities;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 11/17/2017.
 */

public final class MediaPlayerUtils {

    private MediaPlayerUtils() {
    }

    public static MediaPlayerUtils get() {
        return MediaPlayerInstanceHolder.INSTANCE;
    }

    private static final class MediaPlayerInstanceHolder {

        private final static MediaPlayerUtils INSTANCE = new MediaPlayerUtils();
    }

    private final MediaPlayer mediaPlayer;

    {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
    }


    public void play(Context context, String fileName) {
        try {
            AssetFileDescriptor afd = context.getAssets().openFd(fileName);
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        clearReference();
    }

    private void clearReference() {
        if (mediaPlayer == null) {
            return;
        }
        mediaPlayer.reset();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}
