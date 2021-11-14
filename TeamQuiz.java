import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeamQuiz {
	private static final int NUM_ALTERNATIVES = 4;
	private String inputFileName;
	private String[] allAlternatives;
	private String[] providers;
	private String[] pictures;

	public TeamQuiz(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public void readInput() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
		String line = reader.readLine();
		while (line != null) {
			String[] parts = line.split("=");
			if (parts.length != 2) {
				System.err.println("Each input line should contain a parameter and its value, separated with '='");
			} else {
				String parameter = parts[0].trim();
				switch (parameter) {
					case "alternatives":
						allAlternatives = parts[1].split(",");
						break;
					case "providers":
						providers = parts[1].split(",");
						break;
					case "pictures":
						pictures = parts[1].split(",");
						break;
					default:
						System.err.println("Skipping unknown input parameter " + parameter);
						break;
				}
			}
			line = reader.readLine();
		}
		reader.close();
	}
	
	public void printAlternatives() {
		List<String> questions = questions();
		List<List<String>> alternatives = alternatives(questions);
		printAlternatives(questions, alternatives);
	}

	private List<String> questions() {
		final int numProviders = providers.length;
		final int numPictures = pictures.length;
		List<String> questions = new ArrayList<>(numProviders * numPictures);
		for (int i=0; i<numProviders; i++) {
			for (int j=0; j<numPictures; j++) {
				int index = i*numPictures + j;
				String question = providers[i].trim() + " -- " + pictures[j].trim();
				questions.add(index, question);
			}
		}
		Collections.shuffle(questions);
		return questions;
	}

	private List<List<String>> alternatives(List<String> questions) {
		final int numQuestions = questions.size();
		List<List<String>> result = new ArrayList<>(numQuestions);
		for (int i=0; i<numQuestions; i++) {
			result.add(i, new ArrayList<>(NUM_ALTERNATIVES));
			String pictureProvider = questions.get(i).split(" -- ")[0];
			List<String> alternativesForQuestion = result.get(i);
			alternativesForQuestion.add(pictureProvider);
			int j = 1;
			while (j<NUM_ALTERNATIVES) {
				int index = (int)(Math.random() * allAlternatives.length);
				String alternative = allAlternatives[index].trim();
				if (!alternativesForQuestion.contains(alternative)) {
					alternativesForQuestion.add(alternative);
					j++;
				}
			}
			Collections.shuffle(result.get(i));	// Otherwise the picture provider will always be the first alternative
		}
		return result;
	}

	private void printAlternatives(List<String> questions, List<List<String>> alternatives) {
		final int numQuestions = questions.size();
		for (int i=0; i<numQuestions; i++) {
			System.out.println("Picture: " + questions.get(i));
			System.out.println("Alternatives:");
			for (int j=0; j<NUM_ALTERNATIVES; j++) {
				System.out.println("\t" + alternatives.get(i).get(j));
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		TeamQuiz quiz = new TeamQuiz("teamquiz.input");
		try {
			quiz.readInput();
		} catch (IOException e) {
			System.err.println("Error whe reading input file: " + e.getMessage());
		}
		quiz.printAlternatives();
	}
}
