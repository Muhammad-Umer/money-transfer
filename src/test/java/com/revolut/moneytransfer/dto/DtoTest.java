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

    private static final String DIRECTORY = "com.revolut.moneytransfer.dto";

    @Test
    public void testControllerJsonDtos() throws IOException {
        Set<ClassPath.ClassInfo> set = ClassPath.from(this.getClass().getClassLoader()).getTopLevelClasses(DIRECTORY);

        for (ClassPath.ClassInfo classUnderTest : set) {
            assertPojoMethodsFor(classUnderTest.getName())
                    .testing(Method.GETTER, Method.SETTER)
                    .testing(Method.CONSTRUCTOR)
                    .areWellImplemented();
        }
    }

}
