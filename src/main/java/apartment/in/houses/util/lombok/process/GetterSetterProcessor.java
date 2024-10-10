package apartment.in.houses.util.lombok.process;

import apartment.in.houses.util.lombok.annotation.Getter;
import apartment.in.houses.util.lombok.annotation.Setter;
import apartment.in.houses.util.lombok.util.StringUtil;

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.SourceVersion;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.Writer;
import java.util.Set;

@SupportedAnnotationTypes({"Getter", "Setter"})
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class GetterSetterProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Getter.class)) {
            if (element.getKind() == ElementKind.FIELD) {
                generateGetter(element);
            }
        }

        for (Element element : roundEnv.getElementsAnnotatedWith(Setter.class)) {
            if (element.getKind() == ElementKind.FIELD) {
                generateSetter(element);
            }
        }
        return true;
    }

    private void generateGetter(Element field) {
        String fieldName = field.getSimpleName().toString();
        String className = ((TypeElement) field.getEnclosingElement()).getQualifiedName().toString();
        String getterName = "get" + StringUtil.capitalize(fieldName);
        String fieldType = field.asType().toString();

        try {
            JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(className + "Getter");
            try (Writer writer = builderFile.openWriter()) {
                writer.write("package " + StringUtil.getPackageName(className) + ";\n\n");
                writer.write("public class " + className + "Getter {\n");
                writer.write("    public " + fieldType + " " + getterName + "() {\n");
                writer.write("        return this." + fieldName + ";\n");
                writer.write("    }\n");
                writer.write("}\n");
            }
        } catch (Exception e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.toString());
        }
    }

    private void generateSetter(Element field) {
        String fieldName = field.getSimpleName().toString();
        String className = ((TypeElement) field.getEnclosingElement()).getQualifiedName().toString();
        String setterName = "set" + StringUtil.capitalize(fieldName);
        String fieldType = field.asType().toString();

        try {
            JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(className + "Setter");
            try (Writer writer = builderFile.openWriter()) {
                writer.write("package " + StringUtil.getPackageName(className) + ";\n\n");
                writer.write("public class " + className + "Setter {\n");
                writer.write("    public void " + setterName + "(" + fieldType + " " + fieldName + ") {\n");
                writer.write("        this." + fieldName + " = " + fieldName + ";\n");
                writer.write("    }\n");
                writer.write("}\n");
            }
        } catch (Exception e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.toString());
        }
    }

}
