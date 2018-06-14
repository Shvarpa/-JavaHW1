package classes;

import java.util.LinkedList;

import interfaces.Stack;

public class FixedStack<E> implements Stack<E>{
	private LinkedList<E> stack;
	private int size;
	
	public FixedStack(int size){
		this.size = size;
		this.stack = new LinkedList<E>();
	}
	@Override
	public synchronized void push(E item) {
		stack.addFirst(item);
		if(stack.size()>size)
			stack.removeLast();
	}
	@Override
	public synchronized E pop() {
		if(stack.size()>=1)
			return stack.removeFirst();
		return null;
	}
	@Override
	public synchronized E peek() {
		return stack.getFirst();
	}
	@Override
	public synchronized boolean isEmpty() {
		return stack.isEmpty();
	}
	
	@Override
	public int size() {
		return stack.size();
	}
}