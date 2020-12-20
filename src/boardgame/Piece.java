package boardgame;

public abstract class Piece {
	
	protected Position position;			// foi colocada com 'protected', para n�o ficar vis�vel na camada do jogo
	private Board board;
	
	public Piece(Board board) {				// construtor somente do tabuleiro (board) porque, inicialmente, as posi��es do tabuleiro...
		this.board = board;					// ... iniciar�o vazias. Ent�o n�o � necess�rio fazer um construtor do 'position'
		position = null;					// n�o � necess�rio setar 'position' como 'null' pois, por padr�o do Java, j� inicia 'null'
	}

	protected Board getBoard() {			// get do 'board'. O set foi apagado para n�o permitir que o tabuleiro seja alterado
		return board;						// o getboard ser� 'protected', para ser acessado somente na camada do tabuleiro
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
