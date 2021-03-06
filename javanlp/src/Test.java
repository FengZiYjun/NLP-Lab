
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.fnlp.nlp.cn.CNFactory;
import org.fnlp.nlp.parser.dep.DependencyTree;
import org.fnlp.util.exception.LoadModelException;

public class Test {
	
	public static HashMap<String, Integer> sentenceDep(String sentence) throws LoadModelException{
		
		CNFactory factory = CNFactory.getInstance("models");
		DependencyTree tree = factory.parse2T(sentence);
		ArrayList<java.util.List<String>> result = tree.toList();
		
		HashMap<String, Integer> grammar_counter = new HashMap<String, Integer>();
		List<String> tmp;
		
		for(int i=0;i<result.size();i++){
			//System.out.println(result.get(i).toString());
			
			tmp = result.get(i);
			String grammar_tag = tmp.get(3);

			if(!grammar_counter.containsKey(grammar_tag)){
				grammar_counter.put(grammar_tag, 1);
			}else{
				grammar_counter.put(grammar_tag, grammar_counter.get(grammar_tag) + 1);
			}
		}
		
		//System.out.println("grammar_counter\n" + grammar_counter.toString());
		
		return grammar_counter;
	}
	
	public String[] split(String filenames){
		return filenames.split(",");
	}
	
	
	public static void main(String[] args) { 
		// TODO Auto-generated method stub
		final String PATH = "..\\LAB\\src\\output\\";
		final String FILE = "cleaned_" + args[0] + ".txt";
		System.out.println("dealing with " + FILE);
		
		String text = new String();
		HashMap<String, Integer> features = new HashMap<String, Integer>();
		File fileDirs = new File(PATH + FILE);
		
		try {
			//Scanner in = new Scanner(Paths.get(PATH + "cleaned_test.txt"));
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDirs), "UTF-8"));
			
			while( (text = in.readLine()) != null ){
				if(text.isEmpty()){
					continue;
				}
				HashMap<String, Integer> dependencies = sentenceDep(text);
				
				for(Entry<String, Integer> item :dependencies.entrySet()){
					if(!features.containsKey(item.getKey())){
						features.put(item.getKey(), item.getValue());
					}else{
						features.put(item.getKey(), features.get(item.getKey()) + item.getValue());
					}		
				}
			}
			in.close();
			
			Double sum = 0.0;
			for(String item : features.keySet()){
				sum += features.get(item);
			}
			//System.out.print(sum);
			
			HashMap<String, Double> feat_distr = new HashMap<String, Double>();
			for(String key : features.keySet()){
				feat_distr.put(key, features.get(key)/sum);
			}
			
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(PATH + "depen_" + args[1] + ".txt"), "UTF-8")));
			out.println(feat_distr.toString());
			out.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Java Program Finished!");
	
	}

}
