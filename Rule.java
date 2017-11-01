import java.util.*;

/**
 * Object to store rule with weight, LHS and the expression
 * written by Tianyi Lin on 10/29/2017
 */
public class Rule {
    private double weight;
    private String lhs;
    private ArrayList<String> entireRule;

    //constructor
    public Rule(double weight, String lhs, ArrayList<String> entireRule) {
        this.weight = weight;
        this.lhs = lhs;
        this.entireRule = entireRule;
    }

    public double getWeight() {
        return weight;
    }

    public String getLhs() {
        return lhs;
    }

    public ArrayList<String> getRuleExpression() {
        return entireRule;
    }

    @Override
    public String toString() {
        return "\"" + weight +" " + entireRule + "\"";
    }
}
