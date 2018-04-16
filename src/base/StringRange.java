//Pavel Shvarchov - 319270583
//Mordy Dabah - 203507017

package base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class StringRange extends ArrayList<String>{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StringRange(String[] range) {super(Arrays.asList(range));}
	public StringRange(Set<String> range) {super(range);}
	public StringRange(List<String> range) {super(range);}
	
	public boolean containsIgnoreCaps(String other) {
		for(String s:this) {
			if (s.equalsIgnoreCase(other)) return true;
		}
		return false;
	}
	
	public int indexOfIgnoreCaps(String other) {
		for(String s:this) {
			if (s.equalsIgnoreCase(other)) return this.indexOf(s);
		}
		return -1;
	}
	
	public String FixCaps(String input) {
		int index=this.indexOfIgnoreCaps(input);
		return (index>-1 ? this.get(index) : input);
	}
}
