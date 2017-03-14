package blti;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ServiceRegistry {

	static {
		servicesClasses = new Vector<>();
	}
	private static List<Class<? extends Service>> servicesClasses;

	// ajoute une classe de service après contrôle de la norme BLTi
	public static void addService(Class<? extends Service> classChange) throws Exception {
		if (Modifier.isPrivate(classChange.getModifiers()))
			throw new Exception("La class est privée et ne respecte donc pas la norme BLTi");

		boolean hasRightConstructor = false;

		for (Constructor<?> m : classChange.getConstructors())
			if ( m.getParameterTypes().length == 1 && m.getParameterTypes()[0] == Socket.class && m.getParameterTypes().length == 1 && m.getExceptionTypes().length == 0)
				hasRightConstructor = true;
		if (!hasRightConstructor)
			throw new Exception("La class ne possède pas de concstructeur correct et ne respecte donc pas la norme BLTi");

		boolean hasSocketField = false;
		for (Field f : classChange.getDeclaredFields()){
			if (f.getType() == Socket.class && Modifier.isFinal(f.getModifiers()) && Modifier.isPrivate(f.getModifiers()))
				hasSocketField = true;
		}
		if (!hasSocketField)
			throw new Exception("La class ne possède pas d'attribut socket privée final et ne respecte donc pas la norme BLTi");

		if (!Modifier.isStatic((classChange.getMethod("toStringue",(Class<?>[]) null).getModifiers())))
			throw new Exception("La class ne possède pas de méthode toStringue static et ne respecte donc pas la norme BLTi");
		// si non conforme --> exception avec message clair
		servicesClasses.add(classChange);
	}

	// renvoie la classe de service (numService -1)	
	public static Class<? extends Service> getServiceClass(int numService) {
		return servicesClasses.get(--numService);
	}

	public static String toStringue(){
		String result = "Activités présentes :##";
		try{
			synchronized (servicesClasses) {
				for (Class<? extends Service> classe : servicesClasses){
					String toStringue = (String) classe.getMethod("toStringue").invoke(null);
					result += toStringue;
				}
			}
		}
		catch (Exception e){e.printStackTrace();}
		return result;
	}

}
