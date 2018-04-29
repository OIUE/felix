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
package org.apache.felix.deploymentadmin;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

import org.osgi.framework.BundleContext;
import org.osgi.service.deploymentadmin.DeploymentException;

/**
 * This class represents a deployment package that is read from a jar stream.
 */
public class StreamDeploymentPackage extends AbstractDeploymentPackage {
    private final JarInputStream m_input;
    private final List m_names = new ArrayList();
<<<<<<< HEAD
=======
    private boolean m_inMetaInf = true;
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368

    /**
     * Creates an instance of this class.
     *
     * @param input The stream from which the deployment package can be read.
     * @param bundleContext The bundle context.
     * @throws DeploymentException If it was not possible to read a valid deployment package from the specified stream.
     */
    public StreamDeploymentPackage(JarInputStream input, BundleContext bundleContext, DeploymentAdminImpl deploymentAdmin) throws DeploymentException {
        super(input.getManifest(), bundleContext, deploymentAdmin);
        m_input = input;
    }

    public InputStream getBundleStream(String symbolicName) {
        throw new UnsupportedOperationException("Not applicable for stream-based deployment package");
    }

<<<<<<< HEAD
    // This only works for those resources that have been read from the stream already, no guarantees for remainder of stream
=======
    public InputStream getCurrentEntryStream() {
        return new NonCloseableStream(m_input);
    }

    public AbstractInfo getNextEntry() throws IOException {
        String name;

        boolean metaInfFile = true;
        do {
            ZipEntry nextEntry = m_input.getNextJarEntry();
            if (nextEntry == null) {
                return null;
            }
            name = nextEntry.getName();

            // FELIX-518: do not try to process signature or localization files...
            metaInfFile = isMetaInfFile(name);
            if (metaInfFile) {
                if (!m_inMetaInf) {
                    throw new IOException("Unexpected signature file found after manifest files: " + name);
                }
                else {
                    continue;
                }
            }
        }
        while (metaInfFile);

        m_inMetaInf = false;
        m_names.add(name);

        return getAbstractInfoByPath(name);
    }

    // This only works for those resources that have been read from the stream already, no guarantees for remainder of
    // stream
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    public BundleInfoImpl[] getOrderedBundleInfos() {
        List result = new ArrayList();

        // add all bundle resources ordered by location in stream
<<<<<<< HEAD
        for(Iterator i = m_names.iterator(); i.hasNext();) {
=======
        for (Iterator i = m_names.iterator(); i.hasNext();) {
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
            String indexEntry = (String) i.next();
            AbstractInfo bundleInfo = getBundleInfoByPath(indexEntry);
            if (bundleInfo != null) {
                result.add(bundleInfo);
            }
        }

        // add bundle resources marked missing to the end of the result
        BundleInfoImpl[] bundleInfoImpls = getBundleInfoImpls();
        for (int i = 0; i < bundleInfoImpls.length; i++) {
<<<<<<< HEAD
            if(bundleInfoImpls[i].isMissing()) {
=======
            if (bundleInfoImpls[i].isMissing()) {
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
                result.add(bundleInfoImpls[i]);
            }
        }
        return (BundleInfoImpl[]) result.toArray(new BundleInfoImpl[result.size()]);
    }

    public ResourceInfoImpl[] getOrderedResourceInfos() {
        throw new UnsupportedOperationException("Not applicable for stream-based deployment package");
    }
<<<<<<< HEAD

    public AbstractInfo getNextEntry() throws IOException {
        ZipEntry nextEntry = m_input.getNextJarEntry();
        if (nextEntry == null) {
            return null;
        }
        String name = nextEntry.getName();
        m_names.add(name);
        AbstractInfo abstractInfoByPath = getAbstractInfoByPath(name);
        return abstractInfoByPath;
    }

    public InputStream getCurrentEntryStream() {
        return new NonCloseableStream(m_input);
    }
=======
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
}