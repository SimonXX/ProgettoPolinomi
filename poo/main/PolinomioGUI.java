package poo.main;

import poo.polinomi.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.*;
import java.util.*;

import static poo.polinomi.PolinomioUtil.*;

@SuppressWarnings("serial")

class FrameGUI extends JFrame{
	
	
	private FrameAL fAL = null;
	
	private FrameInserisciPolinomio frameIP = null; 
	private FrameInserisciPolinomioDiretto frameIPD = null;
	private FrameCambiaPolinomio frameCP = null;
	private FrameValuta frameVL = null;
	
	
	private int capacita = 50;
	
	private File fileDiSalvataggio=null;
	
	
	private final String FONT1 = "Century Schoolbook";
	private final String FONT2 = "Californian FB";
	private final String FONT3 = "Perpetua";
	private final String FONT4 = "Poor Richard";
	private final String FONT5 = "Book Antiqua";
	
	private final int COLOR1 = 0xFFF0F5;//JButton
	private final int COLOR2 = 0xB0E0E6;//RadioButton
	private final int COLOR3 = 0xFFCC66;//JMenu
	private final int COLOR4 = 0xFFFFE1;//JMenuItem

	private enum Tipo{PolinomioArrayList, PolinomioLinkedList, PolinomioSet, PolinomioMap, PolinomioListaConcatenataOrdinta, PolinomioABR}
	
	private Tipo tipo = Tipo.PolinomioMap;
	private String titolo = "Calcolatrice Polinomi";
	private String impl = " MAP ";
	
	Toolkit kit=Toolkit.getDefaultToolkit();
	
	private JMenuBar menuBar;
	
	private JMenuItem apri, salva, salvaConNome, esci, derivata, valuta, cambia, addizione, moltiplicazione, inserisci, inserisciDiretto, elimina, svuota, about;
	
	private FileNameExtensionFilter filtro = new FileNameExtensionFilter("File di testo", "txt");
	
	
	//Variabili globali
	

	//ad ogni checkbox corrisponderà un polinomio creato
	private static  Integer numeroCheckbox = 0;
	private static int selezionate  = 0; //conta quante celle sono state selezionate
	private static Map<Integer, JCheckBox> elencoCheckbox= new TreeMap<>();//poiché useremo come chiave il numerodi checkbox, le checkbox della mappa saranno ordinate per inserimento
	
	private static Polinomio polinomio = null;//questa variabile, una volta inizializzata attraverso il radioButton, sarà utilizzata
	  										  //come base per creare, attraverso il metodo factory, tutti i polinomi implementati con la struttura dati scelta
	private static JPanel pannelloCheckbox ;
	
	JScrollPane scrollPaneRis;
	private static JTextArea console;
	
	
	public FrameGUI() throws IOException{
		setTitle(titolo + impl);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		addWindowListener( new WindowAdapter() {
	        public void windowClosing(WindowEvent e){
	       	 if( consensoUscita() ) System.exit(0);
	        }
	     } );

		setBounds(200, 200, 763, 503);
	
		 RadioButtonFrame frame1 =new RadioButtonFrame();
	     frame1.setVisible(true);
	     
	     //elementi finestra principale
	     menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			
			JMenu file = new JMenu("File");
			menuBar.add(file);
			file.setOpaque(true);
			file.setBackground(new Color(COLOR3));
			file.setFont(new Font(FONT1, Font.BOLD, 12));
			
			apri = new JMenuItem("Apri");
			file.add(apri);
			apri.setOpaque(true);
			apri.setBackground(new Color(COLOR4));
			
			salva = new JMenuItem("Salva");
			file.add(salva);
			salva.setOpaque(true);
			salva.setBackground(new Color(COLOR4));
			
			salvaConNome = new JMenuItem("Salva con nome");
			file.add(salvaConNome);
			salvaConNome.setOpaque(true);
			salvaConNome.setBackground(new Color(COLOR4));
			
			JSeparator separator = new JSeparator();
			file.add(separator);
			
			esci = new JMenuItem("Esci");
			file.add(esci);
			esci.setOpaque(true);
			esci.setBackground(new Color(COLOR4));
			
			JMenu operazioni = new JMenu("Operazioni");
			menuBar.add(operazioni);
			operazioni.setOpaque(true);
			operazioni.setBackground(new Color(COLOR3));
			operazioni.setFont(new Font(FONT1, Font.BOLD, 12));
			
			JMenu unarie = new JMenu("Unarie");
			operazioni.add(unarie);
			unarie.setOpaque(true);
			unarie.setBackground(new Color(COLOR4));
			
			
			derivata = new JMenuItem("Derivata");
			unarie.add(derivata);
			derivata.setOpaque(true);
			derivata.setBackground(new Color(COLOR4));
			
			cambia = new JMenuItem("Cambia");
			unarie.add(cambia);
			cambia.setOpaque(true);
			cambia.setBackground(new Color(COLOR4));
			
			valuta = new JMenuItem("Valuta");
			unarie.add(valuta);
			valuta.setOpaque(true);
			valuta.setBackground(new Color(COLOR4));
			
			JMenu binarie = new JMenu("Binarie");
			operazioni.add(binarie);
			binarie.setOpaque(true);
			binarie.setBackground(new Color(COLOR4));
			
			addizione = new JMenuItem("Addizione");
			binarie.add(addizione);
			addizione.setOpaque(true);
			addizione.setBackground(new Color(COLOR4));
			
			
			moltiplicazione = new JMenuItem("Moltiplicazione");
			binarie.add(moltiplicazione);
			moltiplicazione.setOpaque(true);
			moltiplicazione.setBackground(new Color(COLOR4));
			
			JMenu modifica = new JMenu("Modifica");
			menuBar.add(modifica);
			modifica.setOpaque(true);
			modifica.setBackground(new Color(COLOR3));
			modifica.setFont(new Font(FONT1, Font.BOLD, 12));
			
			inserisci = new JMenuItem("Inserisci");
			modifica.add(inserisci);
			inserisci.setOpaque(true);
			inserisci.setBackground(new Color(COLOR4));
			
			inserisciDiretto = new JMenuItem("Inserimento Rapido");
			modifica.add(inserisciDiretto);
			inserisciDiretto.setOpaque(true);
			inserisciDiretto.setBackground(new Color(COLOR4));
			

			JSeparator separator2 = new JSeparator();
			modifica.add(separator2);
			
			elimina = new JMenuItem("Elimina");
			modifica.add(elimina);
			elimina.setOpaque(true);
			elimina.setBackground(new Color(COLOR4));
			
			svuota = new JMenuItem("Svuota");
			modifica.add(svuota);
			svuota.setOpaque(true);
			svuota.setBackground(new Color(COLOR4));
			
			
			JMenu help = new JMenu("Help");
			menuBar.add(help);
			help.setOpaque(true);
			help.setBackground(new Color(COLOR3));
			help.setFont(new Font(FONT1, Font.BOLD, 12));
			

			about = new JMenuItem("About");
			help.add(about);
			about.setOpaque(true);
			about.setBackground(new Color(COLOR4));
	
			
			JPanel pannello1 = new JPanel();
			pannello1.setBackground(new Color(240, 255, 240));
			pannello1.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(pannello1);
			pannello1.setLayout(null);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setBounds(220, 126, 310, 180);
			pannello1.add(scrollPane);
			
			pannelloCheckbox = new JPanel();
		
			pannelloCheckbox.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			scrollPane.setViewportView(pannelloCheckbox);
			pannelloCheckbox.setLayout(new GridLayout(0, 1, 0, 0));
			
			//LE CHECKBOX VENGONO AGGIUNTE OGNI VOLTA CHE VIENE INSERITO UN POLINOMIO
		
			//muniamo la console della scrollbar, inserendola in un JPanel
			scrollPaneRis = new JScrollPane();
			scrollPaneRis.setBounds(151, 352, 440, 50);
 			pannello1.add(scrollPaneRis);
 			
			console = new JTextArea();
			scrollPaneRis.setViewportView(console);
			console.setBackground(new Color(255, 255, 255));
			console.setEditable(false);
			console.setBounds(151, 352, 440, 50);
			//pannello1.add(console);
			
			JTextField pannelloTitolo = new JTextField();
			pannelloTitolo.setEditable(false);
			pannelloTitolo.setBackground(SystemColor.info);
			pannelloTitolo.setForeground(new Color(72, 61, 139));
			pannelloTitolo.setFont(new Font("Rockwell", Font.PLAIN, 15));
			pannelloTitolo.setHorizontalAlignment(SwingConstants.CENTER);
			pannelloTitolo.setText("POLINOMI INSERITI");
			pannelloTitolo.setBounds(230, 78, 290, 44);
			pannello1.add(pannelloTitolo);
			pannelloTitolo.setColumns(10);
			
		
			Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		
			console.setBorder(border);
			console.setFont(new Font(FONT5, Font.BOLD, 15));
			
			JLabel label1 = new JLabel("Risultato");
			label1.setFont(new Font(FONT5, Font.BOLD, 15));
			label1.setBounds(335, 327, 80, 16);
			pannello1.add(label1);
			
		
			JButton clear = new JButton("Clear");
			clear.setFont(new Font(FONT5, Font.BOLD, 15));
			clear.setBounds(600, 350, 100, 50);
			pannello1.add(clear);
			
			clear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					console.setText("");
				}
			});
			
			AscoltatoreEventiAzione listener = new AscoltatoreEventiAzione();
			//apri, salva, salvaConNome, esci, operazioni, dividi, derivata, valuta, addizione, sottrazione, moltiplicazione, inserisci, elimina, doc, about;
			
			apri.addActionListener(listener);
			salva.addActionListener(listener);
			salvaConNome.addActionListener(listener);
			esci.addActionListener(listener);
			derivata.addActionListener(listener);
			valuta.addActionListener(listener);
			cambia.addActionListener(listener);
			addizione.addActionListener(listener);
			moltiplicazione.addActionListener(listener);
			inserisci.addActionListener(listener);
			inserisciDiretto.addActionListener(listener);
			elimina.addActionListener(listener);
			svuota.addActionListener(listener);
			about.addActionListener(listener);
			
		
	}//FrameGUI
	


	
 	//SCHERMATA INIZIALE
 	private class RadioButtonFrame extends JFrame implements ActionListener{
 		
 		
 
 		private JRadioButton RadioButtonMAP, RadioButtonAL, RadioButtonSET, RadioButtonLL, RadioButtonLCO, RadioButtonABR;
 		private ButtonGroup gruppo1 = new ButtonGroup();
 		private String pathIconButton = "src\\poo\\polinomi\\image\\icon_button.png";
 		private JTextArea nome, testo1;
 		private JPanel p1, p2;
 		private JButton conferma;
 		

 		
 	
 		public RadioButtonFrame() {
 		
 			setBackground(new Color(230, 230, 250));
 			setBounds(300, 300, 690, 388);
 			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 			setTitle("Struttura Dati");
 			setLocationByPlatform(true);
 			
 			p2 = new JPanel();
 			p2.setBackground(new Color(240, 255, 255));
 			getContentPane().add(p2, BorderLayout.CENTER);
 			p2.setLayout(null);
 			
 			nome = new JTextArea();
 			nome.setEditable(false);
 			nome.setFont(new Font(FONT1, Font.PLAIN, 23));
 			nome.setText("CALCOLATRICE POLINOMI");
 			nome.setBounds(185, 50, 327, 32);
 			p2.add(nome);
 			
 			testo1 = new JTextArea();
 			testo1.setBounds(110, 145, 462, 26);
 			testo1.setFont(new Font(FONT3, Font.PLAIN, 20));
 			testo1.setTabSize(1);
 			testo1.setBackground(new Color(255, 255, 224));
 			testo1.setEditable(false);
 			testo1.setRows(1);
 			testo1.setText("Scegliere una struttura dati per la memorizzazione dei polinomi");
 			
 			p2.add(testo1);
 			
 			conferma = new JButton("OK");
 			conferma.setFont(new Font(FONT4, Font.PLAIN, 11));
 			conferma.setBackground(new Color(COLOR2));
 			conferma.setIcon(new ImageIcon(pathIconButton));
 			conferma.setBounds(289, 263, 118, 57);
 			p2.add(conferma);
 			
 			p1 = new JPanel();
 			p1.setBounds(196, 201, 287, 400);
 			p2.add(p1);
 			p1.setBackground(new Color(240, 248, 255));
 			p1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
 			
 			//RadioButton
 			RadioButtonAL = new JRadioButton("ArrayList", false);
 			RadioButtonAL.setFont(new Font(FONT2, Font.PLAIN, 14));
 			gruppo1.add(RadioButtonAL);
 			RadioButtonAL.setBackground(new Color(COLOR1));
 			p1.add(RadioButtonAL);
 			
 			RadioButtonLL = new JRadioButton("LinkedList", false);
 			RadioButtonLL.setFont(new Font(FONT2, Font.PLAIN, 14));
 			gruppo1.add(RadioButtonLL);
 			RadioButtonLL.setBackground(new Color(COLOR1));
 			p1.add(RadioButtonLL);
 			
 			RadioButtonSET = new JRadioButton("Set", false);
 			RadioButtonSET.setFont(new Font(FONT2, Font.PLAIN, 14));
 			gruppo1.add(RadioButtonSET);
 			RadioButtonSET.setBackground(new Color(COLOR1));
 			p1.add(RadioButtonSET);
 			
 			RadioButtonMAP = new JRadioButton("Map", true);
 			RadioButtonMAP.setFont(new Font(FONT2, Font.PLAIN, 14));
 			gruppo1.add(RadioButtonMAP);
 			RadioButtonMAP.setBackground(new Color(COLOR1));
 			p1.add(RadioButtonMAP);
 			
 			RadioButtonLCO = new JRadioButton("ListaConcatenataOrdinata", false);
 			RadioButtonLCO.setFont(new Font(FONT2, Font.PLAIN, 14));
 			gruppo1.add(RadioButtonLCO);
 			RadioButtonLCO.setBackground(new Color(COLOR1));
 			p1.add(RadioButtonLCO);
 			
 			RadioButtonABR = new JRadioButton("AlberoBinario", false);
 			RadioButtonABR.setFont(new Font(FONT2, Font.PLAIN, 14));
 			gruppo1.add(RadioButtonABR);
 			RadioButtonABR.setBackground(new Color(COLOR1));
 			p1.add(RadioButtonABR);
 		
 			
 			conferma.addActionListener(this);
 			RadioButtonAL.addActionListener(this);
 			RadioButtonLL.addActionListener(this);
 			RadioButtonSET.addActionListener(this);
 			RadioButtonMAP.addActionListener(this);
 			RadioButtonLCO.addActionListener(this);
 			RadioButtonABR.addActionListener(this);
 			
 			
 		}//RadioButtonFrame


 		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == RadioButtonAL) {
				impl = " AL ";
				tipo = Tipo.PolinomioArrayList;
				FrameGUI.this.setTitle(titolo+impl);
			}
			
			else if(e.getSource() == RadioButtonLL) {
				impl = " LL ";
				tipo = Tipo.PolinomioLinkedList;
				FrameGUI.this.setTitle(titolo+impl);
			}
			
			else if(e.getSource() == RadioButtonSET) {
				impl = " SS ";
				tipo = Tipo.PolinomioSet;
				FrameGUI.this.setTitle(titolo+impl);
			}
			
			else if(e.getSource() == RadioButtonMAP) {
				impl = " MAP ";
				tipo = Tipo.PolinomioMap;
				FrameGUI.this.setTitle(titolo+impl);
			}
			
			else if(e.getSource() == RadioButtonLCO) {
				impl = "LCO";
				tipo = Tipo.PolinomioListaConcatenataOrdinta;
				FrameGUI.this.setTitle(titolo+impl);
			}
			else if(e.getSource() == RadioButtonABR) {
				impl = "ABR";
				tipo = Tipo.PolinomioABR;
				FrameGUI.this.setTitle(titolo+impl);
			}
			
			if(e.getSource() == conferma) {
				
				if( tipo==Tipo.PolinomioArrayList ) {
	        		polinomio =new PolinomioAL(capacita);
	        		if( fAL==null ) fAL=new FrameAL();
		  			 fAL.setVisible(true);}
				
	        	 else if( tipo==Tipo.PolinomioLinkedList ) 
	        		polinomio =new PolinomioLL();
	        	 else if( tipo==Tipo.PolinomioSet ) 
	        		 polinomio =new PolinomioSet();
	        	 else if(tipo == Tipo.PolinomioListaConcatenataOrdinta)
	        		 polinomio = new PolinomioLCO();
	        	 else if(tipo == Tipo.PolinomioABR)
	        		 polinomio = new PolinomioABR();
	        	 else 
	        		polinomio=new PolinomioMap();
				
				 FrameGUI.this.setTitle(titolo+impl);
				 
				 if(tipo!=Tipo.PolinomioArrayList)
					 FrameGUI.this.setVisible(true); //se ne occuperà direttamente FrameAL
				 
				 dispose();	 
			}
			
		}//actionPerformed
 		
 		
 	}//RadioButtonFrame
 	
 	//INSERIMENTO CAPACITA' CREAZIONE ARRAY
 	 private class FrameAL extends JFrame implements ActionListener{
 		 
    	 private JTextField capacita;
    	 private JButton ok;
    	 public FrameAL(){
    		 setTitle("Capacita' Array List");
    		 setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	     JPanel p=new JPanel();
    	     p.setLayout( new FlowLayout() ); //ridondante
    	     p.add( new JLabel("Capacita'", JLabel.RIGHT) );
    	     p.add( capacita=new JTextField(""+FrameGUI.this.capacita,12) );
    	     p.add( ok=new JButton("OK") );
    	     p.setBackground(new Color(230, 230, 250));
    	     add(p);	 
    		 capacita.addActionListener(this);
    		 ok.addActionListener(this);
    		 setBounds(300, 300, 400, 100);
    	 }
    	 public void actionPerformed( ActionEvent e ){
    		 if( e.getSource()==capacita ){
    			 FrameGUI.this.capacita=Integer.parseInt(capacita.getText() );
    		 }
    		 else if( e.getSource()==ok ){   		 			 
    			 this.dispose();
    			 FrameGUI.this.setVisible(true);
    		 }
    	 }//actionPerformed
    	 
     }//FrameAL

 	private boolean consensoUscita() {
 		
 		  int option=JOptionPane.showConfirmDialog(
 				   null, "Continuare?", "L'uscita comporterà la perdita dei dati",
 				   JOptionPane.YES_NO_OPTION);
 		  
 		   return option==JOptionPane.YES_OPTION;
 		   
 	}//consenso uscita
 	
 	//ASCOLTATORE EVENTI AZIONE
 	
 	private class FrameInserisciPolinomio extends JFrame implements ActionListener{
 		
 		private JTextField textField;
 		private JButton aggiungiMonomio, fine;
 		private String monomio;
 		private JTextArea testo1;
 		private Monomio m;
 		
 		//si utilizza il polinomio dichiarato globalmente per creare altri polinomi vuoti, diversi, attraverso il metodo factory, dello stesso tipo
 		
 		//L'idea è quello di usare un oggetto polinomio, creato con il metodo factory a partire dalla variabile globale polinomio
 		//in modo tale che di volta in volta l'oggetto venga sovrascritto e i polinomi
 		//verranno ripresi dalle checkbox convertendo il loro testo in polinomi
 		//in questo modo anche se si implementano tantissimi polinomi, si lavorerà con 1 o 2 oggetti alla volta, a seconda dell'operazione binaria o unaria che sia, creati al momento, in base alla scelta
 		
 		private Polinomio p = polinomio.factory();
 		
 		public FrameInserisciPolinomio() {
 			
 			setTitle("Inserimento Polinomio");
 			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
 			
 			addWindowListener(new WindowAdapter(){
 				
 				public void windowClosing(WindowEvent e) {
 					
 					if(p.size() != 0) {//se chiudiamo la finestra, ma abbiamo già costruito il polinomio, lo inseriamo lo stesso
 						gestioneCheckbox(p);
 					}
 					
 					dispose();
 					FrameGUI.this.setVisible(true);//forza l'aggiornamento del frame e quindi del pannello scorrevole con le checkbox
	 				testo1.setText("");
	 				p = polinomio.factory();
	 				textField.setText("");
	 				
 				}
 				
 				
 			});
 		
 			setBounds(100, 100, 450, 300);
 			getContentPane().setLayout(null);
 			
 			JPanel pannello1 = new JPanel();
 			pannello1.setBackground(Color.ORANGE);
 			pannello1.setBounds(48, 84, 300, 35);
 			getContentPane().add(pannello1);
 			pannello1.setLayout(new GridLayout(1, 0, 0, 0));
 			
 			JLabel label1 = new JLabel("Polinomio Corrente");
 			label1.setHorizontalAlignment(SwingConstants.RIGHT);
 			label1.setFont(new Font(FONT5, Font.BOLD, 13));
 			pannello1.add(label1);
 			
 			testo1 = new JTextArea();
 			testo1.setEditable(false);
 			testo1.setTabSize(1);
 			testo1.setRows(1);
 			pannello1.add(testo1);
 			
 			JPanel pannello2 = new JPanel();
 			pannello2.setBackground(Color.ORANGE);
 			pannello2.setBounds(48, 130, 300, 35);
 			getContentPane().add(pannello2);
 			pannello2.setLayout(new GridLayout(1, 0, 0, 0));
 			
 			JLabel label2 = new JLabel("Monomio");
 			label2.setHorizontalAlignment(SwingConstants.RIGHT);
 			label2.setFont(new Font(FONT5, Font.BOLD, 13));
 			pannello2.add(label2);
 			
 			textField = new JTextField();
 			pannello2.add(textField);
 			textField.setColumns(10);
 			
 			fine = new JButton("Fine");
 			fine.setBounds(161, 216, 89, 23);
 			getContentPane().add(fine);
 			
 			aggiungiMonomio = new JButton("Aggiungi Monomio");
 			aggiungiMonomio.setForeground(Color.BLACK);
 			aggiungiMonomio.setBounds(133, 182, 151, 23);
 			getContentPane().add(aggiungiMonomio);
 			
 			Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
 			testo1.setBorder(border);
 			label1.setBorder(border);
 			label2.setBorder(border);
 		
 			getContentPane().setBackground(new Color(240, 255, 240));
 			
 			//se il polinomio diviene troppo lungo, vi è la possibilità di scorrere sulla casella
 			JScrollPane scrollPane = new JScrollPane();
 			pannello1.add(scrollPane);
 			scrollPane.setViewportView(testo1);
 			
 			JLabel label3 = new JLabel("Aggiungere un monomio alla volta");
 			label3.setBounds(100, 39, 248, 14);
 			getContentPane().add(label3);
 			
 			textField.addActionListener(this);
 			aggiungiMonomio.addActionListener(this);
 			fine.addActionListener(this);		
 		
 	}//FrameInserisciPolinomio

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == aggiungiMonomio) {
				
				monomio = textField.getText();
				if(!monomioValido(monomio)) {
					JOptionPane.showMessageDialog(null, "Dato non corretto!");
				}
				else {
					
					m = isolaMonomio(monomio);
					p.add(m);
					testo1.setText(p.toString());
				
				}
				textField.setText("");
				
			}
			
			else if(e.getSource() == fine) {
				
				if(p.size()!=0) {
					
					gestioneCheckbox(p);

				}
				
				
				dispose();
				FrameGUI.this.setVisible(true);//forza l'aggiornamento del frame e quindi del pannello scorrevole con le checkbox
				testo1.setText("");
				p = polinomio.factory();
					 
			}
			
			
		}//actionPermormed
 		
 	}//FrameInserisciPolinomio
 	
 	private static void gestioneCheckbox(Polinomio p) {//questo metodo si occupa di creare la nuova checkbox, aggiornare l'elenco delle checkbox, inserirle nel pannello, e gestire la selezione di esse
 		
 		
 		JCheckBox box = new JCheckBox(p.toString());
		elencoCheckbox.put(numeroCheckbox, box);
		
		box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(e.getSource() == box) {//la casella è stata spuntata
					
					
					if(box.isSelected())
						selezionate++;
					else//se si clicca su una casella e questa risulta non selezionata, significa che è stata appena deselezionata
						selezionate--;
				

				if(selezionate > 2) {
					JOptionPane.showMessageDialog(null, "Selezionare al massimo 2 polinomi");
					box.setSelected(false);
					selezionate--;
				}
		

				}
			}
		});
		
		
		pannelloCheckbox.add(elencoCheckbox.get(numeroCheckbox));
		
		numeroCheckbox++;
		
 		
 	}//gestioneCheckbox

 	private class FrameInserisciPolinomioDiretto extends JFrame implements ActionListener{
 		
 		private Polinomio p = polinomio.factory();
 		private JTextField inserimento;
 		private JButton conferma;
 		private String polinomioString;
 		
 		public FrameInserisciPolinomioDiretto() {
 			
 			setTitle("Inserimento Rapido Polinomio");

			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			 			
			 			addWindowListener(new WindowAdapter(){
			 				
			 				public void windowClosing(WindowEvent e) {//se si chiude la finestra ma era stato inserito, nella casella di testo, il polinomio, se la stringa è valida, viene inserito lo stesso
			 					
			 					polinomioString = inserimento.getText();
			 					
			 					if(!polinomioString.equals("")) {
			 						
				 					if(polinomioValido(polinomioString)) {
				 						
				 						//se il polinomio è corretto, lo dividiamo in stringhe di monomi, che trasformiamo in oggetti monomi e aggiungiamo al polinomio
				 						p = conversioneStringPolinomio(polinomioString);
				 						
				 						if(p.size() != 0) {//il polinomio potrebbe anche semplificarsi ed essere 0
				 	 						gestioneCheckbox(p);
				 	 						JOptionPane.showMessageDialog(null, "Polinomio Inserito");
				 	 	 					
				 	 					}
				 						else {
				 							JOptionPane.showMessageDialog(null, "Polinomio Nullo");
				 						}
				 						
				 						 FrameGUI.this.setVisible(true);//forza l'aggiornamento del frame e quindi del pannello scorrevole con le checkbox
				 		 				 inserimento.setText("");
				 		 				 p = polinomio.factory();
				 		 		       	
				 						}
			 					}
			 					
			 					 dispose();
			 					
			 					
			 				}
			 			
			 			});
 			
 			setBounds(100, 100, 450, 300);
 			getContentPane().setLayout(null);
 			
 			JPanel pannello1 = new JPanel();
 			pannello1.setBackground(Color.ORANGE);
 			pannello1.setBounds(48, 84, 300, 35);
 			getContentPane().add(pannello1);
 			pannello1.setLayout(new GridLayout(1, 0, 0, 0));
 			
 			JLabel label = new JLabel("Polinomio ");
 			label.setEnabled(false);
 			label.setFont(new Font(FONT5, Font.BOLD, 11));
 			label.setHorizontalAlignment(SwingConstants.CENTER);
 			pannello1.add(label);
 			
 			conferma = new JButton("Aggiungi Polinomio");
 			conferma.setForeground(Color.BLACK);
 			conferma.setBounds(133, 182, 151, 23);
 			getContentPane().add(conferma);
 			
 			Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
 			label.setBorder(border);
 		
 			getContentPane().setBackground(new Color(240, 255, 240));
 			
 			JLabel label3 = new JLabel("Digitare il polinomio da inserire");
 			label3.setFont(new Font(FONT5, Font.BOLD, 13));
 			label3.setBounds(111, 49, 248, 14);
 			getContentPane().add(label3);
 			
 			JScrollPane scrollPane = new JScrollPane();
 			scrollPane.setBounds(48, 130, 300, 33);
 			getContentPane().add(scrollPane);
 			
 			inserimento = new JTextField();
 			scrollPane.setViewportView(inserimento);
 			inserimento.setBorder(border);
 			
 			inserimento.addActionListener(this);
 			conferma.addActionListener(this);
 			
 		
 		}//InserisciPolinomioDiretto

		@Override
		public void actionPerformed(ActionEvent e) {
			

			if(e.getSource() == conferma) {
				
				polinomioString = inserimento.getText();
				
				if(!polinomioValido(polinomioString)) {
					JOptionPane.showMessageDialog(null, "Dato non corretto!");	
				}
				
				else {
					
					//se il polinomio è corretto, lo dividiamo in stringhe di monomi, che trasformiamo in oggetti monomi e aggiungiamo al polinomio
					p = conversioneStringPolinomio(polinomioString);
					
					if(p.size() != 0) {//il polinomio potrebbe anche semplificarsi ed essere 0
 						gestioneCheckbox(p);
 						JOptionPane.showMessageDialog(null, "Polinomio Inserito");
 	 					
 					}
					else {
						JOptionPane.showMessageDialog(null, "Polinomio Nullo");
					}
					
					 FrameGUI.this.setVisible(true);//forza l'aggiornamento del frame e quindi del pannello scorrevole con le checkbox
	 				 inserimento.setText("");
	 				 p = polinomio.factory();
	 		       	 dispose();
				
 				
				}
					  
			}//conferma
		
		}//actionPerformed
 		
 	}//InserisciPolinomioDiretto
 	

 	private class FrameValuta extends JFrame implements ActionListener {
 		
 		
 		JTextField input;
 		Polinomio p = polinomio.factory();
 		String x;
 		JButton valuta;
 	
 		
 		public FrameValuta(JCheckBox element) {
 			
 			p = conversioneStringPolinomio(element.getText());
 			

 			setTitle("Valuta Polinomio");
 			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
 			
 			addWindowListener(new WindowAdapter(){
 				
 				public void windowClosing(WindowEvent e) {
 			
 					FrameGUI.this.setVisible(true);
 					dispose();
 				
 				}
 			
 			});
 			
 			setBounds(100, 100, 437, 252);
 			
 			
 			getContentPane().setBackground(new Color(240, 255, 240));
 			getContentPane().setLayout(null);
 			
 			JTextArea testo1 = new JTextArea();
 			testo1.setBounds(170, 35, 98, 22);
 			testo1.setEditable(false);
 			testo1.setBackground(new Color(250, 250, 210));
 			testo1.setRows(1);
 			testo1.setText("Valuta Polinomio");
 			testo1.setFont(new Font(FONT5, Font.BOLD, 13));
 			getContentPane().add(testo1);
 			
 			valuta = new JButton("Valuta");
 			valuta.setBounds(119, 165, 196, 23);
 			getContentPane().add(valuta);
 			valuta.setFont(new Font(FONT5, Font.BOLD, 13));
 			
 			input = new JTextField();
 			input.setBounds(149, 119, 196, 20);
 			getContentPane().add(input);
 			input.setColumns(10);
 			input.setFont(new Font(FONT5, Font.BOLD, 13));
 		
 			
 		
 			
 			JLabel label2 = new JLabel("X");
 			label2.setHorizontalAlignment(SwingConstants.RIGHT);
 			label2.setBounds(93, 122, 46, 14);
 			getContentPane().add(label2);
 			label2.setFont(new Font(FONT5, Font.BOLD, 13));
 			
 			JTextField testo2 = new JTextField();
 			testo2.setEditable(false);
 			testo2.setBounds(149, 77, 196, 20);
 			getContentPane().add(testo2);
 			testo2.setColumns(10);
 			testo2.setFont(new Font(FONT5, Font.BOLD, 13));
 			testo2.setText(element.getText());
 			
 			
 			JLabel label1 = new JLabel("Polinomio");
 			label1.setHorizontalAlignment(SwingConstants.RIGHT);
 			label1.setBounds(30, 80, 109, 14);
 			getContentPane().add(label1);
 			label1.setFont(new Font(FONT5, Font.BOLD, 13));
 			
 			
 			
 			valuta.addActionListener(this);
 			
 			
 		}//FrameValuta

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == valuta) {
			
				x = input.getText();
				
				if(numeroRealeValido(x)){
					
					Double result = p.valore(Double.parseDouble(x));
					String rounded = String.format("%.3f", result);
					
					console.setText(p.toString() + " per x=" + x + " = ");
					console.setText(console.getText() + rounded);
					
					
					FrameGUI.this.setVisible(true);
					dispose();
					
					
				}

				else {
					JOptionPane.showMessageDialog(null,  "Input non valido");
					input.setText("");
				}
				
			}
			
			
		}//actionPerformed
 		
 		
 		
 	}//FrameValuta

 	private class FrameCambiaPolinomio extends JFrame implements ActionListener{

 		JTextField areaModifica;
 		JButton sostituisci;
 		JCheckBox base = null;
 		Polinomio p = polinomio.factory();
 		
 		
 		public FrameCambiaPolinomio(JCheckBox element) {//passo la JCheckbox e non il polinomio, poiché voglio apportare modifiche alla CheckBox, il cui contenuto è allocato dinamicamente
 			
 			base = element;
 			
 		
 			
 			setTitle("Modifica Polinomio");

 			
 			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
 			
 			addWindowListener(new WindowAdapter(){
 				
 				public void windowClosing(WindowEvent e) {
 					
 					JOptionPane.showMessageDialog(null, "Nessuna modifica effettuata");
 					FrameGUI.this.setVisible(true);
 					dispose();
 				
 				}
 			
 			});
 			
 			setBounds(100, 100, 450, 300);
 		
 			
 			getContentPane().setBackground(new Color(240, 255, 240));
 			getContentPane().setLayout(null);
 			
 			JTextArea testo1 = new JTextArea();
 			testo1.setFont(new Font(FONT5, Font.BOLD, 13));
 			testo1.setBounds(160, 98, 125, 22);
 		
 			testo1.setEditable(false);
 			testo1.setBackground(new Color(250, 250, 210));
 			testo1.setRows(1);
 			testo1.setText("Modifica Polinomio");
 			getContentPane().add(testo1);
 			
 			JPanel pannello = new JPanel();
 			pannello.setBounds(83, 155, 263, 47);
 			getContentPane().add(pannello);
 			pannello.setLayout(new GridLayout(0, 1, 0, 0));
 			
 			areaModifica = new JTextField();
 			areaModifica.setText(base.getText());
 			pannello.add(areaModifica);
 			areaModifica.setColumns(10);
 			
 			sostituisci = new JButton("Sostituisci");
 			sostituisci.setBounds(165, 207, 100, 23);
 			getContentPane().add(sostituisci);
 			
 			
 			
 			sostituisci.addActionListener(this);
 			
 		}//FrameCambiaPolinomio
 		
 		
 		
		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource() == sostituisci) {
				
				if(polinomioValido(areaModifica.getText())) {
					
					
					if(!base.getText().equals(areaModifica.getText())) {
						
						p = conversioneStringPolinomio(areaModifica.getText());//ci garantisce che il polinomio venga riordinato e sistemato
						
						
						base.setText(p.toString());
						JOptionPane.showMessageDialog(null, "Modificato con successo");
					}
						
					else {//non è stato alterato il polinomio
						JOptionPane.showMessageDialog(null, "Nessuna modifica effettuata");
					}
					
					FrameGUI.this.setVisible(true);
					dispose();
					
					}
					
				
				else {
					
					JOptionPane.showMessageDialog(null, "Input non valido");
					
				}
			}
				
				
			
		}//actionPerformed
 		
 		
 	}//FrameCambiaPolinomio
 	


 	
 	//data una stringa contenente un polinomio (scritto correttamente poiché validato precedentemente con il metodo polinomioValido) crea il Polinomio corrispondente 
 	private static Polinomio conversioneStringPolinomio(String polinomioString) {
 		
 		//dividiamo la stringa del polinomio, in tante sottostringhe di monomi, attraverso il metodo isolaMonomi, creiamo il monomi, e lo aggiungiamo al polinomio.
 		
 		Polinomio p = polinomio.factory();
 		Monomio m;
 		
 		
 		StringTokenizer st = new StringTokenizer(polinomioString, "+-", true);//gli operatori ci servono
 		
 
 		StringBuilder MonomioString = new StringBuilder();
 		String primo = st.nextToken();
 		
 		if(primo.equals("+")||primo.equals("-")){
 			MonomioString.append(primo);
 			MonomioString.append(st.nextToken());
 		}
 		else
 			MonomioString.append(primo);
 		
 		m = isolaMonomio(MonomioString.toString());
 		p.add(m);
 		
 
 		
 		while(st.hasMoreTokens()) {
 			StringBuilder monomioString2= new StringBuilder();
 			
 			monomioString2.append(st.nextToken());
 			monomioString2.append(st.nextToken());
 			
 			m = isolaMonomio(monomioString2.toString());
 	 		p.add(m);
 	 		monomioString2.delete(0, monomioString2.length());
 	 		
 		}
 		
 		return p;
 		
 	}//conversioneStringPolinomio

 	
 	
	private static void elimina() {
	 		
	 		Iterator<JCheckBox> it = elencoCheckbox.values().iterator();
				
				
				while(it.hasNext()) {
					
					JCheckBox element = it.next();
					
					if(element.isSelected()) {
						pannelloCheckbox.remove(element);
						it.remove();
						numeroCheckbox--;
						selezionate--;
						
					}
						
				}
			
				pannelloCheckbox.updateUI();
				
	 	}//elimina
	
	private static void svuota() {
		
		Iterator<JCheckBox> it = elencoCheckbox.values().iterator();
		selezionate = 0;
		numeroCheckbox = 0;
		console.setText("");
		
		while(it.hasNext()) {
			
			JCheckBox element = it.next();
			pannelloCheckbox.remove(element);
			it.remove();

		}
				
		pannelloCheckbox.updateUI();
		
	}//svuota
	
	private static void salvataggio(String nomeFile) throws IOException {
		
		PrintWriter pw = new PrintWriter (new FileWriter(nomeFile));
		
		Iterator<JCheckBox> it = elencoCheckbox.values().iterator();
		
		
		while(it.hasNext()) {
			
			JCheckBox element = it.next();
			pw.println(element.getText());
			
		}
		
		pw.close();
		
	}//salvataggio
	
	private static void ripristino(String nomeFile) throws IOException{
		
		BufferedReader br=new BufferedReader( new FileReader(nomeFile) );
		boolean successoLettura=true;
		
		ArrayList<String> backup=new ArrayList<>();//conviene fare un backup da tenere fino a quando non siamo sicuri che la lettura avvenga con successo
		
		
		Polinomio p = polinomio.factory();
		
		try {
			
			for(;;) {//ciclo infinito la cui fine è gestita all'interno
				
				String linea=br.readLine();//legge una linea
				
				if( linea==null ) break;//file terminato
				
				backup.add(linea);

			}
			
		}catch( IOException e ) {
			
			System.out.println("Errore rilevato nella procedura di ripristino!");
			successoLettura=false;
			
		}finally {
			br.close();
			
		}
		
		if( successoLettura ) {
			
			svuota();
			for(String linea: backup) {
				
				p = conversioneStringPolinomio(linea);//trasformiamo la linea in polinomio che aggiungiamo come checkbox
				gestioneCheckbox(p);
				
				
			}
		}
	
		
	}//ripristino
	
	
 	
 	
 	
 	private class AscoltatoreEventiAzione implements ActionListener {
 		
 		 public void actionPerformed(ActionEvent e){
 			 
 			 
 	  		 //Menu File
 			 
 			 if( e.getSource()==esci ){
 	  			   if( consensoUscita() ) System.exit(0);
 	  		   }
 			   else if( e.getSource()==about ){
 	  			   JOptionPane.showMessageDialog( null,
 	  					   "Calcolatrice Polinomi\n",
 	  					   "About", JOptionPane.PLAIN_MESSAGE );
 	  		   }
 	   		 
 			 //Salvataggio/ripristino
 			 
 			 else if(e.getSource() == salva) {
 				 
 				   JFileChooser chooser=new JFileChooser("src");

 				   
 				   chooser.setFileFilter(filtro);
 				   
 				   
 	  			   try{
 	  			
 	  				   
 	  				   if( fileDiSalvataggio!=null ){

 	  					 int ans=JOptionPane.showConfirmDialog(null,"Sovrascrivere? " + fileDiSalvataggio.getAbsolutePath() + " ? ", "L'operazione non potrà essere annullata", JOptionPane.YES_NO_OPTION);
 	  		 			 
 	  					   
 						   if( ans==0)
 							   
 							   salvataggio(fileDiSalvataggio.getAbsolutePath());
 						   
 						   else
 							   
 							   JOptionPane.showMessageDialog(null,"Nessun salvataggio!");
 						   
 						   return;
 						   
 					   }
 	  				   //salvataggio di un nuovo file
 	  				   if( chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION ){
 	  					   fileDiSalvataggio=chooser.getSelectedFile();
 	  					   System.out.println(chooser.getSelectedFile());
 	  					   FrameGUI.this.setTitle(titolo+impl+" - "+fileDiSalvataggio.getName());
 	  					   
 	  				   }
 	  				   if( fileDiSalvataggio!=null ){
 	  					   
 	  					   System.out.println(fileDiSalvataggio.getAbsolutePath());
 	  					   salvataggio(fileDiSalvataggio.getAbsolutePath() );}
 	  				   
 	  				   else
 	  					   JOptionPane.showMessageDialog(null,"Nessun Salvataggio!");
 	  				   
 	  			   }catch( Exception exc ){
 	  				   
 	  				   exc.printStackTrace();//permette rintracciare l'eccezione, ovvero di indentificare qual è il metodo che determina il bug
 	  			   }
 				 
 
 			 }//salva
 			 
 			else if( e.getSource()==salvaConNome ){
   			   
   			   //file chooser
   			   JFileChooser chooser=new JFileChooser("src");
   	
			   chooser.setFileFilter(filtro);
			   
   			   try{
   				   if( chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION ){
   						   fileDiSalvataggio=chooser.getSelectedFile();
   						   FrameGUI.this.setTitle(titolo+impl+" - "+fileDiSalvataggio.getName());
   					   }
   				   if( fileDiSalvataggio!=null ){
   					   salvataggio(fileDiSalvataggio.getAbsolutePath());
   				   }
   				   else
   					   JOptionPane.showMessageDialog(null,"Nessun Salvataggio!");
   				   
   			   }catch( Exception exc ){
   				   
   				   exc.printStackTrace();
   			   }  	
   			   
   		   }//salvaConNome
 			 
 			 else if( e.getSource()==apri ){
 	  			   //file chooser
 	  			   JFileChooser chooser=new JFileChooser("src");
				   
				   chooser.setFileFilter(filtro);
				   
 	  			   try{
 	  				   if( chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION ){
 	  					   if( !chooser.getSelectedFile().exists() ){
 	  						   JOptionPane.showMessageDialog(null,"File inesistente!"); 
 	  					   }
 	  					   else{	
 	  						   fileDiSalvataggio=chooser.getSelectedFile();
 	  						   FrameGUI.this.setTitle(titolo+impl+" - "+fileDiSalvataggio.getName());
 	  						   try{
 	  							   ripristino( fileDiSalvataggio.getAbsolutePath());
 	  						   }catch(IOException ioe){
 	  							   JOptionPane.showMessageDialog(null,"File corrotto!");
 	  						   }
 	  					   }
 	  				   }
 	  				   else
 	  					   JOptionPane.showMessageDialog(null,"Nessuna apertura!");
 	  				   
 	  			   }catch( Exception exc ){
 	  				   
 	  				 JOptionPane.showMessageDialog(null,"File errato!");
 	  			   }
 	  			   
 	  		   }//apri
 			 
			 
			 //Menu Modifica

	 		 else if(e.getSource() == inserisci) {
	 			 
	 			 if(frameIP == null) frameIP = new FrameInserisciPolinomio();
	 			 frameIP.setVisible(true);
	 			 
	 		 }
 			 
	 		 else if(e.getSource() == inserisciDiretto) {
	 			 
	 			 if(frameIPD == null) frameIPD = new FrameInserisciPolinomioDiretto();
	 			 
	 			 frameIPD.setVisible(true);
	 		 }
 			 
	 		 else if(e.getSource() == elimina) {
	 			
	 			elimina();
	 			FrameGUI.this.setVisible(true);
	 		
	 		 }
	 		 
	 		 else if(e.getSource() == svuota) {
	 			 
	 			 int ans=JOptionPane.showConfirmDialog(null,"Cancellare tutti i polinomi?", "L'operazione non potrà essere annullata", JOptionPane.YES_NO_OPTION);
	 			 
	 			 if(ans == 0 )
	 				 svuota();
	 			 else 
	 				JOptionPane.showMessageDialog(null,"Nessuna eliminazione!");
	 			 
	 			 FrameGUI.this.setVisible(true);
	 			 
	 			 
	 		 }
 			 
 			 //Menu Operazioni
			 
				 //Unarie
				 //Binarie
 			 
 			 //Unarie
	 		 else if( e.getSource() == derivata || e.getSource() == valuta ||e.getSource() == cambia) {
	 			 
	 			if(selezionate != 1)
	 				JOptionPane.showMessageDialog(null, "Selezionare un solo polinomio");
	 			
	 			else {
	 				
	
	 				
	 				Polinomio p = polinomio.factory();
	 				String stringaPolinomio = null;
	 				
	 				Iterator<JCheckBox> it = elencoCheckbox.values().iterator();
	 				JCheckBox element = null;
	 				
	 				while(it.hasNext()) {
		 		
		 				 element = it.next();
		 				
		 				if(element.isSelected()) {
		 					stringaPolinomio = element.getText();
		 					break;
		 				}
		 					
		 			}
	 				
	 				
	 				//element conterrà la checkbox selezionata, p invece conterrà il polinomio
	 				p = conversioneStringPolinomio(stringaPolinomio);
	 				
	 				if(e.getSource() == cambia) {
	 				
	 					//dobbiamo modificare il contenuto della checkbox attenendoci alla traccia data(contenuto checkbox dinamico)
	 					
	 					 frameCP = new FrameCambiaPolinomio(element);
	 		 			 frameCP.setVisible(true);
	 		 			 
	 				}
	 				
	 				if(e.getSource() == valuta) {
	 					
	 					 frameVL = new FrameValuta(element);
	 		 			 frameVL.setVisible(true);
	 		 			 
	 				}
	 				
	 				if(e.getSource() == derivata) {
	 					
	 					Polinomio risDer = polinomio.factory();
	 					risDer = p.derivata();
	 					
	 					console.setText("( " + p.toString() + " )' = ");
	 					
	 					if(risDer.toString().equals(""))//se la derivata è 0 non aggiungiamo niente
	 					{
	 						console.setText(console.getText() + "0");
	 					}
	 					else {
	 						console.setText(console.getText() + risDer.toString());
		 			 		gestioneCheckbox(risDer);
	 					}
	 					
	 			 		
	 			 		FrameGUI.this.setVisible(true);
	 					
	 				}
	 			 
	 			}
	 			 
	 		 }//fine unarie
 			 
 			 
 			 //Binarie
	 		 else if(e.getSource() == addizione || e.getSource() == moltiplicazione) {
	 			 
	 			if(selezionate != 2)
	 				JOptionPane.showMessageDialog(null, "Selezionare esattamente 2 polinomi");
	 			
	 			else {
	 				
	 				//dobbiamo prendere i due polinomi selezioanti
	 				
	 				
	 				Polinomio p1 = polinomio.factory();
	 				Polinomio p2 = polinomio.factory();
	 				
	 				//siamo sicuri del fatto di trovare due checkbox selezionate
	 				String [] stringaPolinomio = new String[2];
	 				
	 				
	 				Iterator<JCheckBox> it = elencoCheckbox.values().iterator();
	 				JCheckBox element = null;
	 				
	 				int count = 0;
	 				
	 				while(it.hasNext()) {
	 					
		 				element = it.next();
		 				
		 				if(element.isSelected()) {
		 					stringaPolinomio[count] = element.getText();
		 					count++;
		 				}
		 					
		 			}
	 				
	 				p1 = conversioneStringPolinomio(stringaPolinomio[0]);
	 				p2 = conversioneStringPolinomio(stringaPolinomio[1]);

	 				if(e.getSource() == addizione) {
	 					
	 					Polinomio risAdd = polinomio.factory();
	 					risAdd = p1.add(p2);
		 				
	 					console.setText("( " + p1.toString() + " ) " + " + " + " ( " + p2.toString() + " )" + " = ");
	 					
	 					if(risAdd.toString().equals("")) {//se la somma è 0 non aggiungiamo niente
	 						console.setText(console.getText() + "0");
	 					}
	 						
	 					else {
	 						console.setText(console.getText() + risAdd .toString());
	 						gestioneCheckbox(risAdd);
	 						
	 					}
	 					
	 			 		
	 			 		
	 			 		FrameGUI.this.setVisible(true);
	 		 			
	 				}

	 				if(e.getSource() == moltiplicazione) {
	 					
	 					Polinomio risMul = polinomio.factory();
	 					risMul = p1.mul(p2);
	 					
	 					console.setText("( " + p1.toString() + " ) " + " * " + " ( " + p2.toString() + " )" + " = ");
	 					
	 					if(risMul.toString().equals("")) {
	 						console.setText(console.getText() + "0");
	 					}
	 						
	 					else {
		 					console.setText(console.getText() +  risMul.toString());
		 			 		gestioneCheckbox(risMul);
	 					}
	 			 		
	 			 		FrameGUI.this.setVisible(true);
	 					
	 				}
	 			
	 				
	 			}
	 			 
	 		 }//fine binarie
 			 
 			
 			 
 		 }//actionPerformed
 			 
 		 
 		 
 	}//AscoltatoreEventiAzione
	
}//FrameGUI

public class PolinomioGUI {
	
		public static void main(String[] args) {
			
			 EventQueue.invokeLater( new Runnable(){
				 
				  public void run(){
					  
					  try {
				      @SuppressWarnings("unused")
					  JFrame f=new FrameGUI();  
				      
					  }catch(Exception e) {
						  e.printStackTrace();
					  }
					  
				  }
			  });	
			 
		}
	
}//PolinomiGUI
