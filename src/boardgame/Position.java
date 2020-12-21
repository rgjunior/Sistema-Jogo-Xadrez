package boardgame;

public class Position {
	
	private int row;						// vari�veis que ir�o definir a posi��o no tabuleiro (linha e coluna
	private int column;
	
	public Position(int row, int column) {	// construtor com argumentos
		this.row = row;
		this.column = column;
	}

	public int getRow() {					// getters e setters do 'row' e 'column'
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	public void setValues (int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	@Override
	public String toString() {					// m�todo para imprimir a posi��o na tela
		return row + ", " + column;
	}

}
