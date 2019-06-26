package it.polito.tdp.borders.model;

import java.util.Map;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();

		System.out.println("TestModel -- TODO");
		
//		System.out.println("Creo il grafo relativo al 2000");
//		model.createGraph(2000);
		
		model.creaGrafo(2000);
		Map<Country, Integer> confini= model.calcolaConfini(2000);
		for(Country c: confini.keySet()) {
			System.out.println(c+" "+confini.get(c)+"\n");
		}
		System.out.println("componenti connesse: "+model.componentiConnesse());
		
		System.out.print(model.trovaVicini(model.getPaese(2)));
		
//		List<Country> countries = model.getCountries();
//		System.out.format("Trovate %d nazioni\n", countries.size());

//		System.out.format("Numero componenti connesse: %d\n", model.getNumberOfConnectedComponents());
		
//		Map<Country, Integer> stats = model.getCountryCounts();
//		for (Country country : stats.keySet())
//			System.out.format("%s %d\n", country, stats.get(country));		
		
	}

}
