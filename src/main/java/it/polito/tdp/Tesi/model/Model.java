package it.polito.tdp.Tesi.model;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;



public class Model {
	
	public List<Boat> best;
	public int prezzoB;
	public List<Boat> best2;
	public int prezzoB2;
	public List<Boat> parziale;
	public int prezzoP;	


	
	public List<Boat> barche(List<Boat> listaBarche, int budget /*, int lunghezza*/) {
	// inizializzo liste
		best=new ArrayList<>();
		prezzoB=0;
		best2=new ArrayList<>();
		prezzoB2=0;
		parziale=new ArrayList<>();
		prezzoP=0;	
		
		barche_ricorsiva(parziale, budget, listaBarche /*, lunghezza*/ );
		if(this.calcola_guadagno(best)>=this.calcola_guadagno(best2)) {
			best2=new ArrayList<>();
		}
			return best;
//		calprezzo(parziale)==budget
	}
	
	
	private void barche_ricorsiva(List<Boat> parziale, int budget, List<Boat> listaBarche /*int lunghezza*/) {
		if(parziale.size()>0 ) {  //caso terminale
			int disponibile=budget-this.prezzoP;
			int disponibile2=budget+(budget/100)-this.prezzoP;
			//int lunghezza_disponibile=lunghezza-this.calcola_lunghezza(parziale);
			if(this.calcola_guadagno(parziale)>this.calcola_guadagno(best)) { 
				if(disponibile>=0) {
					if((disponibilita(disponibile,listaBarche) /*&& lunghezza_disponibile(lunghezza_disponibile,listaBarche)*/ || disponibile==0 )){
						this.best=new ArrayList<>(parziale);
						return;
					}
				}else if((disponibilita2(disponibile2,listaBarche) /*&& lunghezza_disponibile(lunghezza_disponibile,listaBarche)*/ || disponibile2==0 )){
						if(disponibilita2(disponibile2,listaBarche)) {
							this.best2=new ArrayList<>(parziale);
							return;
						}
				}
			}
		}
		
		for(Boat b:listaBarche) {
			if(budget>=(this.prezzoP+b.prezzo) && !parziale.contains(b) /*&& lunghezza>=(calcola_lunghezza(parziale)+b.lunghezza)*/) {
				parziale.add(b);
				this.prezzoP+=b.prezzo;
				this.barche_ricorsiva(parziale, budget, listaBarche /*,lunghezza*/);
				parziale.remove(b);
				this.prezzoP-=b.prezzo;
			}else if((budget+(budget/100))>=(this.prezzoP+b.prezzo) && !parziale.contains(b) /*&& lunghezza>=(calcola_lunghezza(parziale)+b.lunghezza)*/) {
				parziale.add(b);
				this.prezzoP+=b.prezzo;
				this.barche_ricorsiva(parziale, budget, listaBarche/*,  lunghezza*/);
				parziale.remove(b);
				this.prezzoP-=b.prezzo;
			}
		}
		
	}
	
	
	public int calcola_prezzo(List<Boat> l) {
		int tot=0;
		for(Boat b:l){
			tot+=b.prezzo;
		}
		return tot;
	}
	public int calcola_guadagno(List<Boat> l) {
		int tot=0;
		for(Boat b:l){
			tot+=b.guadagno;
		}
		return tot;
	}
//	public int calcola_lunghezza(List<Boat> l) {
//		int tot=0;
//		for(Boat b:l){
//			tot+=b.lunghezza;
//		}
//		return tot;
//	}
//	
	
	
	public boolean disponibilita(int disponibili,List<Boat> listaBarche) {
		ArrayList<Boat> lb=new ArrayList<>(listaBarche);
		Collections.sort(lb);
		for(Boat b:listaBarche){
			if(b.prezzo<=disponibili && !parziale.contains(b)){	
				return false;
			}
		}
		return true;
	}
	public boolean disponibilita2(int disponib,List<Boat> listaBarche) {
		ArrayList<Boat> lb=new ArrayList<>(listaBarche);
		Collections.sort(lb);
		for(Boat b:listaBarche){
			if(b.prezzo<=disponib && !parziale.contains(b)){	
				return false;
			}
		}
		return true;
	}


	public List<Boat> secondasol() {
		return best2;
	}
	
//	public boolean lunghezza_disponibile(int l ,List<Boat> listaBarche) {
//		ArrayList<Boat> lb=new ArrayList<>(listaBarche);
//		Collections.sort(lb);
//		for(Boat b:listaBarche){
//			if(b.lunghezza<=l && !parziale.contains(b)){	
//				return false;
//			}
//		}
//		return true;
//	}

	
	
	
	
	
}
