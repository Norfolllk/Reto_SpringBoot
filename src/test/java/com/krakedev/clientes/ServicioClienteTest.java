package com.krakedev.clientes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.krakedev.clientes.entidades.Cliente;
import com.krakedev.clientes.services.ServicioCliente;

public class ServicioClienteTest {

	private ServicioCliente servicioCliente;

	@BeforeEach
	public void setUp() {
		servicioCliente = new ServicioCliente();
	}

	@Test
	public void testCrearCliente() {
		Cliente cliente = new Cliente("123", "Andres", "Castillo", "andres@correo.com");
		Cliente creado = servicioCliente.crear(cliente);

		assertNotNull(creado);
		assertEquals("123", creado.getCedula());
		assertEquals("andres@correo.com", creado.getEmail());
	}

	@Test
	public void testCrearClienteDuplicado() {
		servicioCliente.crear(new Cliente("123", "Andres", "Castillo", "andres@correo.com"));

		Cliente resultado = servicioCliente.crear(new Cliente("123", "Otro", "Nombre", "otro@correo.com"));

		assertNull(resultado);
		assertEquals(1, servicioCliente.listar().size());
	}

	@Test
	public void testBuscarPorCedulaExistente() {
		servicioCliente.crear(new Cliente("123", "Andres", "Castillo", "andres@correo.com"));

		Cliente encontrado = servicioCliente.buscarPorCedula("123");

		assertNotNull(encontrado);
		assertEquals("Andres", encontrado.getNombre());
		assertEquals("andres@correo.com", encontrado.getEmail());
	}

	@Test
	public void testBuscarPorCedulaInexistente() {
		Cliente encontrado = servicioCliente.buscarPorCedula("999");

		assertNull(encontrado);
	}

	@Test
	public void testListarClientesVacio() {
		List<Cliente> lista = servicioCliente.listar();

		assertEquals(0, lista.size());
	}

	@Test
	public void testListarClientes() {
		servicioCliente.crear(new Cliente("123", "Andres", "Castillo", "andres@correo.com"));
		servicioCliente.crear(new Cliente("234", "Juan", "Perez", "juan@correo.com"));

		List<Cliente> lista = servicioCliente.listar();

		assertEquals(2, lista.size());
	}

	@Test
	public void testActualizarClienteExistente() {
		servicioCliente.crear(new Cliente("123", "Andres", "Castillo", "andres@correo.com"));

		Cliente datosNuevos = new Cliente(null, "Andres", "Castillo Hernandez", "andres.nuevo@correo.com");
		Cliente actualizado = servicioCliente.actualizar("123", datosNuevos);

		assertNotNull(actualizado);
		assertEquals("Castillo Hernandez", actualizado.getApellido());
		assertEquals("andres.nuevo@correo.com", actualizado.getEmail());
	}

	@Test
	public void testActualizarClienteInexistente() {
		Cliente datosNuevos = new Cliente(null, "Andres", "Castillo", "andres@correo.com");
		Cliente actualizado = servicioCliente.actualizar("999", datosNuevos);

		assertNull(actualizado);
	}

	@Test
	public void testEliminarClienteExistente() {
		servicioCliente.crear(new Cliente("123", "Andres", "Castillo", "andres@correo.com"));

		boolean eliminado = servicioCliente.eliminar("123");

		assertTrue(eliminado);
		assertEquals(0, servicioCliente.listar().size());
	}

	@Test
	public void testEliminarClienteInexistente() {
		boolean eliminado = servicioCliente.eliminar("999");

		assertFalse(eliminado);
	}

}
