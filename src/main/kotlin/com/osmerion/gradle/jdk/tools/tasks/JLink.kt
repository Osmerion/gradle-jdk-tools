package com.osmerion.gradle.jdk.tools.tasks

import com.osmerion.gradle.jdk.tools.internal.finalizeAndGet
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import org.gradle.process.ExecOperations
import java.io.File
import javax.inject.Inject

/**
 * The `JLink` task links a custom runtime image using the JDK's `jlink` tool.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
@CacheableTask
public open class JLink @Inject constructor(
    private val execOperations: ExecOperations,
    private val fsOperations: FileSystemOperations,
    objectFactory: ObjectFactory
) : DefaultTask() {

    /**
     * Configures the module path to use when linking the runtime image.
     *
     * @since   0.1.0
     */
    @get:Classpath
    public val modulePath: ConfigurableFileCollection = objectFactory.fileCollection()

    /**
     * Configures the module path to use when linking the runtime image.
     *
     * @see [ConfigurableFileCollection.from]
     *
     * @since   0.1.0
     */
    public fun modulePath(vararg paths: Any) {
        modulePath.from(paths)
    }

    /**
     * Root modules to resolve in addition to the initial modules from the module path.
     *
     * May also be `ALL-MODULE-PATH` to include all modules in the module path.
     *
     * @since   0.1.0
     */
    @get:Input
    public val addModules: ListProperty<String> = objectFactory.listProperty(String::class.java)

    /**
     * Configures the `--no-header-files` option.
     *
     * By default, this property is set to `true`.
     *
     * @since   0.1.0
     */
    @get:Input
    public val noHeaderFiles: Property<Boolean> = objectFactory.property(Boolean::class.java)
        .convention(true)

    /**
     * Configures the `--no-man-pages` option.
     *
     * By default, this property is set to `true`.
     *
     * @since   0.1.0
     */
    @get:Input
    public val noManPages: Property<Boolean> = objectFactory.property(Boolean::class.java)
        .convention(true)

    /**
     * Additional arguments to pass to the `jlink` executable.
     *
     * @since   0.1.0
     */
    @get:Input
    public val args: ListProperty<String> = objectFactory.listProperty(String::class.java)

    /**
     * The `jlink` executable to use.
     *
     * @since   0.1.0
     */
    @get:InputFile
    @get:PathSensitive(PathSensitivity.NONE)
    public val executable: Property<String> = objectFactory.property(String::class.java)

    /**
     * The directory to which the runtime image will be written.
     *
     * @since   0.1.0
     */
    @get:OutputDirectory
    public val destinationDirectory: DirectoryProperty = objectFactory.directoryProperty()

    @TaskAction
    protected fun linkRuntime() {
        val modulePath = modulePath.files.toSet()
        val addModules = addModules.finalizeAndGet()
        val noHeaderFiles = noHeaderFiles.finalizeAndGet()
        val noManPages = noManPages.finalizeAndGet()
        val args = args.finalizeAndGet()
        val executable = executable.finalizeAndGet()
        val destinationDirectory = destinationDirectory.finalizeAndGet()

        val deletionResult = fsOperations.delete {
            delete(destinationDirectory)
        }

        if (deletionResult.didWork) {
            logger.debug("Deleted existing runtime image at '${destinationDirectory.asFile.absolutePath}'.")
        }

        val programArgs = buildList {
            if (modulePath.isNotEmpty()) {
                add("-p")
                add(modulePath.joinToString(separator = File.pathSeparator, transform = File::getAbsolutePath))
            }

            if (addModules.isNotEmpty()) {
                add("--add-modules")
                add(addModules.joinToString(separator = ","))
            }

            if (noHeaderFiles) add("--no-header-files")
            if (noManPages) add("--no-man-pages")

            addAll(args)

            add("--output")
            add(destinationDirectory.asFile.absolutePath)
        }

        logger.info("Running jlink executable '${executable}' with arguments: ${programArgs.joinToString(" ")}")

        val execResult = execOperations.exec {
            this.executable = executable
            args(programArgs)
        }

        execResult.assertNormalExitValue()
    }

}