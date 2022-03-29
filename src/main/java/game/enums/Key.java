package game.enums;

/**
 * @author NGUYEN TIEN HUNG
 */
public enum Key {
    None(0),
    Up(1),
    Down(2),
    Left(3),
    Right(4),
    Space(5);

    private final int id;

    private Key(int id) {
        this.id = id;
    }

    public int getID() {
        return this.id;
    }
}
