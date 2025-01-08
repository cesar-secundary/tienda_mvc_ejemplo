package app.model;

import app.validator.Validator;
import config.Config;

public class ArticuloDAO {

	private Articulo[] articulos;
	private int numArticulos;
	
	private final int maxArticulos;
	
	public ArticuloDAO(Articulo[] articulos) {

		this.maxArticulos = Config.MAX_ARTICULOS;
		
		if(articulos.length>this.maxArticulos) {
			throw new IllegalArgumentException("La capacidad de almacenamiento de artículos es insuficiente para la cantidad de artículos proporcionada");
		}

		this.articulos = new Articulo[this.maxArticulos];
		for (int i = 0; i < articulos.length; i++) {
		    this.articulos[i] = articulos[i];
		}
		
		this.numArticulos = articulos.length;
	}
	
	// CREATE
	public boolean addArticulo(Articulo articulo) {
		if(articulo==null) {
			throw new IllegalArgumentException("Debe proporcionar un artículo");
		}
		if(this.numArticulos==this.maxArticulos) {
			return false;
		}
		if(getArticuloById(articulo.getId())!=null) {
			return false;
		}
		
		this.articulos[numArticulos] = articulo;
		this.numArticulos++;
		return true;
	}

	// READ
	public Articulo getArticuloById(int id) {
		Validator.validateId(id);
		
		for(int i=0; i<this.numArticulos; i++) {
			if(articulos[i].getId() == id) {
				return articulos[i];
			}
		}
		return null;
	}
	
	public Articulo getArticuloByDenominacion(String denominacion) {

		Validator.validateDenominacion(denominacion);
		
		for(int i = 0; i<numArticulos;i++) {
			String denominacionArticulo = articulos[i].getDenominacion();
			if(denominacionArticulo.contains(denominacion)) {
				return articulos[i];
			}
		}
		return null;
		
	}
	
	public Articulo[] getAllArticulos() {
		if(numArticulos==0) {
			return null;
		}
		
		Articulo[] articulosEncontrados = new Articulo[numArticulos];
		for(int i = 0; i<numArticulos; i++) {
			articulosEncontrados[i] = articulos[i];
		}
		
		return articulosEncontrados;
	}
	
	// UPDATE
	public boolean replaceArticulo(Articulo articulo){
		
		if (articulo == null) {
			throw new IllegalArgumentException("Para reemplazar una artículo debe proporcionar otro");
		}
		int id= articulo.getId();
		
		for(int i=0; i<this.numArticulos; i++) {
			if(articulos[i].getId() == id) {
				articulos[i] = articulo;
				return true;
			}
		}
		return false;
	}
}
