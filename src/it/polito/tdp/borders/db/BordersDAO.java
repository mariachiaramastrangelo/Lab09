package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {
	private Map<Integer, Country> cIdMap;
	
	public Map<Integer, Country> getcIdMap() {
		return cIdMap;
	}
	public void setcIdMap(Map<Integer, Country> cIdMap) {
		this.cIdMap = cIdMap;
	}
	public List<Country> loadAllCountries() {

		String sql = "SELECT CCode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		this.cIdMap= new HashMap<Integer, Country>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				Country c= new Country(rs.getString("StateAbb"), rs.getInt("CCode"),  rs.getString("StateNme"));
				
				if(!cIdMap.containsKey(c.getCCode())) {
					cIdMap.put(c.getCCode(), c);
					result.add(c);
				}else {
					result.add(this.cIdMap.get(c.getCCode()));
				}
			}
			
			conn.close();
			
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	public List<Country> getCountries(int anno){
		String sql= "SELECT Distinct(c.`state1no`) " + 
				"FROM contiguity c  " + 
				"WHERE c.`year`<=? AND c.`conttype`=1 ";
		List<Country> result= new ArrayList<>();
		Connection conn= ConnectDB.getConnection();
		try {
			PreparedStatement st=conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs= st.executeQuery();
			while(rs.next()) {
				result.add(this.cIdMap.get(rs.getInt("state1no")));
			}
			conn.close();
			return result;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Border> getCountryPairs(int anno) {
		String sql= "SELECT c.`state1no`,  c.`state2no` " + 
				"FROM contiguity c " + 
				"WHERE c.`year`<=? AND c.`conttype`=1 AND c.`state1no`<c.`state2no` ";
		List<Border> result= new ArrayList<>();
		Connection conn= ConnectDB.getConnection();
		try {
			PreparedStatement st=conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs= st.executeQuery();
			while(rs.next()) {
				Border b= new Border(rs.getInt("state1no"), rs.getInt("state2no"));
				result.add(b);
			}
			//System.out.println(result);
			conn.close();
			return result;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
}
