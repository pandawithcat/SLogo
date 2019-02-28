package Parser.Commands.Math;

import Parser.Commands.Command;

public class TanCommand extends Command {

    public TanCommand(){
        isConstant = false;
        numParameters = 1;
    }

    public void execute(){
        returnValue =  Math.tan(myChildrenList.get(0).getReturnValue());
    }

}
