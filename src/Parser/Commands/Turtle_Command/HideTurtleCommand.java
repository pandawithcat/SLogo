package Parser.Commands.Turtle_Command;

import GraphicsBackend.Turtle;
import Parser.BackendController;
import Parser.Commands.Command;
import Parser.Commands.TurtleCommand;

/**
 * @author kunalupadya
 * @author Louis Lee
 * @author Dhanush
 */

public class HideTurtleCommand extends TurtleCommand {

    public HideTurtleCommand(){
        setNumParameters(0);
        isOutputCommand = false;
    }

    @Override
    protected void performAction(BackendController backendController, Turtle turtle) {
        turtle.setTurtleVisibility(false);
        setReturnValue(0);
    }

    @Override
    public Command copy() {
        return new HideTurtleCommand();
    }
}
