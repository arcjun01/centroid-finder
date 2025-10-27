package io.github.arcjun01.centroid_finder;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.image.BufferedImage;


public class VideoProcessor {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java VideoExperiment <path_to_video>");
            return;
        }

        String videoPath = args[0];

        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath)) {
            grabber.start();

            // Print metadata
            System.out.println("Video Info:");
            System.out.println("Width: " + grabber.getImageWidth());
            System.out.println("Height: " + grabber.getImageHeight());
            System.out.println("Frame Rate: " + grabber.getFrameRate());
            System.out.println("Length (frames): " + grabber.getLengthInFrames());
            System.out.println("Duration (seconds): " + (grabber.getLengthInTime() / 1_000_000.0));

            // test reading
            Java2DFrameConverter converter = new Java2DFrameConverter();
            for (int i = 0; i < 5; i++) {
                Frame frame = grabber.grabImage();
                if (frame == null) break;
                BufferedImage img = converter.convert(frame);
                if (img != null) {
                    System.out.println("Captured frame " + i + " - Size: " + img.getWidth() + "x" + img.getHeight());
                }
            }

            grabber.stop();
        } catch (Exception e) {
            System.err.println("Error reading video file.");
            e.printStackTrace();
        }
    }
}
