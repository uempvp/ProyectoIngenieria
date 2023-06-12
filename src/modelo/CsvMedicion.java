package modelo;

import java.time.LocalDate;
import java.time.LocalTime;



public class CsvMedicion {

	public Medicion crearMedicion(String lineaTxt) {
		String [] partes= lineaTxt.split(";");
		Medicion medicion = new Medicion ();
		
		
		medicion.setFecha(LocalDate.parse(partes[0]));
		medicion.setHora(LocalTime.parse(partes[1]));
		medicion.setAnchoBanda(Integer.parseInt(partes[2]));
		medicion.setIpConectadas(Integer.parseInt(partes [3]));
		medicion.setThroughput(Integer.parseInt(partes [4]));
		medicion.setLatencia(Integer.parseInt(partes [5]));
		medicion.setTasaErrores(Double.parseDouble(partes[6]));
		medicion.setFlujoDatos(Double.parseDouble(partes[7]));
		
		
		
		return medicion;
		}
	}