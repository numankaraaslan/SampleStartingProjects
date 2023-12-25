package com.example.JavaFX;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application
{
	Button button;
	Label label;
	Slider slider;
	Label sliderLabel;

	@Override
	public void start(Stage stage)
	{
		button = new Button("Click me");
		button.setOnMouseClicked(btnClicked());
		label = new Label("Hello javafx");
		VBox root = new VBox(10);
		root.setPadding(new Insets(10, 10, 10, 10));
		HBox hbox = new HBox(10);
		hbox.setAlignment(Pos.BASELINE_LEFT);
		hbox.getChildren().add(button);
		hbox.getChildren().add(label);
		HBox hbox2 = new HBox(10);
		hbox2.setAlignment(Pos.TOP_LEFT);
		slider = new Slider(1, 10, 1);
		slider.setBlockIncrement(1);
		slider.setMajorTickUnit(1);
		slider.setMinorTickCount(0);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setSnapToTicks(true);
		sliderLabel = new Label("Value = 1");
		slider.valueProperty().addListener(sliderValueChanged());
		hbox2.getChildren().addAll(slider, sliderLabel);
		root.getChildren().addAll(hbox, hbox2);
		Scene scene = new Scene(root, 640, 480);
		stage.setScene(scene);
		stage.show();
	}

	private ChangeListener<? super Number> sliderValueChanged()
	{
		return new ChangeListener<Number>()
		{
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
			{
				sliderLabel.setText("Value = " + newValue);
			}
		};
	}

	private EventHandler<? super MouseEvent> btnClicked()
	{
		return new EventHandler<Event>()
		{
			@Override
			public void handle(Event event)
			{
				label.setText("You clicked");
			}
		};
	}

	public static void main(String[] args)
	{
		launch();
	}

}