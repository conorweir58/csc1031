abstract class Shape
{
    protected String color;

    Shape (String color)
    {
        this.color = color;
    }

    abstract double getArea();

    void displayColor()
    {
        System.out.println("Shape Color: " + this.color);
    }
}


// Subclass Circle
class Circle extends Shape
{
    private double radius;

    // Constructor
    public Circle(String color, double radius)
    {
        super(color);
        this.radius = radius;
    }

    @Override
    double getArea()
    {
        if(getRadius() < 0)
        {
            return 0;
        }

        return Math.PI * Math.pow(getRadius(), 2);
    }

    public double getRadius()
    {
        return radius;
    }
}

// Subclass Rectangle
class Rectangle extends Shape
{
    private double width;
    private double height;

    // Constructor
    public Rectangle(String color, double width, double height)
    {
        super(color);
        this.width = width;
        this.height = height;
    }

    @Override
    double getArea()
    {
        if(getHeight() < 0 || getWidth() < 0)
        {
            return 0;
        }

        return getWidth() * getHeight();        
    }

    public double getWidth()
    {
        return width;
    }

    public double getHeight()
    {
        return height;
    }
}