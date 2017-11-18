package test;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.List;

import org.fnlp.nlp.cn.CNFactory;
import org.fnlp.nlp.parser.dep.DependencyTree;
import org.fnlp.util.exception.LoadModelException;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final String PATH = "D:\\Courses\\NLP\\LAB\\Corpus\\³Ѹȫ��\\test.txt";
		String text = new String();
		ArrayList<java.util.List<String>> result;
		List<String> tmp;
		HashMap<String, Integer> tag_counter = new HashMap<String, Integer>();
		HashMap<String, Integer> grammar_counter = new HashMap<String, Integer>();
		
		try {
			Scanner in = new Scanner(Paths.get(PATH));
			
			CNFactory factory = CNFactory.getInstance("models");
			
			while(in.hasNextLine()){
				text = in.nextLine();
				if(text.isEmpty()){
					continue;
				}
				DependencyTree tree = factory.parse2T(text);
				result = tree.toList();
				for(int i=0;i<result.size();i++){
					System.out.println(result.get(i).toString());
					tmp = result.get(i);
					String word_tag = tmp.get(1);
					String grammar_tag = tmp.get(3);
					if(!tag_counter.containsKey(word_tag)){
						tag_counter.put(word_tag, 1);
					}
					tag_counter.put(word_tag, tag_counter.get(word_tag) + 1);
					
					if(!grammar_counter.containsKey(grammar_tag)){
						grammar_counter.put(grammar_tag, 1);
					}
					grammar_counter.put(grammar_tag, grammar_counter.get(grammar_tag) + 1);
				}
				
				System.out.println("tag_counter: \n"+ tag_counter.toString());
				System.out.println("grammar_counter\n" + grammar_counter.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Finish!");
	}

}
