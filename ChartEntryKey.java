import java.util.*;

/**
 * Use to store all the information in the key for a chart
 */
public class ChartEntryKey {

    private String lhs;
    private ArrayList<String> ruleExpression;
    private int startCol;
    private int dotPosi;

    //declare the rootEntryKey
    public static ChartEntryKey rootEntryKey() {
        Rule rootRule = Grammar.getRulesForSymbol("ROOT").get(0);

        // initialize the root
        return new ChartEntryKey(0, rootRule.getRuleExpression().size(), "ROOT", rootRule.getRuleExpression());
    }

    //constructor
    public ChartEntryKey(int startCol, int dotPosi, String nonTerminal, ArrayList<String> ruleExpression) {
        this.lhs = nonTerminal;
        this.ruleExpression = ruleExpression;
        this.startCol = startCol;
        this.dotPosi = dotPosi;

    }

    public String getRhsSymbolAtIndex(int i) {
        return ruleExpression.get(i);
    }

    public ArrayList<String> getRhs() {
        return ruleExpression;
    }

    public int getStartColumn() {
        return startCol;
    }

    public String getLhs() {
        return lhs;
    }

    public int getDotPosition() {
        return dotPosi;
    }

    public ChartOperation operation() {
        if (dotPosi == ruleExpression.size()) {
            return ChartOperation.ATTACH;
        } else {
            if (Grammar.isTerminal(this.symbolAfterDot())) {
                return ChartOperation.SCAN;
            } else {
                return ChartOperation.PREDICT;
            }
        }
    }

    public String symbolAfterDot() {
        if (dotPosi >= ruleExpression.size()) {
            return null;
        } else{
            return ruleExpression.get(dotPosi);
        }
    }


    /**
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(startCol + " " + lhs + " ");

        int i;
        for (i = 0; i < dotPosi; i++) {
            builder.append(ruleExpression.get(i) + " ");
        }
        builder.append(". ");

        for (int j = i; j < ruleExpression.size(); j++) {
            builder.append(ruleExpression.get(j));
            if (j == ruleExpression.size() - 1) {
                break;
            }
            builder.append(" ");
        }
        return builder.toString();
    }
    **/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChartEntryKey temp = (ChartEntryKey) o;

        if (startCol != temp.startCol) return false;
        if (dotPosi != temp.dotPosi) return false;
        if (lhs != null ? !lhs.equals(temp.lhs) : temp.lhs != null) return false;
        return ruleExpression != null ? ruleExpression.equals(temp.ruleExpression) : temp.ruleExpression == null;

    }

    @Override
    public int hashCode() {
        int result = startCol;
        result = 31 * result + dotPosi;
        result = 31 * result + (lhs != null ? lhs.hashCode() : 0);
        result = 31 * result + (ruleExpression != null ? ruleExpression.hashCode() : 0);
        return result;
    }
}
