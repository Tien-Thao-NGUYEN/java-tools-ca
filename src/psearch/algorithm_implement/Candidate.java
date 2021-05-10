package psearch.algorithm_implement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Candidate {
	private static Random rand = new Random();
	public static List<Integer> changeableIndexList;

	private List<Integer> vector;
	private boolean simul;

	public Candidate(List<Integer> vector, List<Integer> specialStateList) {
		List<Integer> orderAppear = orderAppear(vector, specialStateList);
		this.vector = reorder(vector, orderAppear);
	}

	private List<Integer> orderAppear(List<Integer> vector, List<Integer> specialStateList) {
		return vector.stream().filter(e -> !specialStateList.contains(e)).distinct().collect(Collectors.toList());
	}
	
	private List<Integer> reorder(List<Integer> vector, List<Integer> orderAppear) {
		List<Integer> orderNature = orderAppear.stream().sorted().collect(Collectors.toList());
		return vector.stream().map(e -> {
			int index = orderAppear.indexOf(e);
			if (index != -1)
				return orderNature.get(index);
				
			return e;
		}).collect(Collectors.toList());
	}
	
	public Integer getValue(int index) {
		return vector.get(index);
	}
	
	public void setSimul(boolean good) {
		this.simul = good;
	}
	
	public boolean isSimul() {
		return this.simul;
	}
	
	public List<Candidate> getNeighbourList(List<Integer> specialStateList) {
		List<Candidate> neighbourList = new ArrayList<>();
		for (int iciList = 0; iciList < changeableIndexList.size(); iciList++) {
			int iVec = changeableIndexList.get(iciList);
			//TODO can xem lai cho for nay
			for (int newState = 0; newState < specialStateList.get(2); newState++) {
				if (newState != vector.get(iVec)) {
					List<Integer> neighbourVec = vector.stream().collect(Collectors.toList());
					neighbourVec.set(iVec, newState);
					neighbourList.add(new Candidate(neighbourVec, specialStateList));
				}
			}
		}
		
		return neighbourList;
	}
	
	private List<Integer> getRandomIndex(int pertNumber) {
		Set<Integer> iciSet = new HashSet<>();
		while (iciSet.size() < pertNumber)
			iciSet.add(rand.nextInt(changeableIndexList.size()));
		
		return iciSet.stream().map(e -> changeableIndexList.get(e)).collect(Collectors.toList());
	}
	
	public Candidate perturbation(int pertNumber, List<Integer> specialStateList) {
		Integer specialState = specialStateList.get(2);
		List<Integer> newVec = vector.stream().collect(Collectors.toList());
		List<Integer> iVecList = getRandomIndex(pertNumber);
		for(Integer iVec : iVecList) {
			int randVal = rand.nextInt(specialState);
			while (randVal == newVec.get(iVec))
				randVal = rand.nextInt(specialState);
			
			newVec.set(iVec, randVal);
		}
		
		return new Candidate(newVec, specialStateList);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vector == null) ? 0 : vector.hashCode());
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
		Candidate other = (Candidate) obj;
		if (vector == null) {
			if (other.vector != null)
				return false;
		} else if (!vector.equals(other.vector))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder(vector.get(0).toString());
		for(int iVec = 1; iVec < vector.size(); iVec++) {
			strBuilder.append(" ");
			strBuilder.append(vector.get(iVec).toString());
		}
		
		return strBuilder.toString();
	}
}
