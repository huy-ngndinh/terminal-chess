package com.huy_ngndinh.client;

public class SelectionHandler {
	private int index;
	public SelectionHandler() {
		index = 0;
	}
	public int getCurrent() {
		return index;
	}
	public void moveLeft() {
		if (index % 8 == 0) {
			index += 7;
		} else {
			index -= 1;
		}
	}
	public void moveRight() {
		if ((index + 1) % 8 == 0) {
			index -= 7;
		} else {
			index += 1;
		}
	}
	public void moveUp() {
		if (index >= 56) {
			index = index - 56;
		} else {
			index += 8;
		}
	}
	public void moveDown() {
		if (index <= 7) {
			index = index + 56;
		} else {
			index -= 8;
		}
	}
}
