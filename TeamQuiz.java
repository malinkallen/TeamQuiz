import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TeamQuiz {
	private static final int NUM_ALTERNATIVES = 4;
	private String confFileName;
	private String[] allAlternatives;
	private String[] participants;
	private String[] pictures;

	public TeamQuiz(String confFileName) {
		this.confFileName = confFileName;
	}

	public void readConf() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(confFileName));
		String line = reader.readLine();
		while (line != null) {
			String[] parts = line.split("=");
			if (parts.length != 2) {
				System.err.println("Each configuration line should contain a parameter and its value, separated with '='");
			} else {
				switch (parts[0]) {
					case "alternatives":
						allAlternatives = parts[1].split(",");
						break;
					case "participants":
						participants = parts[1].split(",");
						break;
					case "pictures":
						pictures = parts[1].split(",");
						break;
					default:
						System.err.println("Skipping unknown configuration parameter " + parts[0]);
						break;
				}
			}
			line = reader.readLine();
		}
		reader.close();
	}
	
	public void printAlternatives() {
		String[] questions = questions();
		String[][] alternatives = alternatives(questions);
		printAlternatives(questions, alternatives);
	}

	private String[][] alternatives(String[] questions) {
		final int numQuestions = questions.length;
		String[][] result = new String[numQuestions][NUM_ALTERNATIVES];
		for (int i=0; i<numQuestions; i++) {
			String pictureOwner = questions[i].split(" -- ")[0];
			result[i][0] = pictureOwner;
			int j = 1;
			while (j<NUM_ALTERNATIVES) {
				int index = (int)(Math.random() * numQuestions);
				String alternative = allAlternatives[index];
				if (!contains(result[i], alternative)) {
					result[i][j] = alternative;
					j++;
				}
			}
			shuffle(result[i]);	// Otherwise the owner will always be the first alternative
		}
		return result;
	}

	private String[] questions() {
		final int numParticipants = participants.length;
		final int numPictures = pictures.length;
		String[] questions = new String[numParticipants * numPictures];
		for (int i=0; i<numParticipants; i++) {
			for (int j=0; j<numPictures; j++) {
				questions[i*numPictures + j] = participants[i].trim() + " -- " + pictures[j].trim();
			}
		}
		return shuffle(questions);
	}

	private void printAlternatives(String[] questions, String[][] alternatives) {
		final int numQuestions = questions.length;
		for (int i=0; i<numQuestions; i++) {
			System.out.println("Picture: " + questions[i]);
			System.out.println("Alternatives:");
			for (int j=0; j<NUM_ALTERNATIVES; j++) {
				System.out.println("\t" + alternatives[i][j]);
			}
			System.out.println();
		}
	}

	// TODO: Listor för att slippa detta krångel?
	private boolean contains(String[] arr, String value) {
		return Arrays.asList(arr).contains(value);
	}

	private String[] shuffle(String[] input) {
		List<String> list = Arrays.asList(input);
		Collections.shuffle(list);
		String[] result = new String[list.size()];
		list.toArray(result);
		return result;
	}

	public static void main(String[] args) {
		TeamQuiz quiz = new TeamQuiz("teamquiz.input");
		try {
			quiz.readConf();
		} catch (IOException e) {
			System.err.println("Error whe reading input file: " + e.getMessage());
		}
		quiz.printAlternatives();
	}
}
