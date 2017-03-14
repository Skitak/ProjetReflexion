package utilisateurs;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.Socket;

public class Service implements Comparable{

	private String name;
	private Class<? extends Runnable> loadedClass;
	private boolean isActive = false;

	public Service(String name, Class<? extends Runnable> loadedClass) throws Exception {
		verifyClass(loadedClass);
		this.name = name;
		this.loadedClass = loadedClass;
	}

	public String getName() {
		return name;
	}

	public Class<? extends Runnable> getLoadedClass() {
		return loadedClass;
	}

	public void start(Socket client) throws Exception {
		this.loadedClass.getConstructor(Socket.class).newInstance(client);
	}

	@Override
	public int compareTo(Object o) {
		if (!(o instanceof Service))
			return 0;
		return name.compareTo(((Service)o).name);
	}

	void setActive(boolean value){
		isActive = value;
	}

	public boolean isActive(){
		return isActive;
	}

	void verifyClass(Class<? extends Runnable> loadedClass) throws Exception{
		if (Modifier.isPrivate(loadedClass.getModifiers()))
			throw new Exception("La class est priv�e et ne respecte donc pas la norme BLTi");

		boolean hasRightConstructor = false;

		for (Constructor<?> m : loadedClass.getConstructors())
			if ( m.getParameterTypes().length == 1 && m.getParameterTypes()[0] == Socket.class && m.getParameterTypes().length == 1 && m.getExceptionTypes().length == 0)
				hasRightConstructor = true;
		if (!hasRightConstructor)
			throw new Exception("La class ne poss�de pas de concstructeur correct et ne respecte donc pas la norme BLTi");

		boolean hasSocketField = false;
		for (Field f : loadedClass.getDeclaredFields()){
			if (f.getType() == Socket.class && Modifier.isFinal(f.getModifiers()) && Modifier.isPrivate(f.getModifiers()))
				hasSocketField = true;
		}
		if (!hasSocketField)
			throw new Exception("La class ne poss�de pas d'attribut socket priv�e final et ne respecte donc pas la norme BLTi");

		if (!Modifier.isStatic((loadedClass.getMethod("toStringue",(Class<?>[]) null).getModifiers())))
			throw new Exception("La class ne poss�de pas de m�thode toStringue static et ne respecte donc pas la norme BLTi");
	}
	
	void update(Class<? extends Runnable> loadedClass) throws Exception{
		verifyClass(loadedClass);
		this.loadedClass = loadedClass;
	}
	
	public String toString(){
		return loadedClass.toString();
	}

}
