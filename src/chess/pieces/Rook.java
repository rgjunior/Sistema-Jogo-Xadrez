package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

	public Rook(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "R";
	}
 
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		// verificar se as casas acima da torre estão livres
		p.setValues(position.getRow() - 1, position.getColumn());
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posição existir, e não houver uma peça nela...
			mat[p.getRow()][p.getColumn()] = true; // seta a posição em 'true', ou seja, a peça pode se mover para esta posição
			p.setRow(p.getRow() - 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // se a posição existir, e houver uma peça adversária nela...
			mat[p.getRow()][p.getColumn()] = true; // seta a posição em true, capturando a peça adversária
		}

		// verificar se as casas a esquerda da torre estão livres
		p.setValues(position.getRow(), position.getColumn() - 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posição existir, e não houver uma peça nela...
			mat[p.getRow()][p.getColumn()] = true; // seta a posição em 'true', ou seja, a peça pode se mover para esta posição
			p.setColumn(p.getColumn() - 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { 			// se a posição esistir, e houver uma peça adversária nela...
				mat[p.getRow()][p.getColumn()] = true; 							// seta a posição em true, capturando a peça adversária
		}

		// verificar se as casas a direita da torre estão livres
		p.setValues(position.getRow(), position.getColumn() + 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posição existir, e não houver uma peça nela...
			mat[p.getRow()][p.getColumn()] = true; // seta a posição em 'true', ou seja, a peça pode se mover para esta posição
			p.setColumn(p.getColumn() + 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // se a posição existir, e houver uma peça adversária nela...
			mat[p.getRow()][p.getColumn()] = true; // seta a posição em true, capturando a peça adversária
		}

		// verificar se as casas abaixo da torre estão livres
		p.setValues(position.getRow() + 1, position.getColumn());
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posição existir, e não houver uma peça nela...
			mat[p.getRow()][p.getColumn()] = true; // seta a posição em 'true', ou seja, a peça pode se mover para esta posição
			p.setRow(p.getRow() + 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // se a posição esistir, e houver uma peça adversária nela...
			mat[p.getRow()][p.getColumn()] = true; // seta a posição em true, capturando a peça adversária
		}

		return mat;
	}
}
