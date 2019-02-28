package Parser.Commands.Math;

import Parser.Commands.Command;

public class PowCommand extends Command {

    public PowCommand(){
        isConstant = false;
        numParameters = 2;
    }

    public void execute(){
        returnValue =  Math.pow(myChildrenList.get(0).getReturnValue(),myChildrenList.get(1).getReturnValue());
    }

}
