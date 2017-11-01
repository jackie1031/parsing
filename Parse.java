import java.io.*;
import java.util.*;

/**
 * Main method that does the parsing.
 */
public class Parse {
    public static void main(String[] args) {
        /*we should deal with sentences in .sen one by one in chart, and print out the result*/
        try {
            // FileReader grammarFile = new FileReader(args[0]);
            FileReader sentencesFile = new FileReader(args[1]);
            // BufferedReader grammarBuffered = new BufferedReader(grammarFile);
            BufferedReader sentencesBuffered = new BufferedReader(sentencesFile);

            // Map<String, List<Rule>> grammar = new HashMap<>();
            Grammar.loadGrammar(args[0]);
            // String lhs;
            // ArrayList<String> temp;
            // double tempWeight;
            // Rule tempRule;
            // ArrayList<Rule> tempRuleList;
            ArrayList<String> tempSentence;
            Chart tempChart;
            // while ((line = grammarBuffered.readLine()) != null) {
            //     temp = new ArrayList<>(Arrays.asList(line.split("\\s+")));
            //     tempWeight = (-1) * Math.log10(Double.parseDouble(temp.remove(0)))/Math.log10(2.0);
            //     lhs = temp.get(0);
            //     temp.remove(0);
            //     tempRule = new Rule(tempWeight, lhs, temp);
            //     if (grammar.get(lhs) == null) {
            //         tempRuleList = new ArrayList<Rule>();
            //         tempRuleList.add(tempRule);
            //         grammar.put(lhs, tempRuleList);
            //     } else
            //     {
            //         grammar.get(lhs).add(tempRule);
            //     }
            // }

            // Grammar.loadGrammar(grammar);
            String line = null;
            long startTime = System.currentTimeMillis();
            while ((line = sentencesBuffered.readLine()) != null) {
                if (line.length() > 0) {
                    tempSentence = new ArrayList<>(Arrays.asList(line.split("\\s+")));
//                    System.out.println(grammar);
//                    System.out.println(tempSentence);
                    tempChart = new Chart(tempSentence);
                    tempChart.getParseWeight();
//                    System.out.println(tempChart);
                    System.out.println(tempChart.treeView());
                }
            }
            System.out.println((System.currentTimeMillis() - startTime) / 1000.0 + " s");
        }
        catch (FileNotFoundException ex) {
            System.out.println("Unable to open file");
        }
        catch (IOException ex) {
            System.out.println("Error reading file");
        }

    }
}