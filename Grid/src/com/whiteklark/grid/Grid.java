package com.whiteklark.grid;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Grid {
	private Map<String, Cell> cellMap;
	private int squareCapacity;
	private static final Cell WALL = new Cell("-1", -1,-1);
	Grid(int capacity) {
		this.squareCapacity = capacity;
		cellMap = createGrid(capacity);
	}

	private Map<String, Cell> createGrid(int square) {
		Map<String, Cell> gridMap = new HashMap<>(square);
		for (int x=0;x<square;x++) {
			for(int y=0;y<square;y++) {
				String cellID = x+""+y;
				Cell cell = new Cell(cellID, x,y);
				gridMap.put(cellID, cell);
			}
		}
		return gridMap;
	}
	
	public Cell move(int xStart, int yStart, char... movements) throws InvalidMoveException, InvalidStartException {
		validateStart(xStart, yStart);
		validateMoves(movements);
		String startCellId = xStart+""+yStart;
		Cell startCell = cellMap.get(startCellId);
		Cell destinationCell = startCell;
		for (int i=0;i<movements.length;i++) {
			if (movements[i] == 'L')
				destinationCell = getLeftCellOrElseWall(destinationCell);
			else if (movements[i] == 'R')
				destinationCell = getRightCellOrElseWall(destinationCell);
			else if (movements[i] == 'F')
				destinationCell = getForwardCellOrElseWall(destinationCell);
			if (destinationCell == WALL)
				throw new InvalidMoveException("Hit the Wall");
		}
		return destinationCell;
	}
	
	private void validateStart(int xStart, int yStart) throws InvalidStartException {
		String givenCellId = xStart+""+yStart;
		if (!cellMap.containsKey(givenCellId)) {
			throw new InvalidStartException("The initial position of the car is invalid");
		}
	}
	private void validateMoves(char... movements) throws InvalidMoveException {
		Set<Character> permittedMoves= new HashSet<>();
		permittedMoves.add('L');
		permittedMoves.add('R');
		permittedMoves.add('F');
		for (int i=0;i<movements.length;i++) {
			char movement = movements[i];
			if (!permittedMoves.contains(movement))
				throw new InvalidMoveException("Invalid Movement. Valid movements are L R F");
		}
	}
	protected Cell getLeftCellOrElseWall(Cell currentRoom) {
		Cell leftRoom;
		int xCurrent = currentRoom.getX();
		int yCurrent = currentRoom.getY();
		int xLeft = xCurrent-1;
		int yLeft = yCurrent;
		String roomIdLeft =xLeft+""+yLeft;
		if (this.cellMap.containsKey(roomIdLeft))
			leftRoom = this.cellMap.get(roomIdLeft);
		else 
			leftRoom = WALL;
		return leftRoom;
	}
	protected Cell getRightCellOrElseWall(Cell currentRoom) {
		Cell rightRoom;
		int xCurrent = currentRoom.getX();
		int yCurrent = currentRoom.getY();
		int xRight = xCurrent+1;
		int yRight = yCurrent;
		String roomIdLeft =xRight+""+yRight;
		if (this.cellMap.containsKey(roomIdLeft))
			rightRoom = this.cellMap.get(roomIdLeft);
		else 
			rightRoom = WALL;
		return rightRoom;
	}
	protected Cell getForwardCellOrElseWall(Cell currentRoom) {
		Cell upRoom;
		int xCurrent = currentRoom.getX();
		int yCurrent = currentRoom.getY();
		int xUp = xCurrent;
		int yUp = yCurrent+1;
		String roomIdLeft =xUp+""+yUp;
		if (this.cellMap.containsKey(roomIdLeft))
			upRoom = this.cellMap.get(roomIdLeft);
		else 
			upRoom = WALL;
		return upRoom;
	}
	
	protected Map<String, Cell> getCellMap() {
		return cellMap;
	}

	protected int getSquareCapacity() {
		return squareCapacity;
	}

	protected Cell getCurrentCell(int x, int y) {
		String roomId =x+""+y;
		return this.cellMap.get(roomId);
	}
}
