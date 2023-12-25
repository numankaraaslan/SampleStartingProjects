package com.example.OpenCV;

import java.io.ByteArrayInputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nu.pattern.OpenCV;

public class App extends Application
{
	VideoCapture capture = new VideoCapture();
	int cameraId = 0; // can be 1 2 3...
	ScheduledExecutorService timer;
	ImageView imageView;

	@Override
	public void start(Stage stage)
	{
		Button buttonCaptureWebcam = new Button("Capture Camera");
		buttonCaptureWebcam.setOnMouseClicked(captureWebcam());
		Button buttonCaptureVideo = new Button("Capture Video");
		buttonCaptureVideo.setOnMouseClicked(captureVideo());
		imageView = new ImageView();
		imageView.setFitWidth(854);
		imageView.setFitHeight(480);
		imageView.prefHeight(480);
		imageView.prefWidth(854);
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(10, 10, 10, 10));
		vbox.getChildren().addAll(buttonCaptureWebcam, buttonCaptureVideo, imageView);
		Scene scene = new Scene(vbox, 1280, 720);
		stage.setScene(scene);
		stage.setTitle("OpenCV Application");
		stage.show();
	}

	private EventHandler<? super MouseEvent> captureVideo()
	{
		return new EventHandler<Event>()
		{
			@Override
			public void handle(Event event)
			{
				capture = new VideoCapture("C:\\Somefolder\\somevideo.mp4");
				if (capture.isOpened())
				{
					Runnable frameGrabber = new Runnable()
					{
						@Override
						public void run()
						{
							Mat frame = grabFrame();
							MatOfByte byte_matrix = new MatOfByte();
							Imgcodecs.imencode(".bmp", frame, byte_matrix);
							Image imageToShow = new Image(new ByteArrayInputStream(byte_matrix.toArray()));
							Platform.runLater(new Runnable()
							{
								@Override
								public void run()
								{
									imageView.setImage(imageToShow);
								}
							});
						}
					};
					timer = Executors.newSingleThreadScheduledExecutor();
					timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
				}
				else
				{
					System.err.println("noooooooooo");
				}
			}
		};
	}

	private EventHandler<? super MouseEvent> captureWebcam()
	{
		return new EventHandler<Event>()
		{
			@Override
			public void handle(Event event)
			{
				capture.open(cameraId);
				if (capture.isOpened())
				{
					Runnable frameGrabber = new Runnable()
					{
						@Override
						public void run()
						{
							Mat frame = grabFrame();
							MatOfByte byte_matrix = new MatOfByte();
							Imgcodecs.imencode(".bmp", frame, byte_matrix);
							Image imageToShow = new Image(new ByteArrayInputStream(byte_matrix.toArray()));
							Platform.runLater(new Runnable()
							{
								@Override
								public void run()
								{
									imageView.setImage(imageToShow);
								}
							});
						}
					};
					timer = Executors.newSingleThreadScheduledExecutor();
					timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
				}
				else
				{
				}
			}
		};
	}

	private Mat grabFrame()
	{
		Mat frame = new Mat();
		if (this.capture.isOpened())
		{
			try
			{
				this.capture.read(frame);
				if (!frame.empty())
				{
					Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
				}
			}
			catch (Exception e)
			{
			}
		}

		return frame;
	}

	static
	{
		OpenCV.loadLocally();
	}

	public static void main(String[] args)
	{
		launch();
	}
}