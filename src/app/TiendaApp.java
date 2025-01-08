package app;

import app.controller.ArticuloController;

import app.controller.MainController;
import app.controller.VentaController;
import app.model.*;

public class TiendaApp {

	public static void main(String[] args) {

		Articulo[] articulos = {
			new Articulo(1, "Patinete", 49.95, 0),
			new Articulo(2, "Patinete eléctrico", 379.95, 5),
			new Articulo(3, "Bicicleta", 260, 2),
			new Articulo(4, "Bicicleta eléctrica", 799, 2),
			new Articulo(5, "Triciclo eléctrico", 1495, 1)
		};
		
		// TO-DO: Inicializar los componentes principales de la aplicación, incluyendo el DAO, el controlador y el controlador principal.
		Venta venta = new Venta ();
		ArticuloDAO articuloDAO = new ArticuloDAO(articulos);
		VentaDAO ventaDAO = new VentaDAO(venta);
		ArticuloController articuloController = new ArticuloController(articuloDAO);
		VentaController ventaController = new VentaController(ventaDAO, articuloDAO,articuloController);
		MainController mainController = new MainController(articuloController, ventaController);
		
		// Manejar las peticiones del usuario
		mainController.run();
	}
}
