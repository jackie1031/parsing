// import java.util.List;
// import java.util.Map;
import java.util.*;
import java.io.*;

/**
 * Stores all grammar rules using a map
 * key = lhs rule, value = a list of Rule objects.
 */
public class Grammar {
    private static Map<String, List<Rule>> grammar;

    public static void loadGrammar(String grammarFile) {
        grammar = new HashMap<>();
        try {
            FileReader grammarFileReader = new FileReader(grammarFile);
            BufferedReader grammarBuffered = new BufferedReader(grammarFileReader);
            String line = null;
            String lhs;
            ArrayList<String> ruleAsString;
            double tempWeight;
            Rule tempRule;
            ArrayList<Rule> tempRuleList;
            while ((line = grammarBuffered.readLine()) != null) {
                ruleAsString = new ArrayList<>(Arrays.asList(line.split("\\s+")));
                tempWeight = (-1) * Math.log10(Double.parseDouble(ruleAsString.remove(0)))/Math.log10(2.0);
                //lhs = ruleAsString.get(0);
                lhs = ruleAsString.remove(0);
                tempRule = new Rule(tempWeight, lhs, ruleAsString);
                if (grammar.get(lhs) == null) {
                    tempRuleList = new ArrayList<Rule>();
                    tempRuleList.add(tempRule);
                    grammar.put(lhs, tempRuleList);
                } else {
                    grammar.get(lhs).add(tempRule);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file");
        } catch (IOException ex) {
            System.out.println("Error reading file");
        }

    }

    public static List<Rule> getRulesForSymbol(String symbol) {
        return grammar.get(symbol);
    }

    public static boolean isTerminal(String str) {
        return grammar.get(str) == null;
    }

    @Override
    public String toString() {
        return grammar.toString();
    }
}