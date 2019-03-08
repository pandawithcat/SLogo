package Parser.Commands.Turtle_Command;

import Main.BackendController;
import Parser.Commands.Command;

public class IfCommand extends ControlCommand {

    private final int commandList = 1;

    public IfCommand() {
        super();
        isConstant = false;
        numParameters = 2;
    }

    @Override
    protected void performAction(BackendController backendController) {
        if (initialExpression.getReturnValue() != 0) {
            setListToRun((ListStartCommand) myChildrenList.get(commandList));
        }
        runAgain = false;
    }

    @Override
    public void setInitialExpression() {
        initialExpression = myChildrenList.get(0);
    }

    @Override
    public Command copy() {
        return new IfCommand();
    }
}
