/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.felix.deploymentadmin.spi;

<<<<<<< HEAD
=======
import java.io.Closeable;
import java.io.IOException;
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.felix.deploymentadmin.Constants;
import org.osgi.framework.Bundle;
import org.osgi.service.deploymentadmin.DeploymentException;

/**
<<<<<<< HEAD
 * Commands describe a group of tasks to be executed within the execution a deployment session.
 * A command that has already executed can be rolled back and a command that is currently in progress
 * can be signaled to stop it's activities by canceling it.
 */
public abstract class Command {

    private final List m_rollback = new ArrayList();
    private final List m_commit = new ArrayList();
    private volatile boolean m_cancelled;

    /**
     * Executes the command, the specified <code>DeploymentSession</code> can be used to obtain various
     * information about the deployment session which the command is part of.
     *
     * @param session The deployment session this command is part of.
     * @throws DeploymentException Thrown if the command could not successfully execute.
     */
    public abstract void execute(DeploymentSessionImpl session) throws DeploymentException;

    /**
     * Rolls back all actions that were added through the <code>addRollback(Runnable r)</code> method (in reverse
     * adding order). It is not guaranteed that the state of everything related to the command will be as if the
     * command was never executed, a best effort should be made though.
     * 
     * @param session the deployment session to rollback, should be used for logging purposes.
     */
    protected void rollback(DeploymentSessionImpl session) {
        for (ListIterator i = m_rollback.listIterator(m_rollback.size()); i.hasPrevious();) {
            Runnable runnable = (Runnable) i.previous();
            runnable.run();
        }
        cleanUp();
    }

    /**
     * Commits all changes the command may have defined when it was executed by calling the <code>execute()</code> method.
     * 
     * @param session the deployment session to commit, should be used for logging purposes.
     */
    protected void commit(DeploymentSessionImpl session) {
        for (ListIterator i = m_commit.listIterator(); i.hasNext();) {
            Runnable runnable = (Runnable) i.next();
            runnable.run();
        }
        cleanUp();
=======
 * Commands describe a group of tasks to be executed within the execution a
 * deployment session. A command that has already executed can be rolled back
 * and a command that is currently in progress can be signaled to stop it's
 * activities by canceling it.
 */
public abstract class Command implements Constants {

    private final List m_rollback = new ArrayList();
    private final List m_commit = new ArrayList();

    private volatile boolean m_cancelled;

    /**
     * Executes the command, the specified <code>DeploymentSession</code> can be
     * used to obtain various information about the deployment session which the
     * command is part of.
     * 
     * @param session The deployment session this command is part of.
     * @throws DeploymentException Thrown if the command could not successfully
     *         execute.
     */
    public final void execute(DeploymentSessionImpl session) throws DeploymentException {
        try {
            doExecute(session);
        }
        catch (Exception e) {
            if (e instanceof DeploymentException) {
                throw (DeploymentException) e;
            }
            throw new DeploymentException(CODE_OTHER_ERROR, "Command failed!", e);
        }
    }

    /**
     * Executes the command, the specified <code>DeploymentSession</code> can be
     * used to obtain various information about the deployment session which the
     * command is part of.
     * 
     * @param session The deployment session this command is part of.
     * @throws Exception in case of this command could not terminate
     *         successfully.
     */
    protected abstract void doExecute(DeploymentSessionImpl session) throws Exception;

    /**
     * Rolls back all actions that were added through the
     * <code>addRollback(Runnable r)</code> method (in reverse adding order). It
     * is not guaranteed that the state of everything related to the command
     * will be as if the command was never executed, a best effort should be
     * made though.
     * 
     * @param session the deployment session to rollback, should be used for
     *        logging purposes.
     */
    protected void rollback(DeploymentSessionImpl session) {
        try {
            for (ListIterator i = m_rollback.listIterator(m_rollback.size()); i.hasPrevious();) {
                Runnable runnable = (Runnable) i.previous();
                runnable.run();
            }
        }
        finally {
            cleanUp();
        }
    }

    /**
     * Commits all changes the command may have defined when it was executed by
     * calling the <code>execute()</code> method.
     * 
     * @param session the deployment session to commit, should be used for
     *        logging purposes.
     */
    protected final void commit(DeploymentSessionImpl session) {
        try {
            for (ListIterator i = m_commit.listIterator(); i.hasNext();) {
                Runnable runnable = (Runnable) i.next();
                runnable.run();
            }
        }
        finally {
            cleanUp();
        }
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    }

    private void cleanUp() {
        m_rollback.clear();
        m_commit.clear();
        m_cancelled = false;
    }

    /**
<<<<<<< HEAD
     * Determines if the command was canceled. This method should be used regularly by implementing classes to determine if
     * their command was canceled, if so they should return as soon as possible from their operations.
     *
=======
     * Determines if the command was canceled. This method should be used
     * regularly by implementing classes to determine if their command was
     * canceled, if so they should return as soon as possible from their
     * operations.
     * 
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
     * @return true if the command was canceled, false otherwise.
     */
    protected boolean isCancelled() {
        return m_cancelled;
    }

    /**
     * Adds an action to be executed in case of a roll back.
<<<<<<< HEAD
     *
     * @param runnable The runnable to be executed in case of a roll back.
     */
    protected void addRollback(Runnable runnable) {
=======
     * 
     * @param runnable The runnable to be executed in case of a roll back.
     */
    protected void addRollback(AbstractAction runnable) {
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        m_rollback.add(runnable);
    }

    /**
     * Adds an action to be executed in case of a commit
<<<<<<< HEAD
     *
     * @param runnable The runnable to be executes in case of a commit.
     */
    protected void addCommit(Runnable runnable) {
        m_commit.add(runnable);
    }
    
    /**
     * Sets the command to being cancelled, this does not have an immediate effect. Commands that are executing should
     * check regularly if they were cancelled and if so they should make an effort to stop their operations as soon as possible
     * followed by throwing an <code>DeploymentException.CODE_CANCELLED</code> exception.
=======
     * 
     * @param runnable The runnable to be executes in case of a commit.
     */
    protected void addCommit(AbstractAction runnable) {
        m_commit.add(runnable);
    }

    /**
     * Sets the command to being cancelled, this does not have an immediate
     * effect. Commands that are executing should check regularly if they were
     * cancelled and if so they should make an effort to stop their operations
     * as soon as possible followed by throwing an
     * <code>DeploymentException.CODE_CANCELLED</code> exception.
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
     */
    public void cancel() {
        m_cancelled = true;
    }

    /**
     * Determines whether a given bundle is actually a fragment bundle.
<<<<<<< HEAD
     * @param bundle the bundle to test, cannot be <code>null</code>.
     * @return <code>true</code> if the given bundle is actually a fragment bundle, <code>false</code> otherwise.
     */
    static final boolean isFragmentBundle(Bundle bundle) {
        Object fragmentHost = bundle.getHeaders().get(Constants.FRAGMENT_HOST);
        return fragmentHost != null;
    }
=======
     * 
     * @param bundle the bundle to test, cannot be <code>null</code>.
     * @return <code>true</code> if the given bundle is actually a fragment
     *         bundle, <code>false</code> otherwise.
     */
    static final boolean isFragmentBundle(Bundle bundle) {
        Object fragmentHost = bundle.getHeaders().get(FRAGMENT_HOST);
        return fragmentHost != null;
    }

    static final void closeSilently(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            }
            catch (IOException e) {
                // Not much we can do...
            }
        }
    }
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
}
