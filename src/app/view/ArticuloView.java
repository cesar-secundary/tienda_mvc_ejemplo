package app.view;

import javax.swing.JOptionPane;

import app.model.Articulo;
import utils.*;

public class ArticuloView {

	public static int solicitarId() {
		return Integer.parseInt(JOptionPane.showInputDialog("Introduce el id del artículo:"));
	}

	public static String solicitarDenominacion() {
		return JOptionPane.showInputDialog("Introduce la denominación del artículo:");
	}

	public static double solicitarPvp() {
		return Double.parseDouble(JOptionPane.showInputDialog("Introduce el pvp del artículo:"));
	}

	public static int solicitarStock() {
		return Integer.parseInt(JOptionPane.showInputDialog("Introduce las unidades en stock del artículo:"));
	}

	public static String solicitarArticulo(Articulo Articulo) {
		// TO-DO

		return JOptionPane.showInputDialog("Introduce el nombre del articulo");
	}

	public static void mostrarArticulos(Articulo[] articulos) throws Exception {
		// TO-DO: Utilizad los métodos estáticos showInConsole y showInDialog de
		// TablePrinter para mostrar las películas por consola y en cuadro de diálogo
		TablePrinter.showInConsole(articulos, 25, "Los artículos de la tienda");
		TablePrinter.showInDialog(articulos, "Los artículos de la tienda");
	}

	public static void mostrarArticulo(Articulo articulo) {
		// TO-DO: Utilizad el método de instancia toString() para mostrar la película
		// por consola y en cuadro de diálogo
		MainView.mostrarMensaje(articulo);
	}

}
