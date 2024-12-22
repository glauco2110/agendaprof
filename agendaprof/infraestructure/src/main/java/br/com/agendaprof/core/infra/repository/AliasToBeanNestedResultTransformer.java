package br.com.agendaprof.core.infra.repository;

import jakarta.persistence.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.property.access.internal.PropertyAccessStrategyFieldImpl;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.ResultTransformer;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class AliasToBeanNestedResultTransformer<T> extends AliasToBeanResultTransformer {

    private static final long serialVersionUID = -8047276133980128266L;

    private Class<T> resultClass;

    public static <T> T transformTuple(Tuple tuple, Class<T> classe){
        return new AliasToBeanNestedResultTransformer<>(classe).transformTuple(tuple);
    }

    public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
        return false;
    }

    public AliasToBeanNestedResultTransformer(Class<T> resultClass) {
        super(resultClass);
        this.resultClass = resultClass;
    }

    private T transformTuple(Tuple tuple) {
        List<Object> tuples = new ArrayList<>();
        List<String> aliases = new ArrayList<>();

        tuple.getElements().forEach(tupleElement -> {
            String alias = tupleElement.getAlias();

            aliases.add(alias);
            tuples.add(tuple.get(alias));
        });

        return transformTuple(tuples.toArray(new Object[0]), aliases.toArray(new String[0]));
    }

    public T transformTuple(Object[] tuple, String[] aliases) {

        List<String> nestedAliases = new ArrayList<>();

        Map<String, BeanClass> beanSubClass = new HashMap<>();

        try {
            for (int i = 0; i < aliases.length; i++) {

                if(Objects.isNull(tuple[i])){
                    continue;
                }

                String alias = aliases[i];
                if (alias.contains(".")) {
                    nestedAliases.add(alias);

                    String[] sp = alias.split("\\.");
                    Class<?> classAnterior = resultClass;
                    for(int indiceSp = 0; indiceSp < sp.length; indiceSp++){
                        //se for o ultimo
                        if(indiceSp == (sp.length -1)){

                        }else{
                            String fieldName = sp[indiceSp];
                            String aliasName = sp[indiceSp + 1];

                            Class<?> subclass = extractClass(classAnterior, fieldName);
                            classAnterior = subclass;

                            String identificadorAtributo = fieldName;
                            if(indiceSp != 0){
                                identificadorAtributo = alias.split("\\." + fieldName + "\\.")[0];
                                identificadorAtributo += "." + fieldName;
                            }

                            if(!beanSubClass.containsKey(identificadorAtributo)){
                                BeanClass bean = new BeanClass();
                                bean.classe = subclass;
                                bean.nomeAtributo = fieldName;

                                beanSubClass.put(identificadorAtributo, bean);
                            }

                            //se for o penultimo
                            if(indiceSp == (sp.length -2)){
                                BeanClass bean = beanSubClass.get(identificadorAtributo);
                                bean.atributos.add(aliasName);
                                bean.objetos.add(tuple[i]);
                            }
                        }
                    }
                }
            }
        }catch (NoSuchFieldException e) {
            throw new HibernateException( "Could not instantiate resultclass: " + resultClass.getName() );
        }

        Object[] newTuple = new Object[aliases.length - nestedAliases.size()];
        String[] newAliases = new String[aliases.length - nestedAliases.size()];
        int i = 0;
        for (int j = 0; j < aliases.length; j++) {
            if(Objects.isNull(tuple[j])){
                continue;
            }

            if (!nestedAliases.contains(aliases[j])) {
                newTuple[i] = tuple[j];
                newAliases[i] = aliases[j];
                ++i;
            }
        }

        ResultTransformer rootTransformer = new AliasToBeanResultTransformer(resultClass);
        T root = (T) rootTransformer.transformTuple(newTuple, newAliases);

        for(String alias : nestedAliases){

            Object lastReference = root;
            String[] sp = alias.split("\\.");
            for(int indiceSp = 0; indiceSp < sp.length; indiceSp++){

                String fieldName = sp[indiceSp];
                String identificadorAtributo = fieldName;
                if(indiceSp != 0){
                    String[] parts = alias.split("\\." + fieldName + "\\.");
					identificadorAtributo = parts[0];
                    if (parts.length > 1) {
						identificadorAtributo += "." + fieldName;
					}
                }

                if(beanSubClass.containsKey(identificadorAtributo)){

                    BeanClass bean = beanSubClass.get(identificadorAtributo);
                    if(bean.instanciaObject == null){

                        if(bean.objetos.isEmpty()){
                            try {
                                bean.instanciaObject = Class.forName(bean.classe.getName()).getConstructor().newInstance();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else{
                            ResultTransformer rt = new AliasToBeanResultTransformer(bean.classe);
                            bean.instanciaObject = rt.transformTuple(bean.objetos.toArray(), bean.atributos.toArray(new String[0]));
                        }

                        try {

                        	Object instancia = null;
                        	
                        	Class<?> tipoAtributo = getDeepField(lastReference.getClass(), bean.nomeAtributo).getType();
							if (tipoAtributo.isAssignableFrom(List.class)) {
                        		instancia = Arrays.asList(bean.instanciaObject);
                        	} else if (tipoAtributo.isAssignableFrom(Set.class)) {
                        		instancia = Arrays.asList(bean.instanciaObject).stream()
                        			.collect(Collectors.toSet());
                        	} else {
                        		instancia = bean.instanciaObject;
                        	}
                        	
                        	PropertyAccessStrategyFieldImpl.INSTANCE
	                        	.buildPropertyAccess(lastReference.getClass(), bean.nomeAtributo,true)
	                        	.getSetter()
	                        	.set(lastReference, instancia);
                        	
                        } catch (Exception ex) {
                        	log.error("Não foi possível definir instância da propriedade", ex);
                        }
                    }

                    lastReference = bean.instanciaObject;
                }
            }

        }

        return root;
    }

    private Field getDeepField(Class<?> clazz, String nomeAtributo) {
    	if (clazz.getSuperclass() != null) {
    		Field superClassField = getDeepField(clazz.getSuperclass(), nomeAtributo);
    		
    		if (superClassField != null) {
    			return superClassField;
    		}
    	}
    	
    	return getFieldByName(clazz, nomeAtributo);
	}

	private Field getFieldByName(Class<?> clazz, String nomeAtributo) {
		try {
			Field field = clazz.getDeclaredField(nomeAtributo);
			
			if (field != null) {
				return field;
			}
		} catch (Exception e) {} 
    	
		return null;
	}

	private Class<?> extractClass(Class<?> classToExtract, String field) throws NoSuchFieldException{
        Class<?> fieldClass;

        try{
            Field declaredField = classToExtract.getDeclaredField(field);
            
            if (declaredField.getType().isAssignableFrom(Set.class) ||
            		declaredField.getType().isAssignableFrom(List.class) || 
            		declaredField.getType().isAssignableFrom(Collection.class)) {
            	ParameterizedType parameterizedType = (ParameterizedType) declaredField.getGenericType();
            	Type type = parameterizedType.getActualTypeArguments()[0];
            	fieldClass = (Class<?>) type;
            } else {
            	fieldClass = declaredField.getType();
            }
            
        }catch (NoSuchFieldException e) {
            fieldClass = extractClass(classToExtract.getSuperclass(), field);
        }

        return fieldClass;
    }
}

class BeanClass{
    Object instanciaObject;
    String nomeAtributo;
    Class<?> classe;
    ArrayList<Object> objetos = new ArrayList<>();
    ArrayList<String> atributos = new ArrayList<>();
}

