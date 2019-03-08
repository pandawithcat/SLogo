package Parser;

import Main.BackendController;
import Parser.Commands.Command;
import Parser.Commands.RootCommand;
import Parser.Commands.Turtle_Command.*;
import javafx.scene.control.Alert;

import java.util.List;

/**
 * @author Kunal Upadya
 * @author Louis Lee
 */

public class ExecuteCommand {

    private static final String PARAMETERS_MISSING = "Parameters missing";
    private static final String WRONG_NUMBER_OF_PARAMETERS = "Wrong number of parameters";
    private static final int EXPRESSION_INDEX = 1;
    private Command headNode;
    private BackendController backendController;

    public ExecuteCommand(List<Command> commandsList, BackendController backendController) {
        ParsingTree parsingTree = new ParsingTree(commandsList, backendController);
        headNode = parsingTree.getRoot();
        this.backendController = backendController;
    }

    void runCommands(){
        //post traversal starting from headNode
        try {
            traverse(headNode);
        }
        catch (SyntaxError e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    private void traverse(Command node){
        if (node.getIsConstant()){
            return;
        }
        if (node.getClass() == MakeUserInstructionCommand.class || node.getClass() == ListEndCommand.class || node.getClass() == GroupEndCommand.class){
            //makeuserinstruction is the only class that is executed as it is parsed, so it does not need to be reparsed
            return;
        }
        if (node.getClass() == MakeVariableCommand.class){
            handleMakeVariableCommand(node);
            return;
        }
        if (node instanceof ControlCommand){
            handleControlCommand((ControlCommand) node);
            return;
        }
        if (node.getClass() == GroupStartCommand.class){
            node.getChildren().get(0).setIsConstant(true);
            traverseChildren(node);
            node.getChildren().get(0).setIsConstant(false);
            node.execute(backendController);
            return;
        }
        if (node.getClass() == IfCommand.class||node.getClass() == TellCommand.class){
            traverse(node.getChildren().get(0));
            node.execute(backendController);
        }
        if (node.getClass() == AskCommand.class){
            traverse(node.getChildren().get(0));
            node.execute(backendController);
            traverseChildren(node);
            backendController.loadTurtleTell();
            return;
        }
        // any commands that need to be executed before children are run happen before this point
        traverseChildren(node);
        handleAfterGenerationOfChildren(node);
    }

    private void handleControlCommand(ControlCommand node) {
        List<Command> initExpr = node.getInitialExpressions();
        for (Command expr: initExpr) {
            traverse(expr);
        }
        node.setUpLoop();
        while(node.shouldRunAgain()) {
            node.execute(backendController);
            if (node.getListToRun() != null) {
                handleListStartCommand(node.getListToRun());
                node.setReturnValue(node.getListToRun().getReturnValue());
            }
        }
    }

    private void handleListStartCommand(Command node) {
        traverseChildren(node);
    }

    private void handleAfterGenerationOfChildren(Command node) {
        if (node.getClass() == TextCommand.class){
            handleTextCommand(node);
        }
        else if (node.getNumParameters() == node.getChildren().size()){
            node.execute(backendController);
        }
        else if (node.getNumParameters() == (int) Double.POSITIVE_INFINITY){
            // do nothing, the root command should not throw an error
        }
        else{
            throw new SyntaxError(WRONG_NUMBER_OF_PARAMETERS);//TODO replace with another exception
        }
    }

    private void handleTextCommand(Command node) {
        node.execute(backendController);
        traverseChildren(node);
    }

    private void traverseChildren(Command node) {
        for (Command child : node.getChildren()) {
            traverse(child);
        }
    }

    private void handleMakeVariableCommand(Command node) {
        traverse(node.getChildren().get(EXPRESSION_INDEX));
        node.execute(backendController);
    }

}
