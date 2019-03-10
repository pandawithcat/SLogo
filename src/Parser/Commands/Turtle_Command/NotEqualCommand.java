package Parser.Commands.Turtle_Command;

import GraphicsBackend.Turtle;
import Parser.BackendController;
import Parser.Commands.Command;

/**
 * @author kunalupadya
 */
public class NotEqualCommand extends BooleanCommand{

    public NotEqualCommand(){
        super();
        setNumParameters(2);
    }

    @Override
    protected void performAction(BackendController backendController, Turtle turtle) {
        setReturnValue(returnValue(getChildren().get(0).getReturnValue() != getChildren().get(1).getReturnValue()));
    }

    @Override
    public Command copy() {
        return new NotEqualCommand();
    }
}
