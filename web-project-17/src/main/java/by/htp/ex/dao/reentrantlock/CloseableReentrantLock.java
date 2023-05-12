package by.htp.ex.dao.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

public class CloseableReentrantLock extends ReentrantLock implements AutoCloseable {

	private static final long serialVersionUID = 1L;

	public CloseableReentrantLock open() {
		this.lock();
		return this;
	}

	@Override
	public void close() {
		this.unlock();
	}
}
