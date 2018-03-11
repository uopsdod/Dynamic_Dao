package com.dao.dynamic;


public abstract class ConProvider {
	protected abstract Object getTxCon();
	protected abstract Object getCon();
}
