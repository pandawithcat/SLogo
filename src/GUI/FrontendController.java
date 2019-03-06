package GUI;

import GUI.Controls.*;
import GUI.Modules.*;
import GraphicsBackend.Pen;
import GraphicsBackend.Turtle;
import Main.BackendController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class will contain the initial layout for the Window
 *
 * @author Januario Carreiro & David Liu
 */
public class FrontendController {
    private Stage myStage;
    private BorderPane myContainer;
    private Editor editor;
    private AvailableVars availableVars;
    private UserCommands userCommands;
    private GraphicsArea graphicsArea;
    private Palettes palettes;
    private CurrentState currentState;
    private Console console;
    private OpenHelp openHelp;
    private MoveTurtle moveTurtle;
    private SwitchLanguages switchLanguages;
    private Control redo, run, undo, stopExecution, setTurtleImage, save;
    private ColorPicker setBackgroundColor, setPenColor;
    private BackendController backendController;
    private String defaultLanguage = "English";
    private List<Turtle> turtles;

    /**
     * TODO: add JavaDoc
     *
     * @param root
     */
    public FrontendController(BorderPane root, Stage stage) {
        this.myStage = stage;
        this.myContainer = root;
        this.editor = new Editor(200, 200, this);
        this.availableVars = new AvailableVars(200, 100, this);
        this.userCommands = new UserCommands(200, 100, this);
        this.console = new Console(600, 100, this);
        this.graphicsArea = new GraphicsArea(400, 400, this);
        this.palettes = new Palettes(200, 200, this);
        this.currentState = new CurrentState(200, 200, this);

        var leftBorderPane = new BorderPane();
        leftBorderPane.setTop(palettes.getContent());
        leftBorderPane.setCenter(currentState.getContent());

        var rightBorderPane = new BorderPane();
        rightBorderPane.setTop(availableVars.getContent());
        rightBorderPane.setCenter(editor.getVBox());
        rightBorderPane.setBottom(userCommands.getContent());

        root.setTop(returnButtons());
        root.setBottom(console.getContent());
        root.setLeft(leftBorderPane);
        root.setCenter(graphicsArea.getContent());
        root.setRight(rightBorderPane);

        setBackgroundColor(Color.LIGHTBLUE);
    }

    private HBox returnButtons() {
        var buttonHandler = new HBox();

        buttonHandler.setId("buttonHandler");
        buttonHandler.setMinWidth(600);
        buttonHandler.setMinHeight(30);

        openHelp = new OpenHelp(this);
        openHelp.getHyperlink().setTooltip(new Tooltip("Help"));

        setPenColor = new SetPenColor().getColorPicker();
        setPenColor.setTooltip(new Tooltip("Set Pen Color"));

        setBackgroundColor = new SetBackgroundColor(this).getColorPicker();
        setBackgroundColor.setTooltip(new Tooltip("Set Background Color"));

        setTurtleImage = new SetTurtleImage(this);
        setTurtleImage.getButton().setTooltip(new Tooltip("Set Turtle Image"));

        switchLanguages = new SwitchLanguages(this);
        switchLanguages.getButton().setTooltip(new Tooltip("Switch Languages"));

        save = new Save(this);
        save.getButton().setTooltip(new Tooltip("Save"));

        moveTurtle = new MoveTurtle(this);
        moveTurtle.getButton().setTooltip(new Tooltip("Move Turtle"));

        undo = new Undo(this);
        undo.getButton().setTooltip(new Tooltip("Undo"));

        redo = new Redo(this);
        redo.getButton().setTooltip(new Tooltip("Redo"));

        stopExecution = new StopExecution(this);
        stopExecution.getButton().setTooltip(new Tooltip("Stop Execution"));

        run = new Run(editor);
        run.getButton().setTooltip(new Tooltip("Run"));

        var leftButtons = new HBox(openHelp.getHyperlink(), switchLanguages.getButton(),
                setBackgroundColor, setPenColor, setTurtleImage.getButton(), save.getButton());
        leftButtons.getStyleClass().add("button-container");

        var padding = new Region();

        var rightButtons = new HBox(moveTurtle.getButton(), undo.getButton(), redo.getButton(), stopExecution.getButton(), run.getButton());
        rightButtons.getStyleClass().add("button-container");

        buttonHandler.setHgrow(padding, Priority.ALWAYS);
        buttonHandler.getChildren().addAll(leftButtons, padding, rightButtons);
        return buttonHandler;
    }

    public BorderPane getContainer() {
        return myContainer;
    }

    /**
     * TODO: make it possible to set image of any button through reflection
     */
    public void handleSetTurtleImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("data/turtles"));
        File chosenFile = fileChooser.showOpenDialog(myStage);
        if (chosenFile != null) {
            try {
                setTurtleImage.setImage(chosenFile);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Error 404", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    public void setBackendController(BackendController backendController) {
        this.backendController = backendController;
        changeLanguage(defaultLanguage);
        setGraphicsArea();
    }

    public void setGraphicsArea(){
        List<Line> lines = backendController.getMyGrid().getAllObjects();
        turtles = backendController.getMyTurtles();
        List<ImageView> turtleImages = new ArrayList<>();
        List<Boolean> turtleActives = new ArrayList<>();
        for (Turtle turtle:turtles) {
            ImageView turtleImage = turtle.getAdjustedTurtleImageView(0,0);
            Boolean isTurtleActive = turtle.getIsTurtleActive();
            turtleImages.add(turtleImage);
            turtleActives.add(isTurtleActive);
        }
        graphicsArea.setVariables(lines, turtleImages, turtleActives);
    }

    public void setBackgroundColor(Paint color) {
        graphicsArea.setColor(color);
    }

    public void sendCommandString(String commandString) {
        backendController.parseAndRun(commandString);
    }

    public void switchTurtleActive(int turtleNumber) {
        if (turtles.get(turtleNumber).getIsTurtleActive()) {
            turtles.get(turtleNumber).setTurtleActive(false);
        }
        else {
            turtles.get(turtleNumber).setTurtleActive(true);
        }
    }

//    public void addToPrevCommands(String commandString) {
//        console.addToHistory(commandString);
//    }

    public void addToConsole(String commandString) {
        console.addToConsole(commandString);
    }

    public void consoleShowError(String errorString) {
        //errorString is truly coming from BackEnd though
        console.showError(errorString);
    }

    public void getAvailableVars(List<String> availableVarsList) {
        //availableVarsList also truly coming from BackEnd though
        availableVars.setAvailableVars(availableVarsList);
    }

    public void getUserCommands(List<String> userCommandsList) {
        //userCommandsList also truly coming from BackEnd though
        userCommands.setUserCommands(userCommandsList);
    }

    public void setCurrentState() {
        turtles = backendController.getMyTurtles();
        int counter = 0;
        List<Integer> ids = new ArrayList<>();
        List<Double> xPositions = new ArrayList<>();
        List<Double> yPositions = new ArrayList<>();
        List<Optional<Color>> penColors = new ArrayList<>();
        List<Double> angles = new ArrayList<>();
        List<Boolean> penUp = new ArrayList<>();
        //Or Is It Pen Width???
        List<Integer> penSize = new ArrayList<>();
        for (Turtle turtle: turtles) {
            Pen pen = turtle.getMyPen();
            ids.add(counter);
            xPositions.add(turtle.getxPos());
            yPositions.add(turtle.getyPos());
            angles.add(turtle.getMyAngle());
            penColors.add(pen.getPenColor());
            penUp.add(pen.getPenUp());
            penSize.add(pen.getPenSize());
            counter++;
        }
        currentState.getTurtleAndPens(ids, xPositions, yPositions, angles, penColors, penUp, penSize);
    }

    public void changeLanguage(String language) {
        backendController.setCommandLanguage(language);
    }

    public void step() {
        setGraphicsArea();
        setCurrentState();
    }

    public void save() {}
}