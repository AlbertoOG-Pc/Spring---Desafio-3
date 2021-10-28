package com.everis.spring.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.everis.spring.repository.EverisCustomer;
import com.everis.spring.repository.EverisCustomerRepository;

/**
 * Formación Spring.
 * 
 * @author Everis
 *
 */
@Service
public class EverisCustomerManagementServiceImpl implements EverisCustomerManagementServiceI {

	/** Repositorio - Tabla: t_h_customer */
	@Autowired
	private EverisCustomerRepository customerRepository;

	@Override
	public EverisCustomer insertNewCustomer(EverisCustomer newCustomer) {

		// Verificación de nulidad.
		if (newCustomer != null && newCustomer.getId() == null) {

			// Insercción de cliente.
			newCustomer = customerRepository.save(newCustomer);
		}

		return newCustomer;
	}

	@Override
	public List<EverisCustomer> searchAllCustomers() {
		List<EverisCustomer> resultcustomerList = new ArrayList<>();
		// Consulta clientes.
		final Iterable<EverisCustomer> customerList = customerRepository.findAll();

		// Iteramos la lista y la guardamos en la nueva
		customerList.forEach(resultcustomerList::add);

		// Devolvemos la lista de clientes
		return resultcustomerList;
		// Itera e imprime los clientes.
		// customerList.forEach((final EverisCustomer customer) ->
		// System.out.println(customer.toString()));
	}

	@Override
	public List<EverisCustomer> searchByFullName(final String name, final String surname1, final String surname2) {

		// Obtención del listado de clientes por nombre completo.
		final List<EverisCustomer> searchClients = customerRepository.findByNameAndSurName1AndSurName2(name, surname1,
				surname2);
		// Devuelve el primero, por no modificar la lectura
		return searchClients;

		// Muestra el listado obtenido.
		// printCustomers(searchClients);
	}

	@Override
	public List<EverisCustomer> searchByName(final String name) {

		// Obtención del listado de clientes por nombre.
		final List<EverisCustomer> searchCustomers = customerRepository.findByName(name);

		// Devuelve el primero, por no modificar la lectura.
		return searchCustomers;
		// Muestra el listado obtenido.
		// printCustomers(searchCustomers);
	}

	public EverisCustomer searchByDocNumber(String dni) {
		final EverisCustomer searchCustomers = customerRepository.findByIdentityDocNumber(dni);

		// Devuelve el primero, por no modificar la lectura.
		return searchCustomers;
	}
	
	@Override
	public void deleteCustomer(int idDeleteCustomer) {
		//Borramos objeto
		customerRepository.delete(customerRepository.getOne(Integer.toUnsignedLong(idDeleteCustomer)));
	}

	/**
	 * Recorre e imprime listado de clientes.
	 * 
	 * @param searchCustomers
	 */
	private void printCustomers(final List<EverisCustomer> searchCustomers) {
		if (!CollectionUtils.isEmpty(searchCustomers)) {
			for (EverisCustomer customer : searchCustomers) {
				System.out.println(customer.toString());
			}
		}
	}

}
