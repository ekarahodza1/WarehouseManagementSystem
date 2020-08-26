package sample.data;

public enum Type {
    CLOTHES(1),
    FOOD(2),
    HYGENE(3),
    ELECTRONICS(4),
    CONSTRUCTION_MATERIALS(5);

    private final int value;
    Type(int value) { this.value = value; }
    public int getValue() { return value; }
}
