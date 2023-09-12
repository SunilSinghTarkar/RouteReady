package com.routeready.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.routeready.model.Admin;
import com.routeready.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admins")
public class AdminController {
	@Autowired
	private AdminService adminService;

	@PostMapping("/")
	public ResponseEntity<Admin> createCustomer(@Valid @RequestBody Admin admin) {
		System.out.println(admin.toString());
		admin.setRole("Admin");
		admin.setActive(true);
		Admin adm = adminService.crateAdmin(admin);
		return new ResponseEntity<Admin>(adm, HttpStatus.CREATED);
	}

	@GetMapping("/{adminId}")
	public ResponseEntity<Admin> getCustomer(@PathVariable Integer adminId) {
		Admin admin = adminService.getAdmin(adminId);
		return new ResponseEntity<Admin>(admin, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<Admin>> getCustomer() {
		List<Admin> list = adminService.getAllAdmins();
		return new ResponseEntity<List<Admin>>(list, HttpStatus.OK);
	}

	@PatchMapping("/{customerId}")
	public ResponseEntity<Admin> updateCustomer(@RequestParam String address, @RequestParam String mobileNumber,
			@PathVariable Integer adminId) {
		Admin admin = adminService.updateAdmin(address, mobileNumber, adminId);
		return new ResponseEntity<Admin>(admin, HttpStatus.OK);
	}

	@DeleteMapping("/{customerId}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Integer adminId) {
		String str = adminService.deleteAdmin(adminId);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}
}
