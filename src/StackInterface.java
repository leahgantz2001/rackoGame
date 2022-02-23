

public interface StackInterface <T>{
public void pop() throws StackEmptyException; //remove the top element from the Stack
public void push(T t);  //add another element to the top of the Stack
public T  top() throws StackEmptyException ;  //allow you to view what is currently on the top
    public boolean isEmpty();
}
