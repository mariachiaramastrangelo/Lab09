package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	private SimpleGraph<Country, DefaultEdge> grafo;
	private Map<Integer, Country> cIdMap;
	private BordersDAO dao;

	public Model() {
		
		dao= new BordersDAO();
	}

	public void creaGrafo(int anno) {
		grafo= new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		List<Country> paesi= dao.loadAllCountries();
		cIdMap= dao.getcIdMap();
		Graphs.addAllVertices(grafo, dao.getCountries(anno));
		
		List<Border> confinanti= dao.getCountryPairs(anno);
		
		for(Border b: confinanti) {
			Country c1= this.cIdMap.get(b.getState1no());
			Country c2= this.cIdMap.get(b.getState2no());
			grafo.addEdge(c1, c2);
			
		}
		
	}

	public Map<Country, Integer> calcolaConfini(int anno) {
		//metodo per la creazione del grafo 
		Map<Country, Integer> numeroConfini= new HashMap<>();
		for(Country c: grafo.vertexSet()) {
			numeroConfini.put(c, grafo.inDegreeOf(c));
		}
		return numeroConfini;
	}
	
	public int componentiConnesse(){
		ConnectivityInspector ci= new ConnectivityInspector(grafo);
		return ci.connectedSets().size();
	}
	
	public List<Country> trovaVicini(Country paese){
		DepthFirstIterator<Country, DefaultEdge> gfi=new DepthFirstIterator<Country, DefaultEdge>(grafo, paese);
		List<Country> vicini= new ArrayList<>();
		while(gfi.hasNext()) {
			vicini.add(gfi.next());
		}
		return  vicini;
	}
	
	public Country getPaese(int CCode) {
		return this.cIdMap.get(CCode);
	}
}
