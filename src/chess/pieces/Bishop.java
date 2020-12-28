package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

	public Bishop(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "B";
	}
 
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		// verificar se as casas a noroeste do bispo estão livres
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posição existir, e não houver uma peça nela...
			mat[p.getRow()][p.getColumn()] = true; // seta a posição em 'true', ou seja, a peça pode se mover para esta posição
			p.setValues(p.getRow() - 1, p.getColumn() - 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // se a posição existir, e houver uma peça adversária nela...
			mat[p.getRow()][p.getColumn()] = true; // seta a posição em true, capturando a peça adversária
		}

		// verificar se as casas a nordeste do bispo estão livres
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posição existir, e não houver uma peça nela...
			mat[p.getRow()][p.getColumn()] = true; // seta a posição em 'true', ou seja, a peça pode se mover para esta posição
			p.setValues(p.getRow() - 1, p.getColumn() + 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { 			// se a posição esistir, e houver uma peça adversária nela...
				mat[p.getRow()][p.getColumn()] = true; 							// seta a posição em true, capturando a peça adversária
		}

		// verificar se as casas a sudeste do bispo estão livres
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posição existir, e não houver uma peça nela...
			mat[p.getRow()][p.getColumn()] = true; // seta a posição em 'true', ou seja, a peça pode se mover para esta posição
			p.setValues(p.getRow() + 1, p.getColumn() + 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // se a posição existir, e houver uma peça adversária nela...
			mat[p.getRow()][p.getColumn()] = true; // seta a posição em true, capturando a peça adversária
		}

		// verificar se as casas a sudoeste do bispo estão livres
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posição existir, e não houver uma peça nela...
			mat[p.getRow()][p.getColumn()] = true; // seta a posição em 'true', ou seja, a peça pode se mover para esta posição
			p.setValues(p.getRow() + 1, p.getColumn() - 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // se a posição esistir, e houver uma peça adversária nela...
			mat[p.getRow()][p.getColumn()] = true; // seta a posição em true, capturando a peça adversária
		}

		return mat;
	}
}
