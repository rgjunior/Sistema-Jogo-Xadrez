package boardgame;					// classe do tabuleiro

public class Board {
	
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		if (rows < 1 || columns < 1) {				// programa��o defensiva: vai testar se h� pelo menos uma linha ou coluna criada, e retornar msg de erro, caso seja constatado erro
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
		if (!positionExists(row, column)) {						// programa��o defensiva: verifica se a posi��o n�o existe. Em caso positivo, mostra a mensagem de erro
			throw new BoardException("N�o h� esta posi��o no tabuleiro");
		}
		return pieces[row][column];
	}
	
	public Piece piece(Position position) {
		if (!positionExists(position)) {						// programa��o defensiva: verifica se a posi��o n�o existe. Em caso positivo, mostra a mensagem de erro
			throw new BoardException("N�o h� esta posi��o no tabuleiro");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece (Piece piece, Position position) {				// m�todo respons�vel por colocar as pe�as na posi��o inicial
		if (thereIsAPiece(position)) {					// programa��o defensiva: testa se j� h� alguma pe�a posicionada na posi��o de destino
			throw new BoardException("J� existe uma pe�a na posi��o " + position);
		}
		
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
		}
	
	private boolean positionExists(int row, int column) {
		return row >=0 && row < rows && column >=0 && column < columns;			// condi��o para saber se uma posi��o existe no tabuleiro
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean thereIsAPiece(Position position) {
		if (!positionExists(position)) {						// programa��o defensiva: verifica se a posi��o n�o existe. Em caso positivo, mostra a mensagem de erro
			throw new BoardException("N�o h� esta posi��o no tabuleiro");
		}
		return piece(position) != null;
	}
}
