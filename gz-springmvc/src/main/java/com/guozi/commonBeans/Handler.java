package com.guozi.commonBeans;

import java.lang.reflect.Method;

/**
 * 为了方便利用java反射调用
 * 处理器对象的方法而设计的一个类。
 * 		mh.invoke(obj)
 */
public class Handler {
	private Method mh;
	private Object obj;
	
	public Handler(Method mh, Object obj) {
		this.mh = mh;
		this.obj = obj;
	}
	
	public Method getMh() {
		return mh;
	}
	public void setMh(Method mh) {
		this.mh = mh;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	
}
