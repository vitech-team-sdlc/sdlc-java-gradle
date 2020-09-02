package com.vitech.moodfeed.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.vitech.moodfeed.MediumTest;
import com.vitech.moodfeed.RepoMediumTest;
import com.vitech.moodfeed.WebMediumTest;
import liquibase.Liquibase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.testcontainers.containers.MySQLContainer;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

@AnalyzeClasses(packages = "com.vitech.moodfeed")
public class MediumTestsArchTest {

    @ArchTest
    static final ArchRule methods_naming_convention = methods()
            .that().areAnnotatedWith(Test.class)
            .or().areAnnotatedWith(ParameterizedTest.class)
            .should().haveNameMatching("test.*");

    @ArchTest
    static final ArchRule methods_naming_convention_reversed = methods()
            .that().haveNameMatching("test.*")
            .should().beAnnotatedWith(Test.class)
            .orShould().beAnnotatedWith(ParameterizedTest.class);

    @ArchTest
    static final ArchRule medium_test_rules = classes()
            .that().haveSimpleNameEndingWith("IT")
            .should().beAssignableTo(MediumTest.class);

    @ArchTest
    static final ArchRule medium_test_rules_reversed = classes()
            .that().areAssignableTo(MediumTest.class)
            .and().areNotAssignableFrom(RepoMediumTest.class)
            .and().areNotAssignableFrom(WebMediumTest.class)
            .should().haveSimpleNameEndingWith("IT");

    @ArchTest
    static final ArchRule controller_medium_test_rules = classes()
            .that().haveSimpleNameEndingWith("ControllerIT")
            .should().beAssignableTo(WebMediumTest.class);

    @ArchTest
    static final ArchRule controller_medium_test_rules_reversed = classes()
            .that().areAssignableTo(WebMediumTest.class)
            .and().areNotAssignableFrom(WebMediumTest.class)
            .should().haveSimpleNameEndingWith("ControllerIT");

    @ArchTest
    static final ArchRule repository_medium_test_rules = classes()
            .that().haveSimpleNameEndingWith("RepositoryIT")
            .should().beAssignableTo(RepoMediumTest.class);

    @ArchTest
    static final ArchRule repository_medium_test_rules_reversed = classes()
            .that().areAssignableTo(RepoMediumTest.class)
            .and().areNotAssignableFrom(RepoMediumTest.class)
            .should().haveSimpleNameEndingWith("RepositoryIT");

    @ArchTest
    static final ArchRule shared_fields_declared_in_base_class = fields()
            .that().areDeclaredInClassesThat().areAssignableTo(MediumTest.class)
            .and().areDeclaredInClassesThat().areNotAssignableFrom(RepoMediumTest.class)
            .and().areDeclaredInClassesThat().areNotAssignableFrom(WebMediumTest.class)
            .should().notHaveRawType(MySQLContainer.class)
            .andShould().notHaveRawType(TestRestTemplate.class)
            .andShould().notHaveRawType(Liquibase.class);

}
