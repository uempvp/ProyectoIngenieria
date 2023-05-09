package modelo;

import java.time.LocalDate;
import java.time.LocalTime;



public class CsvMedicion {

	public Medicion crearMedicion(String lineaTxt) {
		String [] partes= lineaTxt.split(";");
		Medicion medicion = new Medicion ();
		
		int anchoBanda= 0;
		int ipConectadas= 0;
		int throughput= 0;
		int latencia = 0;
		double tasaErrores = 0; 
		float flujoDatos = 0;
	
	
		medicion.setFecha(LocalDate.parse(partes[0]));
		medicion.setHora(LocalTime.parse(partes[1]));
		anchoBanda = Integer.parseInt(partes[2]);
		ipConectadas = Integer.parseInt(partes [3]);
		throughput = Integer.parseInt(partes [4]);
		latencia = Integer.parseInt(partes [5]);
		tasaErrores = Double.parseDouble(partes[6]);
		flujoDatos = Float.parseFloat(partes[7]);
		medicion.setAnchoBanda(anchoBanda);
		medicion.setIpConectadas(ipConectadas);
		medicion.setThroughput(throughput);
		medicion.setLatencia(latencia);
		medicion.setTasaErrores(tasaErrores);
		medicion.setFlujoDatos(flujoDatos);
		
		return medicion;
		}
	}