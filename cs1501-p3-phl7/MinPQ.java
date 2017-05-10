//Patrick Lyons
//Project 3
//Min PQ.java

/*This file holds info for a MinPQ for CarTracker.java
and allows a user to make a priority queue based on mileage
or price, in addition to any other information*/

import java.util.NoSuchElementException;

public class MinPQ{

    //Gives priority of i (cars[i])
    private Car[] cars;

    //Current number of items
    private int n;

    //Max number of items
    private int max_n;

    //Store items at indices 1 to n
    private int[] pq;

    //Inverse of pq (if pq[n] = i, then pqInv[i] = n)
    private int[] pqInv;


    public MinPQ(int max_n) {
        if (max_n < 0) throw new IllegalArgumentException();

        this.max_n = max_n;

        cars = new Car[max_n + 1];
        pq = new int[max_n + 1];
        pqInv = new int[max_n + 1];

        for (int i = 0; i <= max_n; i++)
            pqInv[i] = -1;
    }

    public boolean isEmpty() {
      if (n == 0)
        return true;
      else
        return false;
    }

    public boolean contains(int i) {
        if (i < 0 || i >= max_n) throw new IndexOutOfBoundsException();
        return pqInv[i] != -1;
    }

    public int size() {
        return n;
    }

    public void insertPrice(int i, Car car) {
        if (i < 0 || i >= max_n) throw new IndexOutOfBoundsException();
        if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue");
        n++;
        pqInv[i] = n;
        pq[n] = i;
        cars[i] = car;
        swimPrice(n);
    }
    public void insertMile(int i, Car car) {
        if (i < 0 || i >= max_n) throw new IndexOutOfBoundsException();
        if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue");
        n++;
        pqInv[i] = n;
        pq[n] = i;
        cars[i] = car;
        swimMileage(n);
    }

    public Car minCar() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        return cars[pq[1]];
    }

    public Car carOf(int i) {
        if (i < 0 || i >= max_n) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("Priority queue underflow");
        else return cars[i];
    }


    public void changePriceCar(int i, Car car) {
        if (i < 0 || i >= max_n) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("Priority queue underflow");
        cars[i] = car;
        swimPrice(pqInv[i]);
        sinkPrice(pqInv[i]);
    }

    public void changeMileageCar(int i, Car car) {
        if (i < 0 || i >= max_n) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("Priority queue underflow");
        cars[i] = car;
        swimMileage(pqInv[i]);
        sinkMileage(pqInv[i]);
    }

    //Similar to delMin from princeton code
    public void deletePrice(int i) {
        if (i < 0 || i >= max_n) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("Priority queue underflow");
        int index = pqInv[i];
        exch(index, n--);
        swimPrice(index);
        sinkPrice(index);
        cars[i] = null;
        pqInv[i] = -1;
    }

    //Similar to delMin from princeton code
    public void deleteMile(int i) {
        if (i < 0 || i >= max_n) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("Priority queue underflow");
        int index = pqInv[i];
        exch(index, n--);
        swimMileage(index);
        sinkMileage(index);
        cars[i] = null;
        pqInv[i] = -1;
    }


    public int delMinP() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        int min = pq[1];

        exch(1, n--);
        sinkPrice(1);

        pqInv[min] = -1;
        cars[pq[n + 1]] = null;
        pq[n + 1] = -1;

        return min;
    }
    public int delMinM() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        int min = pq[1];

        exch(1, n--);
        sinkMileage(1);

        pqInv[min] = -1;
        cars[pq[n + 1]] = null;
        pq[n + 1] = -1;

        return min;
    }

    //Finds highest priority car for price (iterates in level order)
    public void makeMinP(String make, String model){
        int nextLevel = 1;
        int car = 1;
        for(int j = 1; j <= n; j++){
            car = pq[j];
            for( ; j<=nextLevel; ++j)
            {
                if(j > n)
                    break;

                else if(cars[pq[j]].getMake().equals(make) &&
                          cars[pq[j]].getModel().equals(model) &&
                          largerPrice(car, j))
                    car = pq[j];
            }

            if(cars[car].getMake().equals(make) && cars[car].getModel().equals(model)){
                System.out.println(cars[car].toString());
                break;
            }
            nextLevel = (2 * j) + 1;
        }
    }

    //Finds highest priority car for mileage (iterates in level order)
    public void makeMinM(String make, String model){
        int nextLevel = 1;
        int car = 1;
        for(int j = 1; j <= n; j++){
            car = pq[j];
            for( ; j<=nextLevel; ++j)
            {

                if(j > n)
                    break;

                else if(cars[pq[j]].getMake().equals(make) &&
                          cars[pq[j]].getModel().equals(model) &&
                          higherMileage(car,j))
                    car = pq[j];
            }

            if(cars[car].getMake().equals(make) && cars[car].getModel().equals(model)){
                System.out.println(cars[car].toString());
                break;
            }
            nextLevel = (2 * j) + 1;
        }
    }

    //largerPrice and higherMileage are equivalent of greater function from princeton code
    private boolean largerPrice(int i, int j) {
        return cars[pq[i]].getPrice() > (cars[pq[j]]).getPrice();
    }

    private boolean higherMileage(int i, int j) {
        return cars[pq[i]].getMileage() > (cars[pq[j]]).getMileage();
    }

    //Altered exch fcn from princeton code
    private void exch(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;

        //Added inverses
        pqInv[pq[i]] = i;
        pqInv[pq[j]] = j;
    }

    //Swim function for price
    private void swimPrice(int k) {
        while (k > 1 && largerPrice(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    //Swim function for mileage
    private void swimMileage(int k) {
        while (k > 1 && higherMileage(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    //Sink function for price
    private void sinkPrice(int k) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && largerPrice(j, j + 1)) j++;
            if (!largerPrice(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    //Sink function for mileage
    private void sinkMileage(int k) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && higherMileage(j, j + 1)) j++;
            if (!higherMileage(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

}
