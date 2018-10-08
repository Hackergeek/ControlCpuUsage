package com.skyward.controlcpuload;

import android.os.SystemClock;

import java.util.ArrayList;

/**
 * @author uidp5296
 */
public class ControlCpuLoadUtils {

    private static ArrayList<Thread> sThreads = new ArrayList<>();

    public static synchronized void start(int numCore, int numThreadsPerCore, long load) {
        for (int thread = 0; thread < numCore * numThreadsPerCore; thread++) {
            Thread busyThread = new BusyThread("Thread" + thread, load);
            sThreads.add(busyThread);
            busyThread.start();
        }
    }

    public static synchronized void stop() {
        for (Thread thread : sThreads) {
            thread.interrupt();
        }
        sThreads.clear();
    }

    private static final String TAG = "ControlCpuLoadUtils";

    private static class BusyThread extends Thread {
        private double load;


        BusyThread(String name, long load) {
            super(name);
            this.load = load;
        }

        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    if (SystemClock.elapsedRealtime() % 100 == 0) {
                        Thread.sleep((long) (100 - load));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
