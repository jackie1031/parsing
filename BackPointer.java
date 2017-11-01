/**
 * stores the backpointer of the entry in the chart
 * created by Tianyi Lin on 10/29/2017
 */
public class BackPointer {
    private ChartEntryKey key;
    private int column;

    public ChartEntryKey getKey() {
        return key;
    }

    public int getColumn() {
        return column;
    }

    public BackPointer(ChartEntryKey key, int column) {
        this.key = key;
        this.column = column;
    }
}
