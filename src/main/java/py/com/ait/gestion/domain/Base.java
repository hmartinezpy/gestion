package py.com.ait.gestion.domain;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import py.com.ait.gestion.constant.Definiciones;


public class Base {

	@SuppressWarnings("rawtypes")
	public final String asXML() {
		Class clase = this.getClass();
		Field[] fields = clase.getDeclaredFields();
		Annotation[] annotations = null;

		String cadenaFinal = "";
		for (Field f : fields) {
			annotations = f.getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation instanceof javax.persistence.Column) {
					try {
						f.setAccessible(true);

						if (!cadenaFinal.equals(Definiciones.CadenaVacia)) {
							cadenaFinal += Definiciones.Separador;
						}
						if (f.getType().toString()
								.equals("class java.util.Date")) {
							SimpleDateFormat formatter = new SimpleDateFormat(
									"dd/MM/yyyy HH:mm:ss");
							if (f.get(this) != null) {
								cadenaFinal += f.getName() + "="
										+ formatter.format(f.get(this));
							} else {
								cadenaFinal += f.getName() + "=null";
							}

						} else if (f.getType().equals(Timestamp.class)) {
							SimpleDateFormat formatter = new SimpleDateFormat(
									"dd/MM/yyyy HH:mm:ss");
							if (f.get(this) != null) {
								cadenaFinal += f.getName()
										+ "="
										+ formatter.format((Timestamp) f
												.get(this));
							} else {
								cadenaFinal += f.getName() + "=null";
							}
						} else {
							cadenaFinal += f.getName() + "=" + f.get(this);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				if (annotation instanceof javax.persistence.JoinColumn) {

					if (!f.getType().isPrimitive()) {
						try {
							f.setAccessible(true);

							Object obj2 = f.get(this);

							Class clase2 = obj2.getClass();

							Field[] fields2 = clase2.getDeclaredFields();

							Annotation[] annotations2 = null;
							for (Field f2 : fields2) {

								annotations2 = f2.getAnnotations();

								for (Annotation annotation2 : annotations2) {

									if (annotation2 instanceof javax.persistence.Id) {
										try {
											f2.setAccessible(true);
											if (!cadenaFinal
													.equals(Definiciones.CadenaVacia)) {
												cadenaFinal += Definiciones.Separador;
											}

											cadenaFinal += f.getName() + "."
													+ f2.getName() + "="
													+ f2.get(obj2);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								}
							}

						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}

				}

			}
		}

		return cadenaFinal;
	}

	@SuppressWarnings({ "unused", "rawtypes" })
	public final String getTabla() {
		Class clase = this.getClass();
		Field[] fields = clase.getDeclaredFields();
		Annotation[] annotations = null;

		annotations = clase.getDeclaredAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof javax.persistence.Table) {
				try {
					javax.persistence.Table an = (javax.persistence.Table) annotation;
					return an.name();

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

		return "";
	}

}