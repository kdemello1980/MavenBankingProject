package com.kdemello.mavenbanking.dao;

import java.io.Serializable;
import java.util.List;

public interface DAO<T, I extends Serializable> {

	List<T> findAll();
	T findById(I id);
	T create(T obj);
	T update(T obj);
	// optional T delete(I id);
	
	/*
	 * Adding default methods so that we can instantiate the DAO
	 * concrete classes with a REFERENCE to the interface.
	 * 
	 * To use:
	 * 
	 * public class User implements DAO<User, Integer> {
	 * 	...some mentods from the DAO...
	 * }
	 */
}
