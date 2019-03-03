package Parser.Commands.Queries;

import GraphicsBackend.Turtle;
import Main.BackendController;
import Parser.Commands.TurtleCommand;

public class XcorCommand extends TurtleCommand {

    public XcorCommand(){
        isConstant = false;
        numParameters = 0;
    }

    @Override
    protected void turtleAction(Turtle turtle) {
        returnValue =turtle.getxPos();
    }
}
