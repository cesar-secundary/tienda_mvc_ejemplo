package app.controller;

import app.model.Articulo;

import app.model.ArticuloDAO;
import app.model.Venta;
import app.model.VentaDAO;
import app.validator.Validator;
import app.view.MainView;
import app.view.VentaView;

public class VentaController {
	
	// TO-DO: Declarar variables de instancia
	private VentaDAO ventaDAO;
	private Venta venta;
	private ArticuloDAO articuloDAO;
	private ArticuloController articuloController;

	
	// TO-DO: Implementar constructor parametrizado
	
	public VentaController (VentaDAO ventaDAO, ArticuloDAO articuloDAO , ArticuloController articuloController, Venta venta) {
		this.ventaDAO = ventaDAO;
		this.articuloDAO = articuloDAO;
		this.articuloController = articuloController;
		this.venta = venta;
	}
	

	public void mostrarVentas() throws Exception{
		Venta[] ventas = ventaDAO.getAllVentas();
		if(ventas!=null) {
			VentaView.mostrarVentas(ventas);
		} else {
			MainView.mostrarMensaje("No hay ventas registradas");
		}
		
	}
	
	public void buscarVentasPorArticulo(int id) throws Exception{
		
		// 1- Validar con el Validator

			Validator.validateId(id);
		// 2- Interactuar con el Model (DAO)
			
			ventaDao.
		
		// 3- Presentar con la View
		
	}
	


	public void vender(Venta venta){

		// TO-DO
		/* Ayuda: Obtenemos el articulo asociado a la venta.
		          Si es null, entonces lanzamos una excepción con mensaje "No se puede vender un artículo que no existe".
		          Si no, registramos la venta.
		             Si se registro la venta, entonces actualizamos el stock del articulo con Articulo.bajarStock y mostramos la venta
		             Si no, mostramos un mensaje para advertir al usuario
		*/

	}
	
	public void hacerDevolucion(int id){

		/* TO-DO: Si existe la venta, actualizamos el stock con Articulo.subirStock y registramos la devolución 
		          La devolución es una nueva venta del mismo artículo pero con cantidad negativa*/
		
	}
}
