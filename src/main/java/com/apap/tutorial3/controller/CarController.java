package com.apap.tutorial3.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial3.model.CarModel;
import com.apap.tutorial3.service.CarService;

@Controller
public class CarController {
	@Autowired
	private CarService mobilService;
	
	@RequestMapping("/car/add")
	public String add(@RequestParam(value = "id", required = true) String id, 
			@RequestParam(value = "brand", required = true) String brand,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "price", required = true) Long price,
			@RequestParam(value = "amount", required = true) Integer amount) {
		CarModel car = new CarModel(id, brand, type, price, amount);
		mobilService.addCar(car);
		return "add";
	}
	
	@RequestMapping("/car/view")
	public String view(@RequestParam("id") String id, Model model) {
		CarModel archive = mobilService.getCarDetail(id);
		
		model.addAttribute("car", archive);
		return "view-car";
	}
	
	@RequestMapping("/car/viewall")
	public String viewall(Model model) {
		List<CarModel> archive = mobilService.getCarList();
		
		model.addAttribute("listCar", archive);
		return "viewall-car";
	}
	
	@RequestMapping("/car/view/{id}")
	public String viewById(@PathVariable String id, Model model) {
		CarModel car = mobilService.getCarDetail(id);
		
		if (car != null) {
			model.addAttribute("car", car);
			return "view-car";
		}
		
		String message = "Car dengan id " + id + " tidak ada.";
		model.addAttribute("message", message);
		return "fleksible";
	}
	
	@RequestMapping("/car/update/{id}/amount/{amount}")
	public String updateAmount(@PathVariable String id, @PathVariable String amount, Model model) {
		CarModel car = mobilService.getCarDetail(id);
		
		String message = "";
		if (car != null) {
			car.setAmount(Integer.parseInt(amount));
			message = "Update amount car dengan id " + id + " berhasil diubah menjadi " + amount;
		} else
			message = "Update gagal. Tidak ditemukan car dengan id " + id;
		
		model.addAttribute("message", message);
		return "fleksible";
	}
	
	@RequestMapping("/car/delete/{id}")
	public String deleteCar(@PathVariable String id, Model model) {
		List<CarModel> allCar = mobilService.getCarList();
		
		int index = -1;
		for (int i = 0; i < allCar.size(); i++) {
			if (allCar.get(i).getId().equals(id)) {
				index = i;
			}
		}
		
		if (index != -1) {
			allCar.remove(index);
			model.addAttribute("message", "Car " + id + " berhasil dihapus.");
			return "fleksible";
		}
		
		model.addAttribute("message", "Car " + id + " tidak ada.");
		return "fleksible";
	}
}
