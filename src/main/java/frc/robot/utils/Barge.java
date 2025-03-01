package frc.robot.utils;

public enum Barge {
    RED(7.56,1.3646 ,0,4),
    BLUE(7.56,1.3646 ,4,8);

    private final double x1;
    private final double width;
    private final double y1;
    private final double height;

    Barge(double x1, double width, double y1, double height) {
        this.x1 = x1;
        this.width = width;
        this.y1 = y1;
        this.height = height;
    }

    public double getX1() {
        return x1;
    }

    public double getWidth() {
        return width;
    }

    public double getY1() {
        return y1;
    }

    public double getHeight() {
        return height;
    }
}
