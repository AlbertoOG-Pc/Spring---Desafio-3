package com.everis.spring.services;

import java.util.List;

import com.everis.spring.repository.EverisCustomer;

public interface EverisCustomerManagementServiceI {

	/**
	 * Añade un nuevo cliente.
	 * 
	 * @param newCustomer
	 * @return EverisCustomer
	 */
	public EverisCustomer insertNewCustomer(final EverisCustomer newCustomer);

	/**
	 * Consulta todos los clientes.
	 */
	public List<EverisCustomer> searchAllCustomers();

	/**
	 * Búsqueda por nombre completo.
	 * 
	 * @param name
	 * @param surname1
	 * @param surname2
	 */
	public List<EverisCustomer> searchByFullName(final String name, final String surname1, final String surname2);

	/**
	 * Búsqueda por nombre.
	 * 
	 * @param name
	 */
	public List<EverisCustomer> searchByName(final String name);
	
	/**
	 * Búsqueda por DNI.
	 * 
	 * @param dni
	 */
	public EverisCustomer searchByDocNumber(final String dni);
	
	public void deleteCustomer(final int deleteCustomerId);

}
