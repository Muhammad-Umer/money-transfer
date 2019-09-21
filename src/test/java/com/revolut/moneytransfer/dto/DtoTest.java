package com.revolut.moneytransfer.dto;

import com.google.common.reflect.ClassPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import pl.pojo.tester.api.assertion.Method;

import java.io.IOException;
import java.util.Set;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("UnstableApiUsage")
public class DtoTest {

    private static final String DTO = "com.revolut.moneytransfer.dto";
    private static final String RESPONSE = "com.revolut.moneytransfer.response";

    @Test
    public void testControllerJsonDtos() throws IOException {
        Set<ClassPath.ClassInfo> set = ClassPath.from(this.getClass().getClassLoader()).getTopLevelClasses(DTO);

        for (ClassPath.ClassInfo classUnderTest : set) {
            assertPojoMethodsFor(classUnderTest.getName())
                    .testing(Method.GETTER, Method.SETTER)
                    .testing(Method.CONSTRUCTOR)
                    .areWellImplemented();
        }
    }

    @Test
    public void testControllerJsonResponse() throws IOException {
        Set<ClassPath.ClassInfo> set = ClassPath.from(this.getClass().getClassLoader()).getTopLevelClasses(RESPONSE);

        for (ClassPath.ClassInfo classUnderTest : set) {
            assertPojoMethodsFor(classUnderTest.getName())
                    .testing(Method.CONSTRUCTOR)
                    .areWellImplemented();
        }
    }

}
