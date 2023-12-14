package it.polito.tdp.Tesi.db;

import it.polito.tdp.Tesi.model.Boat;

public class TestDAO {

	public static void main(String[] args) throws Exception  {
		// TODO Auto-generated method stub
		TestDAO testDao = new TestDAO();
		testDao.run();
	}
	
	public void run() throws Exception {
		BoatDAO dao = new BoatDAO();
		dao.listAllBoat();
		for(Boat b: dao.listAllBoat() ){
			System.out.println(b.toString());
		}
	}	

}
