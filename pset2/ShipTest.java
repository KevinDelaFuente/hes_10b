/**
 * ShipTest demonstrates and tests the Ship class hierarchy, including CruiseShip, CargoShip, and ContainerVessel.
 * It creates arrays of Ship objects, exercises constructors, mutators, and prints their details.
 */
class ShipTest {
    public static void main(String[] args) {
        Ship[] ships = {
            new CruiseShip(10000, false),
            new CruiseShip("Titanic", 1912, Ship.EngineType.STEAM_ENGINE, 2240, true),
            new CargoShip(),
            new CargoShip("Evergreen", 2022, Ship.EngineType.DIESEL, 100000),
            new ContainerVessel("Harvard's Pride",50000, 1000),
            new ContainerVessel(300000)
        };

        System.out.println("--- Ship Array Test 1 ---");
        for (Ship ship : ships) {
            System.out.println(ship);
        }

        // Expanded testing array
        Ship[] expandedShips = new Ship[6];
        expandedShips[0] = new CruiseShip("TA of the Seas", 25, false);
        expandedShips[1] = new CruiseShip("Covid Carnival", 3646, true);
        expandedShips[2] = new CargoShip("Big Cargo Boat", 2021, Ship.EngineType.DIESEL, 200000.0);
        expandedShips[3] = new CargoShip("Maersk", 50000.0);
        expandedShips[4] = new ContainerVessel("MSC Kevin", 2015, Ship.EngineType.DIESEL, 197362.0, 19224);
        expandedShips[5] = new ContainerVessel(12000);

        // Use mutators to change some values
        ((CruiseShip)expandedShips[0]).setMaxNumberPassengers(2600);
        ((CruiseShip)expandedShips[1]).setInfectedNorovirus(false);
        ((CargoShip)expandedShips[2]).setCargoCapacity(210000.0);
        ((ContainerVessel)expandedShips[5]).setNumberOfContainers(15000);

        System.out.println("\n--- Ship Array Test 2 ---");
        for (Ship ship : expandedShips) {
            System.out.println(ship);
        }
    }
}