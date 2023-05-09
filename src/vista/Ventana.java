package vista;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;
import control.Controlador;




public class Ventana extends JFrame {

	private BarraMenu barraMenu;
	private PanelBotones panelBotones;
	private PanelMostrar panelMostrar;
	private JScrollPane scroll;
	private final String [] nombresColumna = {"Fecha", "Hora", "AnchoBanda","IP Conectadas","Throughput","Latencia","Tasa Errores","Flujo Datos"};
	private String [] [] mediciones = {};

	private DefaultTableModel dtm;
	private JTable tabla; 
	private DefaultCategoryDataset dataBarChart;
	private DefaultCategoryDataset dataLineChart;


	public Ventana(Controlador controlador) {
		JPanel panelCentral = new JPanel();
		
		barraMenu = new BarraMenu();
		this.setJMenuBar(barraMenu);
		
		dtm = new DefaultTableModel (mediciones,nombresColumna);
		tabla = new JTable (dtm);
		panelCentral.add(setTabla());

		panelMostrar = new PanelMostrar();
		panelCentral.add(panelMostrar);

		panelBotones = new PanelBotones ();
		panelCentral.add(panelBotones);

		this.getContentPane().add(panelCentral);

		barraMenu.leerFichero.addActionListener(controlador);
		barraMenu.guardarFichero.addActionListener(controlador);
		barraMenu.grafica.addActionListener(controlador);
		barraMenu.barChart.addActionListener(controlador);
		barraMenu.lineChart.addActionListener(controlador);
		panelBotones.limpiar.addActionListener(controlador);
		panelBotones.escanear.addActionListener(controlador);
		panelBotones.detener.addActionListener(controlador);

	}


	private class BarraMenu extends JMenuBar {
		public JMenu inicio;
		public JMenu grafica;
		public JMenuItem leerFichero;
		public JMenuItem guardarFichero;
		public JMenuItem barChart;
		public JMenuItem lineChart;


		BarraMenu () {
			inicio = new JMenu("Inicio");
			inicio.setMnemonic(KeyEvent.VK_I);
			grafica = new JMenu ("Estadistísticas");
			grafica.setMnemonic(KeyEvent.VK_E);

			leerFichero = new JMenuItem("Leer");
			leerFichero.setMnemonic (KeyEvent.VK_L);

			guardarFichero = new JMenuItem ("Guardar");
			guardarFichero.setMnemonic(KeyEvent.VK_G);

			barChart = new JMenuItem ("Reportes diarios");
			barChart.setMnemonic(KeyEvent.VK_R);

			lineChart = new JMenuItem ("Ancho banda consumido");
			lineChart.setMnemonic(KeyEvent.VK_A);

			inicio.add(leerFichero);
			inicio.add(guardarFichero);

			grafica.add(barChart);
			grafica.add(lineChart);


			this.add(inicio);
			this.add(grafica);

		}
	}

	private class PanelMostrar extends JPanel{
		public JTextArea textArea;
		public JScrollPane scroll; 

		PanelMostrar () {
			textArea = new JTextArea(7,65);
			textArea.setBorder(new LineBorder(Color.BLUE));
			textArea.setEditable(false);
			scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			this.add(scroll);
		}
	}

	private class PanelBotones extends JPanel{

		public JButton escanear;
		public JButton detener;
		public JButton limpiar;

		PanelBotones () {
			escanear = new JButton("Escanear");
			this.add(escanear);

			detener = new JButton("Detener");
			detener.setEnabled(false);
			this.add(detener);

			limpiar = new JButton("Limpiar");
			this.add(limpiar);
		}


	}

	public JScrollPane setTabla () {

		//Se ajusta el ancho de las columnas, se centran los datos, y se añade scroll vertical
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();

		center.setHorizontalAlignment(JLabel.CENTER);
		tabla.getColumnModel().getColumn(0).setCellRenderer(center);
		tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
		tabla.getColumnModel().getColumn(1).setCellRenderer(center);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(50);
		tabla.getColumnModel().getColumn(2).setCellRenderer(center);
		tabla.getColumnModel().getColumn(2).setPreferredWidth(50);
		tabla.getColumnModel().getColumn(3).setCellRenderer(center);
		tabla.getColumnModel().getColumn(3).setPreferredWidth(50);
		tabla.getColumnModel().getColumn(4).setCellRenderer(center);
		tabla.getColumnModel().getColumn(4).setPreferredWidth(50);
		tabla.getColumnModel().getColumn(5).setCellRenderer(center);
		tabla.getColumnModel().getColumn(5).setPreferredWidth(50);
		tabla.getColumnModel().getColumn(6).setCellRenderer(center);
		tabla.getColumnModel().getColumn(6).setPreferredWidth(80);
		tabla.getColumnModel().getColumn(7).setCellRenderer(center);
		tabla.getColumnModel().getColumn(7).setPreferredWidth(80);

		tabla.setFillsViewportHeight(true);
		tabla.setEnabled(false);
		scroll = new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		scroll.setPreferredSize(new Dimension (950,400));

		return scroll;
	}

	public void actualizarTabla (String [] medicion) {
		this.dtm.addRow(medicion);
	}

	public void limpiarTabla() {
		while (this.dtm.getRowCount()>0) {
			dtm.removeRow(0);
		}
	}

	public void borrarPanelMostrar() {
		panelMostrar.textArea.setText(null);
	}

	public void anadirPanelMostrar(String texto) {
		panelMostrar.textArea.append(texto);
	}

	public void configurarBotonesEscaneo (){
		this.panelBotones.escanear.setEnabled(false);
		this.panelBotones.detener.setEnabled(true);
		this.panelBotones.limpiar.setEnabled(false);
		this.barraMenu.inicio.setEnabled(false);
		this.barraMenu.grafica.setEnabled(false);

	}

	public void configurarBotonesDetener () {
		this.panelBotones.escanear.setEnabled(true);
		this.panelBotones.detener.setEnabled(false);
		this.panelBotones.limpiar.setEnabled(true);
		this.barraMenu.inicio.setEnabled(true);
		this.barraMenu.grafica.setEnabled(true);
	}

	public void setDataBarChart () {
		this.dataBarChart = new DefaultCategoryDataset ();
	}
	

	public void dataBarChart(Integer reportes, String fecha) {
		dataBarChart.addValue(reportes, "", fecha );
	}


	public void ventanaBarChart () {

		JFreeChart chart = ChartFactory.createBarChart(" Nº reportes diarios por fecha","Fechas", "Nº reportes",dataBarChart,PlotOrientation.VERTICAL,false,false,false);
		//Establecemos tamaño maximo de las barras histograma en caso de que haya pocas para que no nos ocupe todo la pantalla
		CategoryPlot plot= chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinesVisible (true);
		plot.setRangeGridlinePaint (Color.BLACK);
		
		BarRenderer br = (BarRenderer) plot.getRenderer();
		br.setMaximumBarWidth(.08);
		br.setSeriesPaint(0, Color.DARK_GRAY);
		

		ChartPanel chartPanel = new ChartPanel (chart);

		JFrame f = new JFrame ();
		f.add(chartPanel);
		f.pack();
		RefineryUtilities.centerFrameOnScreen(f);
		f.setVisible(true);

	}

	public void setDataLineChart () {
		this.dataLineChart= new DefaultCategoryDataset ();
	}

	public String elegirDia(String [] dias) {
		String [] elecciones = new String [dias.length];
		System.arraycopy(dias,0, elecciones, 0, dias.length);

		return (String)JOptionPane.showInputDialog(this, "Selecciona fecha : ", "¿Fecha a elegir?", 
				JOptionPane.DEFAULT_OPTION, null, elecciones, elecciones[0]);		
	}

	public void dataLineChart(Integer valor, String hora) {
		dataLineChart.addValue(valor, "", hora);
	}
	
	public void ventanaLineChart (String dia) {
		JFreeChart chart= ChartFactory.createLineChart ("Consumo ancho banda "+ dia,"Horas","Ancho Banda %",dataLineChart,PlotOrientation.VERTICAL, false,false,false);

		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinesVisible (true);
		plot.setRangeGridlinePaint (Color.BLACK);
		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint (Color.BLACK);

		LineAndShapeRenderer br = (LineAndShapeRenderer) plot.getRenderer();
		br.setSeriesStroke(0, new BasicStroke(2.0f));

		ChartPanel chartPanel = new ChartPanel (chart);
		
		JFrame f = new JFrame ();
		f.add(chartPanel);
		f.pack();
		RefineryUtilities.centerFrameOnScreen(f);
		f.setVisible(true);
	}

	public void ventanaNoDatos () {
		JOptionPane.showMessageDialog(this, "¡No existe ningún muestreo!", "Error",JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	public boolean comprobarDatosTabla () {

		if (this.dtm.getRowCount()>0) {
			return true;
		}
		else {
			return false;
		}

	}

	public void mostrarVentana() {

		this.setTitle("Gestor de Mediciones");
		this.setResizable(false); 
		this.setSize(1000,600);
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


}
