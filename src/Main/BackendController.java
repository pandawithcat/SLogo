package Main;


import Parser.ParseCommand;

public class InternalController {

    private ParseCommand parser;
    String codeLanguage;

    public InternalController(){
        setLanguage("English");
    }

    private void setLanguage(String language) {
        if (!codeLanguage.equals(language)){
            codeLanguage = language;
            parser = new ParseCommand(language);
        }
    }

    public void parseAndRun(String userInput){
        rootExpr = parser.parseInput(userInput);
        executeCommandTree(rootExpr);
    }

    private void executeCommandTree(Expression rootExpr) {
    }

    public String getCodeLanguage(){
        return codeLanguage;
    }

    public ParseCommand getParser(){
        return parser;
    }

}
