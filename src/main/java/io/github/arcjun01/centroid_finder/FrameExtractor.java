package io.github.arcjun01.centroid_finder;

import org.bytedeco.javacv.*;

public class FrameExtractor {
    private final String videoPath;
    private FFmpegFrameGrabber grabber;

    public FrameExtractor(String videoPath) {
        this.videoPath = videoPath;
    }

    public void start() throws Exception {
        grabber = new FFmpegFrameGrabber(videoPath);
        grabber.start();
    }

    public Frame grabFrame() throws Exception {
        return grabber.grabImage();
    }

    public double getCurrentTimestampSeconds() {
        // microseconds → seconds
        return grabber.getTimestamp() / 1_000_000.0;
    }

    public void stop() throws Exception {
        if (grabber != null) {
            grabber.stop();
            grabber.close();
        }
    }
}