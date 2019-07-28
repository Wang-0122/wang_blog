/******************************************************************************
 *                                                                             
 *                      Woodare PROPRIETARY INFORMATION                        
 *                                                                             
 *          The information contained herein is proprietary to Woodare         
 *           and shall not be reproduced or disclosed in whole or in part      
 *                    or used for any design or manufacture                    
 *              without direct written authorization from FengDa.              
 *                                                                             
 *            Copyright (c) 2013 by Woodare.  All rights reserved.             
 *                                                                             
 *****************************************************************************/
package com.wang.wangblog.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * ClassName: SaftyBeanUtils
 * 
 * @description
 * @author framework
 * @Date 2013-4-1
 */
public class SaftyBeanUtils {

	public static void main(String[] arg) {

		A a = new A();

		List<B> bLst = new ArrayList<B>();
		a.setB(bLst);

		B b = new B();
		bLst.add(b);

		C c = new C();
		b.setC(c);
		c.setTest("this is test");

		A2 a2 = new A2();

		copyProperties(a, a2, a2.getClass(), null);

		System.out.println(a2.getB().get(0).getC().getTest());
	}

	/**
	 * @param type
	 * @param i
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static Class getClass(Type type, int i) {
		if (type instanceof ParameterizedType) { // 处理泛型类型
			return getGenericClass((ParameterizedType) type, i);
		}
		else if (type instanceof TypeVariable) {
			return (Class) getClass(((TypeVariable) type).getBounds()[0], 0); // 处理泛型擦拭对象
		}
		else {// class本身也是type，强制转型
			return (Class) type;
		}
	}

	/**
	 * @param parameterizedType
	 * @param i
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static Class getGenericClass(ParameterizedType parameterizedType, int i) {
		Object genericClass = parameterizedType.getActualTypeArguments()[i];
		if (genericClass instanceof ParameterizedType) { // 处理多级泛型
			return (Class) ((ParameterizedType) genericClass).getRawType();
		}
		else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
			return (Class) ((GenericArrayType) genericClass).getGenericComponentType();
		}
		else if (genericClass instanceof TypeVariable) { // 处理泛型擦拭对象
			return (Class) getClass(((TypeVariable) genericClass).getBounds()[0], 0);
		}
		else {
			return (Class) genericClass;
		}
	}

	/**
	 * @param source
	 * @param target
	 * @param targetClass
	 * @param ignoreProperties
	 */
	public static <T> T cloneTo(Object source, Class<T> targetClass) {
		return cloneTo(source, targetClass, new String[0]);
	}

	/**
	 * @param source
	 * @param target
	 * @param targetClass
	 * @param ignoreProperties
	 */
	public static <T> T cloneTo(Object source, Class<T> targetClass, String[] ignoreProperties) {
		if (source == null) {
			return null;
		}

		T ret = null;
		try {
			ret = targetClass.newInstance();
			copyProperties(source, ret, targetClass, ignoreProperties);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * @param source
	 * @param target
	 * @param targetClass
	 * @param ignoreProperties
	 */
	public static <T, K> List<T> cloneToList(List<K> sourceLst, Class<T> targetClass, String[] ignoreProperties) {
		if (sourceLst == null) {
			return null;
		}
		List<T> rets = new ArrayList<T>();
		try {
			for (Object source : sourceLst) {
				rets.add(cloneTo(source, targetClass, ignoreProperties));
			}
		} catch (Exception e) {
		}
		return rets;
	}

	/**
	 * @param source
	 * @param target
	 * @param targetClass
	 * @param ignoreProperties
	 */
	public static <T, K> List<T> cloneToList(List<K> sourceLst, Class<T> targetClass) {
		return cloneToList(sourceLst, targetClass, new String[0]);
	}

	/**
	 * @param source
	 * @param target
	 * @param targetClass
	 * @param ignoreProperties
	 */
	public static <T> void copyProperties(Object source, Object target) {
		copyProperties(source, target, target.getClass(), null);
	}

	/**
	 * @param source
	 * @param target
	 * @param targetClass
	 * @param ignoreProperties
	 */
	public static void copyProperties(Object source, Object target, String[] ignoreProperties) {
		copyProperties(source, target, target.getClass(), ignoreProperties);
	}

	/**
	 * @param source
	 * @param target
	 * @param ignoreProperties
	 */
	public static void copyNotEmptyProperties(Object source, Object target) {
		copyNotEmptyProperties(source, target, target.getClass(), null);
	}

	/**
	 * @param source
	 * @param target
	 * @param ignoreProperties
	 */
	public static void copyNotEmptyProperties(Object source, Object target, String[] ignoreProperties) {
		copyNotEmptyProperties(source, target, target.getClass(), ignoreProperties);
	}

	/**
	 * @param source
	 * @param target
	 * @param targetClass
	 * @param ignoreProperties
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void copyNotEmptyProperties(Object source, Object target, Class<?> targetClass, String[] ignoreProperties) {
		if (source == null || target == null) {
			return;
		}

		Class<?> actualEditable = targetClass;
		PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;

		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
				PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());

				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						Method writeMethod = targetPd.getWriteMethod();
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}
						boolean emptyValue = false;
						if (value != null) {
							if (value instanceof String) {
								emptyValue = "".equals(value);
							}

							Field field = ReflectionUtils.findField(targetClass, targetPd.getName());
							Type genericType = field.getGenericType();

							if (targetPd.getPropertyType().isAssignableFrom(List.class)) {
								Class<?> subObjClass = getClass(genericType, 0);

								List valLst = (List) value;
								List targetItems = null;

								if (valLst.size() > 0) {
									if (valLst.get(0).getClass().equals(subObjClass)) {
										targetItems = valLst;
									}
									else {
										targetItems = new ArrayList();
										Iterator iterator = ((List) value).iterator();

										while (iterator.hasNext()) {
											Object item = iterator.next();

											Object targetItem = subObjClass.newInstance();

											copyProperties(item, targetItem, subObjClass, ignoreProperties);

											targetItems.add(targetItem);
										}
									}
								}
								value = targetItems;
							}
							else if (!targetPd.getPropertyType().equals(sourcePd.getPropertyType())) {
								Object sourceValue = value;
								value = field.getType().newInstance();
								copyProperties(sourceValue, value, field.getType(), ignoreProperties);
							}
						}
						else {
							emptyValue = true;
						}
						if (!emptyValue) {
							writeMethod.invoke(target, value);
						}
					} catch (Throwable ex) {
						throw new FatalBeanException("Could not copy properties[" + targetPd.getName() + "] from source to target", ex);
					}
				}
			}
		}
	}

	/**
	 * @param source
	 * @param target
	 * @param targetClass
	 * @param ignoreProperties
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void copyProperties(Object source, Object target, Class<?> targetClass, String[] ignoreProperties) {
		if (source == null || target == null) {
			return;
		}

		Class<?> actualEditable = targetClass;
		PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;

		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
				PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());

				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						Method writeMethod = targetPd.getWriteMethod();
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}

						if (value != null) {
							Field field = ReflectionUtils.findField(targetClass, targetPd.getName());
							Type genericType = field.getGenericType();

							if (targetPd.getPropertyType().isAssignableFrom(List.class)) {
								Class<?> subObjClass = getClass(genericType, 0);

								List valLst = (List) value;
								List targetItems = null;

								if (valLst.size() > 0) {
									if (valLst.get(0).getClass().equals(subObjClass)) {
										targetItems = valLst;
									}
									else {
										targetItems = new ArrayList();
										Iterator iterator = ((List) value).iterator();

										while (iterator.hasNext()) {
											Object item = iterator.next();

											Object targetItem = subObjClass.newInstance();

											copyProperties(item, targetItem, subObjClass, ignoreProperties);

											targetItems.add(targetItem);
										}
									}
								}
								value = targetItems;
							}
							else if (!targetPd.getPropertyType().equals(sourcePd.getPropertyType())) {
								Object sourceValue = value;
								value = field.getType().newInstance();
								copyProperties(sourceValue, value, field.getType(), ignoreProperties);
							}
						}
						writeMethod.invoke(target, value);
					} catch (Throwable ex) {
						throw new FatalBeanException("Could not copy properties[" + targetPd.getName() + "] from source to target", ex);
					}
				}
			}
		}
	}

	static class A {
		private List<B> b;

		/**
		 * @return the b
		 */
		public List<B> getB() {
			return b;
		}

		/**
		 * @param b
		 *            the b to set
		 */
		public void setB(List<B> b) {
			this.b = b;
		}
	}

	static class A2 {
		private List<B2> b;

		/**
		 * @return the b
		 */
		public List<B2> getB() {
			return b;
		}

		/**
		 * @param b
		 *            the b to set
		 */
		public void setB(List<B2> b) {
			this.b = b;
		}
	}

	static class B {
		private C c;

		/**
		 * @return the c
		 */
		public C getC() {
			return c;
		}

		/**
		 * @param c
		 *            the c to set
		 */
		public void setC(C c) {
			this.c = c;
		}

	}

	static class B2 {
		private C2 c;

		/**
		 * @return the c
		 */
		public C2 getC() {
			return c;
		}

		/**
		 * @param c
		 *            the c to set
		 */
		public void setC(C2 c) {
			this.c = c;
		}

	}

	static class C {
		private String test;

		/**
		 * @return the test
		 */
		public String getTest() {
			return test;
		}

		/**
		 * @param test
		 *            the test to set
		 */
		public void setTest(String test) {
			this.test = test;
		}

	}

	static class C2 {
		private String test;

		/**
		 * @return the test
		 */
		public String getTest() {
			return test;
		}

		/**
		 * @param test
		 *            the test to set
		 */
		public void setTest(String test) {
			this.test = test;
		}

	}
}
