package utilisateurs;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.Socket;

@SuppressWarnings("rawtypes")
public class Service implements Comparable {

	private String name;
	private Class<?> loadedClass;
	private boolean isActive = false;

	public Service(String name, Class<?> loadedClass) throws Exception {
		verifyClass(loadedClass);
		this.name = name;
		this.loadedClass = loadedClass;
	}

	public String getName() {
		return name;
	}

	public Class<?> getLoadedClass() {
		return loadedClass;
	}

	public void start(Socket client) throws Exception {
		Method run = loadedClass.getDeclaredMethod("run");
		run.invoke(loadedClass.getConstructor(Socket.class).newInstance(client));
	}

	@Override
	public int compareTo(Object o) {
		if (!(o instanceof Service))
			return 0;
		return name.compareTo(((Service) o).name);
	}

	void setActive(boolean value) {
		isActive = value;
	}

	public boolean isActive() {
		return isActive;
	}

	void verifyClass(Class<?> loadedClass2) throws Exception {
		if (Modifier.isPrivate(loadedClass2.getModifiers()))
			throw new Exception("La classe est privée et ne respecte donc pas la norme BLTi.");

		boolean hasRightConstructor = false;

		for (Constructor<?> m : loadedClass2.getConstructors())
			if (m.getParameterTypes().length == 1 && m.getParameterTypes()[0] == Socket.class
					&& m.getParameterTypes().length == 1 && m.getExceptionTypes().length == 0)
				hasRightConstructor = true;
		if (!hasRightConstructor)
			throw new Exception(
					"La classe ne possède pas de concstructeur correct et ne respecte donc pas la norme BLTi.");

		boolean hasSocketField = false;
		for (Field f : loadedClass2.getDeclaredFields()) {
			if (f.getType() == Socket.class && Modifier.isFinal(f.getModifiers())
					&& Modifier.isPrivate(f.getModifiers()))
				hasSocketField = true;
		}
		if (!hasSocketField)
			throw new Exception(
					"La classe ne possède pas d'attribut socket privée final et ne respecte donc pas la norme BLTi.");

		if (!Modifier.isStatic((loadedClass2.getMethod("toStringue", (Class<?>[]) null).getModifiers())))
			throw new Exception(
					"La classe ne possède pas de méthode toStringue static et ne respecte donc pas la norme BLTi.");
		boolean methodRunFound = false;
		for (Method m : loadedClass2.getDeclaredMethods()){
			if (m.getName().equals("run") && Modifier.isPublic(m.getModifiers()) && m.getParameterTypes().length == 0)
				methodRunFound = true;
		}
		if (!methodRunFound)
			throw new Exception(
					"La classe ne possède pas de méthode \"run\" et ne respecte donc pas la norme BLTi.");
	}

	void update(Class<?> loadedClass) throws Exception {
		verifyClass(loadedClass);
		this.loadedClass = loadedClass;
	}

	public String toString() {
		return loadedClass.toString();
	}

}
