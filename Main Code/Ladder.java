public class Ladder{
    int fromPosition;
    int toPosition;
    
    Ladder(int from, int to){
        this.fromPosition = from;
        this.toPosition = to;
    }
    void setFromPosition(int from){
        this.fromPosition =  from;
    }
    void setToPosition(int to){
        this.toPosition = to;
    }
    int getFromPosition(){
        return fromPosition;
    }
    int getToPosition(){
        return toPosition;
    }
}