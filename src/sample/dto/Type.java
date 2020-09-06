package sample.dto;

public enum Type {
    CLOTHES(1),
    FOOD(2),
    HYGENE(3),
    ELECTRONICS(4),
    CONSTRUCTION(5);

    private final int value;
    Type(int value) { this.value = value; }
    public int getValue() { return value; }
    public Type getType (int i) {
        if (i == 1) return Type.CLOTHES;
        if (i == 2) return Type.FOOD;
        if (i == 3) return Type.HYGENE;
        if (i == 4) return Type.ELECTRONICS;
        if (i == 5) return Type.CONSTRUCTION;
        return null;
    }
}
