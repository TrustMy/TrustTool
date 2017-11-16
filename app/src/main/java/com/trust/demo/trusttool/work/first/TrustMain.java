package com.trust.demo.trusttool.work.first;

public class TrustMain {

	public static void main(String[] args) {
		UnidirectionalLinkedList<Integer> linkedList = new UnidirectionalLinkedList<>();
		linkedList.add(0);
		linkedList.add(1);
		linkedList.add(2);
		linkedList.add(3);
		
		linkedList.logCat();
		linkedList.reverse();
		linkedList.logCat();

		System.out.println("size :"+linkedList.size);

	}

}
