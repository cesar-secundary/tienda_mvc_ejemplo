package app.controller;

import app.model.Articulo;
import app.model.Venta;
import app.view.ArticuloView;
import app.view.MainView;
import app.view.VentaView;

public class MainController {
	
	private ArticuloController articuloController;
	private VentaController ventaController;
	
	public MainController (ArticuloController articuloController, VentaController ventaController) {
		this.articuloController = articuloController;
		this.ventaController = ventaController;
	}
	
	public void run() {
		
		boolean continuar = true;
		
		do {
			String opcion = MainView.solicitarOpcion();
			if(opcion == null) {
				break;
			}
			try {
				switch(opcion) {
					case "0":
						continuar = false;
						break;
					case "1":
						// 1- Mostrar los artículos
						articuloController.mostrarArticulos();
						break;
					case "2":
						// 2- Buscar artículo
						String denominacion = ArticuloView.solicitarDenominacion();
						articuloController.buscarArticuloPorDenominacion(denominacion);
						break;
					case "3":
						// 3- Introducir artículo
						Articulo articulo = ArticuloView.solicitarArticulo();
						articuloController.introducirArticulo(articulo);
						break;
					case "4":
						// 5- Modificar artículo
						articulo = ArticuloView.solicitarArticulo();
						articuloController.modificarArticulo(articulo);
						break;
					case "5":
						// 5- Venta
						// TO-DO

					case "6":
						// 6- Devolución
						// TO-DO
	
					case "7":
						// 7- Ver ventas
						ventaController.mostrarVentas();
						break;
					case "8":
						// 8- Buscar ventas por articulo
						// TO-DO
						
					default:
						MainView.mostrarMensaje("Opción no válida");
						break;
				} 
			}catch(NumberFormatException e) {
				MainView.mostrarMensaje("Se esperaba una entrada numérica");
				
			}catch(Exception e) {
				MainView.mostrarMensaje(e.getMessage());
				
			}
		} while(continuar);
	}
}
