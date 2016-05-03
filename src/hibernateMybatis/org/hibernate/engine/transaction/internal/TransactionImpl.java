/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package org.hibernate.engine.transaction.internal;

import javax.transaction.Synchronization;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.hibernate.internal.CoreLogging;
import org.hibernate.resource.transaction.SynchronizationRegistry;
import org.hibernate.resource.transaction.TransactionCoordinator;
import org.hibernate.resource.transaction.TransactionCoordinator.TransactionDriver;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.jboss.logging.Logger;

public class TransactionImpl implements Transaction {
	private static final Logger LOG = CoreLogging.logger(TransactionImpl.class);
	private final TransactionCoordinator transactionCoordinator;
	private final TransactionCoordinator.TransactionDriver transactionDriverControl;
	private boolean valid = true;

	public TransactionImpl(TransactionCoordinator transactionCoordinator) {
		this.transactionCoordinator = transactionCoordinator;
		this.transactionDriverControl = transactionCoordinator.getTransactionDriverControl();
	}

	public void begin() {
		TransactionStatus status = this.transactionDriverControl.getStatus();

		if (!(this.valid)) {
			throw new TransactionException("Transaction instance is no longer valid");
		}
		if (status == TransactionStatus.ACTIVE) {
			return;
		}

		LOG.debug("begin");
		this.transactionDriverControl.begin();
	}

	public void commit() {
		TransactionStatus status = this.transactionDriverControl.getStatus();
		if (status != TransactionStatus.ACTIVE) {
			throw new TransactionException("Transaction not successfully started");
		}

		LOG.debug("committing");
		try {
			this.transactionDriverControl.commit();

			invalidate();
		} finally {
			invalidate();
		}
	}

	public void rollback() {
		TransactionStatus status = this.transactionDriverControl.getStatus();
		if ((status == TransactionStatus.ROLLED_BACK) || (status == TransactionStatus.NOT_ACTIVE)) {
			LOG.debug("rollback() called on an inactive transaction");
			invalidate();
			return;
		}

		if (!(status.canRollback())) {
			throw new TransactionException("Cannot rollback transaction in current status [" + status.name() + "]");
		}

		LOG.debug("rolling back");
		if ((status == TransactionStatus.FAILED_COMMIT) && (!(allowFailedCommitToPhysicallyRollback())))
			return;
		try {
			this.transactionDriverControl.rollback();

			invalidate();
		} finally {
			invalidate();
		}
	}

	public TransactionStatus getStatus() {
		return this.transactionDriverControl.getStatus();
	}

	public void registerSynchronization(Synchronization synchronization) throws HibernateException {
		this.transactionCoordinator.getLocalSynchronizations().registerSynchronization(synchronization);
	}

	public void setTimeout(int seconds) {
		this.transactionCoordinator.setTimeOut(seconds);
	}

	public int getTimeout() {
		return this.transactionCoordinator.getTimeOut();
	}

	public void markRollbackOnly() {
		this.transactionDriverControl.markRollbackOnly();
	}

	public void invalidate() {
		this.valid = false;
	}

	protected boolean allowFailedCommitToPhysicallyRollback() {
		return false;
	}
}