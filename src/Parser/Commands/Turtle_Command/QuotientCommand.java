package Parser.Commands.Turtle_Command;

import GraphicsBackend.Turtle;
import Parser.BackendController;
import Parser.Commands.Command;

public class QuotientCommand extends Command {

    public QuotientCommand(){
        setIsEvaluated(false);
        setNumParameters(2);
        isOutputCommand = true;
    }

    @Override
    protected void performAction(BackendController backendController, Turtle turtle) {
        setReturnValue(getChildren().get(0).getReturnValue() / getChildren().get(1).getReturnValue());
    }

    @Override
    public Command copy() {
        return new QuotientCommand();
    }
}
