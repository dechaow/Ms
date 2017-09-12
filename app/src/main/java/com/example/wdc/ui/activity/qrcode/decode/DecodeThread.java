package com.example.wdc.ui.activity.qrcode.decode;

import android.os.Handler;
import android.os.Looper;


import com.example.wdc.ui.activity.qrcode.CaptureActivity;

import java.util.concurrent.CountDownLatch;

/**
 * This thread does all the heavy lifting of decoding the images.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public class DecodeThread extends Thread {

    public static final String BARCODE_BITMAP = "BARCODE_BITMAP";
    public static final String DECODE_MODE = "DECODE_MODE";
    public static final String DECODE_TIME = "DECODE_TIME";

    private final CaptureActivity activity;
    private Handler handler;
    private final CountDownLatch handlerInitLatch;

    public DecodeThread(CaptureActivity activity) {

        this.activity = activity;
        handlerInitLatch = new CountDownLatch(1);
    }

    public Handler getHandler() {
        try {
            handlerInitLatch.await();
        } catch (InterruptedException ie) {
            // continue?
        }
        return handler;
    }

    @Override
    public void run() {
        Looper.prepare();
        handler = new DecodeHandler(activity);
        handlerInitLatch.countDown();
        Looper.loop();
    }

}
