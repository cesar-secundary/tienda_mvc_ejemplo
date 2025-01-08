package app.model;

import app.validator.Validator;

public class Articulo {

	private int id;
	private String denominacion;
	private double pvp;
	private int stock;

	// Variables de instancia: id, denominacion, pvp, stock
	// Nota: se permiten stocks negativos

	// Constructor parametrizado (usar los setters)

	public Articulo(int id, String denominacion, double pvp, int stock) {
		setId(id);
		setDenominacion(denominacion);
		setPvp(pvp);
		setStock(stock);
	}

	// getters

	public int getId() {
		return this.id;
	}

	public String getDenominacion() {
		return this.denominacion;
	};

	public double getPvp() {
		return this.pvp;
	}

	public int getStock() {
		return this.stock;
	}

	// setters con validación (usar Validator)

	public void setId(int id) {
		Validator.validateId(id);
		this.id = id;
	};

	public void setDenominacion(String denominacion) {
		Validator.validateDenominacion(denominacion);
		this.denominacion = denominacion;
	}

	public void setPvp(double pvp) {
		Validator.validatePvp(id);
		this.pvp = pvp;
	};

	public void setStock(int stock) {
		this.stock = stock;
	};

	// Métodos de instancia para actualizar stocks
	public void subirStock(int unidades) {
		this.stock += unidades;
	};

	public void bajarStock(int unidades) {
		this.stock -= unidades;
	};

	// TO-DO: toString
	public String toString() {
		/*
		 * String mensaje = "Articulo: " + this.id + " Denominación: " +
		 * this.denominacion + "Precio: " + this.pvp; return mensaje;
		 */

		return String.format("Articulo:\nId = %d", "La denominacion es: %S", "El precio del articulo es de %f",
				"El stock del articulo es %s", this.id, this.denominacion, this.pvp, this.denominacion);
	}
}
