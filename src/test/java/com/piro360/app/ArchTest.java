package com.piro360.app;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.piro360.app");

        noClasses()
            .that()
            .resideInAnyPackage("com.piro360.app.service..")
            .or()
            .resideInAnyPackage("com.piro360.app.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.piro360.app.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
