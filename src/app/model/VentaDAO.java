package app.model;

import app.validator.Validator;

import config.Config;

public class VentaDAO {
	
	private final int maxVentas;
	private int numVentas;
	 private Venta[] ventas;

	// TO-DO: Declarar el resto de variables de instancia
	
	// TO-DO: Implementar constructor parametrizado. Parámetro: Tamaño máximo del array de ventas
	
	public VentaDAO (int maxVentas) {
		this.maxVentas = maxVentas;
		Venta[] ventas  = new Venta [maxVentas];
		
	}
	
	// CREATE
	public boolean addVenta(Venta venta) {
		
		if(venta==null) {
			throw new IllegalArgumentException("Debe proporcionar una venta");
		}
		if(this.numVentas==this.maxVentas) {
			return false;
		}
		
		this.ventas[numVentas] = venta;
		this.numVentas++;
		
		venta.setId(numVentas);
		
		return true;
	}

	// READ
	public Venta getVentaById(int id) {
		Validator.validateId(id);
		
		for(int i=0; i<this.numVentas; i++) {
			if(ventas[i].getId() == id) {
				return ventas[i];
			}
		}
		return null;
	}
	
	public int countVentasByArticuloId(int id) {
		// TO-DO: Obtener la cantidad de ventas registradas
		
		int numVentas = 0;
		
		for (int i = 0; i < ventas.length; i++) {
			System.out.println("El numero de ventas regiistradas es" + ventas.length);
		}
		
		return numVentas;
	}
	
	
	public Venta[] getVentasByArticuloId(int id) {
		// TO-DO: Utilizar Validator para validar el id
		Validator.validateId(id);
		
		int count = this.countVentasByArticuloId(id);
		if(count == 0) return null;
		
		Venta[] ventasEncontradas = new Venta[count];
		
		for(int i=0, j=0; i<numVentas; i++) {
			if(ventas[i].getArticuloId() == id) {
				ventasEncontradas[j] = ventas[i];
				j++;
			}
		}
		return ventasEncontradas;
		
	}
	
	public Venta[] getAllVentas() {
			System.out.println("El numero de ventas es " + ventas.length);
		return ventas;
		
	
		
	}

}
