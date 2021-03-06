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

import org.apache.felix.deploymentadmin.AbstractDeploymentPackage;
import org.apache.felix.deploymentadmin.ResourceInfoImpl;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.deploymentadmin.spi.ResourceProcessor;
<<<<<<< HEAD
import org.osgi.service.deploymentadmin.spi.ResourceProcessorException;
=======
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
import org.osgi.service.log.LogService;

/**
 * Command that drops resources.
 */
public class DropResourceCommand extends Command {

    private final CommitResourceCommand m_commitCommand;

    /**
<<<<<<< HEAD
     * Creates an instance of this command. The commit command is used to make sure
     * the resource processors used to drop resources will be committed at a later stage in the process.
     *
     * @param commitCommand The commit command that will be executed at a later stage in the process.
=======
     * Creates an instance of this command. The commit command is used to make
     * sure the resource processors used to drop resources will be committed at
     * a later stage in the process.
     * 
     * @param commitCommand The commit command that will be executed at a later
     *        stage in the process.
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
     */
    public DropResourceCommand(CommitResourceCommand commitCommand) {
        m_commitCommand = commitCommand;
    }

<<<<<<< HEAD
    public void execute(DeploymentSessionImpl session) {
        // Allow proper rollback in case the drop fails...
        addRollback(new RollbackCommitAction(session));
        
=======
    protected void doExecute(DeploymentSessionImpl session) throws Exception {
        // Allow proper rollback in case the drop fails...
        addRollback(new RollbackCommitAction(session));

>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        AbstractDeploymentPackage target = session.getTargetAbstractDeploymentPackage();
        AbstractDeploymentPackage source = session.getSourceAbstractDeploymentPackage();
        BundleContext context = session.getBundleContext();
        LogService log = session.getLog();

        ResourceInfoImpl[] orderedTargetResources = target.getOrderedResourceInfos();
        for (int i = orderedTargetResources.length - 1; i >= 0; i--) {
            ResourceInfoImpl resourceInfo = orderedTargetResources[i];
<<<<<<< HEAD
=======
            // FELIX-4491: only resources that need to be processed should be handled...
            if (!resourceInfo.isProcessedResource()) {
                session.getLog().log(LogService.LOG_INFO, "Ignoring non-processed resource: " + resourceInfo.getPath());
                continue;
            }

>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
            String path = resourceInfo.getPath();
            if (source.getResourceInfoByPath(path) == null) {
                ServiceReference ref = target.getResourceProcessor(path);
                if (ref != null) {
                    ResourceProcessor resourceProcessor = (ResourceProcessor) context.getService(ref);
                    if (resourceProcessor != null) {
                        try {
                            if (m_commitCommand.addResourceProcessor(resourceProcessor)) {
<<<<<<< HEAD
                            	resourceProcessor.begin(session);
                            }
                            resourceProcessor.dropped(path);
                        }
                        catch (ResourceProcessorException e) {
=======
                                resourceProcessor.begin(session);
                            }
                            resourceProcessor.dropped(path);
                        }
                        catch (Exception e) {
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
                            log.log(LogService.LOG_WARNING, "Not allowed to drop resource '" + path + "'", e);
                        }
                    }
                }
            }
        }
    }
<<<<<<< HEAD
    
    private class RollbackCommitAction implements Runnable {
        private final DeploymentSessionImpl m_session; 
        
        public RollbackCommitAction(DeploymentSessionImpl session) {
            m_session = session;
        }
        
        public void run() {
=======

    private class RollbackCommitAction extends AbstractAction {
        private final DeploymentSessionImpl m_session;

        public RollbackCommitAction(DeploymentSessionImpl session) {
            m_session = session;
        }

        protected void doRun() {
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
            m_commitCommand.rollback(m_session);
        }
    }
}
