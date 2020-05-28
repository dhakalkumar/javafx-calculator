package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class Calculator extends Application {
	private TextField display;
	private Button[] buttons;
	private String[] buttonLabels = { 
			"7", "8", "9", "+",
			"4", "5", "6", "-",
			"1", "2", "3", "x",
			"C", "0", "=","/"
	};
	private int result = 0;
	private String str = "0";
	private char lastOperator = ' ';

	// event handler for the buttons
	EventHandler<ActionEvent> handler = e -> {
		String currentButton = ((Button) e.getSource()).getText();
		switch (currentButton) {
		// numbers
		case "0":
		case "1":
		case "2":
		case "3":
		case "4":
		case "5":
		case "6":
		case "7":
		case "8":
		case "9":
			if (str.equals("0")) {
				str = currentButton;
			} else {
				str += currentButton;
			}
			display.setText(str);
			if (lastOperator == '=') {
				result = 0;
				lastOperator = ' ';
			}
			break;

		case "+":
			compute();
			lastOperator = '+';
			break;

		case "-":
			compute();
			lastOperator = '-';
			break;

		case "x":
			compute();
			lastOperator = '*';
			break;

		case "/":
			compute();
			lastOperator = '/';
			break;

		case "=":
			compute();
			lastOperator = '=';
			break;

		case "C":
			result = 0;
			str = "0";
			lastOperator = '=';
			display.setText("0");
			break;

		}
	};

	// computation of the results
	private void compute() {
		try {
			int input = Integer.parseInt(str);
			str = "0";
			if (lastOperator == ' ') {
				result = input;
			} else if (lastOperator == '+') {
				result += input;
			} else if (lastOperator == '-') {
				result -= input;
			} else if (lastOperator == '*') {
				result *= input;
			} else if (lastOperator == '/') {
				result /= input;
			} else if (lastOperator == '=') {

			}
			display.setText(result + "");
		} catch (Exception e) {
			display.setText("Error!");
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primarysStage) {
		display = new TextField("0");
		display.setEditable(false);
		display.setAlignment(Pos.CENTER_RIGHT);

		int cols = 4;
		int rows = 4;
		GridPane buttonsPane = new GridPane();
		buttonsPane.setPadding(new Insets(50, 0, 15, 0));
		buttonsPane.setVgap(5);
		buttonsPane.setHgap(5);
		ColumnConstraints[] columns = new ColumnConstraints[cols];
		for (int i = 0; i < cols; i++) {
			columns[i] = new ColumnConstraints();
			columns[i].setHgrow(Priority.ALWAYS);
			columns[i].setFillWidth(true);
			buttonsPane.getColumnConstraints().add(columns[i]);
		}

		buttons = new Button[16];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new Button(buttonLabels[i]);
			buttons[i].setOnAction(handler);
			;
			buttons[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			buttonsPane.add(buttons[i], i % cols, i / rows);
		}

		BorderPane root = new BorderPane();
		root.setPadding(new Insets(15, 15, 15, 15));
		root.setTop(display);
		root.setCenter(buttonsPane);

		Scene scene = new Scene(root, 200, 250);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primarysStage.setScene(scene);
		primarysStage.setTitle("Calculator");
		primarysStage.getIcons().add(new Image("file:calculator.png"));
		// primarysStage.setResizable(false);
		primarysStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
