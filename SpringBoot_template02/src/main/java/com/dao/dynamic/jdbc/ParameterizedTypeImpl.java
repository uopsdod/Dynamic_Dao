package com.dao.dynamic.jdbc;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ParameterizedTypeImpl implements ParameterizedType{

	  private final Type[] actualTypeArguments;
	  private final Type ownerType;
	  private final Type rawType;

	  public ParameterizedTypeImpl(Type ownerType, Type rawType,
	      Type[] actualTypeArguments) {
	    this.ownerType = ownerType;
	    this.actualTypeArguments = actualTypeArguments;
	    this.rawType = rawType;
	  }

	  public ParameterizedTypeImpl(Type rawType, Type... actualTypeArguments) {
	    this(null, rawType, actualTypeArguments);
	  }

	  public static Type paramType(Type rawType, Type... actualTypeArguments) {
	    return new ParameterizedTypeImpl(rawType, actualTypeArguments);
	  }

	  @Override
	  public Type[] getActualTypeArguments() {
	    return actualTypeArguments.clone();
	  }

	  @Override
	  public java.lang.reflect.Type getOwnerType() {
	    return ownerType;
	  }

	  @Override
	  public java.lang.reflect.Type getRawType() {
	    return rawType;
	  }

	  @Override
	  public int hashCode() {
	    int h = getRawType().hashCode();
	    if (getOwnerType() != null)
	      h ^= getOwnerType().hashCode();
	    for (int i = 0, n = actualTypeArguments.length; i < n; i++)
	      h ^= actualTypeArguments[i].hashCode();
	    return h;
	  }

	  static boolean eq(Object a, Object b) {
	    if ((a == null) != (b == null))
	      return false;
	    if (a != null && !a.equals(b))
	      return false;
	    return true;
	  }

	  @Override
	  public boolean equals(Object o) {
	    if (o == null || !(o instanceof ParameterizedTypeImpl))
	      return false;

	    ParameterizedTypeImpl t = (ParameterizedTypeImpl) o;
	    if (!eq(getRawType(), t.getRawType()))
	      return false;
	    if (!eq(getOwnerType(), t.getOwnerType()))
	      return false;

	    Object[] tp = t.actualTypeArguments;
	    if (actualTypeArguments.length != tp.length)
	      return false;

	    for (int i = 0, n = actualTypeArguments.length; i < n; i++)
	      if (!eq(actualTypeArguments[i], tp[i]))
	        return false;

	    return true;
	  }

}
