package app.view;

import javax.swing.JOptionPane;

import app.model.Articulo;
import app.model.Venta;
import app.validator.Validator;
import utils.*;

public class VentaView {
	
	public static int solicitarId() {
		// TO-DO
	}
	public static int solicitarArticuloId() {
		// TO-DO
	}
	public static int solicitarCantidad() {
		int cantidad = Integer.parseInt(
			JOptionPane.showInputDialog("Introduce la cantidad: ")
		);
		Validator.validateCantidad(cantidad);
		return cantidad;
	}
	public static String solicitarCodigoPostal() {
		// TO-DO
	}
	
	public static Venta solicitarVenta() {
		return new Venta(
			solicitarArticuloId(),
			solicitarCantidad(),
			solicitarCodigoPostal()
		);
	}
	
	public static void mostrarVentas(Venta[] ventas) throws Exception {
		// TO-DO: Utilizad los métodos estáticos showInConsole y showInDialog de TablePrinter para mostrar por consola y en cuadro de diálogo
		TablePrinter.showInConsole(ventas, 25, "Registro de ventas");
		TablePrinter.showInDialog(ventas, "Registro de ventas");
	}
	
	public static void mostrarVenta(Venta venta, Articulo articulo) {
		// TO-DO: Utilizad el método de instancia toString() para mostrar por consola y en cuadro de diálogo
		String mensaje = String.format(
			"Venta nº: %d\n  Artículo: %s\n  Cantidad: %d\n  Importe: %.2f\n  Importe total: %.2f euros\n",
			venta.getId(), articulo.getDenominacion(), venta.getCantidad(), articulo.getPvp(), venta.getCantidad()*articulo.getPvp()
		);
		MainView.mostrarMensaje(mensaje);
	}

}
