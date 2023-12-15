
package it.polito.tdp.Tesi;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.Tesi.db.BoatDAO;
import it.polito.tdp.Tesi.model.Boat;
import it.polito.tdp.Tesi.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	public Model model;
	public Boat boat;
	public BoatDAO dao = new BoatDAO();
	String condi = "Used boat";
	double Lmin = 0;
	double Lmax = 0;
	String tipo = null;
	int Amin = 0;
	int Amax = 0;
	int budg = 0;
	int id = 0;
	List<Boat> lBoatSel = new ArrayList<Boat>();

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField AnnoMax;

	@FXML
	private TextField AnnoMin;

	@FXML
	private Button BtnId;

	@FXML
	private TextField Budget;

	@FXML
	private ChoiceBox<String> Condizione;

	@FXML
	private TextField Id;

	@FXML
	private Button Lista;

	@FXML
	private TextField LunghezzaMax;

	@FXML
	private TextField LunghezzaMin;

	@FXML
	private Button ResetFiltri;

	@FXML
	private Button Soluzione;

	@FXML
	private ChoiceBox<String> Tipologia;

	@FXML
	private TextArea TxtResult;

	@FXML
	void ActionBtnId(ActionEvent event) {

	}

	@FXML
	void ActionLista(ActionEvent event) {
		TxtResult.clear();
		if(setFiltri()) {
			if (this.budg != 0) {
				if (this.lBoatSel.size() != 0) {
					TxtResult.appendText("LISTA\n\nLe barche che rispettano i criteri sono: \n\n");
					for (Boat b : this.lBoatSel) {
						TxtResult.appendText(b.toString() + "\n");
					}
				} else {
					TxtResult.appendText(
							"LISTA\n\nNon ci sono barche che rispettano i criteri.\n\nModificare i parametri inseriti o effettuare una nuova ricerca ");
				}
			}
		}
	}

	@FXML  
	void ActionResetFiltri(ActionEvent event) {
		TxtResult.clear();
		Condizione.setValue("");
		AnnoMax.clear();
		AnnoMin.clear();
		Budget.clear();
		Id.clear();
		LunghezzaMax.clear();
		LunghezzaMin.clear();
		Tipologia.setValue("");
	}

	@FXML
	void ActionSoluzione(ActionEvent event) {
		TxtResult.clear();
		if(setFiltri()){
			if (this.budg != 0) {
				List<Boat> best = this.model.barche(this.lBoatSel, budg);
				TxtResult.appendText("SOLUZIONE\n\nLa soluzione ottimale è:\n ");
				for (Boat b : best) {
					TxtResult.appendText(b.toString() + "\n");
				}
				TxtResult.appendText("\n\nGuadagno atteso: " + this.model.calcola_guadagno(best));
				if (!this.model.secondasol().isEmpty()) {
					List<Boat> best2 = this.model.secondasol();
					TxtResult.appendText("\nIn alternativa la soluzione ottimale è:\n ");
					for (Boat b : best2) {
						TxtResult.appendText(b.toString() + "\n");
					}
					int diff = this.model.calcola_prezzo(best2) - this.model.calcola_prezzo(best);
					TxtResult.appendText("\n\nSpendendo" + diff + " in più.");
					TxtResult.appendText("\n\nGuadagno atteso: " + this.model.calcola_guadagno(best2));
				}
	
			}
		}
	}

	private boolean setFiltri() {
		try {
//setto Lunghezza Minima per la ricerca	   
			if (Tipologia.getValue().isEmpty()) {
				// all tipo
			} else {
				tipo = Tipologia.getValue();
			}

//setto Lunghezza Minima per la ricerca	   

			if (LunghezzaMin.getText().isEmpty()) {
				Lmin = dao.getLmin();
			} else {
				Lmin = Double.parseDouble(LunghezzaMin.getText());
			}

//setto Lunghezza Massima per la ricerca	   		

			if (LunghezzaMax.getText().isEmpty()) {
				Lmax = dao.getLmax();
			} else {
				Lmax = Double.parseDouble(LunghezzaMax.getText());
			}

//setto condizione per la ricerca	

			if (Condizione.getValue().isEmpty()) {
				// all confi
			} else {
				condi = Condizione.getValue();
			}

//setto Anno Minimo per la ricerca	   		

			if (AnnoMin.getText().isEmpty()) {
				Amin = dao.getAmin();
			} else {
				Amin = Integer.parseInt(AnnoMin.getText());
			}

//setto Anno Minimo per la ricerca	   		

			if (AnnoMax.getText().isEmpty()) {
				Amax = dao.getAmax();
			} else {
				Amax = Integer.parseInt(AnnoMax.getText());
			}

//setto Budget per la ricerca in caso stampo errore   						

			if (Budget.getText().isEmpty() || Integer.parseInt(Budget.getText()) == 0) {
				TxtResult.appendText("INSERIRE UN BUDGET VALIDO");
			} else {
				budg = Integer.parseInt(Budget.getText());
			}

			lBoatSel = new ArrayList<Boat>(dao.listBoatSelected(condi, Lmin, Lmax, tipo, Amin, Amax));
			
			return true;
		} catch (NumberFormatException e) {
			TxtResult.appendText("Inserire un valore corretto \n");
			return false;
		}
	}

	@FXML
	void initialize() {
		assert AnnoMax != null : "fx:id=\"AnnoMax\" was not injected: check your FXML file 'Scene.fxml'.";
		assert AnnoMin != null : "fx:id=\"AnnoMin\" was not injected: check your FXML file 'Scene.fxml'.";
		assert BtnId != null : "fx:id=\"BtnId\" was not injected: check your FXML file 'Scene.fxml'.";
		assert Budget != null : "fx:id=\"Budget\" was not injected: check your FXML file 'Scene.fxml'.";
		assert Condizione != null : "fx:id=\"Condizione\" was not injected: check your FXML file 'Scene.fxml'.";
		assert Id != null : "fx:id=\"Id\" was not injected: check your FXML file 'Scene.fxml'.";
		assert Lista != null : "fx:id=\"Lista\" was not injected: check your FXML file 'Scene.fxml'.";
		assert LunghezzaMax != null : "fx:id=\"LunghezzaMax\" was not injected: check your FXML file 'Scene.fxml'.";
		assert LunghezzaMin != null : "fx:id=\"LunghezzaMin\" was not injected: check your FXML file 'Scene.fxml'.";
		assert ResetFiltri != null : "fx:id=\"ResetFiltri\" was not injected: check your FXML file 'Scene.fxml'.";
		assert Soluzione != null : "fx:id=\"Soluzione\" was not injected: check your FXML file 'Scene.fxml'.";
		assert Tipologia != null : "fx:id=\"Tipologia\" was not injected: check your FXML file 'Scene.fxml'.";
		assert TxtResult != null : "fx:id=\"TxtResult\" was not injected: check your FXML file 'Scene.fxml'.";
		Lmin = 0;
		Lmax = 0;
		Amin = 0;
		Amax = 0;
		budg = 0;
		id = 0;
	}

	private void setComboItemsCondizione() {
		List<String> l = new ArrayList<String>();
		l.add("Qualsiasi");
		l.add("New boat");
		l.add("Used boat");
		Condizione.getItems().setAll(l);
	}

	private void setComboItemsTipologia() {
		List<String> l = new ArrayList<String>();
		l.add("Qualsiasi");
		l.addAll(dao.listAllTipologia());
		Tipologia.getItems().setAll(l);
	}

	public void setModel(Model model) {
		this.model = model;
		setComboItemsCondizione();
		setComboItemsTipologia();

	}

}
