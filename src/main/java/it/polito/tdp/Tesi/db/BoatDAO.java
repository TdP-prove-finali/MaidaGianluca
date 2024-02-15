package it.polito.tdp.Tesi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.Tesi.model.Boat;



public class BoatDAO {

	public List<Boat> listAllBoat() {
		String sql = "SELECT * "
				+ "FROM boat";
		List<Boat> result = new ArrayList<Boat>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Boat b = new Boat(res.getInt("Id"), res.getInt("Prezzo"),res.getString("Tipologia"),res.getString("Manifattura"),res.getString("Condizione"), 
						res.getInt("Anno"), res.getDouble("Lunghezza"),	res.getDouble("Larghezza"), res.getString("Luogo"));
				result.add(b);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Boat> listBoatSelected(int prezzo, List<String> condi, double Lmin, double Lmax, String tipologia, int Amin, int Amax){
		String sql = "SELECT * "
				+ "FROM boat b "
				+ "WHERE b.Lunghezza>=? AND b.Lunghezza<=? "
				+ "AND b.Prezzo<=? "
				+ "AND b.Anno>=? AND b.Anno<=? ";
		if(condi.size()>1) {
			sql= sql+"AND (b.Condizione=? OR b.Condizione=?)";
		}else {
			sql= sql+"AND b.Condizione=?";
		}
		if(!tipologia.equals("Qualsiasi")) {
			sql= sql+" AND b.Tipologia LIKE '%"+tipologia+"%'";
		}
		List<Boat> result = new ArrayList<Boat>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);

			st.setDouble(1, Lmin);
			st.setDouble(2, Lmax);
			st.setInt(3, prezzo);
			st.setInt(4, Amin);
			st.setInt(5, Amax);
			int j=6;
			for(int i=0;i<condi.size();i++) {
				st.setString(j,condi.get(i));
				j++;
			}		
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Boat b = new Boat(res.getInt("Id"), res.getInt("Prezzo"),res.getString("Tipologia"),res.getString("Manifattura"),
						res.getString("Condizione"), res.getInt("Anno"), res.getDouble("Lunghezza"),	
						res.getDouble("Larghezza"), res.getString("Luogo"));
				result.add(b);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<String> listAllTipologia() {
		String sql = "SELECT DISTINCT Tipologia "
				+ "FROM boat "
				+ "WHERE Tipologia NOT LIKE '%,%'";
		List<String> result = new ArrayList<String>();
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				String b = res.getString("Tipologia");
				result.add(b);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public double getLmin() {
		String sql = "SELECT MIN(Lunghezza) AS Lmin "
				+ "FROM boat";
		Connection conn = DBConnect.getConnection();		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			double b=0; 
			while (res.next()) {
				 b = res.getDouble("Lmin");
			}
			conn.close();
			return b;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public double getLmax() {
		String sql = "SELECT MAX(Lunghezza) AS Lmax "
				+ "FROM boat";
		Connection conn = DBConnect.getConnection();	
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			double b=0; 
			while (res.next()) {
				b = res.getDouble("Lmax");
			}
			conn.close();
			return b;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int getAmin() {
		String sql = "SELECT MIN(Anno) AS Amin "
				+ "FROM boat";
		Connection conn = DBConnect.getConnection();		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			int b=0; 
			while (res.next()) {
				b = res.getInt("Amin");
			}
			conn.close();
			return b;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int getAmax() {
		String sql = "SELECT MAX(Anno) AS Amax "
				+ "FROM boat";
		Connection conn = DBConnect.getConnection();		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			int b=0; 
			while (res.next()) {
				b = res.getInt("Amax");
			}
			conn.close();
			return b;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	
	
}

