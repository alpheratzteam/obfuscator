package org.objectweb.asm.commons;

import org.objectweb.asm.*;

/**
 * @author hp888 on 13.06.2019.
 */

public class RemappingClassAdapter extends ClassVisitor {
    protected final Remapper remapper;
    protected String className;

    public RemappingClassAdapter(ClassVisitor classVisitor, Remapper remapper) {
        this(393216, classVisitor, remapper);
    }

    protected RemappingClassAdapter(int api, ClassVisitor classVisitor, Remapper remapper) {
        super(api, classVisitor);
        this.remapper = remapper;
    }

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = name;
        super.visit(version, access, this.remapper.mapType(name), this.remapper.mapSignature(signature, false), this.remapper.mapType(superName), interfaces == null ? null : this.remapper.mapTypes(interfaces));
    }

    public ModuleVisitor visitModule(String name, int flags, String version) {
        throw new RuntimeException("RemappingClassAdapter is deprecated, use ClassRemapper instead");
    }

    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitAnnotation(this.remapper.mapDesc(descriptor), visible);
        return annotationVisitor == null ? null : this.createRemappingAnnotationAdapter(annotationVisitor);
    }

    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitTypeAnnotation(typeRef, typePath, this.remapper.mapDesc(descriptor), visible);
        return annotationVisitor == null ? null : this.createRemappingAnnotationAdapter(annotationVisitor);
    }

    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        FieldVisitor fieldVisitor = super.visitField(access, this.remapper.mapFieldName(this.className, name, descriptor), this.remapper.mapDesc(descriptor), this.remapper.mapSignature(signature, true), this.remapper.mapValue(value));
        return fieldVisitor == null ? null : this.createRemappingFieldAdapter(fieldVisitor);
    }

    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        String newDescriptor = this.remapper.mapMethodDesc(descriptor);
        MethodVisitor methodVisitor = super.visitMethod(access, this.remapper.mapMethodName(this.className, name, descriptor), newDescriptor, this.remapper.mapSignature(signature, false), exceptions == null ? null : this.remapper.mapTypes(exceptions));
        return methodVisitor == null ? null : this.createRemappingMethodAdapter(access, newDescriptor, methodVisitor);
    }

    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        super.visitInnerClass(this.remapper.mapType(name), outerName == null ? null : this.remapper.mapType(outerName), innerName, access);
    }

    public void visitOuterClass(String owner, String name, String descriptor) {
        super.visitOuterClass(this.remapper.mapType(owner), name == null ? null : this.remapper.mapMethodName(owner, name, descriptor), descriptor == null ? null : this.remapper.mapMethodDesc(descriptor));
    }

    protected FieldVisitor createRemappingFieldAdapter(FieldVisitor fieldVisitor) {
        return new RemappingFieldAdapter(fieldVisitor, this.remapper);
    }

    protected MethodVisitor createRemappingMethodAdapter(int access, String newDescriptor, MethodVisitor methodVisitior) {
        return new RemappingMethodAdapter(access, newDescriptor, methodVisitior, this.remapper);
    }

    protected AnnotationVisitor createRemappingAnnotationAdapter(AnnotationVisitor av) {
        return new RemappingAnnotationAdapter(av, this.remapper);
    }
}
