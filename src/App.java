import java.util.ArrayList;

import iiitb.ess201a7.a7base.*;

public class App {

	private Display disp;
	private Platform platform;
	private int timeStep = 500;  // millisecs - change this as needed

	public App(Platform p, Display d) {
		platform = p;
		disp = d;
		disp.setApp(this);
	}

	// called by Display when user requests a trip
	public void handleTripRequest(Location start, Location dest) {
		Trip newTrip = new Trip(start, dest);
		platform.assignCar(newTrip);
	}

	// initilize the app - updates the initial state of the display and starts a thread
	// to simulate movement of cars
	public void init() {

		// v3 - added
		disp.init();

		ArrayList<Car> cars = platform.findCars();
		for(Car car: cars) {
			disp.draw(car);
		}


		Thread simulator = new Thread(new Runnable() {
			public void run() {
				for(int i =0;i<1000;i++) {
					try {
						Thread.sleep(timeStep);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					updateLocations();
					updateDisplay();
				}
			}
		});
		simulator.start();
	}

	private void updateDisplay() {
		ArrayList<Car> cars = platform.findCars();
		for(Car car: cars) {
			disp.draw(car);
			if(car.getStatus()==3){
				disp.drawLine(car.getLocation(),car.getDest());
			}
			else if(car.getStatus()==2){
				disp.drawLine(car.getLocation(),car.getStart());
			}
			disp.update();
			// if car is not idle,
			// if OnTrip, draw line from current location to drop point
			// else from current location to destination.
			// Use disp.drawLine(loc1, loc2);

		}
	}

	private void updateLocations() {
		ArrayList<Car> cars = platform.findCars();
		for(Car car: cars) {
				car.updateLocation(timeStep/1000.0);
		}
	}
}
