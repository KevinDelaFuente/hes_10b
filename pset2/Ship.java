/**
 * Abstract base class representing a generic ship with a name, year built, and engine type.
 */
public abstract class Ship {
    private String name;
    private int year;
    private EngineType engineType;
    public enum EngineType {STEAM_ENGINE, STEAM_TURBINE,GAS_TURBINE, DIESEL, ELECTRIC, WIND, HUMAN_FORCE, UNKNOWN};

    // Constructor
    public Ship(){
        this.name = "UNKNOWN";
        this.year = 1900;
        this.engineType = EngineType.UNKNOWN;
    }

    public Ship(String name){
        this.name = name;
        this.year = 1900;
        this.engineType = EngineType.UNKNOWN;
    }

    public Ship(String name, int year, EngineType engineType){
        this.name = name;
        this.year = year;
        this.engineType = engineType;
    }

    // Accessor
    public String getName(){
        return this.name;
    }

    public int getYear(){
        return this.year;
    }

    public EngineType getEngineType(){
        return this.engineType;
    }

    // Mutator
    public void setName(String name){
        this.name = name;
    }

    public void setYear(int year){
        this.year = year;
    }

    public void setEngineType(EngineType engineType){
        this.engineType = engineType;
    }

    // toString

    public String toString(){
        System.out.println("Ship's name: " + this.name);
        System.out.println("Year built: " + this.year);
        System.out.println("Engine Type: " + this.engineType);
        return String.format("[Ship's name: %s, Year built: %d, Engine Type: %s]", this.name, this.year, this.engineType.name());
    }

}

/**
 * Represents a cruise ship, with a maximum number of passengers and norovirus infection status.
 */
class CruiseShip extends Ship {
    private int maxNumberPassengers;
    private boolean infectedNorovirus;

    // Constructor
    public CruiseShip(){
        super();
        this.maxNumberPassengers = 0;
        this.infectedNorovirus = false;
    }

    public CruiseShip(int maxNumberPassengers, boolean infectedNorovirus){
        super();
        this.maxNumberPassengers = maxNumberPassengers;
        this.infectedNorovirus = infectedNorovirus;
    }

    public CruiseShip(String name, int year, EngineType engineType, int maxNumberPassengers, boolean infectedNorovirus){
        super(name, year, engineType);
        this.maxNumberPassengers = maxNumberPassengers;
        this.infectedNorovirus = infectedNorovirus;
    }

    public CruiseShip(String name, int maxNumberPassengers, boolean infectedNorovirus) {
    super(name); // Created a constructor in parent class with name as only parameter
    this.maxNumberPassengers = maxNumberPassengers;
    this.infectedNorovirus = infectedNorovirus;
}

    // Accesor
    public int getMaxNumberPassengers(){
        return this.maxNumberPassengers;
    }

    public boolean isInfectedNorovirus(){
        return infectedNorovirus;
    }

    // Mutator
    public void setMaxNumberPassengers(int maxNumberPassengers){
        this.maxNumberPassengers = maxNumberPassengers;
    }

    public void setInfectedNorovirus(boolean infectedNorovirus){
        this.infectedNorovirus = infectedNorovirus;
    }
    
    // toString
    @Override
    public String toString() {
        return String.format("[Ship's name: %s, Maximum number of passengers: %d]", getName(), getMaxNumberPassengers());
    }

}

/**
 * Represents a cargo ship, with a cargo capacity in tonnage.
 */
class CargoShip extends Ship {
    private double cargoCapacity;

    // Constructor
    public CargoShip(){
        super();
        this.cargoCapacity = 0.0;
    }

    public CargoShip(double cargoCapacity){
        super();
        this.cargoCapacity = cargoCapacity;
    }

    public CargoShip(String name, int year, EngineType engineType, double cargoCapacity){
        super(name, year, engineType);
        this.cargoCapacity = cargoCapacity;
    }

    public CargoShip(String name, double cargoCapacity) {
        super(name); // Created a constructor in parent class with name as only parameter
        this.cargoCapacity = cargoCapacity;
    }

    // Accessor
    public double getCargoCapacity(){
        return this.cargoCapacity;
    }

    // Mutator
    public void setCargoCapacity(double cargoCapacity){
        this.cargoCapacity = cargoCapacity;
    }

    // toString
    @Override
    public String toString(){
        return String.format("[Ship's name: %s, Cargo capacity in tons: %,.1f]", getName(), getCargoCapacity());
    }
}

/**
 * Represents a container vessel, a specialized cargo ship with a number of containers field.
 */
class ContainerVessel extends CargoShip {
    private int numberOfContainers;

    // Constructor
    public ContainerVessel(){
        this.numberOfContainers = 0;
    }

    public ContainerVessel(int numberOfContainers){
        this.numberOfContainers = numberOfContainers;
    }

    public ContainerVessel(String name, int year, EngineType engineType, double cargoCapacity, int numberOfContainers){
        super(name, year, engineType, cargoCapacity);
        this.numberOfContainers = numberOfContainers;
    }

    public ContainerVessel(String name, double cargoCapacity, int numberOfContainers) {
        super(name, cargoCapacity); 
        this.numberOfContainers = numberOfContainers;
    }
    // Accessor
    public int getNumberOfContainers(){
        return this.numberOfContainers;
    }

    // Mutator
    public void setNumberOfContainers(int numberOfContainers){
        this.numberOfContainers = numberOfContainers;
    }

    // toString
    @Override
    public String toString(){
        return String.format("[Ship's name: %s, Number of containers: %d]", getName(), getNumberOfContainers());
    }
}
