/*
* Copyright 2002-2006 Bediako George.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.lucidtechnics.blackboard;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.Label;

import net.sf.cglib.core.CollectionUtils;
import net.sf.cglib.core.Predicate;
import net.sf.cglib.core.MethodInfo;
import net.sf.cglib.core.Signature;
import net.sf.cglib.core.MethodInfoTransformer;
import net.sf.cglib.proxy.Enhancer;

import com.lucidtechnics.blackboard.util.error.ErrorManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TargetConstructor
{
	private static Log logger = LogFactory.getLog(TargetConstructor.class);

	private static final Map generatedClassMap = new HashMap();
	
	public TargetConstructor() {}

	public static final boolean canConstructTarget(Class _class)
	{
		boolean isFinal = Modifier.isFinal(_class.getModifiers());
		boolean isArray = _class.isArray();
		boolean isEnum = _class.isEnum();
							   
		return (isFinal == false && isArray == false && isEnum == false);
	}
	
	public static final Target constructTarget(String _targetName, Class _class)
	{
		Target target = null;

		try
		{
			Class targetClass = (Class) generatedClassMap.get(_class);

			if (targetClass == null)
			{
				synchronized(generatedClassMap)
				{
					byte[] classByteArray = createWrapperObjectByteArray(_targetName, _class);
					targetClass = loadClass(classByteArray);
					generatedClassMap.put(_class, targetClass);
				}
			}

			target = (Target) targetClass.newInstance();
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().throwException(e, logger); 
		}

		return target;
	}

	private static final byte[] createWrapperObjectByteArray(String _targetName, Class _class)
	{
		ClassWriter classWriter = new ClassWriter(true);
		
		FieldVisitor fieldVisitor;
		MethodVisitor methodVisitor;
		String superClassName = _class.getCanonicalName().replaceAll("\\.", "/");

		String[] superClassNameParts = superClassName.split("/");
		String simpleClassName = superClassNameParts[superClassNameParts.length - 1];
		String blackboardSubClassName = "com/lucidtechnics/blackboard/wrapper/" + superClassName  + "Wrapper";
		
		classWriter.visit(Opcodes.V1_5,
				  Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER,
				  blackboardSubClassName,
				  null,
				  superClassName,
				  new String[] { "com/lucidtechnics/blackboard/Target" });

		classWriter.visitSource(simpleClassName, null);

		{
			fieldVisitor = classWriter.visitField(Opcodes.ACC_PRIVATE, "blackboardObject", "Ljava/lang/Object;", null, null);
			fieldVisitor.visitEnd();
		}

		{
			fieldVisitor = classWriter.visitField(Opcodes.ACC_PRIVATE, "intercepter", "Lcom/lucidtechnics/blackboard/Intercepter;", null, null);
			fieldVisitor.visitEnd();
		}

		{
			methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC,
													"getBlackboardObject",
													"()Ljava/lang/Object;",
													null,
													null);
			methodVisitor.visitCode();
			methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
			methodVisitor.visitFieldInsn(Opcodes.GETFIELD, blackboardSubClassName, "blackboardObject", "Ljava/lang/Object;");
			methodVisitor.visitInsn(Opcodes.ARETURN);
			methodVisitor.visitMaxs(0, 0);
			methodVisitor.visitEnd();
		}

		{
			methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC,
													"getIntercepter",
													"()Lcom/lucidtechnics/blackboard/Intercepter;",
													null,
													null);
			methodVisitor.visitCode();
			methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
			methodVisitor.visitFieldInsn(Opcodes.GETFIELD, blackboardSubClassName, "intercepter", "Lcom/lucidtechnics/blackboard/Intercepter;");
			methodVisitor.visitInsn(Opcodes.ARETURN);
			methodVisitor.visitMaxs(0, 0);
			methodVisitor.visitEnd();
		}

		{
			methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC,
													"setBlackboardObject",
													"(Ljava/lang/Object;)V",
													null,
													null);
			methodVisitor.visitCode();
			methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
			methodVisitor.visitVarInsn(Opcodes.ALOAD, 1);
			methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, blackboardSubClassName, "blackboardObject", "Ljava/lang/Object;");
			methodVisitor.visitInsn(Opcodes.RETURN);
			methodVisitor.visitMaxs(0, 0);
			methodVisitor.visitEnd();
		}

		{
			methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC,
													"setIntercepter",
													"(Lcom/lucidtechnics/blackboard/Intercepter;)V",
													null,
													null);
			methodVisitor.visitCode();
			methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
			methodVisitor.visitVarInsn(Opcodes.ALOAD, 1);
			methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, blackboardSubClassName, "intercepter", "Lcom/lucidtechnics/blackboard/Intercepter;");
			methodVisitor.visitInsn(Opcodes.RETURN);
			methodVisitor.visitMaxs(0, 0);
			methodVisitor.visitEnd();
		}

		{
			methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
			methodVisitor.visitCode();
			methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
			methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, superClassName, "<init>", "()V");
			methodVisitor.visitInsn(Opcodes.RETURN);
			methodVisitor.visitMaxs(0, 0);
			methodVisitor.visitEnd();
		}

		List mutatorMethodList = findMutatorMethods(_class);

		Iterator mutatorMethods = mutatorMethodList.iterator();

		while (mutatorMethods.hasNext() == true)
		{
			Method method = (Method) mutatorMethods.next();
			MethodInfo methodInfo = (MethodInfo) MethodInfoTransformer.getInstance().transform(method);
			Signature signature = methodInfo.getSignature();
			String methodName = signature.getName();
			
			String parameterType = signature.getArgumentTypes()[0].getDescriptor();
			String returnType = signature.getReturnType().getDescriptor();
			String[] exceptionTypeArray = createExceptionTypeArray(methodInfo.getExceptionTypes());
					
			{
				methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC,
									methodName,
									"(" + parameterType + ")" + returnType,
									null,
					exceptionTypeArray);

				methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
				methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, blackboardSubClassName, "getIntercepter", "()Lcom/lucidtechnics/blackboard/Intercepter;");
				Label l1 = new Label();
				methodVisitor.visitJumpInsn(Opcodes.IFNULL, l1);

				methodVisitor.visitCode();
				methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
				methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, blackboardSubClassName,
								   "getIntercepter",
								   "()Lcom/lucidtechnics/blackboard/Intercepter;");
				
				methodVisitor.visitLdcInsn(superClassName);
				methodVisitor.visitLdcInsn(blackboardSubClassName);
				methodVisitor.visitLdcInsn(methodName);
				methodVisitor.visitLdcInsn(_targetName);
				methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);

				int tempOpCode = getLoadOpcode(parameterType);
				methodVisitor.visitVarInsn(tempOpCode, 1);
				
				if (tempOpCode == Opcodes.ALOAD)
				{
					//this is an object.
					methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE,
						"com/lucidtechnics/blackboard/Intercepter",
						"monitor",
						"(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V");
				}
				else
				{
					//it is a primitive.
					methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE,
						"com/lucidtechnics/blackboard/Intercepter",
						"monitor",
						"(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;" + parameterType + ")V");
				}
				
				methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
				methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, blackboardSubClassName, "getBlackboardObject", "()Ljava/lang/Object;");
				methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, superClassName);
				methodVisitor.visitVarInsn(getLoadOpcode(parameterType), 1);
				methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, superClassName, methodName, "(" + parameterType + ")" + returnType);

				methodVisitor.visitLabel(l1);
				
				methodVisitor.visitInsn(getReturnOpcode(returnType));
				methodVisitor.visitMaxs(0, 0);
				methodVisitor.visitEnd();
			}
		}

		List otherPublicMethodList = findOtherPublicMethods(_class);

		Iterator otherPublicMethods = otherPublicMethodList.iterator();

		while (otherPublicMethods.hasNext() == true)
		{
			Method method = (Method) otherPublicMethods.next();
			MethodInfo methodInfo = (MethodInfo) MethodInfoTransformer.getInstance().transform(method);
			Signature signature = methodInfo.getSignature();
			String methodName = signature.getName();

			String parameterTypes = constructParameterTypes(signature.getArgumentTypes());
			String returnType = signature.getReturnType().getDescriptor();
			String[] exceptionTypeArray = createExceptionTypeArray(methodInfo.getExceptionTypes());

			if (logger.isDebugEnabled() == true)
			{
				logger.debug("Processing method: " + methodName);
			}
			if (logger.isDebugEnabled() == true)
			{
				logger.debug("Parameter types are: " + parameterTypes);
			}
			
			if ("toString".equalsIgnoreCase(methodName) == false)
			{
				methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC,
					methodName,
					"(" + parameterTypes + ")" + returnType,
					null,
					exceptionTypeArray);

				methodVisitor.visitCode();
				methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
				methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, blackboardSubClassName, "getBlackboardObject", "()Ljava/lang/Object;");
				methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, superClassName);
				methodVisitor.visitVarInsn(Opcodes.ASTORE, signature.getArgumentTypes().length + 1);
				methodVisitor.visitVarInsn(Opcodes.ALOAD, signature.getArgumentTypes().length + 1);
				
				loadParameters(methodVisitor, signature.getArgumentTypes());

				methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, superClassName, methodName, "(" + parameterTypes + ")" + returnType);
				methodVisitor.visitInsn(getReturnOpcode(returnType));
				methodVisitor.visitMaxs(0, 0);
				methodVisitor.visitEnd();
			}
		}

		List protectedMethodList = findProtectedMethods(_class);

		Iterator protectedMethods = protectedMethodList.iterator();

		while (protectedMethods.hasNext() == true)
		{
			Method method = (Method) protectedMethods.next();
			MethodInfo methodInfo = (MethodInfo) MethodInfoTransformer.getInstance().transform(method);
			Signature signature = methodInfo.getSignature();
			String methodName = signature.getName();

			String parameterTypes = constructParameterTypes(signature.getArgumentTypes());
			String returnType = signature.getReturnType().getDescriptor();
			String[] exceptionTypeArray = createExceptionTypeArray(methodInfo.getExceptionTypes());

			if (logger.isDebugEnabled() == true)
			{
				logger.debug("Processing method: " + methodName + " and parameter types are: " + parameterTypes);
			}

			{
				methodVisitor = classWriter.visitMethod(Opcodes.ACC_PROTECTED,
					methodName,
					"(" + parameterTypes + ")" + returnType,
					null,
					exceptionTypeArray);

				methodVisitor.visitCode();
				methodVisitor.visitTypeInsn(Opcodes.NEW, "com/lucidtechnics/blackboard/BlackboardException");
				methodVisitor.visitInsn(Opcodes.DUP);
				methodVisitor.visitLdcInsn("Unable to access protected methods on Blackboard object");
				methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/lucidtechnics/blackboard/BlackboardException", "<init>", "(Ljava/lang/String;)V");
				methodVisitor.visitInsn(Opcodes.ATHROW);
				methodVisitor.visitMaxs(0, 0);
				methodVisitor.visitEnd();
			}
		}

		{
			methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "toString", "()Ljava/lang/String;", null, null);
			methodVisitor.visitCode();
			methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
			methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, blackboardSubClassName, "getBlackboardObject", "()Ljava/lang/Object;");
			methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "com/lucidtechnics/blackboard/util/Utility", "toString", "(Ljava/lang/Object;)Ljava/lang/String;");
			methodVisitor.visitInsn(getReturnOpcode("Ljava/lang/String;"));
			methodVisitor.visitMaxs(0, 0);
			methodVisitor.visitEnd();
		}
		
		classWriter.visitEnd();

		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Finished creating new class: " + blackboardSubClassName + " for target class: " + superClassName + ".");
		}

		return classWriter.toByteArray();
	}

	private final static void loadParameters(MethodVisitor _methodVisitor, Type[] _typeArray)
	{
		int register = 0;
		for (int i = 0; i < _typeArray.length; i++)
		{
			if (i >= 3)
			{
				register = i + 2;
			}
			else
			{
				register = i + 1;
			}

			_methodVisitor.visitVarInsn(getLoadOpcode(_typeArray[i].getDescriptor()), register);
		}
	}
	
	private final static String constructParameterTypes(Type[] _typeArray)
	{
		StringBuffer stringBuffer = new StringBuffer();
		
		for (int i = 0; i < _typeArray.length; i++)
		{
			stringBuffer.append(_typeArray[i].getDescriptor());
		}

		return stringBuffer.toString();
	}
	
	private final static String[] createExceptionTypeArray(Type[] _typeArray)
	{
		String[] exceptionTypeArray = null;
		
		if (_typeArray != null)
		{
			exceptionTypeArray = new String[_typeArray.length];

			for (int i = 0; i < _typeArray.length; i++)
			{
				exceptionTypeArray[0] = _typeArray[i].getInternalName();
			}
		}

		return exceptionTypeArray;
	}

	private final static List findMutatorMethods(Class _class)
	{
		List methodList = new ArrayList();
		
		Enhancer.getMethods(_class, null, methodList);

		Predicate mutatorPredicate = new Predicate() {
			public boolean evaluate(Object _object)
			{
				Method method = (Method) _object;

				boolean startsWithSet = (method.getName().startsWith("set") == true);
				boolean returnTypeIsVoid = ("void".equals(method.getReturnType().getName()) == true);
				boolean parameterTypeCountIsOne = (method.getParameterTypes().length == 1);
				boolean isPublic = (Modifier.isPublic(method.getModifiers()) == true);
								
				return startsWithSet && returnTypeIsVoid && parameterTypeCountIsOne && isPublic;
			}
		};

		CollectionUtils.filter(methodList, mutatorPredicate);
		
		return methodList;
	}

	private final static List findOtherPublicMethods(Class _class)
	{
		List methodList = new ArrayList();

		Enhancer.getMethods(_class, null, methodList);

		Predicate mutatorPredicate = new Predicate() {
			public boolean evaluate(Object _object)
			{
				Method method = (Method) _object;

				boolean startsWithSet = (method.getName().startsWith("set") == true);
				boolean returnTypeIsVoid = ("void".equals(method.getReturnType().getName()) == true);
				boolean parameterTypeCountIsOne = (method.getParameterTypes().length == 1);
				boolean isPublic = (Modifier.isPublic(method.getModifiers()) == true);
				
				return ((startsWithSet && returnTypeIsVoid && parameterTypeCountIsOne) == false) &&
						isPublic;
			}
		};

		CollectionUtils.filter(methodList, mutatorPredicate);

		return methodList;
	}

	private final static List findProtectedMethods(Class _class)
	{
		List methodList = new ArrayList();

		Enhancer.getMethods(_class, null, methodList);

		Predicate mutatorPredicate = new Predicate() {
			public boolean evaluate(Object _object)
			{
				Method method = (Method) _object;
				
				boolean isProtected = (Modifier.isProtected(method.getModifiers()) == true);

				return isProtected;
			}
		};

		CollectionUtils.filter(methodList, mutatorPredicate);

		return methodList;
	}


	private final static void printMethods(Class _class)
	{
		List methodList = new ArrayList();

		Enhancer.getMethods(_class, null, methodList);

		Predicate mutatorPredicate = new Predicate() {
			public boolean evaluate(Object _object)
			{
				Method method = (Method) _object;

				if (logger.isDebugEnabled() == true)
				{
					logger.debug("Printing out specifics for method: " + method.getName() +
								 " with return type: " + method.getReturnType().getName());
				}

				for (int i = 0; i < method.getParameterTypes().length; i++)
				{
					if (logger.isDebugEnabled() == true)
					{
						logger.debug("and with parameter type: " + method.getParameterTypes()[i]);
					}
				}

				return true;
			}
		};

		CollectionUtils.filter(methodList, mutatorPredicate);
	}
	
	private final static ClassLoader getClassLoader()
	{
		ClassLoader classLoader = TargetConstructor.class.getClassLoader();

		if (classLoader == null)
		{
			classLoader = ClassLoader.getSystemClassLoader();
		}
		
		if (classLoader == null)
		{
			classLoader = Thread.currentThread().getContextClassLoader();
		}
		
		if (classLoader == null)
		{
			throw new RuntimeException("Cannot determine classloader");
		}
		
		return classLoader;
	}

	private static final Class loadClass (byte[] _byteArray)
		throws Exception
	{
		//override classDefine (as it is protected) and define the class.
		Class myClass = null;
		
		ClassLoader classLoader = getClassLoader();
		Class classLoaderClass = Class.forName("java.lang.ClassLoader");
		Method method = classLoaderClass.getDeclaredMethod("defineClass", new Class[] { String.class, byte[].class, int.class, int.class });

		// protected method invocaton
		method.setAccessible(true);

		try
		{
			Object[] argumentArray = new Object[] { null, _byteArray, new Integer(0), new Integer(_byteArray.length)};
			myClass = (Class) method.invoke(classLoader, argumentArray);
		}
		finally
		{
			method.setAccessible(false);
		}
			
		return myClass;
	}

	private static String transformClassName(String _className)
	{
		String transformedClassName = _className.replaceAll("\\.", "/");
		return new StringBuffer().append("L").append(transformedClassName).append(";").toString();
	}

	private static int getLoadOpcode(String _parameterType)
	{
		int opCode = 0;
		char typeChar = _parameterType.toCharArray()[0];

		switch (typeChar)
		{
			case 'Z':
			case 'B':
			case 'C':
			case 'I':
			case 'S':
				opCode = Opcodes.ILOAD;
				break;

			case 'D':
				opCode = Opcodes.DLOAD;
				break;
				
			case 'F':
				opCode = Opcodes.FLOAD;
				break;
				
			case 'J':
				opCode = Opcodes.LLOAD;
				break;
				
			case 'L':
			case '[':
				opCode = Opcodes.ALOAD;
				break;
								
			default:
				if (true) throw new RuntimeException("Cannot handle parameter type identified by first character: " + typeChar);
				break;
		}

		return opCode;
	}

	private static int getReturnOpcode(String _returnType)
	{
		int opCode = 0;

		char firstChar = _returnType.toCharArray()[0];

		switch (firstChar)
		{
			case 'Z':
			case 'B':
			case 'C':
			case 'I':
			case 'S':
				opCode = Opcodes.IRETURN;
				break;

			case 'D':
				opCode = Opcodes.DRETURN;
				break;

			case 'F':
				opCode = Opcodes.FRETURN;
				break;

			case 'J':
				opCode = Opcodes.LRETURN;
				break;

			case 'L':
				opCode = Opcodes.ARETURN;
				break;

			case 'V':
				opCode = Opcodes.RETURN;
				break;

			case '[':
				opCode = Opcodes.ARETURN;
				break;

			default:
				if (true) throw new RuntimeException("Cannot handle return type identified by first character: " + firstChar);
				break;
		}

		return opCode;
	}
}