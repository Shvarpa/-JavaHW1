package interfaces;

public interface Stack<E>{
	public void push(E item);
	public E pop();
	public E peek();
	public boolean isEmpty();
}
