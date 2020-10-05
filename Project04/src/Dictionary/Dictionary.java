package Dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Dictionary {
	private HashMap<String, String> wordsMap;
	private ArrayList<String> wordsList;
	
	public Dictionary() {
		File f = new File("Dictionary\\word.txt");
		wordsMap = new HashMap<String, String>();
		wordsList = new ArrayList<String>();
		if(f.exists()) {
			try {
				System.out.println("사전단어를 읽습니다.");
				BufferedReader br = new BufferedReader(new FileReader(f));
				while(true) {
					String line = null;
					line = br.readLine();
					if(line == null) break;
					if(line.split("///").length == 2) {
						wordsMap.put(line.split("///")[0].trim(), line.split("///")[1]);
						wordsList.add(line);
					}
					
				}
				br.close();
				System.out.println("단어 개수 : " + wordsList.size());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println(f.getPath() + f.getName() + "이 존재하지 않습니다.");
		}
		
	}
	
	public HashMap<String, String> getWordsMap() {return wordsMap;}
	public ArrayList<String> getWordsList() {return wordsList;}
}
