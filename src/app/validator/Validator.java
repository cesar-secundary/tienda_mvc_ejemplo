package app.validator;

public class Validator {
	
	public static void validateId(int id) {
		// TO-DO: "El id debe ser mayor que cero"
		
		if(id < 0) {
			 throw new IllegalArgumentException("tiene que ser un id Valido");
		};
	}
	public static void validateDenominacion(String denominacion) {
		// TO-DO: "Se debe proporcionar una denominación no nula y que no esté en blanco"
		
		if(denominacion == null && denominacion.isBlank()) {
			 throw new IllegalArgumentException("La denominacion no puede estar nula");
		}
	}
	public static void validatePvp(double pvp) {
		if (pvp < 0) {
			throw new IllegalArgumentException("El precio de venta no puede ser 0");
		}
	}
	
	public static void validateCantidad(int cantidad) {
		// TO-DO: "La cantidad debe ser positiva"
		
		if (cantidad < 0) {
			throw new IllegalArgumentException("La cantidad no puede ser menor de 0");
		}
	}
	public static void validateCodigoPostal(String codigoPostal) {
		if(codigoPostal == null || codigoPostal.length() != 5)
			throw new IllegalArgumentException("Se debe proporcionar un código postal no nulo");

		// Verificar que todos los caracteres sean dígitos
        for (int i = 0; i < codigoPostal.length(); i++) {
            if (codigoPostal.charAt(i) < '0' || codigoPostal.charAt(i) > '9') {
                throw new IllegalArgumentException("El código postal debe estar compuesto por 5 dígitos");
            }
        }
	}
}
