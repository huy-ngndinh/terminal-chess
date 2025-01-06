package com.huy_ngndinh.client;

public class CounterHandler {
	private int counter;
	public CounterHandler(int counter) {
		this.counter = counter;
	}
	public int returnCounter() {
		return counter;
	}
	public void incrementCounter() {
		counter++;
	}
}
