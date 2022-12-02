package com.github.bhlangonijr.chesslib;

import static org.junit.Assert.*;

import java.text.NumberFormat;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;

import com.github.bhlangonijr.chesslib.game.GameContext;
import com.github.bhlangonijr.chesslib.move.Move;

public class JMTest {
	//@Test
	public void test() {
		final long start = System.currentTimeMillis();
		long count = new PerftTest().testPerft("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", 6);
		final long duration = System.currentTimeMillis() - start;
		System.out.println (count+" moves in "+duration+" ms ("+NumberFormat.getInstance().format(count*1000/duration)+" mv/s)");
	}
	
	@Test
	public void testLegalMoves() throws Exception {
		final Board board = new Board();
		board.loadFromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		final int warmupCount = 5000000;
		IntStream.range(0, warmupCount).forEach(i -> board.legalMoves());
		final long moveCount = board.legalMoves().size();
		
		final int count = 2000000;
		for (int i=0;i<1;i++) {
			Chrono c = new Chrono();
			IntStream.range(0, count).forEach(j -> board.legalMoves());
			final long duration = c.time();
			System.out.println("legalMoves -> "+NumberFormat.getInstance().format(moveCount*count*1000/duration)+" mv/s");
		}
	}

	@Test
	public void testMove() throws Exception {
		final Board board = new Board(new GameContext(), false);
		board.loadFromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		final int warmupCount = 5000000;
		final List<Move> moves = board.legalMoves();
		IntStream.range(0, warmupCount).forEach(i -> doAllMoves(board, moves));
		final long moveCount = moves.size();
		
		final int count = 2000000;
		for (int i=0;i<1;i++) {
			Chrono c = new Chrono();
			IntStream.range(0, count).forEach(j -> doAllMoves(board, moves));
			final long duration = c.time();
			System.out.println("Move -> "+NumberFormat.getInstance().format(moveCount*count*1000/duration)+" mv/s");
		}
	}

	private void doAllMoves(final Board board, final List<Move> moves) {
		moves.stream().forEach(m -> {
			board.doMove(m);
			board.undoMove();
		});
	}

	private static class Chrono {
		long start = System.currentTimeMillis();
		public long time() {
			return System.currentTimeMillis()-start;
		}
		public void lap() {
			start = System.currentTimeMillis();
		}
	}
}
