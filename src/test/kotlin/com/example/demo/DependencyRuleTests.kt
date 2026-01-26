package com.example.demo

import com.example.demo.archunit.HexagonalArchitecture
import com.tngtech.archunit.core.importer.ClassFileImporter
import org.junit.jupiter.api.Test

class DependencyRuleTests {

    @Test
    fun validateRegistrationContextArchitecture() {
        HexagonalArchitecture.basePackage("com.example.demo")
            .withDomainLayer("account.domain")
            .withAdaptersLayer("account.adapter")
            .incoming("in.web")
            .outgoing("out.persistence")
            .and()
            .withApplicationLayer("account.application")
            .incomingPorts("port.in")
            .outgoingPorts("port.out")
            .and()
//            .withConfiguration("common") // @WebAdapter와 @PersistenceAdapter 어노테이션이 common 패키지에 있어서 발생하는 문제
            .check(
                ClassFileImporter()
                    .importPackages("com.example.demo..")
            )
    }

}