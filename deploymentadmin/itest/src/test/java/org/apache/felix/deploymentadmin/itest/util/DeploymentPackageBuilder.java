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
package org.apache.felix.deploymentadmin.itest.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
<<<<<<< HEAD
=======
import java.io.Closeable;
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
<<<<<<< HEAD
=======
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
<<<<<<< HEAD
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
=======
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368

import org.osgi.framework.Version;

/**
 * Builder for deployment packages. Can handle bundles, resource processors and artifacts.
 */
public class DeploymentPackageBuilder {

    /**
     * Convenience resource filter for manipulating JAR manifests.
     */
    public abstract static class JarManifestFilter implements ResourceFilter {

        public final InputStream createInputStream(URL url) throws IOException {
            byte[] buffer = new byte[BUFFER_SIZE];

            JarInputStream jis = new JarInputStream(url.openStream());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            JarOutputStream jos = new JarOutputStream(baos, filterManifest(jis.getManifest()));

            JarEntry input;
            while ((input = jis.getNextJarEntry()) != null) {
                jos.putNextEntry(input);
                int read;
                while ((read = jis.read(buffer)) > 0) {
                    jos.write(buffer, 0, read);
                }
                jos.closeEntry();
            }
            jos.close();
            jis.close();

            return new ByteArrayInputStream(baos.toByteArray());
        }
<<<<<<< HEAD
        
        protected abstract Manifest filterManifest(Manifest manifest);
    }
    
=======

        protected abstract Manifest filterManifest(Manifest manifest);
    }

>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    /**
     * Simple manifest JAR manipulator implementation.
     */
    public static class JarManifestManipulatingFilter extends JarManifestFilter {
        private final String[] m_replacementEntries;
<<<<<<< HEAD
        
=======

>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        public JarManifestManipulatingFilter(String... replacementEntries) {
            if (replacementEntries == null || ((replacementEntries.length) % 2 != 0)) {
                throw new IllegalArgumentException("Entries must be a multiple of two!");
            }
            m_replacementEntries = Arrays.copyOf(replacementEntries, replacementEntries.length);
        }

        @Override
        protected Manifest filterManifest(Manifest manifest) {
            for (int i = 0; i < m_replacementEntries.length; i += 2) {
                String key = m_replacementEntries[i];
<<<<<<< HEAD
                String value = m_replacementEntries[i+1];
=======
                String value = m_replacementEntries[i + 1];
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
                manifest.getMainAttributes().putValue(key, value);
            }
            return manifest;
        }
    }
<<<<<<< HEAD
    
    private static final int BUFFER_SIZE = 32 * 1024;

    private final String m_symbolicName;
    private final String m_version;
    private final List<ArtifactData> m_bundles = new ArrayList<ArtifactData>();
    private final List<ArtifactData> m_processors = new ArrayList<ArtifactData>();

    private final List<ArtifactData> m_artifacts = new ArrayList<ArtifactData>();
    private String m_fixPackageVersion;

    private boolean m_verification;
=======

    private static final int BUFFER_SIZE = 32 * 1024;

    private final DPSigner m_signer;
    private final String m_symbolicName;
    private final String m_version;
    private final List<ArtifactData> m_localizationFiles = new ArrayList<ArtifactData>();
    private final List<ArtifactData> m_bundles = new ArrayList<ArtifactData>();
    private final List<ArtifactData> m_processors = new ArrayList<ArtifactData>();
    private final List<ArtifactData> m_artifacts = new ArrayList<ArtifactData>();

    private String m_fixPackageVersion;
    private boolean m_verification;
    private PrivateKey m_signingKey;
    private X509Certificate m_signingCert;
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368

    private DeploymentPackageBuilder(String symbolicName, String version) {
        m_symbolicName = symbolicName;
        m_version = version;
<<<<<<< HEAD
        
        m_verification = true;
=======

        m_verification = true;
        m_signer = new DPSigner();
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    }

    /**
     * Creates a new deployment package builder.
     * 
     * @param name the name of the deployment package
     * @param version the version of the deployment package
     * @return a builder to further add data to the deployment package
     */
    public static DeploymentPackageBuilder create(String name, String version) {
        return new DeploymentPackageBuilder(name, version);
    }

<<<<<<< HEAD
=======
    static void closeSilently(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            }
            catch (IOException e) {
                // Ignore...
            }
        }
    }

>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    /**
     * Adds an artifact to the deployment package.
     * 
     * @param builder the artifact data builder to use.
     * @return this builder.
     * @throws Exception if something goes wrong while building the artifact.
     */
    public DeploymentPackageBuilder add(ArtifactDataBuilder builder) throws Exception {
        ArtifactData artifactData = builder.build();
        if (artifactData.isCustomizer()) {
            m_processors.add(artifactData);
        }
        else if (artifactData.isBundle()) {
            m_bundles.add(artifactData);
        }
<<<<<<< HEAD
=======
        else if (artifactData.isLocalizationFile()) {
            m_localizationFiles.add(artifactData);
        }
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        else {
            m_artifacts.add(artifactData);
        }
        return this;
    }

    /**
     * Creates a new deployment package builder with the same symbolic name as this builder.
     * 
     * @param name the name of the deployment package
     * @param version the version of the deployment package
     * @return a builder to further add data to the deployment package
     */
    public DeploymentPackageBuilder create(String version) {
        return new DeploymentPackageBuilder(getSymbolicName(), version);
    }

    public BundleDataBuilder createBundleResource() {
        return new BundleDataBuilder();
    }

<<<<<<< HEAD
=======
    public LocalizationResourceDataBuilder createLocalizationResource() {
        return new LocalizationResourceDataBuilder();
    }

>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    public ResourceDataBuilder createResource() {
        return new ResourceDataBuilder();
    }

    public ResourceProcessorDataBuilder createResourceProcessorResource() {
        return new ResourceProcessorDataBuilder();
    }

    /**
<<<<<<< HEAD
     * Disables the verification of the generated deployment package, potentially causing an erroneous result to be generated.
     *  
=======
     * Disables the verification of the generated deployment package, potentially causing an erroneous result to be
     * generated.
     * 
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
     * @return this builder.
     */
    public DeploymentPackageBuilder disableVerification() {
        m_verification = false;
        return this;
    }

    /**
     * Generates a deployment package and streams it to the output stream you provide. Before
     * it starts generating, it will first validate that you have actually specified a
     * resource processor for each type of artifact you provided.
     * 
     * @return the input stream containing the deployment package.
     * @throws Exception if something goes wrong while validating or generating
     */
    public InputStream generate() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        generate(baos);
<<<<<<< HEAD

=======
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        return new ByteArrayInputStream(baos.toByteArray());
    }

    /**
     * Generates a deployment package and streams it to the output stream you provide. Before
     * it starts generating, it will first validate that you have actually specified a
     * resource processor for each type of artifact you provided.
     * 
     * @param output the output stream to write to
     * @throws Exception if something goes wrong while validating or generating
     */
    public void generate(OutputStream output) throws Exception {
<<<<<<< HEAD
        List<ArtifactData> artifacts = new ArrayList<ArtifactData>();
        artifacts.addAll(m_bundles);
        artifacts.addAll(m_processors);
        artifacts.addAll(m_artifacts);
        
        if (m_verification) {
            validateProcessedArtifacts();
            validateMissingArtifacts(artifacts);
        }
        
        Manifest m = createManifest(artifacts);
=======
        Manifest m = createManifest();
        List<ArtifactData> artifacts = getArtifactList();
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        writeStream(artifacts, m, output);
    }

    /**
     * @return the symbolic name of the deployment package.
     */
    public String getSymbolicName() {
        return m_symbolicName;
    }

    /**
     * @return the version of the deployment package.
     */
    public String getVersion() {
        return m_version;
    }

    /**
     * Marks this deployment package as a 'fix' package.
     * 
     * @return this builder.
     */
    public DeploymentPackageBuilder setFixPackage() {
        Version v1 = new Version(m_version);
        Version v2 = new Version(v1.getMajor() + 1, 0, 0);
        String version = String.format("[%d.%d, %d.%d)", v1.getMajor(), v1.getMinor(), v2.getMajor(), v2.getMinor());
        return setFixPackage(version);
    }

    /**
     * Marks this deployment package as a 'fix' package.
     * 
     * @param versionRange the version range in which this fix-package should be applied.
     * @return this builder.
     */
    public DeploymentPackageBuilder setFixPackage(String versionRange) {
        m_fixPackageVersion = versionRange;
        return this;
    }

<<<<<<< HEAD
=======
    /**
     * Enables the creating of a signed deployment package, equivalent to creating a signed JAR file.
     * <p>
     * This method assumes the use of self-signed certificates for the signing process.
     * </p>
     * 
     * @param signingKey the private key of the signer;
     * @param signingCert the public certificate of the signer.
     * @return this builder.
     */
    public DeploymentPackageBuilder signOutput(PrivateKey signingKey, X509Certificate signingCert) {
        m_signingKey = signingKey;
        m_signingCert = signingCert;
        return this;
    }

    final Manifest createManifest() throws Exception {
        List<ArtifactData> artifacts = new ArrayList<ArtifactData>();
        artifacts.addAll(m_localizationFiles);
        artifacts.addAll(m_bundles);
        artifacts.addAll(m_processors);
        artifacts.addAll(m_artifacts);

        if (m_verification) {
            validateProcessedArtifacts();
            validateMissingArtifacts(artifacts);
        }

        return createManifest(artifacts);
    }

    final List<ArtifactData> getArtifactList() {
        // The order in which the actual entries are added to the JAR is different than we're using for the manifest...
        List<ArtifactData> artifacts = new ArrayList<ArtifactData>();
        artifacts.addAll(m_bundles);
        artifacts.addAll(m_processors);
        artifacts.addAll(m_localizationFiles);
        artifacts.addAll(m_artifacts);
        return artifacts;
    }

>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    private Manifest createManifest(List<ArtifactData> files) throws Exception {
        Manifest manifest = new Manifest();
        Attributes main = manifest.getMainAttributes();
        main.putValue("Manifest-Version", "1.0");
        main.putValue("DeploymentPackage-SymbolicName", m_symbolicName);
        main.putValue("DeploymentPackage-Version", m_version);

        if ((m_fixPackageVersion != null) && !"".equals(m_fixPackageVersion)) {
            main.putValue("DeploymentPackage-FixPack", m_fixPackageVersion);
        }

        Map<String, Attributes> entries = manifest.getEntries();

<<<<<<< HEAD
        Iterator<ArtifactData> filesIter = files.iterator();
        while (filesIter.hasNext()) {
            ArtifactData file = filesIter.next();

            Attributes a = new Attributes();
            a.putValue("Name", file.getFilename());

            if (file.isBundle()) {
                a.putValue("Bundle-SymbolicName", file.getSymbolicName());
                a.putValue("Bundle-Version", file.getVersion());
                if (file.isCustomizer()) {
                    a.putValue("DeploymentPackage-Customizer", "true");
                    a.putValue("Deployment-ProvidesResourceProcessor", file.getProcessorPid());
                }
            }
            else {
                a.putValue("Resource-Processor", file.getProcessorPid());
            }

            if (file.isMissing()) {
                a.putValue("DeploymentPackage-Missing", "true");
            }

            entries.put(file.getFilename(), a);
=======
        for (ArtifactData file : files) {
            Attributes attrs = new Attributes();
            attrs.putValue("Name", file.getFilename());

            if (file.isBundle()) {
                attrs.putValue("Bundle-SymbolicName", file.getSymbolicName());
                attrs.putValue("Bundle-Version", file.getVersion());
                if (file.isCustomizer()) {
                    attrs.putValue("DeploymentPackage-Customizer", "true");
                    attrs.putValue("Deployment-ProvidesResourceProcessor", file.getProcessorPid());
                }
            }
            else if (file.isResourceProcessorNeeded()) {
                attrs.putValue("Resource-Processor", file.getProcessorPid());
            }

            if (file.isMissing()) {
                attrs.putValue("DeploymentPackage-Missing", "true");
            }

            if (isAddSignatures()) {
                m_signer.addDigestAttribute(attrs, file);
            }

            entries.put(file.getFilename(), attrs);
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        }

        return manifest;
    }
<<<<<<< HEAD
    
    private void validateMissingArtifacts(List<ArtifactData> files) throws Exception {
        boolean missing = false;
        
        Iterator<ArtifactData> artifactIter = files.iterator();
        while (artifactIter.hasNext() && !missing) {
            ArtifactData data = artifactIter.next();
            
=======

    private boolean isAddSignatures() {
        return m_signingKey != null && m_signingCert != null;
    }

    private void validateMissingArtifacts(List<ArtifactData> files) throws Exception {
        boolean missing = false;

        Iterator<ArtifactData> artifactIter = files.iterator();
        while (artifactIter.hasNext() && !missing) {
            ArtifactData data = artifactIter.next();

>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
            if (data.isMissing()) {
                missing = true;
            }
        }
<<<<<<< HEAD
        
=======

>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
        if (missing && (m_fixPackageVersion == null || "".equals(m_fixPackageVersion))) {
            throw new Exception("Artifact cannot be missing without a fix package version!");
        }
    }

    private void validateProcessedArtifacts() throws Exception {
        Iterator<ArtifactData> artifactIter = m_artifacts.iterator();
        while (artifactIter.hasNext()) {
            ArtifactData data = artifactIter.next();
            String pid = data.getProcessorPid();
<<<<<<< HEAD
            boolean found = false;

            Iterator<ArtifactData> processorIter = m_processors.iterator();
            while (processorIter.hasNext()) {
                ArtifactData processor = processorIter.next();
                if (pid.equals(processor.getProcessorPid())) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                throw new Exception("No resource processor found for artifact " + data.getURL()
                    + " with processor PID " + pid);
=======
            boolean found = pid == null;

            Iterator<ArtifactData> processorIter = m_processors.iterator();
            while (!found && processorIter.hasNext()) {
                ArtifactData processor = processorIter.next();
                if (pid.equals(processor.getProcessorPid())) {
                    found = true;
                }
            }

            if (!found && data.isResourceProcessorNeeded()) {
                throw new Exception("No resource processor found for artifact " + data.getURL() + " with processor PID " + pid);
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
            }
        }
    }

    private void writeStream(List<ArtifactData> files, Manifest manifest, OutputStream outputStream) throws Exception {
<<<<<<< HEAD
        JarOutputStream output = null;
        InputStream fis = null;
        try {
            output = new JarOutputStream(outputStream, manifest);
            byte[] buffer = new byte[BUFFER_SIZE];

            Iterator<ArtifactData> filesIter = files.iterator();
            while (filesIter.hasNext()) {
                ArtifactData file = filesIter.next();
=======
        byte[] buffer = new byte[BUFFER_SIZE];

        try (JarOutputStream output = new JarOutputStream(outputStream)) {
            // Write out the manifest...
            if (isAddSignatures()) {
                m_signer.writeSignedManifest(manifest, output, m_signingKey, m_signingCert);
            }
            else {
                output.putNextEntry(new ZipEntry(JarFile.MANIFEST_NAME));
                manifest.write(output);
                output.closeEntry();
            }

            for (ArtifactData file : files) {
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
                if (file.isMissing()) {
                    // No need to write the 'missing' files...
                    continue;
                }

                output.putNextEntry(new JarEntry(file.getFilename()));

<<<<<<< HEAD
                ResourceFilter filter = file.getFilter();
                if (filter != null) {
                    fis = filter.createInputStream(file.getURL());
                }
                else {
                    fis = file.getURL().openStream();
                }

                try {
                    int bytes = fis.read(buffer);
                    while (bytes != -1) {
                        output.write(buffer, 0, bytes);
                        bytes = fis.read(buffer);
                    }
                }
                finally {
                    fis.close();
                    fis = null;

=======
                try (InputStream is = file.createInputStream()) {
                    int bytes;
                    while ((bytes = is.read(buffer)) != -1) {
                        output.write(buffer, 0, bytes);
                    }
                }
                finally {
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
                    output.closeEntry();
                }
            }
        }
<<<<<<< HEAD
        finally {
            if (fis != null) {
                fis.close();
            }
            if (output != null) {
                output.close();
            }
        }
=======
>>>>>>> 502e622adcc798bcbd433d6b42ca78673cfab368
    }
}
