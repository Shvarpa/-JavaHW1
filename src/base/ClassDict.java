package base;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class ClassDict extends Hashtable<String,Class>{
	public ClassDict() {super();}
	
	public Class getIgnoreCaps(String key) {
		StringRange keys=new StringRange(this.keySet());
		return this.get(keys.FixCaps(key));
	}
	
	Object createInstance(String className, Object[] parameters) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<Class> loadParametersClasses=new ArrayList<>();
		Class[] parametersClasses= new Class[parameters.length];
		for(int i=0;i<parameters.length;i++) {parametersClasses[i]=parameters[i].getClass();}
		return this.getIgnoreCaps(className).getConstructor(parametersClasses).newInstance(parameters);
	}
}
