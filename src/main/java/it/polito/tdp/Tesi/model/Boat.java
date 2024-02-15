package it.polito.tdp.Tesi.model;


public class Boat implements Comparable<Boat> {

	Integer id;
	Integer prezzo;
	String tipologia;
	String manifattura;
	String condizione;
	Integer anno;
	Double lunghezza;
	Double larghezza;
	String Luogo;
	Integer guadagno;
	
	public Boat(Integer id, Integer prezzo, String tipologia, String manifattura, String condizione, Integer anno,
			Double lunghezza, Double larghezza, String luogo) {
		super();
		this.id = id;
		this.prezzo = prezzo;
		this.tipologia = tipologia;
		this.manifattura = manifattura;
		this.condizione = condizione;
		this.anno = anno;
		this.lunghezza = lunghezza;
		this.larghezza = larghezza;
		Luogo = luogo;
		this.setGuadagno();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Integer prezzo) {
		this.prezzo = prezzo;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getManifattura() {
		return manifattura;
	}

	public void setManifattura(String manifattura) {
		this.manifattura = manifattura;
	}

	public String getCondizione() {
		return condizione;
	}

	public void setCondizione(String condizione) {
		this.condizione = condizione;
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public Double getLarghezza() {
		return larghezza;
	}

	public void setLarghezza(Double larghezza) {
		this.larghezza = larghezza;
	}

	public String getLuogo() {
		return Luogo;
	}

	public void setLuogo(String luogo) {
		Luogo = luogo;
	}
	
	public Double getLunghezza() {
		return lunghezza;
	}

	public void setLunghezza(Double lunghezza) {
		this.lunghezza = lunghezza;
	}

	public Integer getGuadagno() {
		return guadagno;
	}

	public void setGuadagno() {
		//condizione
		if(condizione.equals("Nuovo")) {
			this.guadagno=(prezzo*5/100);
		}else {
			this.guadagno=(prezzo*10/100);
		}
		//lunghezza
		if(this.lunghezza<10) { // natanti
			this.guadagno+=350;
		}else if(this.lunghezza<15){ // barche targate
			this.guadagno+=500;
		}else if(this.lunghezza<20){
			this.guadagno+=750;
		}else if(this.lunghezza<24){
			this.guadagno+=1000;
		}else{                      // navi
			this.guadagno+=2000;
			this.guadagno+=(prezzo*1/1000);
		}
		//anno
		if(this.anno>2020) { 
			this.guadagno+=200;
		}else if(this.anno>2010){ 
			this.guadagno+=150;
		}else if(this.anno>2000){
			this.guadagno+=100;
		}else if(this.anno>1990){
			this.guadagno+=50;
		}else{
			this.guadagno+=100;  //epoca -> più di 30 anni
		}
		
		

	}




	@Override
	public String toString() {
		return "\nBoat: id=" + id + ", prezzo=" + prezzo + ", tipologia=" + tipologia +  ", condizione=" + condizione 
				+ ", anno=" + anno + ", lunghezza=" + lunghezza + "\nGuadagno atteso:" + guadagno;
	}

	@Override
	public int compareTo(Boat o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Luogo == null) ? 0 : Luogo.hashCode());
		result = prime * result + ((anno == null) ? 0 : anno.hashCode());
		result = prime * result + ((condizione == null) ? 0 : condizione.hashCode());
		result = prime * result + ((guadagno == null) ? 0 : guadagno.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((larghezza == null) ? 0 : larghezza.hashCode());
		result = prime * result + ((lunghezza == null) ? 0 : lunghezza.hashCode());
		result = prime * result + ((manifattura == null) ? 0 : manifattura.hashCode());
		result = prime * result + ((prezzo == null) ? 0 : prezzo.hashCode());
		result = prime * result + ((tipologia == null) ? 0 : tipologia.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Boat other = (Boat) obj;
		if (Luogo == null) {
			if (other.Luogo != null)
				return false;
		} else if (!Luogo.equals(other.Luogo))
			return false;
		if (anno == null) {
			if (other.anno != null)
				return false;
		} else if (!anno.equals(other.anno))
			return false;
		if (condizione == null) {
			if (other.condizione != null)
				return false;
		} else if (!condizione.equals(other.condizione))
			return false;
		if (guadagno == null) {
			if (other.guadagno != null)
				return false;
		} else if (!guadagno.equals(other.guadagno))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (larghezza == null) {
			if (other.larghezza != null)
				return false;
		} else if (!larghezza.equals(other.larghezza))
			return false;
		if (lunghezza == null) {
			if (other.lunghezza != null)
				return false;
		} else if (!lunghezza.equals(other.lunghezza))
			return false;
		if (manifattura == null) {
			if (other.manifattura != null)
				return false;
		} else if (!manifattura.equals(other.manifattura))
			return false;
		if (prezzo == null) {
			if (other.prezzo != null)
				return false;
		} else if (!prezzo.equals(other.prezzo))
			return false;
		if (tipologia == null) {
			if (other.tipologia != null)
				return false;
		} else if (!tipologia.equals(other.tipologia))
			return false;
		return true;
	}
	
	
	
	
}
