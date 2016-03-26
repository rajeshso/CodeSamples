package com.db.tw.distribution.config;

/**
 * 
 * @author Mukesh
 *
 */
public class BlockClassLoader extends ClassLoader {

	public IBlock invokeClassMethod(String classBinName) {
		IBlock config = null;
		try {
			ClassLoader classLoader = this.getClass().getClassLoader();
			Class<?> loadedClass = classLoader.loadClass(classBinName);
			System.out.println("Loaded class name: " + loadedClass.getName());
			if (loadedClass != null) {
				Object classObject = loadedClass.newInstance();
				if (classObject instanceof IBlock) {
					config = (IBlock) classObject;
				}
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Unable to find the class {0} for the config "
					+ classBinName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return config;
	}
}
