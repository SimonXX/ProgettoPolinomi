package poo.polinomi;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class PolinomioMap extends PolinomioAstratto{

	private Map<Monomio, Monomio> map = new TreeMap<>();

	public PolinomioMap factory() { return new PolinomioMap();}
	
	public int size() {return map.size();}
	
	public Iterator<Monomio> iterator(){return map.values().iterator();} 
	public void add(Monomio m) {
		
		if(m.getCoeff() == 0) return;
		
		Monomio m1 = map.get(m);
		if(m1 == null) map.put(m, m);
		
		else {
			
		
			map.remove(m);
			m1 = m1.add(m);
			if(m1.getCoeff() != 0) map.put(m1,  m1);
		}
	}//add

	
}//PolinomioAstratto
