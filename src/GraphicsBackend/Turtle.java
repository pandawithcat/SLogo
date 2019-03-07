package GraphicsBackend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Turtle {
    public static final String DEFAULT_IMAGE = "/images/initialTurtle.png";
    public static final int TURTLE_SIZE = 50;
    public static final int HALF_TURTLE_SIZE = 25;
    public static final double HALF = 2.0;

    private double xPos;
    private double yPos;
    private double myAngle;
    private Pen myPen;
    private Image turtleImage;
    private Grid myGrid;
    private boolean isTurtleVisible;
    private boolean isTurtleActive;
    //TODO implement turtle shape using int index
    private int myShape;

    public Turtle(Grid grid){
        myGrid = grid;
        xPos = myGrid.getWidth()/ HALF;
        yPos = myGrid.getHeight()/ HALF;
        myPen = new Pen();
        myAngle = 90;
        isTurtleVisible = true;
        isTurtleActive = true;
        turtleImage = new javafx.scene.image.Image(this.getClass().getResourceAsStream(DEFAULT_IMAGE));
//        turtleImageView = new ImageView();
//        updateThisTurtleImageview();
    }

//    private void updateThisTurtleImageview() {
//        updateATurtleImageView(turtleImageView);
//    }

    public void updateATurtleImageView(ImageView turtle){
        turtle.setImage(turtleImage);
        turtle.setX(xPos-HALF_TURTLE_SIZE);
        turtle.setY(yPos-HALF_TURTLE_SIZE);
        turtle.setFitHeight(TURTLE_SIZE);
        turtle.setFitWidth(TURTLE_SIZE);
        turtle.setRotate(myAngle-90);
    }

    public boolean getIsTurtleActive(){
        return isTurtleActive;
    }

    public void setTurtleActive(boolean turtleActive) {
        isTurtleActive = turtleActive;
    }

    public void move(double dist){
        Point newPosition = myGrid.addMovement(xPos, yPos, myAngle, dist, myPen);
        xPos = newPosition.getMyX();
        yPos = newPosition.getMyY();
        System.out.println(xPos);
        System.out.println(yPos);
    }

    public void moveTo(Point point){
        xPos = point.getMyX()+myGrid.getWidth()/ HALF;
        yPos = point.getMyY()+myGrid.getHeight()/ HALF;;
    }

    public void turn(double angle){
        myAngle += angle;
    }

    public void turnTo(double angle){
        myAngle = angle;
    }

    public void setTurtleVisibility(boolean visibility){
        isTurtleVisible = visibility;
    }

    public boolean getTurtleVisibility(){
        return isTurtleVisible;
    }

    public boolean isTurtleVisible(){
        return isTurtleVisible;
    }

    public double getyPos() {
        return yPos;
    }

    public double getxPos() {
        return xPos;
    }

    public Point getPos(){
        return new Point(xPos,yPos);
    }

    public Grid getGrid() {
        return myGrid;
    }

    public int getMyShape(){ return myShape;}

    public double getMyAngle() {
        return myAngle;
    }

    public void setMyShape(int shapeIndex){this.myShape = shapeIndex;}

    public void setTurtleImage(Image turtleImage) {
        this.turtleImage = turtleImage;
    }

    public ImageView getAdjustedTurtleImageView(double xLeftCorner, double yLeftCorner) {
        ImageView returnedTurtle = new ImageView();
        updateATurtleImageView(returnedTurtle);
        returnedTurtle.setX(returnedTurtle.getX()+xLeftCorner);
        returnedTurtle.setY(returnedTurtle.getY()+yLeftCorner);
        return returnedTurtle;
    }

    public Pen getMyPen() {
        return myPen;
    }
}
