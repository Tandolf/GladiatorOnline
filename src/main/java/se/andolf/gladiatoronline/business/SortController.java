package se.andolf.gladiatoronline.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import se.andolf.gladiatoronline.model.NamedPairs;

public class SortController {

	private Random rand = new Random();
	private List<String> fighterNames;

	public List<NamedPairs> newSorting(List<String> names)
			throws IllegalArgumentException {
		this.fighterNames = names;

		List<Pairing> pairs = new ArrayList<Pairing>();

		int numEntries = fighterNames.size();

		for (int x = 0; x < numEntries - 1; ++x) {
			for (int y = x + 1; y < numEntries; ++y) {
				Pairing p = new Pairing(x + 1, y + 1);
				pairs.add(p);
			}
		}
		
		List<Pairing> fightersSorted = new ArrayList<Pairing>();

		int bestVal = Integer.MAX_VALUE;
		String best = "";
		for (int x = 0; x < 1000000; ++x) {
			int backs = countBackToBacks(pairs);
			if (backs < bestVal) {
				bestVal = backs;
				best = pairs.toString();
				fightersSorted = new ArrayList<Pairing>(pairs);
			}
			randomize(pairs);
		}

		System.out.println(best + bestVal);
		
		List<NamedPairs> fighterList = arrangeNames(fightersSorted, names);
		
		return fighterList;
	}

	private List<NamedPairs>arrangeNames(List<Pairing> fightersSorted, List<String> names){
		List<NamedPairs> fighterList = new ArrayList<NamedPairs>();
		
		for (int i = 0; i < fightersSorted.size(); i++) {
			NamedPairs np = new NamedPairs();
			np.setFirst(names.get(fightersSorted.get(i).getContestantA()-1));
			np.setSecond(names.get(fightersSorted.get(i).getContestantB()-1));
			fighterList.add(np);
		}
		
		return fighterList;
		
	}

	private int countBackToBacks(List<Pairing> pairs) {
		int backs = 0;
		for (int x = 0; x < pairs.size() - 1; ++x) {
			Pairing a = pairs.get(x);
			Pairing b = pairs.get(x + 1);
			if (a.getContestantA() == b.getContestantA()
					|| a.getContestantA() == b.getContestantB()
					|| a.getContestantB() == b.getContestantA()
					|| a.getContestantB() == b.getContestantB()) {
				++backs;
			}
		}

		return backs;
	}

	private void randomize(List<Pairing> pairs) throws IllegalArgumentException {
		int a = rand.nextInt(pairs.size());
		int b = rand.nextInt(pairs.size());
		Pairing pa = pairs.get(a);
		Pairing pb = pairs.get(b);
		pairs.set(a, pb);
		pairs.set(b, pa);
	}

}

class Pairing {

	private int contestantA;
	private int contestantB;

	public Pairing(int a, int b) {
		contestantA = a;
		contestantB = b;
	}

	public String toString() {
		return "" + contestantA + " - " + contestantB;
	}

	public int getContestantA() {
		return contestantA;
	}

	public void setContestantA(int contestantA) {
		this.contestantA = contestantA;
	}

	public int getContestantB() {
		return contestantB;
	}

	public void setContestantB(int contestantB) {
		this.contestantB = contestantB;
	}
}