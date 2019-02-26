package org.world.concurrencyinpractice.problem.base;

import lombok.Data;
import org.world.model.Animal;

import java.util.*;

/**
 * @Description 基本类型的局部变量与引用变量的线程封闭性
 */
@Data
public class LoadTheArk {

	private static LoadTheArk ark;
	private List<AnimalPair> pairs = new ArrayList<>();

	static {
		ark = new LoadTheArk();
	}

	public static LoadTheArk getInstance() {
		return ark;
	}

	/**
	 * 如果将animal引用发布，将导致 animal对象逸出
	 *
	 * @param candidates
	 * @return
	 */
	public int loadTheArk(Collection<Animal> candidates) {
		SortedSet<Animal> animals;
		int numPairs = 0;
		Animal candidate = null;

		//animals被封闭在方法中，不要使它们逸出！
		animals = new TreeSet<Animal>(new SpeciesGenderComparator());
		animals.addAll(candidates);
		for (Animal a : animals) {
			if (candidate == null || !candidate.isPotentialMate(a)) {
				candidate = a;
			} else {
				ark.load(new AnimalPair(candidate, a));
				++numPairs;
				candidate = null;
			}
		}
		return numPairs;
	}

	private void load(AnimalPair animalPair) {
		pairs.add(animalPair);
	}

	private class SpeciesGenderComparator implements java.util.Comparator<Animal> {
		@Override
		public int compare(Animal o1, Animal o2) {
			return o1.name.equals(o2.name)&& o1.priority == o2.priority  ? 0 : 1;
		}
	}

	@Data
	private class AnimalPair {
		Map pair = new HashMap(2);

		public AnimalPair(Animal candidate, Animal a) {
			pair.put(candidate.priority, candidate.getName());
			pair.put(a.priority, a.getName());
		}
	}

	public static void main(String[] args) {
		List<Animal> animals = Arrays.asList(new Animal(1, "1"), new Animal(1, "2"), new Animal(1, "3"), new Animal(4, "4"), new Animal(5, "5"), new Animal(6, "6"));
		Collections.sort(animals, new Comparator<Animal>() {
			@Override
			public int compare(Animal o1, Animal o2) {
				return o1.priority > o2.priority ? 1 : (o1.priority == o2.priority ? 0 : -1);
			}
		});
		System.out.println(animals);
		LoadTheArk loadTheArk = LoadTheArk.getInstance();
		loadTheArk.loadTheArk(animals);
		List<AnimalPair> pairs = loadTheArk.getPairs();
		System.out.println(pairs);
	}


}
