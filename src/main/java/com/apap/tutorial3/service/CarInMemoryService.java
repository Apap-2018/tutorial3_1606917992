package com.apap.tutorial3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.apap.tutorial3.model.CarModel;

@Service
public class CarInMemoryService implements CarService{
	private List<CarModel> achiveCar;

	public CarInMemoryService() {
		this.achiveCar = new ArrayList<>();
	}

	@Override
	public void addCar(CarModel car) {
		achiveCar.add(car);
		
	}

	@Override
	public List<CarModel> getCarList() {
		return achiveCar;
	}

	@Override
	public CarModel getCarDetail(String id) {
		CarModel res = null;
		
		for (int i = 0; i < achiveCar.size(); i++) {
			if (achiveCar.get(i).getId().equals(id)) {
				res = achiveCar.get(i);
				break;
			}
		}
		
		return res;
	}
	
}
