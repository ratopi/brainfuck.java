package de.ratopi.brainfuck;

import java.util.ArrayList;
import java.util.List;

/**
 * <p/>
 * Creation Date: 30.07.2004 13:56:12<br/>
 * 
 * @author Ralf Th. Pietsch <ratopi@abwesend.de>
 */
public class Brainfuck implements Runnable
{
	public static void main( final String[] args )
	{
		if ( args.length == 0 )
		{
			final String helloWorld = "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.";
			System.out.println( "Usage: java " + Brainfuck.class.getName() + " <brainfuck code> [<brainfuck code> ...]" );
			System.out.println();
			System.out.println( "If you are new to brainfuck, try:" );
			System.out.println( "java " + Brainfuck.class.getName() + " \"" + helloWorld + "\"" );
			System.exit( 1 );
		}

		for ( final String program : args )
		{
			new Brainfuck( program ).run();
		}
	}

	// ===

	private final int memoryIncrement = 60000;

	private int[] memory = new int[ 60000 ];
	private String programText;
	private int programPointer;
	private int memoryPointer;
	private List<Integer> loopStarts = new ArrayList<Integer>();

	// ==== constructors ====

	public Brainfuck( final String programText )
	{
		this.programText = programText;
	}

	// ==== interface Runnable implementation ====

	public void run()
	{
		programPointer = 0;

		int command;
		while ( ( command = readNextCharacter() ) != -1 )
		{
			programPointer++;
			switch ( command )
			{
				case '.': print(); break;
				case ',': input(); break;
				case '+': increment(); break;
				case '-': decrement(); break;
				case '<': moveLeft(); break;
				case '>': moveRight(); break;
				case '[': loopStart(); break;
				case ']': loopEnd(); break;
			}
		}
	}

	// ==== private methods ====

	private int readNextCharacter()
	{
		if ( programPointer < programText.length() )
		{
			return programText.charAt( programPointer );
		}
		else
		{
			return -1;
		}
	}

	// --- brainfuck commands implementation ---

	private void input()
	{
		throw new UnsupportedOperationException( "Not yet implemented" );
	}

	private void loopEnd()
	{
		if ( memory[ memoryPointer ] == 0 )
		{
			loopStarts.remove( 0 );
		}
		else
		{
			programPointer = loopStarts.get( 0 );
		}
	}

	private void loopStart()
	{
		loopStarts.add( 0, programPointer );
	}

	private void moveRight()
	{
		memoryPointer++;

		if ( memoryPointer >= memory.length )
		{
			int[] newMemory = new int[ memory.length + memoryIncrement ];
			System.arraycopy( memory, 0, newMemory, 0, memory.length );
			memory = newMemory;
		}
	}

	private void moveLeft()
	{
		memoryPointer--;
		if ( memoryPointer < 0 )
		{
			throw new RuntimeException( "Memory underflow" );
		}
	}

	private void decrement()
	{
		memory[ memoryPointer ]--;
	}

	private void increment()
	{
		memory[ memoryPointer ]++;
	}

	private void print()
	{
		System.out.print( (char) memory[ memoryPointer ] );
	}
}
