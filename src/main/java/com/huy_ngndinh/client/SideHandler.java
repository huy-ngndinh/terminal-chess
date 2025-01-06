package com.huy_ngndinh.client;

public class SideHandler {
	private boolean currentSide;
	public SideHandler(boolean side) {
		currentSide = side;
	}
	public boolean getSide() {
		return currentSide;
	}
	public void switchSide() {
		currentSide = !currentSide;
	}
}
