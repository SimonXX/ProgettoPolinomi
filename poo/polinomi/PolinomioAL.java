package poo.polinomi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


public class PolinomioAL extends PolinomioAstratto{

	
	private ArrayList<Monomio> lista;
	
	public PolinomioAL() {
		lista = new ArrayList<>();
	}
	
	public PolinomioAL( int capacita ) {
		if( capacita<1 ) throw new IllegalArgumentException();
		
		lista=new ArrayList<>(capacita);
	}
	
	@Override
	public void add(Monomio m) {
		
		int i=Collections.binarySearch( lista, m );
		
		if( i>=0 ) {
			
			Monomio mc = lista.get(i);
			
			Monomio ms = mc.add(m);
			
			if(ms.getCoeff() == 0)
				lista.remove(i);
			
			else
				lista.set(i, ms);
			
		}
		
		else {
			
			for( int j=0; j<lista.size(); ++j ) {
				
				if( lista.get(j).compareTo(m)>0 ) { 
					lista.add(j,m); return; 	
				}
			}
			lista.add(m);
		}
					
	}//add
	
	public int size() {
		
		return lista.size();
	}

	@Override
	public Polinomio factory() {
		return new PolinomioAL();
	}

	public Iterator<Monomio> iterator(){return lista.iterator();}

}
