package ca.tcp.utils;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Top-level class for Enumerations implementing Bloch's Typesafe Enum pattern,
 * similar to how he extended it for Java 1.5 (with valueOf() method).
 * When we move to 1.5, change all subclasses of this to J2SE 1.5 enums.
 * See Java Cookbook, 2nd Edition, Chapter 8.
 * See http://www.javaworld.com/javaworld/javatips/jw-javatip122.html for
 * info on Serializable and readResolve(); objects used in HttpSessions
 * are required to be Serializable.
 */
public abstract class Enum implements Serializable {
	/** The name of this class, should be set in a static initializer. */
	protected static String className = "(class not set!)";
	/** The value of this instance */
	private String value;
	/** The maximum number of values an enum can have */
	private static final int INITIAL_SIZE = 10;
	
	private static Enum[] all = new Enum[INITIAL_SIZE];
	private static int allIndex;
	
	
	/** Although this is public, the implementing subclass' constructor must be 
	 * private to ensure typesafe enumeration pattern.
	 */
	public Enum(String val) {
		value = val;
		if (allIndex == all.length) {
			Enum[] tmp = new Enum[all.length * 2];
			System.arraycopy(all, 0, tmp, 0, all.length);
			all = tmp;
		}
		all[allIndex++] = this;
	}
	
	/** Returns the value of this value as a String */
	public String toString() {
		return value;
	}

	/** Returns the given Enum instance for the given String.
	 * @throws IllegalArgumentException if the input is not one of the valid values.
	 */
	public static Enum getValueOf(String s) {
		for (int i = 0; i < allIndex; i++) {
			if (all[i].value.equals(s))	{
				return all[i];
			}
		}
		throw new IllegalArgumentException("Value '" + s + "' is not a valid " + className + " enumeration value.");
	}

	/** Return all the values of this Enumeration */
	public static Enum[] values() {
		return (Enum[])all.clone();
	}
	
	/** Needed to avoid having Serialization create objects that bypass the constructor */
    protected Object readResolve() throws ObjectStreamException
    {
    	System.out.println("readResolve: value = " + value);
        return getValueOf(value);
    }
}
