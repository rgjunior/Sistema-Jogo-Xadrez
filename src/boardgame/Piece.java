package boardgame;

public abstract class Piece {
	
	protected Position position;			// foi colocada com 'protected', para não ficar visível na camada do jogo
	private Board board;
	
	public Piece(Board board) {				// construtor somente do tabuleiro (board) porque, inicialmente, as posições do tabuleiro...
		this.board = board;					// ... iniciarão vazias. Então não é necessário fazer um construtor do 'position'
		position = null;					// não é necessário setar 'position' como 'null' pois, por padrão do Java, já inicia 'null'
	}

	protected Board getBoard() {			// get do 'board'. O set foi apagado para não permitir que o tabuleiro seja alterado
		return board;						// o getboard será 'protected', para ser acessado somente na camada do tabuleiro
	}
	
	public abstract boolean[][] possibleMoves();
	
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	
	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		for (int i=0; i<mat.length; i++) {
			for (int j=0; j<mat.length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}
