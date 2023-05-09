package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;



public class GestorMediciones {

	private Vector<Medicion> listaMediciones = new Vector<Medicion>();
	
	public void guardarMediciones(String nombreFichero)  {

		try {
			File fichero = new File(nombreFichero);
			if (!fichero.exists()) {
				fichero.createNewFile();
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));

			for (Medicion m : listaMediciones) {
				bw.write(m.medicionCsv());
				bw.write("\n");
			}

			bw.close();
		} catch (IOException e) {
			System.out.println("No se puede crear el fichero " + nombreFichero);
		}

		System.out.println("" + listaMediciones.size() + " mediciones guardadas!");
	}
	
	public void leerMediciones (String nombreFichero) {

		Vector<Medicion> copiaMediciones = new Vector<Medicion>();
		
		
		try {
			File fichero = new File(nombreFichero);
			BufferedReader br = new BufferedReader(new FileReader(fichero));
			
			String linea = br.readLine();
			while (linea != null) {
				CsvMedicion CsvM= new CsvMedicion();
				copiaMediciones.add(CsvM.crearMedicion(linea));
				linea = br.readLine();
			}
			br.close();
			listaMediciones= copiaMediciones;
			
		} catch (IOException e) {
			System.out.println ("No se puede abrir el fichero");
		}
		
		System.out.println("" + listaMediciones.size() + " mediciones leidas!");	
	}
	
	public Vector<Medicion> getListaMediciones() {
		return listaMediciones;
	}
	
	public Medicion crearMedicion() {
		return new Medicion ();
	}

	public SortedMap <String,Integer> mapaReportes (){
		SortedMap <String,Integer> mapaReportes = new TreeMap<String, Integer> ();
		String fechaConvertida= "";
		
		for (Medicion m : this.listaMediciones) {
			fechaConvertida = m.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			mapaReportes.computeIfPresent(fechaConvertida, (key,value) -> value + m.medicionReportes());
			mapaReportes.computeIfAbsent(fechaConvertida, key -> m.medicionReportes());
			
		}
		
		return mapaReportes;
	}
	
	public SortedMap <String, Vector<Par<String, Integer>>> mapaLinea (){
		
		SortedMap <String, Vector<Par<String,Integer>>>  mapaLinea = new TreeMap<String, Vector<Par<String,Integer>>>  ();
		String fecha= "";
		String hora = "";
		Par<String,Integer>par;
		Vector<Par<String,Integer>> vector;
		
		for (Medicion m : this.listaMediciones) {
			fecha = m.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			hora = m.getHora().format(DateTimeFormatter.ofPattern("kk:mm:ss"));
			par = new Par <String,Integer> (hora, m.getAnchoBanda());
			if (mapaLinea.containsKey(fecha)) {
				mapaLinea.get(fecha).add(par);	
			}
			else {
				vector =new Vector<Par<String,Integer>>();
				vector.add(par);
				mapaLinea.put(fecha, vector);
			}
			
		}
		
		return mapaLinea;
	}
	
	public void borarMediciones () {
		this.listaMediciones.clear();
	}
}
	
	
	
	
	
	

