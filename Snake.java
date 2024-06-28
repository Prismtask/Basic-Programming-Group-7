public class Snake{
    int head;
    int tail;
    
    Snake(int head, int tail){
        this.head = head;
        this.tail = tail;
    }
    void setTail(int tail){
        this.tail = tail;
    }
    void setHead(int head){
        this.head = head;
    }
    int getTail(){
        return this.tail;
    }
    int getHead(){
        return this.head;
    }
}