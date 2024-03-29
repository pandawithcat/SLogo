package Parser.Commands.Turtle_Command;

import Parser.BackendController;
import Parser.Commands.BasicCommand;
import Parser.Commands.Command;

/**
 * @author kunalupadya
 * @author Louis Lee
 * @author Dhanush
 */

public class TurtlesCommand extends BasicCommand {

    public TurtlesCommand(){
        setNumParameters(0);
        isOutputCommand = true;
    }

    @Override
    protected void performAction(BackendController backendController) {
        setReturnValue(backendController.getMyTurtles().size());
    }

    @Override
    public Command copy() {
        return new TurtlesCommand();
    }
}