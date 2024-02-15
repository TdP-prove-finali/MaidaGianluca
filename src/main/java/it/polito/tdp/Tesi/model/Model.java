package it.polito.tdp.Tesi.model;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;



public class Model {
	
	public List<Boat> best;
	public List<Boat> best2;
	public List<Boat> parziale;
	public int prezzoP;	


	
	public List<Boat> barche(List<Boat> listaBarche, int budget) {
		best=new ArrayList<>();
		best2=new ArrayList<>();
		parziale=new ArrayList<>();
		prezzoP=0;	
		barche_ricorsiva(parziale, budget, listaBarche);
		if(this.calcola_guadagno(best)>=this.calcola_guadagno(best2)) {
			best2=new ArrayList<>();
		}
		return best;
	}
	
	
	private void barche_ricorsiva(List<Boat> parziale, int budget, List<Boat> listaBarche ) {
		if(parziale.size()>0 ) {  //caso terminale
			int disponibile=budget-this.prezzoP;
			int disponibile2=budget+(budget/100)-this.prezzoP;
			if(this.calcola_guadagno(parziale)>this.calcola_guadagno(best)) { 
				if(disponibile>=0) {
					if((disponibilita(disponibile,listaBarche) || disponibile==0 )){
						this.best=new ArrayList<>(parziale);
						return;
					}
				}else if((disponibilita2(disponibile2,listaBarche) || disponibile2==0 )){
					if(disponibilita2(disponibile2,listaBarche)) {
						this.best2=new ArrayList<>(parziale);
						return;
					}
				}
			}
		}
		
		for(Boat b:listaBarche) {
			if(budget>=(this.prezzoP+b.prezzo) && !parziale.contains(b)) {
				parziale.add(b);
				this.prezzoP+=b.prezzo;
				this.barche_ricorsiva(parziale, budget, listaBarche );
				parziale.remove(b);
				this.prezzoP-=b.prezzo;
			}else if((budget+(budget/100))>=(this.prezzoP+b.prezzo) && !parziale.contains(b)) {
				parziale.add(b);
				this.prezzoP+=b.prezzo;
				this.barche_ricorsiva(parziale, budget, listaBarche);
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
	
	
	
	
}
