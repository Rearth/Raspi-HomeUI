package raspi_ui.backend.calendar;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import raspi_ui.DisplayController;

import java.util.ArrayList;
import java.util.List;

public class CalendarDesign {

    private static final int height = 34;
    private static final int desiredLength = 30;
    private static final int width = 180;

    private static final Font font = Font.font("Verdana", 15);
    private static final Font titleFont = Font.font("Verdana", 28);
    private static final List<Node> nodes = new ArrayList<>();
    private static final String borderStyle = "-fx-border-color: darkgray; -fx-border-radius: 2; -fx-border-width: 1";

    private static int curHeight;

    public static TextFlow createLabel(CalendarEvent event) {
        Text dateText = new Text(event.getNiceTime() + " ");
        dateText.setFill(Color.DARKGRAY);
        dateText.setFont(font);

        String content = event.getName();
        int remove = content.length() - desiredLength;
        if (remove > 0) {
            content = content.substring(0, content.length() - remove);
            content += "...";
        }

        curHeight = height;
        if (content.length() < 14) {
            curHeight = height / 2;
        }
        content += " ";

        Text contentText = new Text(content);
        contentText.setStyle("-fx-font-weight: bold; -fx-effect: dropshadow(two-pass-box, black, 4, 0.2, 2, 2)");
        contentText.setFill(Color.DARKGRAY);
        contentText.setFont(font);

        TextFlow flow = new TextFlow();
        flow.getChildren().addAll(dateText, contentText);
        flow.setTextAlignment(TextAlignment.RIGHT);

        flow.setStyle(borderStyle);

        return flow;
    }


    //to be run in javafx thread
    public static void setLabels(Pane pane, List<CalendarEvent> events, boolean today) {

        System.out.println("settings labels");

        int curnum = 0;
        int curPos = 0;

        for (CalendarEvent event : events) {
            TextFlow label = createLabel(event);
            nodes.add(label);
            pane.getChildren().add(label);

            label.setPrefWidth(width);

            if (today) {
                label.setLayoutX(1024 - 450);
            } else {
                label.setLayoutX(1024 - 200);
            }

            curPos += curHeight + 7;
            label.setLayoutY(600 - 10  - 4 * curnum - curPos);

            //label.setLayoutY(600 - height * (1 + curnum) - 10);
            System.out.println("creating label for: " + event.getName() + " num= " + curnum + " height: " +  label.getHeight());

            curnum++;
        }

        if (events.size() == 0 ) {

            curPos += height;
            createPlaceHolder(pane, today, curnum, curPos);
            curPos += 12;
        }

        createTitle(pane, today, curnum, curPos);

    }

    private static void createPlaceHolder(Pane pane, boolean today, int curnum, int curPos) {

        Label empty = new Label("Keine Termine");
        nodes.add(empty);
        empty.setStyle("-fx-font-weight: bold; -fx-effect: dropshadow(two-pass-box, black, 4, 0.2, 2, 2)");
        empty.setTextFill(Color.DARKGRAY);
        empty.setFont(font);

        empty.setPrefWidth(180);
        empty.setAlignment(Pos.CENTER_RIGHT);
        empty.setTextAlignment(TextAlignment.RIGHT);

        if (today) {
            empty.setLayoutX(1024 - 450);
        } else {
            empty.setLayoutX(1024 - 200);
        }
        empty.setLayoutY(600 - 10  - 4 * curnum - curPos);

        curnum++;
        pane.getChildren().add(empty);

    }

    private static void createTitle(Pane pane, boolean today, int count, int curPos) {

        Label title = new Label("Heute");
        nodes.add(title);

        title.setFont(titleFont);
        title.setTextFill(Color.LIGHTGRAY);
        title.setStyle("-fx-font-weight: bold");
        title.setPrefWidth(width);
        title.setAlignment(Pos.BASELINE_RIGHT);
        title.setTextAlignment(TextAlignment.RIGHT);

        pane.getChildren().add(title);
        title.setLayoutY(600 - 10  - 4 * count- curPos - height);
        title.setLayoutX(1024 - 450);


        if (!today) {
            title.setText("Morgen");
            title.setLayoutX(1024 - 200);
        }

    }

    public static void clear() {
        DisplayController.getMainPane().getChildren().removeAll(nodes);
        nodes.clear();
    }

}
