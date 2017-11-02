import java.util.*;

/**
 *  Create the actual chart.
 */
public class Chart {
    private Vector<ChartColumn> chartTable;
    private List<String> sentence;

    public Chart(ArrayList<String> sentence) {
        this.sentence = sentence;
        chartTable = new Vector<>(sentence.size() + 1);

        // Add "0 ROOT . S" to first column and empty the rest
        chartTable.add(new ChartColumn(0, true));
        for (int i = 1; i < sentence.size() + 1; i++) {
            chartTable.add(new ChartColumn(i));
        }

        //There is one pointer of the entry iterate through each column one by one. 
        //There is also one pointer of the hashmap in the vector to indicate where to add new entries.
        //when the first pointer comes to a entry, the following situation may happen:
        // 1. predict    2. scan  3. attach
        //the end condition is that the entry pointer is at the end of vector's last hashmap
        //there is a lowest weight parse iff in the vector's last hashmap there is a complete ROOT rule with start column 0.

        for (int i = 0; i < sentence.size() + 1; i++) {
            ChartColumn currentColumn = chartTable.get(i);
            while (currentColumn.hasNextKey()) {
                ChartEntryKey key = currentColumn.peekKey();
                switch (key.operation()) {
                    case PREDICT:
                        currentColumn.addPredictions();
                        break;
                    case ATTACH:
                        currentColumn.getNextKey();
                        this.attachChartKey(key, i);
                        break;
                    case SCAN:
                        this.scanKey(key, i);
                        break;
                }
            }
        }
    }

    public void scanKey(ChartEntryKey key, int currentColumnIndex) {
        if (key.operation() != ChartOperation.SCAN) {
            return;
        }

        if (currentColumnIndex >= sentence.size()) {
            chartTable.get(currentColumnIndex).getNextKey();
            return;
        }

        String word = sentence.get(currentColumnIndex);
        ChartColumn nextColumn = chartTable.get(currentColumnIndex + 1);
        Map.Entry<ChartEntryKey, WeightBackPointer> entry = chartTable.get(currentColumnIndex).scannedKeyAgainstWord(word);
        if (entry == null) {
            return;
        }

        nextColumn.addEntry(entry.getKey(), entry.getValue());
    }

    public void attachChartKey(ChartEntryKey key, int currentColumnIndex) {
        if (key.operation() != ChartOperation.ATTACH) {
            return;
        }

        ChartColumn currentColumn = chartTable.get(currentColumnIndex);
        double currentWeight = currentColumn.getWeightForKey(key).getWeight();
        int startColumn = key.getStartColumn();
        ChartColumn targetColumn = chartTable.get(startColumn);

        for (Map.Entry<ChartEntryKey, Double> entry: targetColumn.keysToAttachForKey(key)) {
            ChartEntryKey childKey = entry.getKey();
            double weight = entry.getValue();
            ChartEntryKey newKey = new ChartEntryKey(childKey.getStartColumn(), childKey.getDotPosition() + 1, childKey.getLhs(), childKey.getRhs());
            BackPointer p1 = new BackPointer(childKey, startColumn);            
            BackPointer p2 = new BackPointer(key, currentColumnIndex);
            currentColumn.addEntry(newKey, new WeightBackPointer(weight + currentWeight, p1, p2));
        }
    }

    public double getParseWeight() {
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (ChartColumn c : chartTable) {
            builder.append(c.toString() + "\n");
        }
        return builder.toString();
    }

    public String treeView() {
        WeightBackPointer w = chartTable.lastElement().getWeightForKey(ChartEntryKey.rootEntryKey());
        if (w == null) {
            return "NONE\n";
        }

        StringBuilder builder = new StringBuilder();
        builder.append(treeViewWithKey(ChartEntryKey.rootEntryKey(), w) + ")");
        builder.append('\n');
        builder.append(w.getWeight() + "\n");

        //return builder.toString().substring(1);
        return builder.toString();

    }

    private String treeViewWithKey(ChartEntryKey key, WeightBackPointer p) {
        StringBuilder b = new StringBuilder();
        if (p.getBackPointer1() != null || p.getBackPointer2() != null) {
            if (p.getBackPointer1() != null) {

                WeightBackPointer p1p = chartTable.get(p.getBackPointer1().getColumn()).getWeightForKey(p.getBackPointer1().getKey());
                if (p.getBackPointer2() == null) {
                    b.append(treeViewWithKey(p.getBackPointer1().getKey(), p1p));
                } else {
                    b.append(treeViewWithKey(p.getBackPointer1().getKey(), p1p));
                }

            }
            if (p.getBackPointer2() != null) {
                WeightBackPointer p2p = chartTable.get(p.getBackPointer2().getColumn()).getWeightForKey(p.getBackPointer2().getKey());
                b.append(treeViewWithKey(p.getBackPointer1().getKey(), p2p) + ")");
            }
            if (key.symbolAfterDot() != null && Grammar.isTerminal(key.symbolAfterDot())) {
                b.append(" " + key.symbolAfterDot());
            }
        } else {
            b.append(" (" + key.getLhs());
            if (key.symbolAfterDot() != null && Grammar.isTerminal(key.symbolAfterDot())) {
                b.append(" " + key.symbolAfterDot());
            }
        }
        return b.toString();
    }
}
