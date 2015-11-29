package serie09;

import java.util.Observable;

public class StdSpeedometerModel 
extends Observable implements SpeedometerModel {

	//ATTRIBUTS
    private double maxSpeed;
    private double step;
    private double speed;
    private SpeedUnit unit;
    private boolean started;
	
	//CONSTRUCTEURS
	public StdSpeedometerModel(double step, double max) {
		if ((1 > step) || (step > max)) {
			throw new IllegalArgumentException();
		}
		this.step = step;
		maxSpeed = max;
		speed = 0;
		unit = SpeedUnit.KMH;
		started = false;
		setChanged();
	}
	
	//REQUETES
	@Override
	public double getMaxSpeed() {
		return maxSpeed;
	}


	@Override
	public double getSpeed() {
		return speed;
	}

	@Override
	public double getStep() {
		return step;
	}

	@Override
	public SpeedUnit getUnit() {
		return unit;
	}

	@Override
	public boolean isOn() {
		return started;
	}

	
	//COMMANDES
	@Override
	public void setUnit(SpeedUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException();
		}
		speed = (getSpeed() / getUnit().getUnitPerKm()) * unit.getUnitPerKm();
		step = (getStep() / getUnit().getUnitPerKm()) * unit.getUnitPerKm();
	    maxSpeed = (getMaxSpeed() / getUnit().getUnitPerKm()) 
	    * unit.getUnitPerKm();
	    this.unit = unit;
	    setChanged();
	}

	@Override
	public void slowDown() {
		if (!isOn()) {
			throw new IllegalStateException();
		}
		speed = Math.max(0, getSpeed() - getStep());
		setChanged();
	}

	@Override
	public void speedUp() {
		if (!isOn()) {
			throw new IllegalStateException();
		}
		speed = Math.min(getMaxSpeed(), getSpeed() + getStep());
		setChanged();
	}

	@Override
	public void turnOff() {
		if (!isOn()) {
			throw new IllegalStateException();
		}
		started = false;
		speed = 0;
		setChanged();
	}

	@Override
	public void turnOn() {
		if (isOn()) {
			throw new IllegalStateException();
		}
		started = true;
		setChanged();
	}

}
