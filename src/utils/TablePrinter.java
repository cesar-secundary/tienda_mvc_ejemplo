package utils;

import java.awt.Dimension;
import java.awt.Font;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class TablePrinter {
	private static final int FONT_SIZE = 14;
	private static final int TITLE_FONT_SIZE = 24;
	private static final int TABLE_FONT_SIZE = 20;
	
	private static final int COLUMN_WIDTH = 150;
	private static final int ROW_HEIGHT = 30;
	
	private static final int MAX_SCROLL_PANE_WIDTH = 1280;

	public static JTable convertToTable(Object[] objects) throws Exception {

		if(objects == null) {
			throw new NullPointerException("El parámetro es null");
		}
		if(!objects.getClass().isArray()) {
			throw new IllegalArgumentException("El parámetro no es un array");
		}
		
		Class<? extends Object> clazz = objects[0].getClass();
		List<ArrayList<Field>> fieldsByClassHierarchy = new ArrayList<>();
		
        while (clazz != null) { // Recorre la jerarquía de clases
        	ArrayList<Field> fieldsInClass = new ArrayList<>();
        	
            for (Field field : clazz.getDeclaredFields()) {
            	field.setAccessible(true);
                fieldsInClass.add(field);
            }
            
            fieldsByClassHierarchy.add(fieldsInClass);
            clazz = clazz.getSuperclass();
        }
        
        ArrayList<Field> fields = new ArrayList<>();
        
        // Recorrer el ArrayList de ArrayLists (de las clases más generales a las más específicas)
        for (int i = fieldsByClassHierarchy.size() - 1; i>=0; i--) {
            fields.addAll(fieldsByClassHierarchy.get(i));
        }

		// Dimensiones de la tabla
		int rows;
		for(rows=0; rows<objects.length; rows++) {
			if(objects[rows]==null) {
				break;
			}
		}
		int columns = fields.size();
		
		DefaultTableModel model = new DefaultTableModel();
		 
		for (int j=0; j<columns; j++) {
			model.addColumn(fields.get(j).getName().toUpperCase());
		}
		for (int i=0; i<rows; i++) {
			Object [] row = new Object[columns];
			for (int j=0;j<columns;j++) {
				row[j] = fields.get(j).get(objects[i]);
			}
			model.addRow(row);
		}
		
		return new JTable(model);
	}
	
	
	public JTable convertToTable (ResultSet rs) throws SQLException, IllegalArgumentException {
		if(rs==null)
			return null;
		if(rs.getType()==ResultSet.TYPE_FORWARD_ONLY)
			throw new IllegalArgumentException ("El ResultSet debe ser \"scrollable\"");
		rs.beforeFirst();
		DefaultTableModel model = new DefaultTableModel();
		int columns = rs.getMetaData().getColumnCount();
		for (int j=1; j<=columns; j++) 
			model.addColumn(rs.getMetaData().getColumnName(j).toUpperCase());

		while(rs.next()) {
			Object [] row = new Object[columns];
			for (int j=1;j<=columns;j++) {
				//row[j-1] = rs.getString(j);
				row[j-1] = rs.getObject(j);
			}
			model.addRow(row);
		}
		JTable table = new JTable(model);
		rs.beforeFirst();
		return table;
	}
	
	public static void showInDialog(Object[] objects, String titulo) throws Exception {
		
		JTable table = convertToTable(objects);
		showInDialog(table, titulo);

	}
	
	public static void showInDialog(JTable table, String titulo) {
		if(table==null) return;
		
		Font titleFont = new Font("Arial", Font.BOLD, TITLE_FONT_SIZE);
		Font tableHeaderFont = new Font("Arial", Font.BOLD, TABLE_FONT_SIZE);
		Font tableBodyFont = new Font("Arial", Font.PLAIN, TABLE_FONT_SIZE);
		
		table.getTableHeader().setFont(tableHeaderFont);
		table.setFont(tableBodyFont);
		table.setDefaultEditor(Object.class, null);
	
		//table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		// Configuración de alineación según el tipo de datos
	    TableColumnModel columnModel = table.getColumnModel();
        DefaultTableCellRenderer numericRenderer = new DefaultTableCellRenderer();
        numericRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        
	    for (int j = 0; j < columnModel.getColumnCount(); j++) {
	    	Object value = table.getValueAt(0, j);
	    
	        Class<?> fieldType = value.getClass();
	        if (Number.class.isAssignableFrom(fieldType) || fieldType.isPrimitive() && (fieldType == int.class || fieldType == double.class || fieldType == float.class || fieldType == long.class)) {
	        	TableColumn column = columnModel.getColumn(j);
	            column.setCellRenderer(numericRenderer);
	        }
	    }
		
		int tableWidth = columnModel.getColumnCount()*COLUMN_WIDTH;
		table.setRowHeight(ROW_HEIGHT);
		int tablePreferredHeight = table.getRowCount()*ROW_HEIGHT;
	    table.setPreferredSize(new Dimension(tableWidth, tablePreferredHeight));
	    
	    int scrollPaneWidth = Math.min(MAX_SCROLL_PANE_WIDTH, tableWidth); // Ancho máximo del JScrollPane
	    table.setPreferredScrollableViewportSize(new Dimension(scrollPaneWidth, tablePreferredHeight));
	    
		JScrollPane tableSP = new JScrollPane(
			table,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
		);
		
		JLabel tituloLabel = new JLabel(titulo, JLabel.CENTER);
		tituloLabel.setFont(titleFont);
		Object[] objs = {tituloLabel, tableSP};
		
		JOptionPane.showMessageDialog(null, objs, titulo, JOptionPane.PLAIN_MESSAGE);
	}
	

	private static JTextArea convertToTextArea (String mensaje) {
		JTextArea textArea = new JTextArea(mensaje);
	    textArea.setOpaque( false );
	    textArea.setEditable( false );
	    textArea.setFont(new Font("Consolas", Font.BOLD, FONT_SIZE));
	    return textArea;
	}
	
	private static void showTextAreaInDialog (JTextArea mensaje) {
	    JOptionPane.showMessageDialog(null, mensaje);
	}
	
	public static void showMonospacedTextInDialog (String mensaje) {
		JTextArea textArea = convertToTextArea(mensaje);
		showTextAreaInDialog (textArea);
	}
	

	public static void showInConsole(Object[] objects, int columnWidth, String titulo) throws Exception {
		
		if (objects == null) {
	        throw new IllegalArgumentException("El parámetro 'objects' no debe ser null. Proporcione un array válido de objetos.");
	    }

	    if (!objects.getClass().isArray()) {
	        throw new IllegalArgumentException("El parámetro 'objects' debe ser un array. Tipo recibido: " + objects.getClass().getName());
	    }
	    
	    if (objects.length == 0) {
	    	System.out.println("La lista está vacía");
	    	return;
	    }

	    if (columnWidth < 1) {
	        throw new IllegalArgumentException("El parámetro 'columnWidth' debe ser mayor que cero");
	    }

		Class<? extends Object> clazz = objects[0].getClass();
		List<ArrayList<Field>> fieldsByClassHierarchy = new ArrayList<>();
		
        while (clazz != null) { // Recorre la jerarquía de clases
        	ArrayList<Field> fieldsInClass = new ArrayList<>();
        	
            for (Field field : clazz.getDeclaredFields()) {
            	field.setAccessible(true);
                fieldsInClass.add(field);
            }
            
            fieldsByClassHierarchy.add(fieldsInClass);
            clazz = clazz.getSuperclass();
        }
        ArrayList<Field> fields = new ArrayList<>();
        
        // Recorrer el ArrayList de ArrayLists (de las clases más generales a las más específicas)
        for (int i = fieldsByClassHierarchy.size() - 1; i>=0; i--) {
            fields.addAll(fieldsByClassHierarchy.get(i));
        }
		
		// Dimensiones de la tabla
		int rows;
		for(rows=0; rows<objects.length; rows++) {
			if(objects[rows]==null) {
				break;
			}
		}
		int columns = fields.size();

		// Nombres y tipos de las columnas
		// El tipo se usa para dar formato a los datos
		List<String> columnNames = new ArrayList<>(columns);
		List<String> columnFormats = new ArrayList<>(columns);
		List<String> headerFormats = new ArrayList<>(columns);
		
		for (int j = 0; j < columns; j++) {
			columnNames.add(fields.get(j).getName().toUpperCase());
			String columnType = fields.get(j).getType().getSimpleName();
			switch (columnType) {
			case "long":
			case "int":
				columnFormats.add("%" + columnWidth + "d");
				break;
			case "double":
			case "float":
				columnFormats.add("%" + columnWidth + ".2f");
				break;
			default:
				columnFormats.add("%-" + columnWidth + "s");
			}
			headerFormats.add("%-" + columnWidth + "s");
		}

		StringBuilder sb = new StringBuilder();
		sb.append(titulo+"\n");

		// Bordes horizontales
		String[] horizontalCellBorders = new String[columns];
		for (int j = 0; j < columns; j++) {
			horizontalCellBorders[j] = "─".repeat(columnWidth + 2);
		}

		// Borde superior de la tabla
		sb.append("┌");
		for (int j = 0; j < columns - 1; j++) {
			sb.append(horizontalCellBorders[j]).append("┬");
		}
		sb.append(horizontalCellBorders[columns - 1]).append("┐\n");

		// Encabezado
		for (int j = 0; j < columns; j++) {
			String formattedField = String.format(headerFormats.get(j), columnNames.get(j));
			if (formattedField.length() > columnWidth) {
				formattedField = formattedField.substring(0, columnWidth - 1) + "+";
			}
			sb.append("| " + formattedField + " ");
		}
		sb.append("|\n");

		// Borde entre encabezado y cuerpo
		sb.append("├");
		for (int j = 0; j < columns - 1; j++) {
			sb.append(horizontalCellBorders[j]).append("┼");
		}
		sb.append(horizontalCellBorders[columns - 1]).append("┤\n");

		// Filas
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				String fieldFormatted = String.format(columnFormats.get(j), fields.get(j).get(objects[i]));
				if (fieldFormatted.length() > columnWidth) {
					fieldFormatted = fieldFormatted.substring(0, columnWidth - 1) + "+";
				}
				sb.append("| " + fieldFormatted + " ");
			}
			sb.append("|\n");
		}
		// Borde inferior tabla
		sb.append("└");
		for (int j = 0; j < columns - 1; j++) {
			sb.append(horizontalCellBorders[j]).append("┴");
		}
		sb.append(horizontalCellBorders[columns - 1]).append("┘\n");
		
		System.out.println(sb.toString());
		
	}
	
	
	public static void showInConsole(Object[] objects, int[] columnWidths, String titulo) throws Exception {

	    if (objects == null) {
	        throw new IllegalArgumentException("El parámetro 'objects' no debe ser null. Proporcione un array válido de objetos.");
	    }

	    if (!objects.getClass().isArray()) {
	        throw new IllegalArgumentException("El parámetro 'objects' debe ser un array. Tipo recibido: " + objects.getClass().getName());
	    }
	    
	    if (objects.length == 0) {
	    	System.out.println("La lista está vacía");
	    	return;
	    }

	    if (columnWidths == null) {
	        throw new IllegalArgumentException("El parámetro 'columnWidths' no debe ser null. Proporcione un array de anchos para las columnas.");
	    }

	    if (columnWidths.length == 0) {
	        throw new IllegalArgumentException("El array 'columnWidths' está vacío. Proporcione al menos un ancho de columna.");
	    }

	    Class<? extends Object> clazz = objects[0].getClass();
		List<ArrayList<Field>> fieldsByClassHierarchy = new ArrayList<>();
		
        while (clazz != null) { // Recorre la jerarquía de clases
        	ArrayList<Field> fieldsInClass = new ArrayList<>();
        	
            for (Field field : clazz.getDeclaredFields()) {
            	field.setAccessible(true);
                fieldsInClass.add(field);
            }
            
            fieldsByClassHierarchy.add(fieldsInClass);
            clazz = clazz.getSuperclass();
        }
        
        ArrayList<Field> fields = new ArrayList<>();
        
        // Recorrer el ArrayList de ArrayLists (de las clases más generales a las más específicas)
        for (int i = fieldsByClassHierarchy.size() - 1; i>=0; i--) {
            fields.addAll(fieldsByClassHierarchy.get(i));
        }
		
		// Dimensiones de la tabla
		int rows;
		for(rows=0; rows<objects.length; rows++) {
			if(objects[rows]==null) {
				break;
			}
		}
		int columns = fields.size();

		// Nombres y tipos de las columnas
		// El tipo se usa para dar formato a los datos
		List<String> columnNames = new ArrayList<>(columns);
		List<String> columnFormats = new ArrayList<>(columns);
		List<String> headerFormats = new ArrayList<>(columns);
		
		for (int j = 0; j < columns; j++) {
			columnNames.add(fields.get(j).getName());
			String columnType = fields.get(j).getType().getSimpleName();
			switch (columnType) {
			case "long":
			case "int":
				columnFormats.add("%" + columnWidths[j] + "d");
				break;
			case "double":
			case "float":
				columnFormats.add("%" + columnWidths[j] + ".2f");
				break;
			default:
				columnFormats.add("%-" + columnWidths[j] + "s");
			}
			headerFormats.add("%-" + columnWidths[j] + "s");
		}

		StringBuilder sb = new StringBuilder();
		sb.append(titulo+"\n");

		// Bordes horizontales
		String[] horizontalCellBorders = new String[columns];
		for (int j = 0; j < columns; j++) {
			horizontalCellBorders[j] = "─".repeat(columnWidths[j] + 2);
		}

		// Borde superior de la tabla
		sb.append("┌");
		for (int j = 0; j < columns - 1; j++) {
			sb.append(horizontalCellBorders[j]).append("┬");
		}
		sb.append(horizontalCellBorders[columns - 1]).append("┐\n");

		// Encabezado
		for (int j = 0; j < columns; j++) {
			String formattedField = String.format(headerFormats.get(j), columnNames.get(j));
			if (formattedField.length() > columnWidths[j]) {
				formattedField = formattedField.substring(0, columnWidths[j] - 1) + "+";
			}
			sb.append("| " + formattedField + " ");
		}
		sb.append("|\n");

		// Borde entre encabezado y cuerpo
		sb.append("├");
		for (int j = 0; j < columns - 1; j++) {
			sb.append(horizontalCellBorders[j]).append("┼");
		}
		sb.append(horizontalCellBorders[columns - 1]).append("┤\n");

		// Filas
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				String fieldFormatted = String.format(columnFormats.get(j), fields.get(j).get(objects[i]));
				if (fieldFormatted.length() > columnWidths[j]) {
					fieldFormatted = fieldFormatted.substring(0, columnWidths[j] - 1) + "+";
				}
				sb.append("| " + fieldFormatted + " ");
			}
			sb.append("|\n");
		}
		// Borde inferior tabla
		sb.append("└");
		for (int j = 0; j < columns - 1; j++) {
			sb.append(horizontalCellBorders[j]).append("┴");
		}
		sb.append(horizontalCellBorders[columns - 1]).append("┘\n");
		
		System.out.println(sb.toString());
	}
	
}
