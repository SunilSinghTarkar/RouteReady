package com.routeready.service;

import java.util.List;

import com.routeready.model.Admin;

public interface AdminService {
	 public Admin crateAdmin(Admin admin);
	  public Admin getAdmin(Integer adminId);
	  public List<Admin> getAllAdmins( );
	  public Admin updateAdmin(String address,String mobileNumber,Integer adminId);
	  public String deleteAdmin(Integer adminId);
}
