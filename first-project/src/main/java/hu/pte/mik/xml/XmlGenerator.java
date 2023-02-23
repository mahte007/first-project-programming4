package hu.pte.mik.xml;

import hu.pte.mik.exception.XmlSerializationException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XmlGenerator {

    /**
     * Ellenőrzi, hogy az objektum serializálható-e, majd elvégzi azt.
     *
     * @param object Az objektum, amit szeretnénk XML-re alakítani.
     * @return A beadott objetum XML leírata.
     */
    public String convertToXml(Object object) {
        this.checkIfSerializable(object);

        StringBuilder stringBuilder = new StringBuilder("<?xml version=\"1.0\"?>").append(System.lineSeparator());

        try {
            this.process(object, stringBuilder);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new XmlSerializationException(e);
        }

        return stringBuilder.toString();
    }

    /**
     * Elvégzi a beadott objektum feldolgozását.
     *
     * @param object        Az objektum, amit szeretnénk XML-re alakítani.
     * @param stringBuilder Ebben építjuk fel az XML-t.
     * @throws IllegalAccessException    Reflection miatt érkezhető exception
     * @throws InvocationTargetException Reflection miatt érkezhető exception
     */
    private void process(Object object,
                         StringBuilder stringBuilder) throws IllegalAccessException, InvocationTargetException {
        this.prepare(object);

        this.serialize(object, stringBuilder);
    }

    /**
     * Meghívja az objektum előkészőti methodjait, ha léteznek.
     *
     * @param object Az objektum, amit szeretnénk XML-re alakítani.
     * @throws InvocationTargetException Reflection miatt érkezhető exception
     * @throws IllegalAccessException    Reflection miatt érkezhető exception
     */
    private void prepare(Object object) throws InvocationTargetException, IllegalAccessException {
        Class<?> clazz = object.getClass();

        List<Method> methods = new ArrayList<>();
        Collections.addAll(methods, clazz.getDeclaredMethods());
        this.addParentMethods(clazz, methods);

        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeSerialization.class)) {
                method.setAccessible(true);
                method.invoke(object);
            }
        }
    }

    /**
     * Hozzáadja a listához az összes felmenő osztály metódusait rekurzívan.
     *
     * @param clazz   Az az osztály, aminek az őseinek a metódusait akarjuk hozzáfűzni a listához.
     * @param methods Ehhez a listához fűzzük hozzá a metódusokat.
     */
    private void addParentMethods(Class<?> clazz, List<Method> methods) {
        Class<?> superclass = clazz.getSuperclass();
        if (superclass.isAnnotationPresent(XmlSerializable.class)) {
            Collections.addAll(methods, superclass.getDeclaredMethods());
            this.addParentMethods(superclass, methods);
        }
    }

    /**
     * Elvégzi az adott osztály szerializálását.
     *
     * @param object        Az objektum, amit szeretnénk XML-re alakítani.
     * @param stringBuilder Ebben építjuk fel az XML-t.
     * @throws IllegalAccessException    Reflection miatt érkezhető exception
     * @throws InvocationTargetException Reflection miatt érkezhető exception
     */
    private void serialize(Object object,
                           StringBuilder stringBuilder) throws IllegalAccessException, InvocationTargetException {
        // Megnézzük, hogy mi lesz az objektum tag neve
        Class<?> clazz = object.getClass();
        XmlSerializable xmlSerializable = clazz.getAnnotation(XmlSerializable.class);
        String classKey = "".equals(xmlSerializable.key()) ? clazz.getSimpleName()
                                                                  .toUpperCase() : xmlSerializable.key();

        // Hozzáfűzzük a kezdő taget az XML-hez
        this.appendStartTag(stringBuilder, classKey);
        stringBuilder.append(System.lineSeparator());
        // Megszerezzük az összes feldolgozandó mezőt
        List<Field> fields = new ArrayList<>();
        this.addParentFields(clazz, fields);
        Collections.addAll(fields, clazz.getDeclaredFields());

        for (Field field : fields) {
            // Megnézzük, hogy szeretnénk-e, hogy az adott mező szerepeljen az XML-ben
            if (field.isAnnotationPresent(XmlElement.class)) {
                // Elérhetővé tesszük a mezőt, ha szükséges
                field.setAccessible(true);
                // Megnézzük, hogy mi legyen a mező tag neve
                XmlElement xmlElement = field.getAnnotation(XmlElement.class);
                String fieldKey = "".equals(xmlElement.key()) ? field.getName()
                                                                     .toUpperCase() : xmlElement.key();
                // Megszerezzük az objektum értékét
                Object value = field.get(object);

                // Hozzáfűzzük az XML-hez az objektum értékét
                this.appendStartTag(stringBuilder, fieldKey);
                if (value != null && value.getClass()
                                          .isAnnotationPresent(XmlSerializable.class)) {
                    stringBuilder.append(System.lineSeparator());
                    this.process(value, stringBuilder);
                } else {
                    stringBuilder.append(value);
                }
                this.appendEndTag(stringBuilder, fieldKey);
            }
        }
        this.appendEndTag(stringBuilder, classKey);
    }

    /**
     * Hozzáadja a listához az összes felmenő osztály mezőit rekurzívan.
     *
     * @param clazz  Az az osztály, aminek az őseinek a mezőit akarjuk hozzáfűzni a listához.
     * @param fields Ehhez a listához fűzzük hozzá a mezőket.
     */
    private void addParentFields(Class<?> clazz, List<Field> fields) {
        Class<?> superclass = clazz.getSuperclass();
        if (superclass.isAnnotationPresent(XmlSerializable.class)) {
            Collections.addAll(fields, superclass.getDeclaredFields());
            this.addParentFields(superclass, fields);
        }
    }

    /**
     * Hozzáfűz a {@link StringBuilder}-hez egy kezdő XML tag-et.
     *
     * @param sb  A {@link StringBuilder}, amihez hozzáfűzzük a tag-et.
     * @param key A kulcs, ami a neve lesz a tag-nek
     */
    private void appendStartTag(StringBuilder sb, String key) {
        sb.append("<")
          .append(key)
          .append(">");
    }

    /**
     * Hozzáfűz a {@link StringBuilder}-hez egy záró XML tag-et.
     *
     * @param sb  A {@link StringBuilder}, amihez hozzáfűzzük a tag-et.
     * @param key A kulcs, ami a neve lesz a tag-nek
     */
    private void appendEndTag(StringBuilder sb, String key) {
        sb.append("</")
          .append(key)
          .append(">")
          .append(System.lineSeparator());
    }

    /**
     * Ellenőrizzük, hogy az adott osztály feldolgozható-e. Ha nem {@link XmlSerializationException}-t dobunk.
     *
     * @param object Az ellenőrizendő objektum.
     */
    private void checkIfSerializable(Object object) {
        if (object == null) {
            throw new XmlSerializationException("The object must not be null!");
        }

        Class<?> clazz = object.getClass();
        if (!clazz.isAnnotationPresent(XmlSerializable.class)) {
            throw new XmlSerializationException(clazz.getName() + " is not annotated with XmlSerializable!");
        }
    }

}
