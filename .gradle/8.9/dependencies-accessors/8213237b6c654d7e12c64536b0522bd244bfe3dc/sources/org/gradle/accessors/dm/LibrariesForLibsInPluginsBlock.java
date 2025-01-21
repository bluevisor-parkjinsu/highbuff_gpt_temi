package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the {@code libs} extension.
 */
@NonNullApi
public class LibrariesForLibsInPluginsBlock extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final AndroidLibraryAccessors laccForAndroidLibraryAccessors = new AndroidLibraryAccessors(owner);
    private final AndroidxLibraryAccessors laccForAndroidxLibraryAccessors = new AndroidxLibraryAccessors(owner);
    private final ArthenicaLibraryAccessors laccForArthenicaLibraryAccessors = new ArthenicaLibraryAccessors(owner);
    private final BumptechLibraryAccessors laccForBumptechLibraryAccessors = new BumptechLibraryAccessors(owner);
    private final Coil3LibraryAccessors laccForCoil3LibraryAccessors = new Coil3LibraryAccessors(owner);
    private final GkonovalovLibraryAccessors laccForGkonovalovLibraryAccessors = new GkonovalovLibraryAccessors(owner);
    private final GoogleLibraryAccessors laccForGoogleLibraryAccessors = new GoogleLibraryAccessors(owner);
    private final KotlinLibraryAccessors laccForKotlinLibraryAccessors = new KotlinLibraryAccessors(owner);
    private final ParksanggwonLibraryAccessors laccForParksanggwonLibraryAccessors = new ParksanggwonLibraryAccessors(owner);
    private final RobotemiLibraryAccessors laccForRobotemiLibraryAccessors = new RobotemiLibraryAccessors(owner);
    private final SquareupLibraryAccessors laccForSquareupLibraryAccessors = new SquareupLibraryAccessors(owner);
    private final TensorflowLibraryAccessors laccForTensorflowLibraryAccessors = new TensorflowLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibsInPluginsBlock(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

    /**
     * Dependency provider for <b>junit</b> with <b>junit:junit</b> coordinates and
     * with version reference <b>junit</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     *
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public Provider<MinimalExternalModuleDependency> getJunit() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
        return create("junit");
    }

    /**
     * Group of libraries at <b>android</b>
     *
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public AndroidLibraryAccessors getAndroid() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
        return laccForAndroidLibraryAccessors;
    }

    /**
     * Group of libraries at <b>androidx</b>
     *
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public AndroidxLibraryAccessors getAndroidx() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
        return laccForAndroidxLibraryAccessors;
    }

    /**
     * Group of libraries at <b>arthenica</b>
     *
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public ArthenicaLibraryAccessors getArthenica() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
        return laccForArthenicaLibraryAccessors;
    }

    /**
     * Group of libraries at <b>bumptech</b>
     *
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public BumptechLibraryAccessors getBumptech() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
        return laccForBumptechLibraryAccessors;
    }

    /**
     * Group of libraries at <b>coil3</b>
     *
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public Coil3LibraryAccessors getCoil3() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
        return laccForCoil3LibraryAccessors;
    }

    /**
     * Group of libraries at <b>gkonovalov</b>
     *
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public GkonovalovLibraryAccessors getGkonovalov() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
        return laccForGkonovalovLibraryAccessors;
    }

    /**
     * Group of libraries at <b>google</b>
     *
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public GoogleLibraryAccessors getGoogle() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
        return laccForGoogleLibraryAccessors;
    }

    /**
     * Group of libraries at <b>kotlin</b>
     *
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public KotlinLibraryAccessors getKotlin() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
        return laccForKotlinLibraryAccessors;
    }

    /**
     * Group of libraries at <b>parksanggwon</b>
     *
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public ParksanggwonLibraryAccessors getParksanggwon() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
        return laccForParksanggwonLibraryAccessors;
    }

    /**
     * Group of libraries at <b>robotemi</b>
     *
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public RobotemiLibraryAccessors getRobotemi() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
        return laccForRobotemiLibraryAccessors;
    }

    /**
     * Group of libraries at <b>squareup</b>
     *
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public SquareupLibraryAccessors getSquareup() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
        return laccForSquareupLibraryAccessors;
    }

    /**
     * Group of libraries at <b>tensorflow</b>
     *
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public TensorflowLibraryAccessors getTensorflow() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
        return laccForTensorflowLibraryAccessors;
    }

    /**
     * Group of versions at <b>versions</b>
     */
    public VersionAccessors getVersions() {
        return vaccForVersionAccessors;
    }

    /**
     * Group of bundles at <b>bundles</b>
     *
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public BundleAccessors getBundles() {
        org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
        return baccForBundleAccessors;
    }

    /**
     * Group of plugins at <b>plugins</b>
     */
    public PluginAccessors getPlugins() {
        return paccForPluginAccessors;
    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidLibraryAccessors extends SubDependencyFactory {
        private final AndroidToolsLibraryAccessors laccForAndroidToolsLibraryAccessors = new AndroidToolsLibraryAccessors(owner);

        public AndroidLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>android.tools</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidToolsLibraryAccessors getTools() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidToolsLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidToolsLibraryAccessors extends SubDependencyFactory {
        private final AndroidToolsDesugarLibraryAccessors laccForAndroidToolsDesugarLibraryAccessors = new AndroidToolsDesugarLibraryAccessors(owner);

        public AndroidToolsLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>android.tools.desugar</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidToolsDesugarLibraryAccessors getDesugar() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidToolsDesugarLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidToolsDesugarLibraryAccessors extends SubDependencyFactory {
        private final AndroidToolsDesugarJdkLibraryAccessors laccForAndroidToolsDesugarJdkLibraryAccessors = new AndroidToolsDesugarJdkLibraryAccessors(owner);

        public AndroidToolsDesugarLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>android.tools.desugar.jdk</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidToolsDesugarJdkLibraryAccessors getJdk() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidToolsDesugarJdkLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidToolsDesugarJdkLibraryAccessors extends SubDependencyFactory {

        public AndroidToolsDesugarJdkLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>libs</b> with <b>com.android.tools:desugar_jdk_libs</b> coordinates and
         * with version reference <b>androidDesugarJdkLibs</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getLibs() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("android.tools.desugar.jdk.libs");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidxLibraryAccessors extends SubDependencyFactory {
        private final AndroidxActivityLibraryAccessors laccForAndroidxActivityLibraryAccessors = new AndroidxActivityLibraryAccessors(owner);
        private final AndroidxCameraxLibraryAccessors laccForAndroidxCameraxLibraryAccessors = new AndroidxCameraxLibraryAccessors(owner);
        private final AndroidxComposeLibraryAccessors laccForAndroidxComposeLibraryAccessors = new AndroidxComposeLibraryAccessors(owner);
        private final AndroidxConcurrentLibraryAccessors laccForAndroidxConcurrentLibraryAccessors = new AndroidxConcurrentLibraryAccessors(owner);
        private final AndroidxConstraintlayoutLibraryAccessors laccForAndroidxConstraintlayoutLibraryAccessors = new AndroidxConstraintlayoutLibraryAccessors(owner);
        private final AndroidxCoreLibraryAccessors laccForAndroidxCoreLibraryAccessors = new AndroidxCoreLibraryAccessors(owner);
        private final AndroidxDatastoreLibraryAccessors laccForAndroidxDatastoreLibraryAccessors = new AndroidxDatastoreLibraryAccessors(owner);
        private final AndroidxHiltLibraryAccessors laccForAndroidxHiltLibraryAccessors = new AndroidxHiltLibraryAccessors(owner);
        private final AndroidxLifecycleLibraryAccessors laccForAndroidxLifecycleLibraryAccessors = new AndroidxLifecycleLibraryAccessors(owner);
        private final AndroidxNavigationLibraryAccessors laccForAndroidxNavigationLibraryAccessors = new AndroidxNavigationLibraryAccessors(owner);
        private final AndroidxTestLibraryAccessors laccForAndroidxTestLibraryAccessors = new AndroidxTestLibraryAccessors(owner);

        public AndroidxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>appcompat</b> with <b>androidx.appcompat:appcompat</b> coordinates and
         * with version reference <b>androidAppcompat</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getAppcompat() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.appcompat");
        }

        /**
         * Dependency provider for <b>window</b> with <b>androidx.window:window</b> coordinates and
         * with version reference <b>androidWindow</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getWindow() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.window");
        }

        /**
         * Group of libraries at <b>androidx.activity</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidxActivityLibraryAccessors getActivity() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidxActivityLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.camerax</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidxCameraxLibraryAccessors getCamerax() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidxCameraxLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.compose</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidxComposeLibraryAccessors getCompose() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidxComposeLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.concurrent</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidxConcurrentLibraryAccessors getConcurrent() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidxConcurrentLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.constraintlayout</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidxConstraintlayoutLibraryAccessors getConstraintlayout() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidxConstraintlayoutLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.core</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidxCoreLibraryAccessors getCore() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidxCoreLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.datastore</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidxDatastoreLibraryAccessors getDatastore() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidxDatastoreLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.hilt</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidxHiltLibraryAccessors getHilt() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidxHiltLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.lifecycle</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidxLifecycleLibraryAccessors getLifecycle() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidxLifecycleLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.navigation</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidxNavigationLibraryAccessors getNavigation() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidxNavigationLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.test</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidxTestLibraryAccessors getTest() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidxTestLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidxActivityLibraryAccessors extends SubDependencyFactory {

        public AndroidxActivityLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>compose</b> with <b>androidx.activity:activity-compose</b> coordinates and
         * with version reference <b>androidActivityCompose</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getCompose() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.activity.compose");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidxCameraxLibraryAccessors extends SubDependencyFactory {

        public AndroidxCameraxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>camera2</b> with <b>androidx.camera:camera-camera2</b> coordinates and
         * with version reference <b>androidCameraX</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getCamera2() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.camerax.camera2");
        }

        /**
         * Dependency provider for <b>lifecycle</b> with <b>androidx.camera:camera-lifecycle</b> coordinates and
         * with version reference <b>androidCameraX</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getLifecycle() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.camerax.lifecycle");
        }

        /**
         * Dependency provider for <b>view</b> with <b>androidx.camera:camera-view</b> coordinates and
         * with version reference <b>androidCameraX</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getView() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.camerax.view");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidxComposeLibraryAccessors extends SubDependencyFactory {
        private final AndroidxComposeRuntimeLibraryAccessors laccForAndroidxComposeRuntimeLibraryAccessors = new AndroidxComposeRuntimeLibraryAccessors(owner);
        private final AndroidxComposeUiLibraryAccessors laccForAndroidxComposeUiLibraryAccessors = new AndroidxComposeUiLibraryAccessors(owner);

        public AndroidxComposeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>bom</b> with <b>androidx.compose:compose-bom</b> coordinates and
         * with version reference <b>androidComposeBom</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getBom() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.compose.bom");
        }

        /**
         * Dependency provider for <b>material3</b> with <b>androidx.compose.material3:material3</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getMaterial3() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.compose.material3");
        }

        /**
         * Group of libraries at <b>androidx.compose.runtime</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidxComposeRuntimeLibraryAccessors getRuntime() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidxComposeRuntimeLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.compose.ui</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidxComposeUiLibraryAccessors getUi() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidxComposeUiLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidxComposeRuntimeLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public AndroidxComposeRuntimeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>runtime</b> with <b>androidx.compose.runtime:runtime</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> asProvider() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.compose.runtime");
        }

        /**
         * Dependency provider for <b>livedata</b> with <b>androidx.compose.runtime:runtime-livedata</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getLivedata() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.compose.runtime.livedata");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidxComposeUiLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {
        private final AndroidxComposeUiTestLibraryAccessors laccForAndroidxComposeUiTestLibraryAccessors = new AndroidxComposeUiTestLibraryAccessors(owner);
        private final AndroidxComposeUiToolingLibraryAccessors laccForAndroidxComposeUiToolingLibraryAccessors = new AndroidxComposeUiToolingLibraryAccessors(owner);

        public AndroidxComposeUiLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>ui</b> with <b>androidx.compose.ui:ui</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> asProvider() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.compose.ui");
        }

        /**
         * Dependency provider for <b>graphics</b> with <b>androidx.compose.ui:ui-graphics</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getGraphics() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.compose.ui.graphics");
        }

        /**
         * Group of libraries at <b>androidx.compose.ui.test</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidxComposeUiTestLibraryAccessors getTest() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidxComposeUiTestLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.compose.ui.tooling</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidxComposeUiToolingLibraryAccessors getTooling() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidxComposeUiToolingLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidxComposeUiTestLibraryAccessors extends SubDependencyFactory {

        public AndroidxComposeUiTestLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>junit4</b> with <b>androidx.compose.ui:ui-test-junit4</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getJunit4() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.compose.ui.test.junit4");
        }

        /**
         * Dependency provider for <b>manifest</b> with <b>androidx.compose.ui:ui-test-manifest</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getManifest() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.compose.ui.test.manifest");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidxComposeUiToolingLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public AndroidxComposeUiToolingLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>tooling</b> with <b>androidx.compose.ui:ui-tooling</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> asProvider() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.compose.ui.tooling");
        }

        /**
         * Dependency provider for <b>preview</b> with <b>androidx.compose.ui:ui-tooling-preview</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getPreview() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.compose.ui.tooling.preview");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidxConcurrentLibraryAccessors extends SubDependencyFactory {
        private final AndroidxConcurrentFuturesLibraryAccessors laccForAndroidxConcurrentFuturesLibraryAccessors = new AndroidxConcurrentFuturesLibraryAccessors(owner);

        public AndroidxConcurrentLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>androidx.concurrent.futures</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidxConcurrentFuturesLibraryAccessors getFutures() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidxConcurrentFuturesLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidxConcurrentFuturesLibraryAccessors extends SubDependencyFactory {

        public AndroidxConcurrentFuturesLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.concurrent:concurrent-futures-ktx</b> coordinates and
         * with version reference <b>androidConcurrent</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getKtx() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.concurrent.futures.ktx");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidxConstraintlayoutLibraryAccessors extends SubDependencyFactory {

        public AndroidxConstraintlayoutLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>compose</b> with <b>androidx.constraintlayout:constraintlayout-compose</b> coordinates and
         * with version reference <b>androidConstraintLayoutCompose</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getCompose() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.constraintlayout.compose");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidxCoreLibraryAccessors extends SubDependencyFactory {

        public AndroidxCoreLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.core:core-ktx</b> coordinates and
         * with version reference <b>androidCore</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getKtx() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.core.ktx");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidxDatastoreLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public AndroidxDatastoreLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>datastore</b> with <b>androidx.datastore:datastore</b> coordinates and
         * with version reference <b>androidDatastore</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> asProvider() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.datastore");
        }

        /**
         * Dependency provider for <b>preferences</b> with <b>androidx.datastore:datastore-preferences</b> coordinates and
         * with version reference <b>androidDatastore</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getPreferences() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.datastore.preferences");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidxHiltLibraryAccessors extends SubDependencyFactory {
        private final AndroidxHiltNavigationLibraryAccessors laccForAndroidxHiltNavigationLibraryAccessors = new AndroidxHiltNavigationLibraryAccessors(owner);

        public AndroidxHiltLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>androidx.hilt.navigation</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidxHiltNavigationLibraryAccessors getNavigation() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidxHiltNavigationLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidxHiltNavigationLibraryAccessors extends SubDependencyFactory {

        public AndroidxHiltNavigationLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>compose</b> with <b>androidx.hilt:hilt-navigation-compose</b> coordinates and
         * with version reference <b>androidHilt</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getCompose() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.hilt.navigation.compose");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidxLifecycleLibraryAccessors extends SubDependencyFactory {
        private final AndroidxLifecycleLivedataLibraryAccessors laccForAndroidxLifecycleLivedataLibraryAccessors = new AndroidxLifecycleLivedataLibraryAccessors(owner);
        private final AndroidxLifecycleRuntimeLibraryAccessors laccForAndroidxLifecycleRuntimeLibraryAccessors = new AndroidxLifecycleRuntimeLibraryAccessors(owner);
        private final AndroidxLifecycleViewmodelLibraryAccessors laccForAndroidxLifecycleViewmodelLibraryAccessors = new AndroidxLifecycleViewmodelLibraryAccessors(owner);

        public AndroidxLifecycleLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>androidx.lifecycle.livedata</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidxLifecycleLivedataLibraryAccessors getLivedata() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidxLifecycleLivedataLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.lifecycle.runtime</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidxLifecycleRuntimeLibraryAccessors getRuntime() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidxLifecycleRuntimeLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.lifecycle.viewmodel</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidxLifecycleViewmodelLibraryAccessors getViewmodel() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidxLifecycleViewmodelLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidxLifecycleLivedataLibraryAccessors extends SubDependencyFactory {

        public AndroidxLifecycleLivedataLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.lifecycle:lifecycle-livedata-ktx</b> coordinates and
         * with version reference <b>androidLifecycle</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getKtx() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.lifecycle.livedata.ktx");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidxLifecycleRuntimeLibraryAccessors extends SubDependencyFactory {

        public AndroidxLifecycleRuntimeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>compose</b> with <b>androidx.lifecycle:lifecycle-runtime-compose</b> coordinates and
         * with version reference <b>androidLifecycle</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getCompose() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.lifecycle.runtime.compose");
        }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.lifecycle:lifecycle-runtime-ktx</b> coordinates and
         * with version reference <b>androidLifecycle</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getKtx() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.lifecycle.runtime.ktx");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidxLifecycleViewmodelLibraryAccessors extends SubDependencyFactory {

        public AndroidxLifecycleViewmodelLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>compose</b> with <b>androidx.lifecycle:lifecycle-viewmodel-compose</b> coordinates and
         * with version reference <b>androidLifecycle</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getCompose() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.lifecycle.viewmodel.compose");
        }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.lifecycle:lifecycle-viewmodel-ktx</b> coordinates and
         * with version reference <b>androidLifecycle</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getKtx() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.lifecycle.viewmodel.ktx");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidxNavigationLibraryAccessors extends SubDependencyFactory {

        public AndroidxNavigationLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>compose</b> with <b>androidx.navigation:navigation-compose</b> coordinates and
         * with version reference <b>androidNavigationCompose</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getCompose() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.navigation.compose");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidxTestLibraryAccessors extends SubDependencyFactory {
        private final AndroidxTestEspressoLibraryAccessors laccForAndroidxTestEspressoLibraryAccessors = new AndroidxTestEspressoLibraryAccessors(owner);
        private final AndroidxTestExtLibraryAccessors laccForAndroidxTestExtLibraryAccessors = new AndroidxTestExtLibraryAccessors(owner);

        public AndroidxTestLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>androidx.test.espresso</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidxTestEspressoLibraryAccessors getEspresso() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidxTestEspressoLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.test.ext</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public AndroidxTestExtLibraryAccessors getExt() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForAndroidxTestExtLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidxTestEspressoLibraryAccessors extends SubDependencyFactory {

        public AndroidxTestEspressoLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>core</b> with <b>androidx.test.espresso:espresso-core</b> coordinates and
         * with version reference <b>androidEspressoCore</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getCore() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.test.espresso.core");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class AndroidxTestExtLibraryAccessors extends SubDependencyFactory {

        public AndroidxTestExtLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>junit</b> with <b>androidx.test.ext:junit</b> coordinates and
         * with version reference <b>androidJunit</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getJunit() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("androidx.test.ext.junit");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class ArthenicaLibraryAccessors extends SubDependencyFactory {
        private final ArthenicaFfmpegLibraryAccessors laccForArthenicaFfmpegLibraryAccessors = new ArthenicaFfmpegLibraryAccessors(owner);

        public ArthenicaLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>arthenica.ffmpeg</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public ArthenicaFfmpegLibraryAccessors getFfmpeg() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForArthenicaFfmpegLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class ArthenicaFfmpegLibraryAccessors extends SubDependencyFactory {
        private final ArthenicaFfmpegKitLibraryAccessors laccForArthenicaFfmpegKitLibraryAccessors = new ArthenicaFfmpegKitLibraryAccessors(owner);

        public ArthenicaFfmpegLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>arthenica.ffmpeg.kit</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public ArthenicaFfmpegKitLibraryAccessors getKit() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForArthenicaFfmpegKitLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class ArthenicaFfmpegKitLibraryAccessors extends SubDependencyFactory {

        public ArthenicaFfmpegKitLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>full</b> with <b>com.arthenica:ffmpeg-kit-full</b> coordinates and
         * with version reference <b>arthenicaFfmpegKit</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getFull() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("arthenica.ffmpeg.kit.full");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class BumptechLibraryAccessors extends SubDependencyFactory {

        public BumptechLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>glide</b> with <b>com.github.bumptech.glide:glide</b> coordinates and
         * with version reference <b>bumptechGlide</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getGlide() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("bumptech.glide");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class Coil3LibraryAccessors extends SubDependencyFactory {
        private final Coil3NetworkLibraryAccessors laccForCoil3NetworkLibraryAccessors = new Coil3NetworkLibraryAccessors(owner);

        public Coil3LibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>compose</b> with <b>io.coil-kt.coil3:coil-compose</b> coordinates and
         * with version reference <b>coil3</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getCompose() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("coil3.compose");
        }

        /**
         * Dependency provider for <b>gif</b> with <b>io.coil-kt.coil3:coil-gif</b> coordinates and
         * with version reference <b>coil3</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getGif() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("coil3.gif");
        }

        /**
         * Group of libraries at <b>coil3.network</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Coil3NetworkLibraryAccessors getNetwork() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForCoil3NetworkLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class Coil3NetworkLibraryAccessors extends SubDependencyFactory {

        public Coil3NetworkLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>okhttp</b> with <b>io.coil-kt.coil3:coil-network-okhttp</b> coordinates and
         * with version reference <b>coil3</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getOkhttp() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("coil3.network.okhttp");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class GkonovalovLibraryAccessors extends SubDependencyFactory {
        private final GkonovalovAndroidLibraryAccessors laccForGkonovalovAndroidLibraryAccessors = new GkonovalovAndroidLibraryAccessors(owner);

        public GkonovalovLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>gkonovalov.android</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public GkonovalovAndroidLibraryAccessors getAndroid() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForGkonovalovAndroidLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class GkonovalovAndroidLibraryAccessors extends SubDependencyFactory {
        private final GkonovalovAndroidVadLibraryAccessors laccForGkonovalovAndroidVadLibraryAccessors = new GkonovalovAndroidVadLibraryAccessors(owner);

        public GkonovalovAndroidLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>gkonovalov.android.vad</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public GkonovalovAndroidVadLibraryAccessors getVad() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForGkonovalovAndroidVadLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class GkonovalovAndroidVadLibraryAccessors extends SubDependencyFactory {

        public GkonovalovAndroidVadLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>silero</b> with <b>com.github.gkonovalov.android-vad:silero</b> coordinates and
         * with version reference <b>gkonovalovAndroidVad</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getSilero() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("gkonovalov.android.vad.silero");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class GoogleLibraryAccessors extends SubDependencyFactory {
        private final GoogleDaggerLibraryAccessors laccForGoogleDaggerLibraryAccessors = new GoogleDaggerLibraryAccessors(owner);
        private final GoogleFirebaseLibraryAccessors laccForGoogleFirebaseLibraryAccessors = new GoogleFirebaseLibraryAccessors(owner);
        private final GoogleProtobufLibraryAccessors laccForGoogleProtobufLibraryAccessors = new GoogleProtobufLibraryAccessors(owner);

        public GoogleLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>google.dagger</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public GoogleDaggerLibraryAccessors getDagger() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForGoogleDaggerLibraryAccessors;
        }

        /**
         * Group of libraries at <b>google.firebase</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public GoogleFirebaseLibraryAccessors getFirebase() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForGoogleFirebaseLibraryAccessors;
        }

        /**
         * Group of libraries at <b>google.protobuf</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public GoogleProtobufLibraryAccessors getProtobuf() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForGoogleProtobufLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class GoogleDaggerLibraryAccessors extends SubDependencyFactory {
        private final GoogleDaggerHiltLibraryAccessors laccForGoogleDaggerHiltLibraryAccessors = new GoogleDaggerHiltLibraryAccessors(owner);

        public GoogleDaggerLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>google.dagger.hilt</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public GoogleDaggerHiltLibraryAccessors getHilt() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForGoogleDaggerHiltLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class GoogleDaggerHiltLibraryAccessors extends SubDependencyFactory {
        private final GoogleDaggerHiltAndroidLibraryAccessors laccForGoogleDaggerHiltAndroidLibraryAccessors = new GoogleDaggerHiltAndroidLibraryAccessors(owner);

        public GoogleDaggerHiltLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>google.dagger.hilt.android</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public GoogleDaggerHiltAndroidLibraryAccessors getAndroid() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForGoogleDaggerHiltAndroidLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class GoogleDaggerHiltAndroidLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public GoogleDaggerHiltAndroidLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>android</b> with <b>com.google.dagger:hilt-android</b> coordinates and
         * with version reference <b>googleHiltAndroid</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> asProvider() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("google.dagger.hilt.android");
        }

        /**
         * Dependency provider for <b>compiler</b> with <b>com.google.dagger:hilt-android-compiler</b> coordinates and
         * with version reference <b>googleHiltAndroid</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getCompiler() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("google.dagger.hilt.android.compiler");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class GoogleFirebaseLibraryAccessors extends SubDependencyFactory {
        private final GoogleFirebaseRealtimeLibraryAccessors laccForGoogleFirebaseRealtimeLibraryAccessors = new GoogleFirebaseRealtimeLibraryAccessors(owner);

        public GoogleFirebaseLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>bom</b> with <b>com.google.firebase:firebase-bom</b> coordinates and
         * with version reference <b>googleFirebaseBom</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getBom() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("google.firebase.bom");
        }

        /**
         * Group of libraries at <b>google.firebase.realtime</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public GoogleFirebaseRealtimeLibraryAccessors getRealtime() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForGoogleFirebaseRealtimeLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class GoogleFirebaseRealtimeLibraryAccessors extends SubDependencyFactory {

        public GoogleFirebaseRealtimeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>database</b> with <b>com.google.firebase:firebase-database</b> coordinates and
         * with <b>no version specified</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getDatabase() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("google.firebase.realtime.database");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class GoogleProtobufLibraryAccessors extends SubDependencyFactory {
        private final GoogleProtobufJavaLibraryAccessors laccForGoogleProtobufJavaLibraryAccessors = new GoogleProtobufJavaLibraryAccessors(owner);

        public GoogleProtobufLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>google.protobuf.java</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public GoogleProtobufJavaLibraryAccessors getJava() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForGoogleProtobufJavaLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class GoogleProtobufJavaLibraryAccessors extends SubDependencyFactory {

        public GoogleProtobufJavaLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>lite</b> with <b>com.google.protobuf:protobuf-javalite</b> coordinates and
         * with version reference <b>googleProtobuf</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getLite() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("google.protobuf.java.lite");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class KotlinLibraryAccessors extends SubDependencyFactory {

        public KotlinLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>reflect</b> with <b>org.jetbrains.kotlin:kotlin-reflect</b> coordinates and
         * with version reference <b>jetbrainsKotlin</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getReflect() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("kotlin.reflect");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class ParksanggwonLibraryAccessors extends SubDependencyFactory {
        private final ParksanggwonTedpermissionLibraryAccessors laccForParksanggwonTedpermissionLibraryAccessors = new ParksanggwonTedpermissionLibraryAccessors(owner);

        public ParksanggwonLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>parksanggwon.tedpermission</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public ParksanggwonTedpermissionLibraryAccessors getTedpermission() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForParksanggwonTedpermissionLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class ParksanggwonTedpermissionLibraryAccessors extends SubDependencyFactory {

        public ParksanggwonTedpermissionLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>coroutine</b> with <b>io.github.ParkSangGwon:tedpermission-coroutine</b> coordinates and
         * with version reference <b>tedPermission</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getCoroutine() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("parksanggwon.tedpermission.coroutine");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class RobotemiLibraryAccessors extends SubDependencyFactory {

        public RobotemiLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>sdk</b> with <b>com.robotemi:sdk</b> coordinates and
         * with version reference <b>robotemiSdk</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getSdk() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("robotemi.sdk");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class SquareupLibraryAccessors extends SubDependencyFactory {
        private final SquareupMoshiLibraryAccessors laccForSquareupMoshiLibraryAccessors = new SquareupMoshiLibraryAccessors(owner);
        private final SquareupOkhttp3LibraryAccessors laccForSquareupOkhttp3LibraryAccessors = new SquareupOkhttp3LibraryAccessors(owner);
        private final SquareupRetrofit2LibraryAccessors laccForSquareupRetrofit2LibraryAccessors = new SquareupRetrofit2LibraryAccessors(owner);

        public SquareupLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>okio</b> with <b>com.squareup.okio:okio</b> coordinates and
         * with version reference <b>squareupOkio</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getOkio() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("squareup.okio");
        }

        /**
         * Group of libraries at <b>squareup.moshi</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public SquareupMoshiLibraryAccessors getMoshi() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForSquareupMoshiLibraryAccessors;
        }

        /**
         * Group of libraries at <b>squareup.okhttp3</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public SquareupOkhttp3LibraryAccessors getOkhttp3() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForSquareupOkhttp3LibraryAccessors;
        }

        /**
         * Group of libraries at <b>squareup.retrofit2</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public SquareupRetrofit2LibraryAccessors getRetrofit2() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForSquareupRetrofit2LibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class SquareupMoshiLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public SquareupMoshiLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>moshi</b> with <b>com.squareup.moshi:moshi</b> coordinates and
         * with version reference <b>squareupMoshi</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> asProvider() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("squareup.moshi");
        }

        /**
         * Dependency provider for <b>kotlin</b> with <b>com.squareup.moshi:moshi-kotlin</b> coordinates and
         * with version reference <b>squareupMoshi</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getKotlin() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("squareup.moshi.kotlin");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class SquareupOkhttp3LibraryAccessors extends SubDependencyFactory {
        private final SquareupOkhttp3LoggingLibraryAccessors laccForSquareupOkhttp3LoggingLibraryAccessors = new SquareupOkhttp3LoggingLibraryAccessors(owner);

        public SquareupOkhttp3LibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>squareup.okhttp3.logging</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public SquareupOkhttp3LoggingLibraryAccessors getLogging() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForSquareupOkhttp3LoggingLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class SquareupOkhttp3LoggingLibraryAccessors extends SubDependencyFactory {

        public SquareupOkhttp3LoggingLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>interceptor</b> with <b>com.squareup.okhttp3:logging-interceptor</b> coordinates and
         * with version reference <b>squareupOkhttp3LoggingInterceptor</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getInterceptor() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("squareup.okhttp3.logging.interceptor");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class SquareupRetrofit2LibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {
        private final SquareupRetrofit2ConverterLibraryAccessors laccForSquareupRetrofit2ConverterLibraryAccessors = new SquareupRetrofit2ConverterLibraryAccessors(owner);

        public SquareupRetrofit2LibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>retrofit2</b> with <b>com.squareup.retrofit2:retrofit</b> coordinates and
         * with version reference <b>squareupRetrofit</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> asProvider() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("squareup.retrofit2");
        }

        /**
         * Group of libraries at <b>squareup.retrofit2.converter</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public SquareupRetrofit2ConverterLibraryAccessors getConverter() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForSquareupRetrofit2ConverterLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class SquareupRetrofit2ConverterLibraryAccessors extends SubDependencyFactory {

        public SquareupRetrofit2ConverterLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>moshi</b> with <b>com.squareup.retrofit2:converter-moshi</b> coordinates and
         * with version reference <b>squareupRetrofit</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getMoshi() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("squareup.retrofit2.converter.moshi");
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class TensorflowLibraryAccessors extends SubDependencyFactory {
        private final TensorflowLiteLibraryAccessors laccForTensorflowLiteLibraryAccessors = new TensorflowLiteLibraryAccessors(owner);

        public TensorflowLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>tensorflow.lite</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public TensorflowLiteLibraryAccessors getLite() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForTensorflowLiteLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class TensorflowLiteLibraryAccessors extends SubDependencyFactory {
        private final TensorflowLiteTaskLibraryAccessors laccForTensorflowLiteTaskLibraryAccessors = new TensorflowLiteTaskLibraryAccessors(owner);

        public TensorflowLiteLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>tensorflow.lite.task</b>
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public TensorflowLiteTaskLibraryAccessors getTask() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return laccForTensorflowLiteTaskLibraryAccessors;
        }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class TensorflowLiteTaskLibraryAccessors extends SubDependencyFactory {

        public TensorflowLiteTaskLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>audio</b> with <b>org.tensorflow:tensorflow-lite-task-audio</b> coordinates and
         * with version reference <b>tensorflowLiteTaskAudio</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         *
         * @deprecated Will be removed in Gradle 9.0.
         */
        @Deprecated
        public Provider<MinimalExternalModuleDependency> getAudio() {
            org.gradle.internal.deprecation.DeprecationLogger.deprecateBehaviour("Accessing libraries or bundles from version catalogs in the plugins block.").withAdvice("Only use versions or plugins from catalogs in the plugins block.").willBeRemovedInGradle9().withUpgradeGuideSection(8, "kotlin_dsl_deprecated_catalogs_plugins_block").nagUser();
            return create("tensorflow.lite.task.audio");
        }

    }

    public static class VersionAccessors extends VersionFactory  {

        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>androidActivityCompose</b> with value <b>1.9.3</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAndroidActivityCompose() { return getVersion("androidActivityCompose"); }

        /**
         * Version alias <b>androidAgp</b> with value <b>8.7.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAndroidAgp() { return getVersion("androidAgp"); }

        /**
         * Version alias <b>androidAppcompat</b> with value <b>1.7.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAndroidAppcompat() { return getVersion("androidAppcompat"); }

        /**
         * Version alias <b>androidCameraX</b> with value <b>1.4.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAndroidCameraX() { return getVersion("androidCameraX"); }

        /**
         * Version alias <b>androidComposeBom</b> with value <b>2024.10.01</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAndroidComposeBom() { return getVersion("androidComposeBom"); }

        /**
         * Version alias <b>androidConcurrent</b> with value <b>1.2.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAndroidConcurrent() { return getVersion("androidConcurrent"); }

        /**
         * Version alias <b>androidConstraintLayoutCompose</b> with value <b>1.1.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAndroidConstraintLayoutCompose() { return getVersion("androidConstraintLayoutCompose"); }

        /**
         * Version alias <b>androidCore</b> with value <b>1.15.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAndroidCore() { return getVersion("androidCore"); }

        /**
         * Version alias <b>androidDatastore</b> with value <b>1.1.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAndroidDatastore() { return getVersion("androidDatastore"); }

        /**
         * Version alias <b>androidDesugarJdkLibs</b> with value <b>2.1.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAndroidDesugarJdkLibs() { return getVersion("androidDesugarJdkLibs"); }

        /**
         * Version alias <b>androidEspressoCore</b> with value <b>3.6.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAndroidEspressoCore() { return getVersion("androidEspressoCore"); }

        /**
         * Version alias <b>androidHilt</b> with value <b>1.2.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAndroidHilt() { return getVersion("androidHilt"); }

        /**
         * Version alias <b>androidJunit</b> with value <b>1.2.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAndroidJunit() { return getVersion("androidJunit"); }

        /**
         * Version alias <b>androidLifecycle</b> with value <b>2.8.7</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAndroidLifecycle() { return getVersion("androidLifecycle"); }

        /**
         * Version alias <b>androidNavigationCompose</b> with value <b>2.8.3</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAndroidNavigationCompose() { return getVersion("androidNavigationCompose"); }

        /**
         * Version alias <b>androidWindow</b> with value <b>1.3.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAndroidWindow() { return getVersion("androidWindow"); }

        /**
         * Version alias <b>arthenicaFfmpegKit</b> with value <b>6.0-2.LTS</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getArthenicaFfmpegKit() { return getVersion("arthenicaFfmpegKit"); }

        /**
         * Version alias <b>bumptechGlide</b> with value <b>4.16.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getBumptechGlide() { return getVersion("bumptechGlide"); }

        /**
         * Version alias <b>coil3</b> with value <b>3.0.4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getCoil3() { return getVersion("coil3"); }

        /**
         * Version alias <b>gkonovalovAndroidVad</b> with value <b>2.0.6</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getGkonovalovAndroidVad() { return getVersion("gkonovalovAndroidVad"); }

        /**
         * Version alias <b>googleFirebaseBom</b> with value <b>33.6.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getGoogleFirebaseBom() { return getVersion("googleFirebaseBom"); }

        /**
         * Version alias <b>googleGmsServices</b> with value <b>4.4.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getGoogleGmsServices() { return getVersion("googleGmsServices"); }

        /**
         * Version alias <b>googleHiltAndroid</b> with value <b>2.52</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getGoogleHiltAndroid() { return getVersion("googleHiltAndroid"); }

        /**
         * Version alias <b>googleKsp</b> with value <b>2.0.0-1.0.24</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getGoogleKsp() { return getVersion("googleKsp"); }

        /**
         * Version alias <b>googleProtobuf</b> with value <b>4.28.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getGoogleProtobuf() { return getVersion("googleProtobuf"); }

        /**
         * Version alias <b>googleProtobufPlugin</b> with value <b>0.9.4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getGoogleProtobufPlugin() { return getVersion("googleProtobufPlugin"); }

        /**
         * Version alias <b>jetbrainsKotlin</b> with value <b>2.0.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJetbrainsKotlin() { return getVersion("jetbrainsKotlin"); }

        /**
         * Version alias <b>junit</b> with value <b>4.13.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJunit() { return getVersion("junit"); }

        /**
         * Version alias <b>robotemiSdk</b> with value <b>1.134.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getRobotemiSdk() { return getVersion("robotemiSdk"); }

        /**
         * Version alias <b>squareupMoshi</b> with value <b>1.15.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getSquareupMoshi() { return getVersion("squareupMoshi"); }

        /**
         * Version alias <b>squareupOkhttp3LoggingInterceptor</b> with value <b>4.12.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getSquareupOkhttp3LoggingInterceptor() { return getVersion("squareupOkhttp3LoggingInterceptor"); }

        /**
         * Version alias <b>squareupOkio</b> with value <b>3.9.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getSquareupOkio() { return getVersion("squareupOkio"); }

        /**
         * Version alias <b>squareupRetrofit</b> with value <b>2.11.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getSquareupRetrofit() { return getVersion("squareupRetrofit"); }

        /**
         * Version alias <b>tedPermission</b> with value <b>3.4.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getTedPermission() { return getVersion("tedPermission"); }

        /**
         * Version alias <b>tensorflowLiteTaskAudio</b> with value <b>0.4.4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getTensorflowLiteTaskAudio() { return getVersion("tensorflowLiteTaskAudio"); }

        /**
         * Version alias <b>undercouchDownload</b> with value <b>4.1.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getUndercouchDownload() { return getVersion("undercouchDownload"); }

    }

    /**
     * @deprecated Will be removed in Gradle 9.0.
     */
    @Deprecated
    public static class BundleAccessors extends BundleFactory {

        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

    }

    public static class PluginAccessors extends PluginFactory {
        private final AndroidPluginAccessors paccForAndroidPluginAccessors = new AndroidPluginAccessors(providers, config);
        private final DePluginAccessors paccForDePluginAccessors = new DePluginAccessors(providers, config);
        private final GooglePluginAccessors paccForGooglePluginAccessors = new GooglePluginAccessors(providers, config);
        private final JetbrainsPluginAccessors paccForJetbrainsPluginAccessors = new JetbrainsPluginAccessors(providers, config);

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of plugins at <b>plugins.android</b>
         */
        public AndroidPluginAccessors getAndroid() {
            return paccForAndroidPluginAccessors;
        }

        /**
         * Group of plugins at <b>plugins.de</b>
         */
        public DePluginAccessors getDe() {
            return paccForDePluginAccessors;
        }

        /**
         * Group of plugins at <b>plugins.google</b>
         */
        public GooglePluginAccessors getGoogle() {
            return paccForGooglePluginAccessors;
        }

        /**
         * Group of plugins at <b>plugins.jetbrains</b>
         */
        public JetbrainsPluginAccessors getJetbrains() {
            return paccForJetbrainsPluginAccessors;
        }

    }

    public static class AndroidPluginAccessors extends PluginFactory {

        public AndroidPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>android.application</b> with plugin id <b>com.android.application</b> and
         * with version reference <b>androidAgp</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getApplication() { return createPlugin("android.application"); }

        /**
         * Plugin provider for <b>android.library</b> with plugin id <b>com.android.library</b> and
         * with version reference <b>androidAgp</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getLibrary() { return createPlugin("android.library"); }

    }

    public static class DePluginAccessors extends PluginFactory {
        private final DeUndercouchPluginAccessors paccForDeUndercouchPluginAccessors = new DeUndercouchPluginAccessors(providers, config);

        public DePluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of plugins at <b>plugins.de.undercouch</b>
         */
        public DeUndercouchPluginAccessors getUndercouch() {
            return paccForDeUndercouchPluginAccessors;
        }

    }

    public static class DeUndercouchPluginAccessors extends PluginFactory {

        public DeUndercouchPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>de.undercouch.download</b> with plugin id <b>de.undercouch.download</b> and
         * with version reference <b>undercouchDownload</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getDownload() { return createPlugin("de.undercouch.download"); }

    }

    public static class GooglePluginAccessors extends PluginFactory {
        private final GoogleDaggerPluginAccessors paccForGoogleDaggerPluginAccessors = new GoogleDaggerPluginAccessors(providers, config);
        private final GoogleDevtoolsPluginAccessors paccForGoogleDevtoolsPluginAccessors = new GoogleDevtoolsPluginAccessors(providers, config);
        private final GoogleGmsPluginAccessors paccForGoogleGmsPluginAccessors = new GoogleGmsPluginAccessors(providers, config);

        public GooglePluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>google.protobuf</b> with plugin id <b>com.google.protobuf</b> and
         * with version reference <b>googleProtobufPlugin</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getProtobuf() { return createPlugin("google.protobuf"); }

        /**
         * Group of plugins at <b>plugins.google.dagger</b>
         */
        public GoogleDaggerPluginAccessors getDagger() {
            return paccForGoogleDaggerPluginAccessors;
        }

        /**
         * Group of plugins at <b>plugins.google.devtools</b>
         */
        public GoogleDevtoolsPluginAccessors getDevtools() {
            return paccForGoogleDevtoolsPluginAccessors;
        }

        /**
         * Group of plugins at <b>plugins.google.gms</b>
         */
        public GoogleGmsPluginAccessors getGms() {
            return paccForGoogleGmsPluginAccessors;
        }

    }

    public static class GoogleDaggerPluginAccessors extends PluginFactory {
        private final GoogleDaggerHiltPluginAccessors paccForGoogleDaggerHiltPluginAccessors = new GoogleDaggerHiltPluginAccessors(providers, config);

        public GoogleDaggerPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of plugins at <b>plugins.google.dagger.hilt</b>
         */
        public GoogleDaggerHiltPluginAccessors getHilt() {
            return paccForGoogleDaggerHiltPluginAccessors;
        }

    }

    public static class GoogleDaggerHiltPluginAccessors extends PluginFactory {

        public GoogleDaggerHiltPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>google.dagger.hilt.android</b> with plugin id <b>com.google.dagger.hilt.android</b> and
         * with version reference <b>googleHiltAndroid</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getAndroid() { return createPlugin("google.dagger.hilt.android"); }

    }

    public static class GoogleDevtoolsPluginAccessors extends PluginFactory {

        public GoogleDevtoolsPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>google.devtools.ksp</b> with plugin id <b>com.google.devtools.ksp</b> and
         * with version reference <b>googleKsp</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getKsp() { return createPlugin("google.devtools.ksp"); }

    }

    public static class GoogleGmsPluginAccessors extends PluginFactory {

        public GoogleGmsPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>google.gms.services</b> with plugin id <b>com.google.gms.google-services</b> and
         * with version reference <b>googleGmsServices</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getServices() { return createPlugin("google.gms.services"); }

    }

    public static class JetbrainsPluginAccessors extends PluginFactory {
        private final JetbrainsKotlinPluginAccessors paccForJetbrainsKotlinPluginAccessors = new JetbrainsKotlinPluginAccessors(providers, config);

        public JetbrainsPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of plugins at <b>plugins.jetbrains.kotlin</b>
         */
        public JetbrainsKotlinPluginAccessors getKotlin() {
            return paccForJetbrainsKotlinPluginAccessors;
        }

    }

    public static class JetbrainsKotlinPluginAccessors extends PluginFactory {

        public JetbrainsKotlinPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>jetbrains.kotlin.android</b> with plugin id <b>org.jetbrains.kotlin.android</b> and
         * with version reference <b>jetbrainsKotlin</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getAndroid() { return createPlugin("jetbrains.kotlin.android"); }

        /**
         * Plugin provider for <b>jetbrains.kotlin.compose</b> with plugin id <b>org.jetbrains.kotlin.plugin.compose</b> and
         * with version reference <b>jetbrainsKotlin</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getCompose() { return createPlugin("jetbrains.kotlin.compose"); }

    }

}
