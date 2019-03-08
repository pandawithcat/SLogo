package Parser.Commands.Turtle_Command;

import Main.BackendController;
import Parser.Commands.Command;
import Parser.Commands.ConstantCommand;
import Parser.Commands.Variable;

public class ForCommand extends ControlCommand {

    private ListStartCommand commandListOrig = (ListStartCommand) myChildrenList.get(1);
    private int increment;
    private Variable loopVar;

    public ForCommand() {
        super();
        isConstant = false;
        numParameters = 2;
    }

    @Override
    protected void performAction(BackendController backendController) {
        updateLoopVar(backendController);
        if (currCount <= limit) {
            setListToRun(copyList(commandListOrig));
            currCount += increment;
            runAgain = true;
        }
        else{
            runAgain = false;
        }
    }

    private void updateLoopVar(BackendController backendController) {
        var makeLoopVar = new MakeVariableCommand();
        makeLoopVar.addChildren(loopVar);
        makeLoopVar.addChildren(new ConstantCommand((double) currCount));
        makeLoopVar.execute(backendController);
    }

    @Override
    public void setInitialExpressions() {
        ListStartCommand loopParam = (ListStartCommand) myChildrenList.get(0);
        initialExpressions.add(loopParam.getChildren().get(1));
        initialExpressions.add(loopParam.getChildren().get(2));
        initialExpressions.add(loopParam.getChildren().get(3));
    }

    @Override
    public void setUpLoop() {
        ListStartCommand loopParam = (ListStartCommand) myChildrenList.get(0);
        loopVar = (Variable) loopParam.getChildren().get(0);
        currCount = (int) initialExpressions.get(0).getReturnValue();
        limit = (int) initialExpressions.get(1).getReturnValue();
        increment = (int) initialExpressions.get(2).getReturnValue();
    }

    @Override
    public Command copy() {
        return new ForCommand();
    }
}