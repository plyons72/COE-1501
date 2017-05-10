//Patrick Lyons
//Project 3
//CarTracker.java


import java.util.HashMap;
import java.util.Scanner;


public class CarTracker{

  //Total number of cars in the system
  private static int numCars = 0;

  //Maps VIN to PQ index
  private static HashMap<String,Integer> carIndex = new HashMap<String, Integer>();

  //Minimum PQ for price
  private static MinPQ pricePQ = new MinPQ(100);

  //Minimum PQ for mileage
  private static MinPQ milePQ = new MinPQ(100);

  //Gets all required info to add a new car to the list
	public static void addCar(){

    //Initializes the scanner
		Scanner scan = new Scanner(System.in);

    //Creates a new instance of car
		Car car = new Car();

    //Sets VIN of car based on user input
		System.out.print("Enter the VIN number: ");
		car.setVIN(scan.next());

    //Sets make of car based on user input
		System.out.print("Enter the make: ");
		car.setMake(scan.next());

    //Sets model of car based on user input
		System.out.print("Enter the model: ");
		car.setModel(scan.next());

    //Sets price of car based on user input
		System.out.print("Enter the price: ");
		car.setPrice(scan.nextDouble());

    //Sets mileage of car based on user input
		System.out.print("Enter the Mileage(rounded to nearest mile): ");
		car.setMileage(scan.nextInt());

    //Sets color of car based on user input
		System.out.print("Enter the color: ");
		car.setColor(scan.next());

    //Adds a new car to the total count
		numCars++;

    //Inserts a car into pq based on price
		pricePQ.insertPrice(numCars,car);

    //Inserts a car to the pq based on mileage
		milePQ.insertMile(numCars,car);


		carIndex.put(car.getVIN(),numCars);
	}

  //Allows a user to change the price, mileage, or color of a car based on VIN number
	public static void updateCar(){

    //Starts a scanner
    Scanner scan = new Scanner(System.in);

    //Asks user for VIN
		System.out.print("Enter the VIN number of the car you'd like to update: ");

    //Stores vin
		String vin = scan.next();

    //Checks if VIN has an associated car
		if(!carIndex.containsKey(vin)){
			System.out.println("VIN not found");
			return;
		}

		System.out.println("What would you like to update?: ");
		System.out.println("\t0. Color");
		System.out.println("\t1. Price");
		System.out.println("\t2. Mileage");
    System.out.println("\t3. Quit");


    int num = scan.nextInt();
		int i = carIndex.get(vin);
    Car car;

		switch(num){
			case 0:
          System.out.println("Enter the Color: ");

          car = milePQ.carOf(i);
          car.setColor(scan.next());
          pricePQ.changePriceCar(i, car);
          milePQ.changeMileageCar(i, car);
        break;

			case 1:
    			System.out.print("Enter the price: ");
    			car = pricePQ.carOf(i);
          car.setPrice(scan.nextDouble());
          pricePQ.changePriceCar(i, car);
				break;

			case 2:
  				System.out.print("Enter the mileage: ");
  				car = milePQ.carOf(i);
          car.setMileage(scan.nextInt());
          milePQ.changeMileageCar(i, car);
				break;

			case 3:
        System.out.println("Nothing was updated.");
      break;

			default:
		    System.out.println("Invalid choice.");
			break;
		} //End of Switch

  }//End of updateCar

	//Remove car in PQ
	public static void removeCar(){

    Scanner scan = new Scanner(System.in);

		System.out.println("Enter VIN of the car to be removed: ");

		String vin = scan.next();

    //Checks if VIN is in the system
		if(!carIndex.containsKey(vin)){
			System.out.println("VIN not found");
			return;
		}

    //Gets index of car based on vin
		int i = carIndex.get(vin);

    //Deletes car from PQ for price
		pricePQ.deletePrice(i);

    //Deletes car from PQ for mileage
		milePQ.deleteMile(i);

    //Decrements number of cars in system
		numCars--;

		System.out.println("The car with VIN " + vin + " has been removed.");
	}//End of removeCar

	//Print out car info
	public static void findCar(int choice){
		Scanner scan = new Scanner(System.in);

    //Sets make and model to null so user can search based on them
		String make = null;
		String model = null;

    //Retrieves car based on make, model, and depending on the switch case, by lowest price or mileage
		switch(choice){

      //Lowest price
			case 4:
				System.out.println((pricePQ.minCar()).toString());
			break;

      //Lowest mileage
			case 5:
				System.out.println((milePQ.minCar()).toString());
			break;

      //Lowest price for given make and model
			case 6:
				System.out.print("Enter the make: ");
				make = scan.next();
				System.out.print("Enter the model: ");
				model = scan.next();
				pricePQ.makeMinP(make,model);
			break;

      //Lowest mileage for given make and model
			case 7:
				System.out.print("Enter the make: ");
				make = scan.next();
				System.out.print("Enter the model: ");
				model = scan.next();
				milePQ.makeMinM(make,model);
			break;
		}

	}//End of findCar

	public static void main(String[] args){
    Scanner scan = new Scanner(System.in);

		boolean done = false;
		int choice = -1;

    //Loops while the user is still engaged
		while(!done){

      //Inserts a divider for the menu.
      System.out.println("\n************************************************************");

      System.out.println("Please select an option: ");

			System.out.println("\t0. Add a new Car");
			System.out.println("\t1. Update an existing Car");
			System.out.println("\t2. Remove a Car");
			System.out.println("\t3. Retrieve the lowest priced Car");
			System.out.println("\t4. Retrieve the lowest mileage Car");
			System.out.println("\t5. Retrieve the lowest price car by make and model");
			System.out.println("\t6. Retrieve the lowest mileage car by make and model");
      System.out.println("\t7. Leave");

      //Switch to menu option based on user input
			switch(scan.nextInt()){

				case 0:
          System.out.println("\nAdd a new car:\n");
					addCar();
				break;

				case 1:
          System.out.println("\nUpdate an existing car:\n");
					updateCar();
				break;

				case 2:
          System.out.println("\nRemove an existing car:\n");
					removeCar();
				break;

				case 3:
          System.out.println("\nFind the car with the lowest price:\n");
					findCar(4);
				break;

				case 4:
          System.out.println("\nFind the car with the lowest mileage:\n");
					findCar(5);
				break;

				case 5:
          System.out.println("\nLowest price by make and model:\n");
					findCar(6);
				break;

				case 6:
          System.out.println("\nLowest mileage by make and model:\n");
					findCar(7);
				break;

        case 7:
          System.out.println("Goodbye");
          done = true;
        break;

				default:
					System.out.println("Invalid input");
				break;
			} //End of switch

		} //End of While

	} //End of main

} //End of program
