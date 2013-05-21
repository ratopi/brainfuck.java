package de.ratopi.brainfuck;

import java.util.ArrayList;

/**
 * <p/>
 * Creation Date: 30.07.2004 13:56:12<br/>
 * 
 * @author Ralf Th. Pietsch <ratopi@abwesend.de>
 */
public class Brainfuck
{
	public static void main( String[] args )
	{
		final String program;
		
		if ( args.length > 0 )
		{
			program = args[ 0 ];
		}
		else
		{
			program = "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.";
		}

		new Brainfuck( program ).run();
	}

	private int[] memory = new int[ 60000 ];
	private char[] commands;
	private int programPointer;
	private int memoryPointer;
	private ArrayList loopStarts = new ArrayList();

	public Brainfuck( String commands )
	{
		this.commands = commands.toCharArray();
	}

	public void run()
	{
		programPointer = 0;
		while ( programPointer < commands.length )
		{
			final char command = commands[ programPointer ];
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
			programPointer = ( (Integer) loopStarts.get( 0 ) ).intValue();
		}
	}

	private void loopStart()
	{
		loopStarts.add( 0, new Integer( programPointer ) );
	}

	private void moveRight()
	{
		memoryPointer++;
	}

	private void moveLeft()
	{
		memoryPointer--;
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
