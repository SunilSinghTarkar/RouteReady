package com.routeready.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routeready.exception.NotFoundException;
import com.routeready.exception.RouteReadyException;
import com.routeready.model.Admin;
import com.routeready.repository.AdminRepository;

import io.swagger.v3.oas.annotations.servers.Server;
@Service
public class AdminServiceImpl implements AdminService{
@Autowired
private AdminRepository adminRepository;
	@Override
	public Admin crateAdmin(Admin admin) {
		if (admin == null)
			throw new RouteReadyException("Please provide valid admin");
		Admin adm = adminRepository.save(admin);
		return adm;
	}

	@Override
	public Admin getAdmin(Integer adminId) {
		Admin admin = adminRepository.findById(adminId)
				.orElseThrow(() -> new NotFoundException("admin not found with given Id; " + adminId));
		
		if (!admin.isActive())
			throw new NotFoundException("admin not found with given Id; " + adminId);
		return admin;
	
	}

	@Override
	public List<Admin> getAllAdmins() {
		List<Admin> list = adminRepository.findAll();
		if (list.isEmpty())
			throw new NotFoundException("admin not found!");
		return list;
	}

	@Override
	public Admin updateAdmin(String address, String mobileNumber, Integer adminId) {
		Admin admin = adminRepository.findById(adminId)
				.orElseThrow(() -> new NotFoundException("admin not found with given Id; " + adminId));
		
		admin.setAddress(address);
		admin.setMobileNumber(mobileNumber);

		return adminRepository.save(admin);
	}

	@Override
	public String deleteAdmin(Integer adminId) {
		Admin admin = adminRepository.findById(adminId)
				.orElseThrow(() -> new NotFoundException("admin not found with given Id; " + adminId));
		
		admin.setActive(false);
		adminRepository.save(admin);
		return "admin deleted succesfully";
	}

}
