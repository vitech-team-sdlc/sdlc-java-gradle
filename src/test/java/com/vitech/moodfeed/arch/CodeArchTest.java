package com.vitech.moodfeed.arch;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.vitech.moodfeed.SmallTest;
import com.vitech.moodfeed.domain.DomainEntity;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = CodeArchTest.APP_BASE_PACKAGE, importOptions = ImportOption.DoNotIncludeTests.class)
public class CodeArchTest extends SmallTest {

    static final String APP_BASE_PACKAGE = "com.vitech.moodfeed";

    @ArchTest
    static final ArchRule spring_boot_app_rules = classes()
            .that().areAnnotatedWith(SpringBootApplication.class)
            .should().haveSimpleNameEndingWith("Application")
            .andShould().resideInAPackage(APP_BASE_PACKAGE);

    @ArchTest
    static final ArchRule configuration_rules = classes()
            .that().areAnnotatedWith(Configuration.class)
            .should().haveSimpleNameEndingWith("Config")
            .andShould().resideInAPackage("..config..");

    @ArchTest
    static final ArchRule controller_rules = classes()
            .that().areAnnotatedWith(Controller.class).or().areAnnotatedWith(RestController.class)
            .should().haveSimpleNameEndingWith("Controller")
            .andShould().resideInAPackage("..domain.*..")
            .andShould().haveOnlyFinalFields();

    @ArchTest
    static final ArchRule controllers_dependencies_rules = noClasses()
            .should().dependOnClassesThat().areAnnotatedWith(Controller.class)
            .orShould().dependOnClassesThat().areAnnotatedWith(RestController.class);

    @ArchTest
    static final ArchRule service_rules = classes()
            .that().areAnnotatedWith(Service.class)
            .should().haveSimpleNameEndingWith("Service")
            .andShould().resideInAPackage("..domain.*..")
            .andShould().haveOnlyFinalFields();

    @ArchTest
    static final ArchRule services_dependencies_rules = classes()
            .that().areAnnotatedWith(Service.class)
            .should().onlyHaveDependentClassesThat().areAnnotatedWith(Controller.class)
            .orShould().onlyHaveDependentClassesThat().areAnnotatedWith(RestController.class);

    @ArchTest
    static final ArchRule repository_rules = classes()
            .that().areAnnotatedWith(Repository.class).or().areAssignableTo(CrudRepository.class)
            .should().haveSimpleNameEndingWith("Repository")
            .andShould().resideInAPackage("..domain.*..")
            .andShould().haveOnlyFinalFields();

    @ArchTest
    static final ArchRule repositories_dependencies_rules = classes()
            .that().areAnnotatedWith(Repository.class).or().areAssignableTo(CrudRepository.class)
            .should().onlyHaveDependentClassesThat().areAnnotatedWith(Service.class);

    @ArchTest
    static final ArchRule forbidden_packages = noClasses()
            .should().resideInAnyPackage("..controller..", "..service..", "..repository..", "..repo..");

    @ArchTest
    static final ArchRule domain_model_rules = fields()
            .that().areDeclaredInClassesThat().areAnnotatedWith(DomainEntity.class)
            .should().beFinal();

    @ArchTest
    static final ArchRule dto_rules = fields()
            .that().areDeclaredInClassesThat().resideInAPackage("..dto..")
            .and().areDeclaredInClassesThat().haveSimpleNameNotEndingWith("Builder")
            .should().beFinal();

}
