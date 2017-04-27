package com.whiteklark.grid;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.Map;

import org.junit.Test;

public class TestGrid {

	@Test
	public void testGridConstructionCapacity() {
		Grid grid = new Grid(5);
		assertThat(grid.getSquareCapacity()).isEqualTo(5);
	}

	@Test
	public void testGridCells() {
		Grid grid = new Grid(3);
		Map<String, Cell> cellMap = grid.getCellMap();
		assertThat(cellMap.keySet().size()).isEqualTo(9);
		assertThat(cellMap.keySet()).contains("00","01","02", "10","11","12", "20","21","22");
	}
	
	@Test
	public void testMoveToLeft() {
		Grid grid = new Grid(3);
		Cell currentCell = grid.getCurrentCell(1, 1);
		assertThat(currentCell.getRoomId()).isEqualTo("11");
		Cell newCell = grid.getLeftCellOrElseWall(currentCell);
		assertThat(newCell.getRoomId()).isEqualTo("01");
		assertThat(newCell.getX()).isEqualTo(0);
		assertThat(newCell.getY()).isEqualTo(1);
	}
	
	@Test
	public void testMoveToRight() {
		Grid grid = new Grid(3);
		Cell currentCell = grid.getCurrentCell(0, 1);
		assertThat(currentCell.getRoomId()).isEqualTo("01");
		Cell newCell = grid.getRightCellOrElseWall(currentCell);
		assertThat(newCell.getRoomId()).isEqualTo("11");
		assertThat(newCell.getX()).isEqualTo(1);
		assertThat(newCell.getY()).isEqualTo(1);
	}
	
	@Test
	public void testMoveUp() {
		Grid grid = new Grid(3);
		Cell currentCell = grid.getCurrentCell(1, 1);
		assertThat(currentCell.getRoomId()).isEqualTo("11");
		Cell newCell = grid.getForwardCellOrElseWall(currentCell);
		assertThat(newCell.getRoomId()).isEqualTo("12");
		assertThat(newCell.getX()).isEqualTo(1);
		assertThat(newCell.getY()).isEqualTo(2);
	}
	
	@Test
	public void testMoveTwoLefts() {
		Grid grid = new Grid(3);
		Cell currentCell = grid.getCurrentCell(2, 2);
		assertThat(currentCell.getRoomId()).isEqualTo("22");
		char[] movements = {'L','L'};
		Cell newCell;
		try {
			newCell = grid.move(currentCell.getX(), currentCell.getY(), movements);
			assertThat(newCell.getRoomId()).isEqualTo("02");
			assertThat(newCell.getX()).isEqualTo(0);
			assertThat(newCell.getY()).isEqualTo(2);
		} catch (InvalidMoveException | InvalidStartException e) {
			fail("Exception is not expected here");
		}
	}
	@Test
	public void testMoveRF() {
		Grid grid = new Grid(15);
		Cell currentCell = grid.getCurrentCell(5, 5);
		assertThat(currentCell.getRoomId()).isEqualTo("55");
		char[] movements = {'R','F'};  
		Cell newCell;
		try {
			newCell = grid.move(currentCell.getX(), currentCell.getY(), movements);
			assertThat(newCell.getRoomId()).isEqualTo("66");
			assertThat(newCell.getX()).isEqualTo(6);
			assertThat(newCell.getY()).isEqualTo(6);
		} catch (InvalidMoveException | InvalidStartException e) {
			fail("Exception is not expected here");
		}
	}
	@Test
	public void testMoveRFLFRFLF() {
		Grid grid = new Grid(15);
		Cell currentCell = grid.getCurrentCell(5, 5);
		assertThat(currentCell.getRoomId()).isEqualTo("55");
		char[] movements = {'R','F','L','F','R','F','L','F'};  
		Cell newCell;
		try {
			newCell = grid.move(currentCell.getX(), currentCell.getY(), movements);
			assertThat(newCell.getRoomId()).isEqualTo("59");
			assertThat(newCell.getX()).isEqualTo(5);
			assertThat(newCell.getY()).isEqualTo(9);
		} catch (InvalidMoveException | InvalidStartException e) {
			fail("Exception is not expected here");
		}
	}
	@Test
	public void testMoveFFLFFLFFLFF() {
		Grid grid = new Grid(15);
		Cell currentCell = grid.getCurrentCell(6, 6);
		assertThat(currentCell.getRoomId()).isEqualTo("66");
		char[] movements = {'F','F','L','F','F','L','F','F','L','F','F'};
		Cell newCell;
		try {
			newCell = grid.move(currentCell.getX(), currentCell.getY(), movements);
			assertThat(newCell.getRoomId()).isEqualTo("314");
			assertThat(newCell.getX()).isEqualTo(3);
			assertThat(newCell.getY()).isEqualTo(14);
		} catch (InvalidMoveException | InvalidStartException e) {
			fail("Exception is not expected here");
		}
	}

	//Test hitting the wall
	@Test
	public void testHitTheWall() {
		Grid grid = new Grid(3);
		Cell currentCell = grid.getCurrentCell(2, 2);
		assertThat(currentCell.getRoomId()).isEqualTo("22");
		Cell newCell = grid.getForwardCellOrElseWall(currentCell);
		assertThat(newCell.getRoomId()).isEqualTo("-1");
		assertThat(newCell.getX()).isEqualTo(-1);
		assertThat(newCell.getY()).isEqualTo(-1);
	}
	
	@Test(expected=InvalidMoveException.class)
	public void testWithInvalidMoves() throws InvalidMoveException, InvalidStartException {
		Grid grid = new Grid(3);
		Cell currentCell = grid.getCurrentCell(2, 2);
		assertThat(currentCell.getRoomId()).isEqualTo("22");
		char movements[] = {'A'};
		Cell newCell;
		newCell = grid.move(2, 2, movements);
	}
	
	@Test(expected=InvalidStartException.class)
	public void testWithInvalidStart() throws InvalidMoveException, InvalidStartException {
		Grid grid = new Grid(3);
		Cell currentCell = grid.getCurrentCell(2, 2);
		assertThat(currentCell.getRoomId()).isEqualTo("22");
		char movements[] = {'L'};
		Cell newCell;
		newCell = grid.move(9, 9, movements);
	}
}
