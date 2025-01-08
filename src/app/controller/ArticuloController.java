package app.controller;

import app.model.Articulo;
import app.model.ArticuloDAO;
import app.validator.Validator;
import app.view.ArticuloView;
import app.view.MainView;

public class ArticuloController {
	
	private ArticuloDAO articuloDAO;
	
	public ArticuloController (ArticuloDAO articuloDAO) {
		this.articuloDAO = articuloDAO;
	}
	
	public void mostrarArticulos() throws Exception{
		Articulo[] articulos = articuloDAO.getAllArticulos();
		if(articulos!=null) {
			ArticuloView.mostrarArticulos(articulos);
		} else {
			MainView.mostrarMensaje("No hay artículos en la lista");
		}
		
	}
	
	public void buscarArticuloPorDenominacion(String denominacion){
		
		// 1- Validar con el Validator
		Validator.validateDenominacion(denominacion);
		
		// 2- Interactuar con el Model (DAO)
		Articulo articulo = articuloDAO.getArticuloByDenominacion(denominacion);
		
		// 3- Presentar con la View
		if(articulo!=null) {
			ArticuloView.mostrarArticulo(articulo);
		} else {
			MainView.mostrarMensaje("No se encontró el artículo");
		}
		
	}
	
	public void introducirArticulo(Articulo articulo){

		// TO-DO
		if(articulo==null) {
			throw new IllegalArgumentException("Se debe proporcionar un artículo no nulo");
		}
		boolean check = articuloDAO.addArticulo(articulo);
		if(check==true) {
			MainView.mostrarMensaje("El artículo se agregó correctamente");
		} else {
			MainView.mostrarMensaje("No se pudo añadir el artículo. El artículo ya existe o la se ha alcanzado la capacidad máxima");
		}
	}
	
	public void modificarArticulo(Articulo articulo) {
		if(articulo == null) {
			throw new IllegalArgumentException("No se proporcionó un artículo");
		}
		if(articuloDAO.replaceArticulo(articulo)) {
			MainView.mostrarMensaje("Artículo modificado con éxito");
		} else {
			MainView.mostrarMensaje("No existe el artículo con id " + articulo.getId());
		}
	}

}
