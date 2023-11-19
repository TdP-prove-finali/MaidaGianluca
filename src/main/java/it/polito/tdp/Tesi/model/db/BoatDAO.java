package it.polito.tdp.Tesi.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.Tesi.model.Boat;



public class BoatDAO {

	public List<Boat> listAllBoat(){
		String sql = "SELECT * "
				+ "FROM barche";
		List<Boat> result = new ArrayList<Boat>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Boat b = new Boat(res.getInt("Id"), res.getInt("Prezzo"),res.getString("Tipologia"),res.getString("Manifattura"),res.getString("Tipo"), 
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

}
