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

		// verificar se as casas acima da torre est�o livres
		p.setValues(position.getRow() - 1, position.getColumn());
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posi��o existir, e n�o houver uma pe�a nela...
			mat[p.getRow()][p.getColumn()] = true; // seta a posi��o em 'true', ou seja, a pe�a pode se mover para esta posi��o
			p.setRow(p.getRow() - 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // se a posi��o existir, e houver uma pe�a advers�ria nela...
			mat[p.getRow()][p.getColumn()] = true; // seta a posi��o em true, capturando a pe�a advers�ria
		}

		// verificar se as casas a esquerda da torre est�o livres
		p.setValues(position.getRow(), position.getColumn() - 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posi��o existir, e n�o houver uma pe�a nela...
			mat[p.getRow()][p.getColumn()] = true; // seta a posi��o em 'true', ou seja, a pe�a pode se mover para esta posi��o
			p.setColumn(p.getColumn() - 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { 			// se a posi��o esistir, e houver uma pe�a advers�ria nela...
				mat[p.getRow()][p.getColumn()] = true; 							// seta a posi��o em true, capturando a pe�a advers�ria
		}

		// verificar se as casas a direita da torre est�o livres
		p.setValues(position.getRow(), position.getColumn() + 1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posi��o existir, e n�o houver uma pe�a nela...
			mat[p.getRow()][p.getColumn()] = true; // seta a posi��o em 'true', ou seja, a pe�a pode se mover para esta posi��o
			p.setColumn(p.getColumn() + 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // se a posi��o existir, e houver uma pe�a advers�ria nela...
			mat[p.getRow()][p.getColumn()] = true; // seta a posi��o em true, capturando a pe�a advers�ria
		}

		// verificar se as casas abaixo da torre est�o livres
		p.setValues(position.getRow() + 1, position.getColumn());
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posi��o existir, e n�o houver uma pe�a nela...
			mat[p.getRow()][p.getColumn()] = true; // seta a posi��o em 'true', ou seja, a pe�a pode se mover para esta posi��o
			p.setRow(p.getRow() + 1);
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // se a posi��o esistir, e houver uma pe�a advers�ria nela...
			mat[p.getRow()][p.getColumn()] = true; // seta a posi��o em true, capturando a pe�a advers�ria
		}

		return mat;
	}
}
