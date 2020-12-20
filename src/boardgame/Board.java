package boardgame;					// classe do tabuleiro

public class Board {
	
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		if (rows < 1 || columns < 1) {				// programação defensiva: vai testar se há pelo menos uma linha ou coluna criada, e retornar msg de erro, caso seja constatado erro
			throw new BoardException("Erro ao criar tabuleiro: deve haver ao menos uma linha ou uma coluna");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Piece piece(int row, int column) {
		if (!positionExists(row, column)) {						// programação defensiva: verifica se a posição não existe. Em caso positivo, mostra a mensagem de erro
			throw new BoardException("Não há esta posição no tabuleiro");
		}
		return pieces[row][column];
	}
	
	public Piece piece(Position position) {
		if (!positionExists(position)) {						// programação defensiva: verifica se a posição não existe. Em caso positivo, mostra a mensagem de erro
			throw new BoardException("Não há esta posição no tabuleiro");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece (Piece piece, Position position) {				// método responsável por colocar as peças na posição inicial
		if (thereIsAPiece(position)) {					// programação defensiva: testa se já há alguma peça posicionada na posição de destino
			throw new BoardException("Já existe uma peça na posição " + position);
		}
		
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
		}
	
	private boolean positionExists(int row, int column) {
		return row >=0 && row < rows && column >=0 && column < columns;			// condição para saber se uma posição existe no tabuleiro
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean thereIsAPiece(Position position) {
		if (!positionExists(position)) {						// programação defensiva: verifica se a posição não existe. Em caso positivo, mostra a mensagem de erro
			throw new BoardException("Não há esta posição no tabuleiro");
		}
		return piece(position) != null;
	}
}
