package com.example.demo.archunit

import com.tngtech.archunit.core.domain.JavaClasses

class Adapters(
    private val parentContext: HexagonalArchitecture,
    basePackage: String
) : ArchitectureElement(basePackage) {

    private val incomingAdapterPackages = mutableListOf<String>()
    private val outgoingAdapterPackages = mutableListOf<String>()

    val adaptersBasePackage: String
        get() = basePackage

    fun outgoing(packageName: String): Adapters {
        incomingAdapterPackages.add(fullQualifiedPackage(packageName))
        return this
    }

    fun incoming(packageName: String): Adapters {
        outgoingAdapterPackages.add(fullQualifiedPackage(packageName))
        return this
    }

    fun allAdapterPackages(): List<String> {
        return incomingAdapterPackages + outgoingAdapterPackages
    }

    fun and(): HexagonalArchitecture = parentContext

    fun dontDependOnEachOther(classes: JavaClasses) {
        val allAdapters = allAdapterPackages()
        for (adapter1 in allAdapters) {
            for (adapter2 in allAdapters) {
                if (adapter1 != adapter2) {
                    denyDependency(adapter1, adapter2, classes)
                }
            }
        }
    }

    fun doesNotDependOn(packageName: String, classes: JavaClasses) {
        denyDependency(basePackage, packageName, classes)
    }

    fun doesNotContainEmptyPackages() {
        denyEmptyPackages(allAdapterPackages())
    }
}