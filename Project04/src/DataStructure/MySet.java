package DataStructure;

import java.util.ArrayList;

public class MySet<A, B, C, D, E, F>{
	private ArrayList<A> sign;
	private ArrayList<B> list;
	private ArrayList<C> amount;
	private ArrayList<D> input;
	private ArrayList<E> edit;
	private ArrayList<F> delete;
	
	public MySet() {
		sign = new ArrayList<A>();
		list = new ArrayList<B>();
		amount = new ArrayList<C>();
		input = new ArrayList<D>();
		edit = new ArrayList<E>();
		delete = new ArrayList<F>();
	}
	
	public void add(A sign, B list, C amount, D input, E edit, F delete) {
		this.sign.add(sign);
		this.list.add(list);
		this.amount.add(amount);
		this.input.add(input);
		this.edit.add(edit);
		this.delete.add(delete);
	}
	
	public void remove(int index) {
		this.sign.remove(index);
		this.list.remove(index);
		this.amount.remove(index);
		this.input.remove(index);
		this.edit.remove(index);
		this.delete.remove(index);
	}
	
	public int size() {
		return sign.size();
	}
	
	public A getSign(int index) {
		return sign.get(index);
	}
	
	public B getList(int index) {
		return list.get(index);
	}
	
	public C getAmount(int index) {
		return amount.get(index);
	}

	public D getInput(int index) {
		return input.get(index);
	}

	public E getEdit(int index) {
		return edit.get(index);
	}

	public F getDelete(int index) {
		return delete.get(index);
	}
}
