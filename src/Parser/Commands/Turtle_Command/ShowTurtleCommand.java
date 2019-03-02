package Parser.Commands.Turtle_Command;

import Parser.Commands.Command;
import Parser.Commands.TurtleCommand;

public class ShowTurtleCommand extends TurtleCommand {

    public ShowTurtleCommand(){
        isConstant = false;
        numParameters = 0;
    }

    public void performAction() {
        getTurtle().setTurtleVisibility(true);
        returnValue = 1;

    }
}