package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
		
	public ChessMatch() {
		board = new Board(8, 8);							// define o tamanho do tabuleiro
		turn = 1;
		currentPlayer = Color.WHITE;
		// n�o � necess�rio setar 'check' para 'false', pois esta vari�vel j� inicia neste valor por padr�o
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer () {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	public ChessPiece[][] getpieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i=0; i<board.getRows(); i++) {
			for (int j=0; j< board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {	// permite imprimir os movimentos poss�veis da pe�a, a partir de uma posi��o de origem
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	public ChessPiece preformChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);						// respons�vel pela valida��o da posi��o de origem
		validateTargetPosition(source, target);				// respons�vel pela valida��o da posi��o de destino
		Piece capturedPiece = makeMove(source, target);		// 'makeMove' ser� respons�vel por realizar o movimento da pe�a
		
		if (testCheck(currentPlayer)) {						// testa se o jogador se colocou em xeque
			undoMove(source, target, capturedPiece);		// desfaz o movimento
			throw new ChessException("Voce nao pode se colocar em xeque");
		}
		
		check = (testCheck(opponent(currentPlayer))) ? true : false; // testa se o oponente est� em xeque
		
		if (TestCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}
		else {
			nextTurn();											// para trocar o turno do jogador
		}
		
		return (ChessPiece)capturedPiece; 
	}
	
	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece)board.removePiece(source);	// retira a pe�a do tabuleiro
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);		// tira do tabuleiro uma poss�vel pe�a capturada, localizada na posi��o de destino
		board.placePiece(p, target);							// coloca, na posi��o de destino, a pe�a que estava na posi��o de origem
		
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);				// retira a pe�a da lista de pe�as no tabuleiro
			capturedPieces.add(capturedPiece);					// adiciona na lista de pe�as capturadas
		}
		
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece)board.removePiece(target);	// retira a pe�a que chegou na posi��o de destino
		p.decreaseMoveCount();
		board.placePiece(p, source);							// recoloca a pe�a na posi��o de origem
		
		if (capturedPiece != null) {							// teste se houve captura de pe�a
			board.placePiece(capturedPiece, target);			// devolve a pe�a capturada a sua posi��o
			capturedPieces.remove(capturedPiece);				// retira a pe�a capturada da lista de pe�as que foram capturadas
			piecesOnTheBoard.add(capturedPiece);				// adiciona a pe�a na lista de pe�as que est�o no tabuleiro 
		}
	}
	
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("Nao existe peca na posi��o de origem");
		}
		
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
			throw new ChessException("A peca escolhida nao e sua");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("Nao existe movimentos possiveis para a peca escolhida");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {		// testa se a posi��o de destino N�O � um movimento poss�vel para a pe�a de origem	
			throw new ChessException("A peca escolhida nao pode se mover para a posicao de destino"); 
		}
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE; 
	}
	
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("O rei " + color + "nao esta no tabuleiro");
	}
	
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition(); // para descobrir a posi��o do rei
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList()); // descobre a lista de pe�as do oponente
		for (Piece p : opponentPieces) {									// vai testar todas as pe�as do oponente
			boolean[][] mat = p.possibleMoves();								// vai descobrir todos os poss�veis movimentos da pe�a 'p'
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {		// se a posi��o na vari�vel 'mat' coincidir com a posi��o do rei...
				return true;												// quer dizer que o rei est� em cheque, e retorna 'true'
			}
		}
		return false;														// significa que o rei n�o est� em xeque
	}
	
	private boolean TestCheckMate(Color color) {
		if (!testCheck(color)) {												// verifica se este jogador est� em xeque
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList()); // pegar todas as pe�as do jogador
		for (Piece p : list) {											// para descobrir se alguma pe�a do jogador consegue evitar o mate
			boolean[][] mat = p.possibleMoves();
			for (int i=0; i<board.getRows(); i++) {						// percorre as linhas da matriz
				for (int j=0; j<board.getColumns(); j++) {				// percorre as colunas da matriz
					if (mat[i][j]) {
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
		
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());	// coloca a pe�a no tabuleiro
		piecesOnTheBoard.add(piece);											// coloca a pe�a na lista de pe�as do tabuleiro
	}
	
	private void initialSetup() {								// respons�vel por iniciar a partida, colocando as pe�as no tabuleiro
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE));
        
        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
        }
}
