

public class StackEmptyException extends Exception {
	public StackEmptyException(){
		super("The stack is empty!");
	}
	
	public StackEmptyException(String str) {
		super(str);
	}
	}
