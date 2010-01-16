package game.common;

import java.util.HashMap;
import com.jme.input.KeyInput;

/**
 * Utility Keys Converter
 * 
 * @author Andrea Martire, Salvatore Loria, Giuseppe Leone
 */
public class KeyConverter {

	public final static HashMap<String,Integer> stringKeyList = new HashMap<String, Integer>();
	static {
		stringKeyList.put("0", KeyInput.KEY_0);
		stringKeyList.put("1", KeyInput.KEY_1);
		stringKeyList.put("2", KeyInput.KEY_2);
		stringKeyList.put("3", KeyInput.KEY_3);
		stringKeyList.put("4", KeyInput.KEY_4);
		stringKeyList.put("5", KeyInput.KEY_5);
		stringKeyList.put("6", KeyInput.KEY_6);
		stringKeyList.put("7", KeyInput.KEY_7);
		stringKeyList.put("8", KeyInput.KEY_8);
		stringKeyList.put("9", KeyInput.KEY_9);
		stringKeyList.put("A", KeyInput.KEY_A);
		stringKeyList.put("B", KeyInput.KEY_B);
		stringKeyList.put("C", KeyInput.KEY_C);
		stringKeyList.put("D", KeyInput.KEY_D);
		stringKeyList.put("DOWN", KeyInput.KEY_DOWN);
		stringKeyList.put("E", KeyInput.KEY_E);
		stringKeyList.put("F", KeyInput.KEY_F);
		stringKeyList.put("G", KeyInput.KEY_G);
		stringKeyList.put("H", KeyInput.KEY_H);
		stringKeyList.put("I", KeyInput.KEY_I);
		stringKeyList.put("J", KeyInput.KEY_J);
		stringKeyList.put("K", KeyInput.KEY_K);
		stringKeyList.put("L", KeyInput.KEY_L);
		stringKeyList.put("LEFT", KeyInput.KEY_LEFT);	
		stringKeyList.put("LSHIFT", KeyInput.KEY_LSHIFT);
		stringKeyList.put("M", KeyInput.KEY_M);
		stringKeyList.put("N", KeyInput.KEY_N);
		stringKeyList.put("NUMPAD0", KeyInput.KEY_NUMPAD0);
		stringKeyList.put("NUMPAD1", KeyInput.KEY_NUMPAD1);
		stringKeyList.put("NUMPAD2", KeyInput.KEY_NUMPAD2);
		stringKeyList.put("NUMPAD3", KeyInput.KEY_NUMPAD3);
		stringKeyList.put("NUMPAD4", KeyInput.KEY_NUMPAD4);
		stringKeyList.put("NUMPAD5", KeyInput.KEY_NUMPAD5);
		stringKeyList.put("NUMPAD6", KeyInput.KEY_NUMPAD6);
		stringKeyList.put("NUMPAD7", KeyInput.KEY_NUMPAD7);
		stringKeyList.put("NUMPAD8", KeyInput.KEY_NUMPAD8);
		stringKeyList.put("NUMPAD9", KeyInput.KEY_NUMPAD9);
		stringKeyList.put("O", KeyInput.KEY_O);
		stringKeyList.put("P", KeyInput.KEY_P);
		stringKeyList.put("Q", KeyInput.KEY_Q);
		stringKeyList.put("R", KeyInput.KEY_R);
		stringKeyList.put("ENTER", KeyInput.KEY_RETURN);
		stringKeyList.put("RIGHT", KeyInput.KEY_RIGHT);
		stringKeyList.put("RSHIFT", KeyInput.KEY_RSHIFT);
		stringKeyList.put("S", KeyInput.KEY_S);
		stringKeyList.put("SPACE", KeyInput.KEY_SPACE);
		stringKeyList.put("T", KeyInput.KEY_T);	
		stringKeyList.put("U", KeyInput.KEY_U);
		stringKeyList.put("UP", KeyInput.KEY_UP);
		stringKeyList.put("V", KeyInput.KEY_V);
		stringKeyList.put("X", KeyInput.KEY_X);
		stringKeyList.put("Y", KeyInput.KEY_Y);
		stringKeyList.put("W", KeyInput.KEY_W);
		stringKeyList.put("Z", KeyInput.KEY_Z);	
	}
	
	public final static HashMap<String,String> keyStringList = new HashMap<String, String>();
	static {
		keyStringList.put( Integer.toString( KeyInput.KEY_0 ), "0" );
		keyStringList.put( Integer.toString( KeyInput.KEY_1 ), "1" );
		keyStringList.put( Integer.toString( KeyInput.KEY_2 ), "2" );
		keyStringList.put( Integer.toString( KeyInput.KEY_3 ), "3" );
		keyStringList.put( Integer.toString( KeyInput.KEY_4 ), "4" );
		keyStringList.put( Integer.toString( KeyInput.KEY_5 ), "5" );
		keyStringList.put( Integer.toString( KeyInput.KEY_6 ), "6" );
		keyStringList.put( Integer.toString( KeyInput.KEY_7 ), "7" );
		keyStringList.put( Integer.toString( KeyInput.KEY_8 ), "8" );
		keyStringList.put( Integer.toString( KeyInput.KEY_9 ), "9" );
		keyStringList.put( Integer.toString(KeyInput.KEY_A), "A" );
		keyStringList.put( Integer.toString(KeyInput.KEY_B), "B" );
		keyStringList.put( Integer.toString(KeyInput.KEY_C), "C" );
		keyStringList.put( Integer.toString(KeyInput.KEY_D), "D" );
		keyStringList.put( Integer.toString(KeyInput.KEY_DOWN), "DOWN" );
		keyStringList.put( Integer.toString(KeyInput.KEY_E), "E" );
		keyStringList.put( Integer.toString(KeyInput.KEY_F), "F" );
		keyStringList.put( Integer.toString(KeyInput.KEY_G), "G" );
		keyStringList.put( Integer.toString( KeyInput.KEY_H ), "H" );
		keyStringList.put( Integer.toString( KeyInput.KEY_I ), "I" );
		keyStringList.put( Integer.toString( KeyInput.KEY_J ), "J" );
		keyStringList.put( Integer.toString( KeyInput.KEY_K ), "K" );
		keyStringList.put( Integer.toString( KeyInput.KEY_L ), "L" );
		keyStringList.put( Integer.toString( KeyInput.KEY_LEFT ), "LEFT" );	
		keyStringList.put( Integer.toString( KeyInput.KEY_LSHIFT), "LSHIFT" );	
		keyStringList.put( Integer.toString( KeyInput.KEY_M ), "M" );
		keyStringList.put( Integer.toString( KeyInput.KEY_N ), "N" );
		keyStringList.put( Integer.toString( KeyInput.KEY_NUMPAD0 ), "NUMPAD0" );
		keyStringList.put( Integer.toString( KeyInput.KEY_NUMPAD1 ), "NUMPAD1" );
		keyStringList.put( Integer.toString( KeyInput.KEY_NUMPAD2 ), "NUMPAD2" );
		keyStringList.put( Integer.toString( KeyInput.KEY_NUMPAD3 ), "NUMPAD3" );
		keyStringList.put( Integer.toString( KeyInput.KEY_NUMPAD4 ), "NUMPAD4" );
		keyStringList.put( Integer.toString( KeyInput.KEY_NUMPAD5 ), "NUMPAD5" );
		keyStringList.put( Integer.toString( KeyInput.KEY_NUMPAD6 ), "NUMPAD6" );
		keyStringList.put( Integer.toString( KeyInput.KEY_NUMPAD7 ), "NUMPAD7" );
		keyStringList.put( Integer.toString( KeyInput.KEY_NUMPAD8 ), "NUMPAD8" );
		keyStringList.put( Integer.toString( KeyInput.KEY_NUMPAD9 ), "NUMPAD9" );
		keyStringList.put( Integer.toString( KeyInput.KEY_O ), "O" );
		keyStringList.put( Integer.toString( KeyInput.KEY_P ), "P" );
		keyStringList.put( Integer.toString( KeyInput.KEY_Q ), "Q" );
		keyStringList.put( Integer.toString( KeyInput.KEY_R ), "R" );
		keyStringList.put( Integer.toString( KeyInput.KEY_RETURN ), "ENTER" );
		keyStringList.put( Integer.toString( KeyInput.KEY_RIGHT ), "RIGHT" );
		keyStringList.put( Integer.toString( KeyInput.KEY_RSHIFT ), "RSHIFT" );	
		keyStringList.put( Integer.toString( KeyInput.KEY_S ), "S" );
		keyStringList.put( Integer.toString( KeyInput.KEY_SPACE ), "SPACE" );
		keyStringList.put( Integer.toString( KeyInput.KEY_T ), "T" );	
		keyStringList.put( Integer.toString( KeyInput.KEY_U ), "U" );
		keyStringList.put( Integer.toString( KeyInput.KEY_UP ), "UP" );
		keyStringList.put( Integer.toString( KeyInput.KEY_V ), "V" );
		keyStringList.put( Integer.toString( KeyInput.KEY_X ), "X" );
		keyStringList.put( Integer.toString( KeyInput.KEY_Y ), "Y" );
		keyStringList.put( Integer.toString( KeyInput.KEY_W ), "W" );
		keyStringList.put( Integer.toString( KeyInput.KEY_Z ), "Z" );	
	}
	
	public static int toKey( String string ){
		return stringKeyList.get( string );
	}
	
	public static String toString( int key ){
		return keyStringList.get( Integer.toString(key) );
	}
}
