package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;
import modelo.GestorMediciones;
import modelo.Medicion;
import modelo.Par;
import vista.Ventana;


public class Controlador implements ActionListener {

	private GestorMediciones gestorMediciones;
	private Ventana ventana;
	private String nombreFichero = "Mediciones.csv";
	private boolean ejecutar =true;
	private Runnable runnable = new Runnable() {
		@Override
		public void run() { 
			while(ejecutar) {
				try {

					Medicion mRun =gestorMediciones.crearMedicion();
					gestorMediciones.getListaMediciones().add(mRun);
					ventana.actualizarTabla(mRun.medicionJTable());
					ventana.anadirPanelMostrar(mRun.medicionJTexPane());	
					Thread.sleep(3000);

				} catch (InterruptedException e) {
					e.printStackTrace ();
				}
			}
		}
	};


	public Controlador() {
		this.gestorMediciones = new GestorMediciones();
		this.ventana = new Ventana(this);
	}	

	public void arrancarApp () {
		ventana.mostrarVentana();
	}

	public void leerFichero() {
		this.limpiarDatos();
		gestorMediciones.leerMediciones(nombreFichero);
		String [] fila= {};

		for (Medicion m: gestorMediciones.getListaMediciones()){
			fila =m.medicionJTable();
			ventana.actualizarTabla(fila);
			if(m.medicionJTexPane()!=null) {
				ventana.anadirPanelMostrar(m.medicionJTexPane());
			}	
		}		
	}

	public void guardarFichero() {
		gestorMediciones.guardarMediciones(nombreFichero);
	}

	public Vector <Medicion> getListaMediciones () {
		return gestorMediciones.getListaMediciones();
	}

	public void limpiarDatos() {
		ventana.limpiarTabla();
		ventana.borrarPanelMostrar();
		gestorMediciones.borarMediciones();
	}

	public void escanear () {
		ejecutar=true;
		ventana.configurarBotonesEscaneo();
		Thread hilo = new Thread (runnable);
		hilo.start();	
	}

	public void detenerEscaneo () {
		ventana.configurarBotonesDetener();
		ejecutar=false;
	}

	public void BarChart () {
		ventana.setDataBarChart();
		SortedMap<String, Integer> mapa = new TreeMap<String, Integer> ();
		mapa = gestorMediciones.mapaReportes();

		for (HashMap.Entry <String,Integer> entry : mapa.entrySet ()) {
			ventana.dataBarChart(entry.getValue(), entry.getKey());
		}
		ventana.ventanaBarChart();
	}

	public void LineChart () {
		ventana.setDataLineChart ();
		String [] dias;
		Vector <String> listaDias = new Vector <String> ();

		SortedMap <String, Vector<Par<String,Integer>>> mapa = gestorMediciones.mapaLinea();
		for (Entry <String, Vector<Par<String,Integer>>> entry : mapa.entrySet()) {
			listaDias.add(entry.getKey());
		}

		dias = new String [listaDias.size()];
		for (int i =0; i<dias.length; i++) {
			dias[i] =listaDias.get(i);	
		}
		String dia = ventana.elegirDia(dias);
		//Comprobamos que efectivamente el usuario haya elegido una opcion
		if (dia!=null) {
			Vector<Par<String,Integer>> datos = mapa.get(dia);
			for (Par<String,Integer> p : datos) {
				ventana.dataLineChart(p.getValor(),p.getClave());
			}

			ventana.ventanaLineChart (dia);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Leer") {
			this.leerFichero();
		}
		else if (e.getActionCommand() == "Guardar") {
			if(ventana.comprobarDatosTabla()) {
				this.guardarFichero();
			}
			else {
				ventana.ventanaNoDatos();
			}
		}
		else if (e.getActionCommand() == "Reportes diarios") {
			if(ventana.comprobarDatosTabla()) {
				this.BarChart();
			}
			else {
				ventana.ventanaNoDatos();	
			}
		}
		else if (e.getActionCommand() == "Ancho banda consumido") {
			if(ventana.comprobarDatosTabla()) {
				this.LineChart();
			}
			else {
				ventana.ventanaNoDatos();	
			}
		}
		else if (e.getActionCommand() == "Limpiar") {
			this.limpiarDatos();
		}
		else if (e.getActionCommand() == "Escanear") {
			this.escanear();
		}
		else if (e.getActionCommand() == "Detener") {
			this.detenerEscaneo();
		}
	}

}