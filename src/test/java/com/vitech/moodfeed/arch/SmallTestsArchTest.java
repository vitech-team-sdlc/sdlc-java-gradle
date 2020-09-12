package com.vitech.moodfeed.arch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.vitech.moodfeed.SmallTest;
import com.vitech.moodfeed.WebSmallTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

@AnalyzeClasses(packages = CodeArchTest.APP_BASE_PACKAGE)
public class SmallTestsArchTest extends SmallTest {

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

//    @ArchTest
//    static final ArchRule small_test_rules = classes()
//            .that().haveSimpleNameEndingWith("Test")
//            .should().beAssignableTo(SmallTest.class);

    @ArchTest
    static final ArchRule small_test_rules_reversed = classes()
            .that().areAssignableTo(SmallTest.class)
            .should().haveSimpleNameEndingWith("Test");

    @ArchTest
    static final ArchRule controller_small_test_rules = classes()
            .that().haveSimpleNameEndingWith("ControllerTest")
            .should().beAssignableTo(WebSmallTest.class)
            .andShould().beAnnotatedWith(WebMvcTest.class);

    @ArchTest
    static final ArchRule controller_small_test_rules_reversed = classes()
            .that().areAssignableTo(WebSmallTest.class)
            .and().areNotAssignableFrom(WebSmallTest.class)
            .should().haveSimpleNameEndingWith("ControllerTest");

    @ArchTest
    static final ArchRule shared_fields_declared_in_base_class = fields()
            .that().areDeclaredInClassesThat().areAssignableTo(SmallTest.class)
            .and().areDeclaredInClassesThat().areNotAssignableFrom(WebSmallTest.class)
            .should().notHaveRawType(MockMvc.class)
            .andShould().notHaveRawType(ObjectMapper.class);

}
