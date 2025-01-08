package app.model;

import app.validator.Validator;

public class Venta {
	
	// Variables de instancia
	private int id;
	private int articuloId;
	private int cantidad;
	private String codigoPostal;
	
	// Constructor parametrizado
	public Venta(int articuloId, int cantidad, String codigoPostal) {
		this.id = 0;
		setArticuloId(articuloId);
		this.cantidad = cantidad;
		setCodigoPostal(codigoPostal);
		this.codigoPostal = codigoPostal;
	}

	// getters
	public int getId() {
		return id;
	}
	public int getArticuloId() {
		return articuloId;
	}
	public int getCantidad() {
		return cantidad;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setId(int id) {
		this.id = id;
	}

	// setters con validación
	public void setArticuloId(int articuloId) {
		Validator.validateId(articuloId);
		this.articuloId = articuloId;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public void setCodigoPostal(String codigoPostal) {
		Validator.validateCodigoPostal(codigoPostal);
		this.codigoPostal = codigoPostal;
	}
	
	// toString()
	@Override
	public String toString() {
		return "Venta [id=" + id + ", articuloId=" + articuloId + ", cantidad=" + cantidad + ", codigoPostal="
				+ codigoPostal + "]";
	}
	
	
}
