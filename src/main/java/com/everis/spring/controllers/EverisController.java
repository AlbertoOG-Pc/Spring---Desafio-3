package com.everis.spring.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.everis.spring.repository.EverisCustomer;
import com.everis.spring.services.EverisCustomerManagementServiceI;

/**
 * @author Alberto
 * @Controller - Indica Spring que esta class es un
 *             controller @RequestMapping("*") - Ruta por defecto end point
 *             localhost/
 */
@Controller
@RequestMapping("*")
public class EverisController {
	@Autowired
	private EverisCustomerManagementServiceI customerService;

	/**
	 * @return Devuelve el nombre de la vista
	 */
	@GetMapping("/*")
	public String showIndexView() {
		return "systemMenu";
	}

	/**
	 * Redireccion a la vista de visualizacion de todos los clientes
	 * 
	 * @param model
	 * @return String Nombre de la vista
	 */
	@GetMapping("/showCustomersView")
	public String showCustomerView(Model model) {
		final List<EverisCustomer> customerList = customerService.searchAllCustomers();
		System.out.println("/** DATOS PARA LA VISTA **/");
		System.out.println(customerList.toString());

		// cargamos la lista en la vista
		model.addAttribute("customersList", customerList);
		model.addAttribute("btnDropCustomerEnabled", Boolean.FALSE);

		return "showCustomers";
	}

	/**
	 * Redireccion a la vista con el formulario de busqueda
	 * 
	 * @return
	 */
	@GetMapping(path = "/searchCustomerByView")
	public String searchCustomerByView() {

		return "searchCustomerBy";
	}

	/**
	 * Redireccion a la vista con el formulario para insertar un nuevo cliente
	 * 
	 * @return
	 */
	@GetMapping(path = "/newCustomerView")
	public String newCustomerView() {

		return "newCustomer";
	}

	/**
	 * 
	 * Ruta de accion de formulario de nuevo formulario
	 * 
	 * @param model
	 * @param customer
	 * @return
	 */
	@RequestMapping(value = "/actAddCustomer", method = RequestMethod.POST)
	public String onSubmit(EverisCustomer customer) {

		customerService.insertNewCustomer(customer);
		// final EverisCustomer customerView =
		// customerService.searchByFullName(customer.getName(),customer.getSurName1(),customer.getSurName2());

		return "redirect:/showCustomersView";
	}

	/**
	 * Ruta de accion de formulario de busqueda
	 * 
	 * @param model
	 * @param customer
	 * @return
	 */
	@RequestMapping(value = "/actSearchCustomer", method = RequestMethod.POST)

	public String actSearchCustomer(Model model, EverisCustomer customer) {
		List<EverisCustomer> customerView = new ArrayList<>();
		if (customer.getIdentityDocNumber().isEmpty()) {
			customerView.add(customerService.searchByDocNumber(customer.getIdentityDocNumber()));
		} else if (customer.getSurName1().isEmpty() || customer.getSurName2().isEmpty()) {
			customerView = customerService.searchByName(customer.getName());
		} else {
			customerView = customerService.searchByFullName(customer.getName(), customer.getSurName1(),
					customer.getSurName2());
		}

		// System.out.println(customerView.toString());
		model.addAttribute("customersList", customerView);
		model.addAttribute("btnDropCustomerEnabled", false);

		return "showCustomers";
	}

	@RequestMapping(value = "/actDropCustomer", method = RequestMethod.POST)
	public String actDropCustomer(Model model, @RequestParam("customerId") Integer id) {
		customerService.deleteCustomer(id);

		return "redirect:/showCustomersView";
	}

}
