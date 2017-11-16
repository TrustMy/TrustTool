package com.trust.demo.trusttool.work.first;

public class UnidirectionalLinkedList <E>{
	private Node<E> head;
	private Node<E> last;
	public int size;
	
	public UnidirectionalLinkedList(){}


	/**
	 * 添加
	 * @param e
	 */
	public void add(E e){
		if(e==null){
			return;
		}
		tailAdd(e);
	}

	
	public void tailAdd(E e){
		Node<E> newNode = new Node<E>(null, e);
		
		if(head==null){
			head = newNode;
		}else{
			Node<E> tail = head;
			while(tail.next != null){
				 tail = tail.next;
			}
			tail.next = newNode;
		}
		size++;
	}
	

	
	static class Node <E>{
		Node<E> next;
		E item;
		public Node(Node<E> next,E item){
			this.next = next;
			this.item = item;
		}
	}
	
	
	public void logCat(){
		Node<E> newNode = head;
		while(newNode!=null){
			System.out.println(newNode.item+",");
			newNode = newNode.next;
		}
		System.out.println("logCat End");
	}


	public void reverse(){
		Node<E> headNode = head;
		Node<E> tail = null;
		while (headNode!=null){
			Node<E> node = headNode;
			headNode = headNode.next;
			node.next = tail;
			tail = node;
		}
		head = tail;
	}
}
