package poo.polinomi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class PolinomioLL extends PolinomioAstratto{

	private LinkedList<Monomio> lista = new LinkedList<>();
	
	//lasciamo il costruttore di default.
	
	public PolinomioLL factory() {return new PolinomioLL();}
	
	public int size() {return lista.size();}


	public Iterator<Monomio> iterator(){return lista.iterator();}
	
	public void add (Monomio m) {
		
		if(m.getCoeff() == 0) return; 
		
		ListIterator<Monomio> lit = lista.listIterator(); 
		boolean flag = false;
		
		while(lit.hasNext() && !flag) {
			
			Monomio mc = lit.next();
			if(mc.equals(m)){
				
	
				Monomio ms = mc.add(m);
				if(ms.getCoeff() == 0)
					lit.remove();
				
				else lit.set(ms);
				
				flag = true;
				
	
			}
			else if(mc.compareTo(m)>0) {
				lit.previous();
				lit.add(m);
				flag = true;
				
			}
		}
		
		if(!flag) lit.add(m); 
		
	}//add
	
}//PolinomioLL
