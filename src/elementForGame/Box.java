package elementForGame;

public enum Box {
    ZERO,
    NUM1,
    NUM2,
    NUM3,
    NUM4,
    NUM5,
    NUM6,
    NUM7,
    NUM8,
    BOMB,
    OPENED,
    CLOSED,
    FLAGED,
    BOMBED,
    NOBOMB;

    public Object image;

    Box getNextNumberBox(){
        return Box.values()[this.ordinal() + 1];
    }

    int getNumber() {       //Узнать какая цифра
        return this.ordinal();
    }
}
