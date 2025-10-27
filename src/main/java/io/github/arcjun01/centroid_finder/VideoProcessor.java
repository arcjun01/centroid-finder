package io.github.arcjun01.centroid_finder;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.image.BufferedImage;


public class VideoProcessor {
    /* Pseudo code - will reimplement this part for video processing!

    IF args.length != 4 THEN
        PRINT usage and EXIT
    END IF

    inputPath - args[0]
    outputCsv - args[1]
    targetColor - parse hex(args[2])
    threshold - parse int(args[3])

    PRINT "Processing video..."

    // These are the helpers
    extractor - new FrameExtractor(inputPath)
    binarizer - new DistanceImageBinarizer(EuclideanColorDistance, targetColor, threshold)
    groupFinder - new DfsBinaryGroupFinder()
    imageFinder - new BinarizingImageGroupFinder(binarizer, groupFinder)
    writer - new CSVDataWriter(outputCsv)

    TRY
        extractor.start()
        frameCount - 0

        WHILE frame - extractor.grabFrame() IS NOT null
            image - convert(frame)
            IF image IS null THEN CONTINUE

            timeSec - extractor.getCurrentTimestampSeconds()
            groups - imageFinder.findConnectedGroups(image)

            x, y - (-1, -1)
            IF groups NOT empty THEN
                largest -groups[0]
                x <- largest.centroid().x()
                y<- largest.centroid().y()
            END IF

            writer.addRecord(timeSec, x, y)

            frameCount++
            IF frameCount % 30 == 0 THEN PRINT progress
        END WHILE

        writer.save()
        extractor.stop()
        PRINT "CSV saved!"

    CATCH Exception e
        PRINT "Error processing video"
    END TRY

    */


    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java VideoExperiment <path_to_video>");
            return;
        }

        String videoPath = args[0];

        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath)) {
            grabber.start();

            // Print metadata
            // System.out.println("Video Info:");
            // System.out.println("Width: " + grabber.getImageWidth());
            // System.out.println("Height: " + grabber.getImageHeight());
            // System.out.println("Frame Rate: " + grabber.getFrameRate());
            // System.out.println("Length (frames): " + grabber.getLengthInFrames());
            // System.out.println("Duration (seconds): " + (grabber.getLengthInTime() / 1_000_000.0));

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
