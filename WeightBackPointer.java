public class WeightBackPointer {
    private BackPointer backPointer1;
    private BackPointer backPointer2;
    private double weight;


    public WeightBackPointer(double weight, BackPointer backPointer1, BackPointer backPointer2) {
        this.weight = weight;
        this.backPointer1 = backPointer1;
        this.backPointer2 = backPointer2;
    }

    public double getWeight() {
        return weight;
    }

    public BackPointer getBackPointer1() {
        return backPointer1;
    }

    public BackPointer getBackPointer2() {
        return backPointer2;
    }
}
