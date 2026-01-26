package com.example.demo.archunit

import com.tngtech.archunit.core.domain.JavaClasses

class HexagonalArchitecture(
    basePackage: String
) : ArchitectureElement(basePackage) {

    private lateinit var adapters: Adapters
    private lateinit var applicationLayer: ApplicationLayer
    private var configurationPackage: String? = null
    private val domainPackages = mutableListOf<String>()

    companion object {
        @JvmStatic
        fun basePackage(basePackage: String): HexagonalArchitecture {
            return HexagonalArchitecture(basePackage)
        }
    }

    fun withAdaptersLayer(adaptersPackage: String): Adapters {
        adapters = Adapters(this, fullQualifiedPackage(adaptersPackage))
        return adapters
    }

    fun withDomainLayer(domainPackage: String): HexagonalArchitecture {
        domainPackages.add(fullQualifiedPackage(domainPackage))
        return this
    }

    fun withApplicationLayer(applicationPackage: String): ApplicationLayer {
        applicationLayer = ApplicationLayer(fullQualifiedPackage(applicationPackage), this)
        return applicationLayer
    }

    fun withConfiguration(packageName: String): HexagonalArchitecture {
        configurationPackage = fullQualifiedPackage(packageName)
        return this
    }

    private fun domainDoesNotDependOnAdapters(classes: JavaClasses) {
        denyAnyDependency(
            domainPackages,
            listOf(adapters.adaptersBasePackage),
            classes
        )
    }

    fun check(classes: JavaClasses) {
        adapters.doesNotContainEmptyPackages()
        adapters.dontDependOnEachOther(classes)
        configurationPackage?.let { config ->
            adapters.doesNotDependOn(config, classes)
            applicationLayer.doesNotDependOn(config, classes)
        }

        applicationLayer.doesNotContainEmptyPackages()
        applicationLayer.doesNotDependOn(adapters.adaptersBasePackage, classes)
        applicationLayer.incomingAndOutgoingPortsDoNotDependOnEachOther(classes)

        domainDoesNotDependOnAdapters(classes)
    }
}