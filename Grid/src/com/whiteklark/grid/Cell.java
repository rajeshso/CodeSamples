package com.whiteklark.grid;

public class Cell {
	public Cell(String roomId, int x, int y) {
		super();
		this.roomId = roomId;
		this.x = x;
		this.y = y;
	}
	/**
	 * This is an unique identifier for the space
	 */
	private String roomId;
	/**
	 * This is part of the Geo Location or address of the Cell
	 */
	private int x;
	/**
	 * This is part of the Geo Location or address of the Cell
	 */
	private int y;
	
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	@Override
	public String toString() {
		return "Cell [roomId=" + roomId + ", x=" + x + ", y=" + y + "]";
	}
	
	
	
}
