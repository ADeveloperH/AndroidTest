package com.example.apt_processor.factory;

import com.example.annotationlib.factory.Factory;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * @author huangjian
 * @create 2018/7/25 0025
 * @Description
 */
@AutoService(Processor.class)
public class FactoryProcessor extends AbstractProcessor {

    //一个用来处理TypeMirror的工具类
    private Types typeUtils;
    //一个用来处理Element的工具类。Element代表的是源代码,Element可以是类、方法、变量等
    private Elements elementUtils;
    //使用Filer你可以创建文件
    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        typeUtils = processingEnvironment.getTypeUtils();
        elementUtils = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
    }

    /**
     * 指定本处理器将处理@Factory注解
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<String>();
        annotations.add(Factory.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    private Map<String, FactoryGroupClasses> factoryClasses =
            new LinkedHashMap<String, FactoryGroupClasses>();

    /**
     * TypeElement代表的是源代码中的类型元素，例如类。然而，TypeElement并不包含类本身的信息。
     * 你可以从TypeElement中获取类的名字，但是你获取不到类的信息，例如它的父类。这种信息需要通过TypeMirror获取。
     * 你可以通过调用elements.asType()获取元素的TypeMirror。
     *
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(Factory.class)) {
            //检查被注解为@Factory的元素是否是一个类
            if (annotatedElement.getKind() != ElementKind.CLASS) {
                error(annotatedElement, "Only classes can be annotated with @%s", Factory.class.getSimpleName());
                return true;// 退出处理
            }

            TypeElement typeElement = (TypeElement) annotatedElement;
            try {
                FactoryAnnotatedClass annotatedClass = new FactoryAnnotatedClass(typeElement);
                if (!isValidClass(annotatedClass)) {
                    return true;
                }
                // 所有检查都没有问题，所以可以添加了
                String qualifiedFactoryGroupName = annotatedClass.getQualifiedFactoryGroupName();
                FactoryGroupClasses factoryClass = factoryClasses.get(qualifiedFactoryGroupName);
                if (factoryClass == null) {
                    factoryClass = new FactoryGroupClasses(qualifiedFactoryGroupName);
                    factoryClasses.put(qualifiedFactoryGroupName, factoryClass);
                }
                // 如果和其他的@Factory标注的类的id相同冲突，
                // 抛出IdAlreadyUsedException异常
                factoryClass.add(annotatedClass);
            } catch (IllegalArgumentException e) {
                // @Factory.id()为空 --> 打印错误信息
                error(typeElement, e.getMessage());
            } catch (ProcessingException e) {
                // 已经存在
                error(e.getElement(), e.getMessage());
            }
            return true;
        }

        try {
            for (FactoryGroupClasses factoryGroupClasses : factoryClasses.values()) {
                factoryGroupClasses.generateCode(elementUtils, filer);
            }

            // 清除factoryClasses
            factoryClasses.clear();

        } catch (IOException e) {
            error(null, e.getMessage());
        }
        return true;
    }

    private boolean isValidClass(FactoryAnnotatedClass annotatedClass) {
        TypeElement classTypeElement = annotatedClass.getTypeElement();
        if (!classTypeElement.getModifiers().contains(Modifier.PUBLIC)) {
            error(classTypeElement, "The class %s is not public.",
                    classTypeElement.getQualifiedName().toString());
            return false;
        }
        // 检查是否是一个抽象类
        if (classTypeElement.getModifiers().contains(Modifier.ABSTRACT)) {
            error(classTypeElement, "The class %s is abstract. You can't annotate abstract classes with @%",
                    classTypeElement.getQualifiedName().toString(), Factory.class.getSimpleName());
            return false;
        }

        // 检查继承关系: 必须是@Factory.type()指定的类型子类
        TypeElement superClassElement = elementUtils.getTypeElement(annotatedClass.getQualifiedFactoryGroupName());
        if (superClassElement.getKind() == ElementKind.INTERFACE) {
            // 检查接口是否实现了
            if (!classTypeElement.getInterfaces().contains(superClassElement.asType())) {
                error(classTypeElement, "The class %s annotated with @%s must implement the interface %s",
                        classTypeElement.getQualifiedName().toString(), Factory.class.getSimpleName(),
                        annotatedClass.getQualifiedFactoryGroupName());
                return false;
            }
        } else {
            // 检查子类
            TypeElement currentClass = classTypeElement;
            while (true) {
                TypeMirror superclassType = currentClass.getSuperclass();
                if (superclassType.getKind() == TypeKind.NONE) {
                    // 到达了基本类型(java.lang.Object), 所以退出
                    error(classTypeElement, "The class %s annotated with @%s must inherit from %s",
                            classTypeElement.getQualifiedName().toString(), Factory.class.getSimpleName(),
                            annotatedClass.getQualifiedFactoryGroupName());
                    return false;
                }

                if (superclassType.toString().equals(annotatedClass.getQualifiedFactoryGroupName())) {
                    // 找到了要求的父类
                    break;
                }
                // 在继承树上继续向上搜寻
                currentClass = (TypeElement) typeUtils.asElement(superclassType);
            }
        }

        // 检查是否提供了默认公开构造函数
        for (Element enclosed : classTypeElement.getEnclosedElements()) {
            if (enclosed.getKind() == ElementKind.CONSTRUCTOR) {
                ExecutableElement constructorElement = (ExecutableElement) enclosed;
                if (constructorElement.getParameters().size() == 0 &&
                        constructorElement.getModifiers().contains(Modifier.PUBLIC)) {
                    // 找到了默认构造函数
                    return true;
                }
            }
        }
        // 没有找到默认构造函数
        error(classTypeElement, "The class %s must provide an public empty default constructor",
                classTypeElement.getQualifiedName().toString());

        return false;
    }


    /**
     * Messager提供给注解处理器一个报告错误、警告以及提示信息的途径。
     * 它不是注解处理器开发者的日志工具，而是用来写一些信息给使用此注解器的第三方开发者的
     *
     * @param annotatedElement
     * @param msg
     * @param args
     */
    private void error(Element annotatedElement, String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), annotatedElement);
    }
}
