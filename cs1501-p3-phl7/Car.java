//Patrick Lyons
//Project 3
//Car.java


//This file holds all the info needed for a given car: make, model, price, color, VIN, and mileage.
public class Car{

  //Variables for all the info needed per car
	private String vin;
	private String make;
	private String model;
	private double price;
	private int mileage;
	private String color;

  //Empty Car (no info given)
	public Car(){
		vin = null;
		make = null;
		model = null;
		price = 0;
		mileage = 0;
		color = null;
	}

  //Car with all info
	public Car(String v, String m1, String m2, double p, int m3, String c){
		vin = v;
		make = m1;
		model = m2;
		price = p;
		mileage = m3;
		color = c;
	}

  //Sets attribute of car and returns to user
	public void setVIN(String v){
		vin = v;
	}

	public void setMake(String m){
		make = m;
	}

	public void setModel(String m){
		model = m;
	}

	public void setPrice(double p){
		price = p;
	}

	public void setMileage(int m){
		mileage = m;
	}

	public void setColor(String c){
		color = c;
	}


  //Gets the needed info and returns to user.
  public String getVIN(){
    return vin;
  }

  public String getMake(){
    return make;
  }

  public String getModel(){
    return model;
  }

  public double getPrice(){
    return price;
  }

  public int getMileage(){
    return mileage;
  }

  public String getColor(){
    return color;
  }


  //Returns all the attributes of the car back to the user as a string
	public String toString(){
    String carInfo = "VIN: " + vin + "\nMake: " + make + "\nModel: " + model + "\nPrice: " + price + "\nMileage: " + mileage + "\nColor: " + color;
    return carInfo;
  }

}
