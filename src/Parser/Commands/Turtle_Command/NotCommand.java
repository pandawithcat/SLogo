package Parser.Commands.Turtle_Command;

import Main.BackendController;
import Parser.Commands.Command;

public class NotCommand extends BooleanCommand{

    public NotCommand(){
        isConstant = false;
        numParameters = 1;
    }

    public void performAction(BackendController backendController){
        returnValue =  returnValue(myChildrenList.get(0).getReturnValue() ==0);
    }

    @Override
    public Command copy() {
        return new NotCommand();
    }
}
