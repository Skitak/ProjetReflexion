package utilisateurs;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.Socket;

public class Service implements Comparable{

	private String name;
	private Class<? extends Runnable> loadedClass;
	private boolean isActive;

	public Service(String name, Class<? extends Runnable> loadedClass) throws Exception {
		
		boolean constructor = false;
		boolean field = false;
		int classMods = loadedClass.getModifiers();
		Constructor<?>[] constrs = loadedClass.getConstructors();
		Field[] fields = loadedClass.getFields();
		Method meth = loadedClass.getMethod("toStringue");
		int methMods = loadedClass.getMethod("toStringue").getModifiers();
		for (Constructor<?> c : constrs) {
			if (!(c.getParameterTypes()[0] == Socket.class) || !(c.getParameterCount() == 1)
					|| !(c.getExceptionTypes().length == 0) || !(Modifier.isPublic(c.getModifiers()))) {
				constructor = true;
			}
		}
		for (Field f : fields) {
			if (f.getType() == Socket.class) {
				if (!Modifier.isPrivate(f.getModifiers()) || !Modifier.isFinal(f.getModifiers())) {
					field = true;
				}
			}
		}
		if (constructor) {
			throw new RuntimeException("Probleme au niveau du constructeur");
		}
		if (field) {
			throw new RuntimeException("Probleme au niveau de l'attribut socket");
		}
		if (!Modifier.isPublic(methMods) || !Modifier.isStatic(methMods) || !(meth.getReturnType() == String.class)
				|| !(meth.getExceptionTypes().length == 0)) {
			throw new RuntimeException("Probleme au niveau de la methode toStringue");
		}
		if (!Modifier.isPublic(classMods)) {
			throw new RuntimeException("Probleme au niveau de la classe");
		}
		
		this.name = name;
		this.loadedClass = loadedClass;
	}

	public String getName() {
		return name;
	}

	public Class<? extends Runnable> getLoadedClass() {
		return loadedClass;
	}

	public void start() throws InstantiationException, IllegalAccessException {
		this.loadedClass.newInstance().run();
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

	void deleteService() {
		// TODO delete service
	}
	
	public boolean isActive(){
		return isActive;
	}

}
