import iiitb.ess201a7.a7base.*;
import java.lang.*;
import java.util.*;

public class Platform {

// all the methods in this class need to be implemented
	private ArrayList<Fleet> fleetList;

	public Platform() {
		fleetList=new ArrayList<Fleet>();
		// cars=new ArrayList<Car>();
	}

	public void addFleet(Fleet f) {
		fleetList.add(f);
		}


	// for a request defined as a Trip, find the best car by checking each of its fleets
	// and assigns the car to this trip
	public Car assignCar(Trip trip) {
		int size=fleetList.size();
		int min=Integer.MAX_VALUE;
		Car nearestCar=null;
		for(int i=0;i<size;i++){
			Car c=fleetList.get(i).findNearestCar(trip.getStart());
			if(c!=null){
				int dist=c.distSqrd(trip.getStart());
				if(dist<min && c.getStatus()==1){
				min=dist;
				nearestCar=c;
				}
			}
		}
		if(nearestCar!=null)
			nearestCar.assignTrip(trip);
		return nearestCar;
	}

	// returns list of all cars (in all the fleets) managed by this platform
	public ArrayList<Car> findCars() {
		ArrayList<Car> cars = new ArrayList<Car>();
		for(Fleet f: fleetList){
			for(Car c: f.getCars()){
				cars.add(c);
			}
		}
		return cars;
	}
}
