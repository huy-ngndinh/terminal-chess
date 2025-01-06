package com.huy_ngndinh.client;

public class PositionInterface {
	private long position;
	public PositionInterface(long position) {
		this.position = position;
	}
	public void reset() {
		position = 0;
	}
	public boolean isEmpty() {
		return position == 0L;
	}
	public boolean isToggle(int index) {
		return (position & (1L << index)) != 0;
	}
	public void toggle(int newPosition) {
		position ^= 1L << newPosition;
	}
	public void toggle(PositionInterface newPosition) {
		position ^= newPosition.position;
	}
	public int findFirst() {
		int answer = -1;
		for (int index = 63; index >= 0; index--) answer = this.isToggle(index) ? index : answer;
		return answer;
	}
}
