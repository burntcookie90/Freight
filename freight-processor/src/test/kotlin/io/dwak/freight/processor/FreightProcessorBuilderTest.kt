package io.dwak.freight.processor

import com.google.testing.compile.JavaFileObjects
import io.dwak.freight.util.processAndAssertEquals
import org.junit.Test

class FreightProcessorBuilderTest {
  @Test
  fun testBuilderNoExtras() {
    val inputFile
            = JavaFileObjects.forSourceLines("test.TestController",
                                             "package test;",
                                             "import com.bluelinelabs.conductor.Controller;",
                                             "import io.dwak.freight.annotation.ControllerBuilder;",
                                             "import android.support.annotation.NonNull;",
                                             "import android.support.annotation.Nullable;",
                                             "import android.view.LayoutInflater;",
                                             "import android.view.View;",
                                             "import android.view.ViewGroup;",
                                             "@ControllerBuilder",
                                             "public class TestController extends Controller {",
                                             "",
                                             "  @NonNull",
                                             "  @Override",
                                             "  protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {",
                                             "    return null;",
                                             "  }",
                                             "}")
    val outputFile =
            JavaFileObjects.forSourceLines("test.TestControllerBuilder",
                    "package test;",
                    "",
                    "import com.bluelinelabs.conductor.RouterTransaction;",
                    "import io.dwak.freight.internal.annotation.HasRequiredMethods;",
                    "import java.lang.SuppressWarnings;",
                    "",
                    "@SuppressWarnings(\"unused\")",
                    "public final class TestControllerBuilder {",
                    "  public TestControllerBuilder() {",
                    "  }",
                    "",
                    "  @HasRequiredMethods({})",
                    "  public final test.TestController build() {",
                    "    return new test.TestController();",
                    "  }",
                    "",
                    "  @HasRequiredMethods({})",
                    "  public final RouterTransaction asTransaction() {",
                    "    return RouterTransaction.with(build());",
                    "  }",
                    "}")

    processAndAssertEquals(inputFile, "test/TestControllerBuilder" to outputFile)

  }

  @Test
  fun oneRequiredExtra() {
    val inputFile
        = JavaFileObjects.forSourceLines("test.TestController",
                                         "package test;",
                                         "import com.bluelinelabs.conductor.Controller;",
                                         "import io.dwak.freight.annotation.ControllerBuilder;",
                                         "import android.support.annotation.NonNull;",
                                         "import android.support.annotation.Nullable;",
                                         "import android.view.LayoutInflater;",
                                         "import android.view.View;",
                                         "import android.view.ViewGroup;",
                                         "import java.lang.String;",
                                         "import io.dwak.freight.annotation.Extra;",
                                         "import android.os.Bundle;",
                                         "@ControllerBuilder",
                                         "public class TestController extends Controller {",
                                         "  @Extra String stringExtra;",
                                         "",
                                         "  public TestController(Bundle bundle) {",
                                         "    super(bundle);",
                                         "  }",
                                         "",
                                         "  @NonNull",
                                         "  @Override",
                                         "  protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {",
                                         "    return null;",
                                         "  }",
                                         "}")
    val outputFile =
        JavaFileObjects.forSourceLines("test.TestControllerBuilder",
                                       "package test;",
                                       "",
                                       "import android.os.Bundle;",
                                       "import com.bluelinelabs.conductor.RouterTransaction;",
                                       "import io.dwak.freight.internal.annotation.HasRequiredMethods;",
                                       "import java.lang.String;",
                                       "import java.lang.SuppressWarnings;",
                                       "",
                                       "@SuppressWarnings(\"unused\")",
                                       "public final class TestControllerBuilder {",
                                       "  private final Bundle bundle;",
                                       "  public TestControllerBuilder() {",
                                       "    this.bundle = new Bundle();",
                                       "  }",
                                       "",
                                       "  public final TestControllerBuilder stringExtra(final String value) {",
                                       "    bundle.putString(\"STRINGEXTRA\", value);",
                                       "    return this;",
                                       "  }",
                                       "",
                                       "  @HasRequiredMethods({\"stringExtra\"})",
                                       "  public final test.TestController build() {",
                                       "    return new test.TestController(bundle);",
                                       "  }",
                                       "",
                                       "  @HasRequiredMethods({\"stringExtra\"})",
                                       "  public final RouterTransaction asTransaction() {",
                                       "    return RouterTransaction.with(build());",
                                       "  }",
                                       "}")
    processAndAssertEquals(inputFile, "test/TestControllerBuilder" to outputFile)
  }

  @Test
  fun twoRequiredExtra() {
    val inputFile
        = JavaFileObjects.forSourceLines("test.TestController",
                                         "package test;",
                                         "import com.bluelinelabs.conductor.Controller;",
                                         "import io.dwak.freight.annotation.ControllerBuilder;",
                                         "import android.support.annotation.NonNull;",
                                         "import android.support.annotation.Nullable;",
                                         "import android.view.LayoutInflater;",
                                         "import android.view.View;",
                                         "import android.view.ViewGroup;",
                                         "import java.lang.String;",
                                         "import io.dwak.freight.annotation.Extra;",
                                         "import android.os.Bundle;",
                                         "@ControllerBuilder",
                                         "public class TestController extends Controller {",
                                         "  @Extra String stringExtra;",
                                         "  @Extra int integerExtra;",
                                         "",
                                         "  public TestController(Bundle bundle) {",
                                         "    super(bundle);",
                                         "  }",
                                         "",
                                         "  @NonNull",
                                         "  @Override",
                                         "  protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {",
                                         "    return null;",
                                         "  }",
                                         "}")
    val outputFile =
        JavaFileObjects.forSourceLines("test.TestControllerBuilder",
                                       "package test;",
                                       "",
                                       "import android.os.Bundle;",
                                       "import com.bluelinelabs.conductor.RouterTransaction;",
                                       "import io.dwak.freight.internal.annotation.HasRequiredMethods;",
                                       "import java.lang.String;",
                                       "import java.lang.SuppressWarnings;",
                                       "",
                                       "@SuppressWarnings(\"unused\")",
                                       "public final class TestControllerBuilder {",
                                       "  private final Bundle bundle;",
                                       "  public TestControllerBuilder() {",
                                       "    this.bundle = new Bundle();",
                                       "  }",
                                       "",
                                       "  public final TestControllerBuilder stringExtra(final String value) {",
                                       "    bundle.putString(\"STRINGEXTRA\", value);",
                                       "    return this;",
                                       "  }",
                                       "",
                                       "  public final TestControllerBuilder integerExtra(final int value) {",
                                       "    bundle.putInt(\"INTEGEREXTRA\", value);",
                                       "    return this;",
                                       "  }",
                                       "",
                                       "  @HasRequiredMethods({\"stringExtra\", \"integerExtra\"})",
                                       "  public final test.TestController build() {",
                                       "    return new test.TestController(bundle);",
                                       "  }",
                                       "",
                                       "  @HasRequiredMethods({\"stringExtra\", \"integerExtra\"})",
                                       "  public final RouterTransaction asTransaction() {",
                                       "    return RouterTransaction.with(build());",
                                       "  }",
                                       "}")
    processAndAssertEquals(inputFile, "test/TestControllerBuilder" to outputFile)
  }

  @Test
  fun oneRequiredExtraOneNonRequiredExtra() {
    val inputFile
        = JavaFileObjects.forSourceLines("test.TestController",
                                         "package test;",
                                         "import com.bluelinelabs.conductor.Controller;",
                                         "import io.dwak.freight.annotation.ControllerBuilder;",
                                         "import android.support.annotation.NonNull;",
                                         "import android.support.annotation.Nullable;",
                                         "import android.view.LayoutInflater;",
                                         "import android.view.View;",
                                         "import android.view.ViewGroup;",
                                         "import java.lang.String;",
                                         "import io.dwak.freight.annotation.Extra;",
                                         "import android.os.Bundle;",
                                         "import android.support.annotation.Nullable;",
                                         "@ControllerBuilder",
                                         "public class TestController extends Controller {",
                                         "  @Extra @Nullable String stringExtra;",
                                         "  @Extra int integerExtra;",
                                         "",
                                         "  public TestController(Bundle bundle) {",
                                         "    super(bundle);",
                                         "  }",
                                         "",
                                         "  @NonNull",
                                         "  @Override",
                                         "  protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {",
                                         "    return null;",
                                         "  }",
                                         "}")
    val outputFile =
        JavaFileObjects.forSourceLines("test.TestControllerBuilder",
                                       "package test;",
                                       "",
                                       "import android.os.Bundle;",
                                       "import com.bluelinelabs.conductor.RouterTransaction;",
                                       "import io.dwak.freight.internal.annotation.HasRequiredMethods;",
                                       "import java.lang.String;",
                                       "import java.lang.SuppressWarnings;",
                                       "",
                                       "@SuppressWarnings(\"unused\")",
                                       "public final class TestControllerBuilder {",
                                       "  private final Bundle bundle;",
                                       "  public TestControllerBuilder() {",
                                       "    this.bundle = new Bundle();",
                                       "  }",
                                       "",
                                       "  public final TestControllerBuilder stringExtra(final String value) {",
                                       "    bundle.putString(\"STRINGEXTRA\", value);",
                                       "    return this;",
                                       "  }",
                                       "",
                                       "  public final TestControllerBuilder integerExtra(final int value) {",
                                       "    bundle.putInt(\"INTEGEREXTRA\", value);",
                                       "    return this;",
                                       "  }",
                                       "",
                                       "  @HasRequiredMethods({\"integerExtra\"})",
                                       "  public final test.TestController build() {",
                                       "    return new test.TestController(bundle);",
                                       "  }",
                                       "",
                                       "  @HasRequiredMethods({\"integerExtra\"})",
                                       "  public final RouterTransaction asTransaction() {",
                                       "    return RouterTransaction.with(build());",
                                       "  }",
                                       "}")
    processAndAssertEquals(inputFile, "test/TestControllerBuilder" to outputFile)
  }
}