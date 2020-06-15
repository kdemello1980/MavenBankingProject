package com.mavenbanking.tests;
import java.util.ArrayList;
import java.util.HashMap;


import com.revature.mavenbanking.dao.impl.*;
import com.revature.mavenbanking.model.*; 
public class TestAccountDao {

	public static void main(String[] args) {
//		try {
//			Connection con = DAOUtilities.getConnection();
//			Statement stmt = con.createStatement();
//			
//			ResultSet rs = stmt.executeQuery("SELECT * FROM kmdm_accounts");
//			while (rs.next()) {
//				System.out.println(rs.getBigDecimal("balance"));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return;
//		}

		
		AccountDaoImpl dao = new AccountDaoImpl();
		ArrayList<Account> list = dao.getAllAccounts();
		System.out.println(list);
//		
//		Account account1 = dao.getAccountById(2);
//		System.out.println(account1);
//		
//		ArrayList<Account> accountByType = dao.getAccountsByType(account1.getType());
//		System.out.println(accountByType);
//		
//		ArrayList<Account> accountByStatus = dao.getAccountsByStatus(account1.getStatus());
//		System.out.println(accountByStatus);
//		
		RoleDaoImpl rdi = new RoleDaoImpl();
		ArrayList<Role> allRoles = rdi.getAllRoles();
		for (Role r : allRoles)
			System.out.println(r);
		
		System.out.println(rdi.getRoleByName("Standard"));
		
		PermissionDaoImpl pdi = new PermissionDaoImpl();
		ArrayList<Permission> allPerms = pdi.getAllPermissions();
		for (Permission p : allPerms) {
			System.out.println(p);
		}
		System.out.println(pdi.getPermissionsByRoleId(1));
//		Permission newp = new Permission();
//		newp.setPermissionName("new_permission");
//		pdi.addPermission("new_permission");
		
		UserDaoImpl udi = new UserDaoImpl();
		ArrayList<User> users = udi.getAllUsers();
		for (User u : users)
			System.out.println(u);
		System.out.println(udi.getUserById(1).getRole().getEffectivePermissions().contains("ea_can_view_all_customer_info"));
//		System.out.print(dao.getAllUserAccounts());

//		System.out.println(udi.getUserByUserName("user2"));
		
//		String foo = "abcdef";
//		System.out.println(foo.substring(0, 2));
		

	}

}
