package app.view;

import javax.swing.JOptionPane;

public class MainView {
	public static String solicitarOpcion() {
		// TO-DO, ayuda:
		String mensaje = """
				1- Mostrar los artículos
				2- Buscar artículo
				3- Introducir artículo
				4- Modificar artículo
				5- Venta
				6- Devolución
				7- Ver ventas
				8- Ver ventas de un artículo

				0- Salir
				""";
		
		return mensaje;

	}

	public static void mostrarMensaje(Object mensaje) {
		System.out.println(mensaje);
		JOptionPane.showMessageDialog(null, mensaje);
	}
}
