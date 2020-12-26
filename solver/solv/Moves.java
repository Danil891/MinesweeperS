package solv;

public enum Moves {
    First("Первый ход"),
    Logical("Логический ход"),
    Random("(псевдо-)Случайный ход");

    Moves(String _message){
        message = _message;
    }
    private String message;
    public String getMessage(){
        return message;
    }
}
