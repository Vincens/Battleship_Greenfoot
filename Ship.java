import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Ship here.
 * 
 * @PeterB BenL
 * @1.0
 */
 
public class Ship extends Actor
{
    private int shipHealth; // i.e. number of unhit squares in the ship
    private int shipLength;
    private boolean sunk;
    private int owner;
    private String endpt1;
    private char type; //will fill the array at ship's location
    private int Ycode, Xcode;
    private int orientation;
    //constructor
    public Ship (int length, char shiptype)
    {
        this.shipLength = length;
        this.shipHealth = length;
        this.owner = 1;
        this.type = shiptype;
        GreenfootImage Ship = new GreenfootImage("ShipPlaceholder.png");
        this.setImage(Ship);
        this.sunk = false;
    }
    
    public Ship (int length, int player, char shiptype)
    {
        this.shipLength = length;
        this.shipHealth = length;
        this.type = shiptype;
        this.owner = player;
        GreenfootImage Ship = new GreenfootImage("ShipPlaceholder.png");
        this.setImage(Ship);
        this.sunk = false;
    }
     //Method used for placement of each of the ships
    public void placement()
    {
        endpt1 = Greenfoot.ask("Input coordinate: ");
        Xcode = Integer.parseInt(endpt1.substring(1)) - 1;
        Ycode = Battlefield.charToInt(endpt1);
        if (Xcode == -1)
            Xcode = 9;
        // ____________ need to create arrow image at endpt1
        orientation = 0;
        // 0=up 1=right 2=down 3=left
        //rotate left and right by 90 degrees until position is set with enter
        GreenfootImage arrow = new GreenfootImage("black-arrow-md.png");
        this.setImage(arrow);
        this.setLocation(this.placeX(), this.placeY());
        String input = Greenfoot.ask("Input orientation: (0 = up, 1 = right, 2 = down, 3 = left)");
        orientation = Integer.parseInt(input);  
        //ensures ship stays in boundaries of grid
        while (Battlefield.checkBoundaries(orientation, Xcode, Ycode, this.shipLength)){
            input = Greenfoot.ask("Out of Bounds, please reinput orientation: (0 = up, 1 = right, 2 = down, 3 = left)");
            orientation = Integer.parseInt(input);
        }
        /**
        //ensure no ships overlap
        while (Battlefield.checkOverlap(orientation, Xcode, Ycode, this.shipLength)){
            input = Greenfoot.ask("Overlap, please reinput orientation: (0 = up, 1 = right, 2 = down, 3 = left)");
            orientation = Integer.parseInt(input);
        }
        **/
        //fills grid1 array with type elements at the location of the ship
        switch (orientation){
            case 0:
                for (int t = 0; t < this.shipHealth; t++)
                    Battlefield.setGrid(1, Xcode, Ycode - t, type);
            break;
            case 1:
                for (int t = 0; t < this.shipHealth; t++)
                    Battlefield.setGrid(1, Xcode + t, Ycode, type);
            break;
            case 2:
                for (int t = 0; t < this.shipHealth; t++)
                    Battlefield.setGrid(1, Xcode, Ycode + t, type);
            break;
            case 3:
                for (int t = 0; t < this.shipHealth; t++)
                    Battlefield.setGrid(1, Xcode - t, Ycode, type);
            break;
            default:
            break;
        }
}
    
    public void hit()
    {
        Greenfoot.playSound("Shotgun.wav");
        this.shipHealth--;
        checkSunk();
    }
    
    public int getHealth()
    {
        return this.shipHealth;
    }
    
    public boolean isSunk()
    {
        return this.sunk;
    }
    
    private void checkSunk()
    {
        if (this.shipHealth == 0)
        {
            this.sunk = true;
            Battlefield.incrementScore();
        }
        
    }
    //
    private int placeX()
    {
        return (81 + 54*Xcode);
    }
    private int placeY()
    {
        return (81 + 55*Ycode);
    }
}

